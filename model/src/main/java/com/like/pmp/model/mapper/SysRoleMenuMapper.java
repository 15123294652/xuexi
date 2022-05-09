package com.like.pmp.model.mapper;

import com.like.pmp.model.entity.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 Mapper 接口
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    //根据角色ID,获取菜单ID列表
    List<Long> queryMenuIdList(@Param("roleId") Long roleId);

    //根据角色ID列表,批量删除
    int deleteBatch(@Param("roleIds") String roleIds);
}
