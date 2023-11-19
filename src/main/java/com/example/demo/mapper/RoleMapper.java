package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2023-11-09
 */
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select r.name from role r left join rel_user_role ur on r.id=ur.rid where ur.uid=#{userId}")
    List<String> getRoleByUserId(Long userId);
}
