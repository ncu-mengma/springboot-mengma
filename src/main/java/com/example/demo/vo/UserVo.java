package com.example.demo.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserVo {
    /**
     * 用户昵称
     *
     */
    private String nickName;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户粉丝数
     */
    private Integer fanCount;
    /**
     * 用户关注数
     */
    private Integer followCount;
    /**
     * 用户账号
     */
    private String account;
    /**
     * 用户角色
     */
    private List<String>roles;
    /**
     * 用户性别
     */
    private String sex;
    /**
     * 用户邮箱
     */
    private String mail;
}
