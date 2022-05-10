package com.like.pmp.model.mapper;

import com.like.pmp.model.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 Mapper 接口
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    void deleteBatch(@Param("roleId") String roleId);

    List<Long> queryRoleIdList(@Param("userId")Long userId);
}
