package com.example.demo.dto;
import com.example.demo.annotation.EnumValue;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
/**
 *  用户数据传输对象
 *
 */
@Data
public class UserDTO {
    /**
     * 用户账号
     *
     */
    @NotBlank(message = "账号不能为空")
    @Size(min = 8, max = 16, message = "账号长度在 8 到 16 个字符")
    private String account;
    /**
     * 用户密码
     *
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 20, message = "密码长度在 8 到 20 个字符")
    @Pattern(message = "密码必须包含一个大写字母和一个数字,且不含有空格",regexp = "^(?=.*[A-Z])(?=.*\\d)\\S*$")
    private String password;
    /**
     * 用户性别
     *
     */
    @NotBlank(message = "性别不能为空")
    @EnumValue(candidateValue = {"boy", "girl"}, message = "性别只能为男或女")
    private String sex;
    /**
     * 用户邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String mail;
    //长度在 2 到 20 个字符
    /**
     * 用户昵称
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 20, message = "用户名长度在 2 到 20 个字符")
    @Pattern(regexp = "^[^\\s!@#$%^&*()_+{}\\[\\]:;<>,.?~\\\\/-]+$", message = "用户名不包含特殊字符")
    private String nickName;
}
