package com.like.pmp.model.mapper;

import com.like.pmp.model.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> queryAll();

    List<SysMenu> queryNotButtonList();

    List<SysMenu> queryByButtonList(Long menuId);

    List<SysMenu> queryListParentId(Long parentId);
}
