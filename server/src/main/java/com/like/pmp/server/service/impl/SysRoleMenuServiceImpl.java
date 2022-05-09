package com.like.pmp.server.service.impl;

import com.google.common.base.Joiner;
import com.like.pmp.common.utils.CommonUtil;
import com.like.pmp.model.entity.SysRoleMenu;
import com.like.pmp.model.mapper.SysRoleMenuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.pmp.server.service.ISysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 服务实现类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {
    @Autowired
    private SysRoleMenuMapper roleMenuMapper;
    /**
     * 维护角色菜单关联信息
     * @author like
     * @date 2022/5/9 21:14
     * @param roleId，menuIdList
     * @return null
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void menuSaveOrUpdate(Long roleId, List<Long> menuIdList) {
        //需要先清除旧的关联数据，在插入新的关联信息（批量删除）
        deleteBatch(Arrays.asList(roleId));

        SysRoleMenu roleMenu;
        if (menuIdList != null || !menuIdList.isEmpty()){
            for (Long menuId:menuIdList) {
                roleMenu = new SysRoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                this.save(roleMenu);
            }
        }
    }
    /**
     * 根据角色id批量删除
     * @author like
     * @date 2022/5/9 21:22
     * @param roleIds
     */
    @Override
    public void deleteBatch(List<Long> roleIds) {
        if (roleIds != null && !roleIds.isEmpty()){
            String delIds = Joiner.on(",").join(roleIds);
            roleMenuMapper.deleteBatch(CommonUtil.concatStrToInt(delIds,","));
        }
    }

    /**
     * 根据角色Id获取菜单列表
     * @author like
     * @date 2022/5/9 22:09
     * @param roleId
     */
    @Override
    public List<Long> queryMenuIdList(Long roleId) {
       return roleMenuMapper.queryMenuIdList(roleId);
    }
}
