package com.like.pmp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.like.pmp.model.entity.SysMenu;
import com.like.pmp.model.entity.SysRoleMenu;
import com.like.pmp.model.mapper.SysMenuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.pmp.model.mapper.SysRoleMenuMapper;
import com.like.pmp.server.service.ISysMenuService;
import com.like.pmp.server.service.ISysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Autowired
    private SysMenuMapper menuMapper;
    @Autowired
    private ISysRoleMenuService roleMenuService;
    @Override
    public List<SysMenu> queryAll() {
       return menuMapper.queryAll();
    }

    @Override
    public List<SysMenu> queryNotButtonList() {
        return menuMapper.queryNotButtonList();
    }

    @Override
    public List<SysMenu> queryByParentId(Long menuId) {
        return menuMapper.queryByButtonList(menuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long menuId) {
        this.removeById(menuId);
       // roleMenuService.removeById(new QueryWrapper<SysRoleMenu>().eq("menu_id",menuId));
    }
}
