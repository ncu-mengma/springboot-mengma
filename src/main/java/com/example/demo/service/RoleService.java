package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Role;
import com.example.demo.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2023-11-09
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {
    @Resource
    private RoleMapper roleMapper;
    public List<String> getRoleByUserId(Long userId){
        return roleMapper.getRoleByUserId(userId);
    }
}