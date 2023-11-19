package com.example.demo.service;


import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.config.AppConfig;
import com.example.demo.entity.FollowDetail;
import com.example.demo.entity.User;
import com.example.demo.enums.ErrorCodeEnum;
import com.example.demo.exception.CustomException;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.vo.UserVo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 *
 *  用户服务实现类
 *
 * @author zhou
 * @since 2023-11-07
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private FollowDetailService followDetailService;
    @Resource
    private RoleMapper roleMapper;
    @Autowired
    private AppConfig appConfig;
    public UserVo login(String account, String password){
        User user=lambdaQuery().eq(User::getAccount, account).select(User::getId,User::getPassword).one();
        CustomException customException = new CustomException(ErrorCodeEnum.UNAUTHORIZED.getCode(), "用户名或密码错误");
        if(user==null){
            throw customException;
        }
        String pw_hash=user.getPassword();
        if(!BCrypt.checkpw(password,pw_hash)){
            throw customException;
        }
        Long id = user.getId();
        UserVo userVo = getInfoById(id);
        StpUtil.login(id);
        return userVo;
    }
    public UserVo getInfo(){
        if(!StpUtil.isLogin()){
            return null;
        }
        return getInfoById(StpUtil.getLoginIdAsLong());
    }

    public UserVo getInfoById(Long id){
        User user = lambdaQuery().eq(User::getId, id).one();
        if(user==null){
            return null;
        }
        UserVo userVo=new UserVo();
        BeanUtils.copyProperties(user,userVo);
        Integer fanCount = followDetailService.lambdaQuery().eq(FollowDetail::getUserId, id).count();
        Integer followCount = followDetailService.lambdaQuery().eq(FollowDetail::getFanId, id).count();
        userVo.setFanCount(fanCount);
        userVo.setFollowCount(followCount);
        List<String> roles = roleMapper.getRoleByUserId(id);
        userVo.setRoles(roles);
        return userVo;
    }
    public void register(User user){
        validRegisterAccount(user.getAccount());
        validRegisterMail(user.getMail());
        //对密码进行加密
        String password=user.getPassword();
        //生成随机盐值
        String salt = BCrypt.gensalt();
        String pw_hash=BCrypt.hashpw(password,salt);
        user.setPassword(pw_hash);
        boolean save = save(user);
        if(!save){
            throw new CustomException(ErrorCodeEnum.UNKNOWN_ERROR.getCode(),"注册失败");
        }
    }
    public void validRegisterAccount(String account){
        User user = lambdaQuery().eq(User::getAccount, account).one();
        if(user!=null){
            throw new CustomException(ErrorCodeEnum.CONFLICT.getCode(),"用户名已存在");
        }
    }
    public void validRegisterMail(String mail){
        User user = lambdaQuery().eq(User::getMail, mail).one();
        if(user!=null){
            throw new CustomException(ErrorCodeEnum.CONFLICT.getCode(),"邮箱已存在");
        }
    }
    public void getMailCode(String mail){
        if(SaManager.getSaTokenDao().get("mailCode:"+mail+":send")!=null){
           throw new CustomException(ErrorCodeEnum.TOO_MANY_REQUESTS.getCode(),"验证码1分钟内只能发送一次");
        }
        //生成一个6位的随机验证码
        String code = RandomStringUtils.randomNumeric(6);
        //使用javaMail发送邮件
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(appConfig.getSenderEmail());
        message.setTo(mail);
        message.setSubject("验证码");
        message.setText("您的验证码为："+code+"，请在5分钟内使用");
        javaMailSender.send(message);
        //将验证码存入redis中，设置过期时间为5分钟
        SaManager.getSaTokenDao().set("mailCode:"+mail,code,300);
        //设置验证码1分钟内只能发送一次
        SaManager.getSaTokenDao().set("mailCode:"+mail+":send",code,60);
    }
    public void validMailCode(String mail,String code){
        //从redis中取出验证码
        String mailCode = SaManager.getSaTokenDao().get("mailCode:" + mail);
        if(mailCode==null){
            throw new CustomException(ErrorCodeEnum.Gone.getCode(),"验证码已过期");
        }
        if(!mailCode.equals(code)){
           throw new CustomException(ErrorCodeEnum.UNAUTHORIZED.getCode(),"验证码错误");
        }
    }
}