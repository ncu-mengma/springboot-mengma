package com.example.demo.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.vo.UserVo;
import com.power.common.model.CommonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;


/**
 *  用户接口
 *
 * @author zhou
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    UserService userService;
    /**
     * 用户根据账号密码登录
     * @param account 用户账号|Zhou1735365300
     * @param password 用户密码|Zhou197asdu@#@$
     * @return
     *
     */
    @GetMapping("/login")
    public CommonResult< UserVo > login(@RequestParam String account,@RequestParam String password){
        return CommonResult.<UserVo>ok().setResult(userService.login(account,password));
    }
    /**
     * 用户根据token登录
     * @apiNote 该接口不需要传参,token值根据cookie中的satoken值
     * @return
     */
    @GetMapping("/getInfo")
    public CommonResult< UserVo > getInfo(){
        return CommonResult.<UserVo>ok().setResult(userService.getInfo());
    }
    /**
     * 用户注册
     * @return
     */
    @PostMapping("/register")
    public void register(@Valid @RequestBody UserDTO userDTO){
        User user=new User();
        BeanUtils.copyProperties(userDTO,user);
        userService.register(user);
    }
    /**
     * 检验账号是否已经注册
     * @param account 用户账号
     * @return
     */
    @SaIgnore
    @GetMapping("/validRegisterAccount")
    public CommonResult<?> validRegisterAccount(@RequestParam String account){
        userService.validRegisterAccount(account);
        return CommonResult.ok();
    }
    /**
     * 检验邮箱是否已经注册
     * @param mail 用户邮箱
     */
    @SaIgnore
    @GetMapping("/validRegisterMail")
    public CommonResult<String> validRegisterMail(@RequestParam @Email(message = "邮箱格式不正确") String mail){
        userService.validRegisterMail(mail);
        return CommonResult.ok();
    }
    /**
     * 登录时获取邮箱验证码
     * @param mail 用户邮箱
     *
     */
    @GetMapping("/getMailCode")
    public CommonResult<String> getMailCode(@Email(message = "邮箱格式不正确") @RequestParam String mail){
        userService.getMailCode(mail);
        return CommonResult.ok();
    }
    /**
     * 检验邮箱验证码是否正确
     * @param mail 用户邮箱
     * @param code 邮箱验证码
     * @return
     */
    @GetMapping("/validMailCode")
    public CommonResult<String> validMailCode(@RequestParam @Email(message = "邮箱格式不正确") String mail,@RequestParam String code){
        userService.validMailCode(mail,code);
        return CommonResult.ok();
    }
}

