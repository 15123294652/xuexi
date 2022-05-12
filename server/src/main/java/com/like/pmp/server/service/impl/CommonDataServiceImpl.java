package com.like.pmp.server.service.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.like.pmp.common.utils.CommonUtil;
import com.like.pmp.common.utils.Constant;
import com.like.pmp.model.entity.SysUser;
import com.like.pmp.model.mapper.SysDeptMapper;
import com.like.pmp.model.mapper.SysUserMapper;
import com.like.pmp.server.service.ICommonDataService;
import com.like.pmp.server.shiro.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author like
 * @date 2022年05月12日 19:50
 * 通用化的部门数据权限
 */
@Service
public class CommonDataServiceImpl implements ICommonDataService {

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysDeptServiceImpl deptService;

    /**
     * 获取当前登录用户的部门数据ID列表
     * @author like
     * @date 2022/5/12 19:52
     * @return java.util.Set<java.lang.Long>
     */
    @Override
    public Set<Long> getCurrUserDataDeptIds() {
        Set<Long> dataIds = Sets.newHashSet();
        SysUser user = ShiroUtil.getUserEntity();
        if (Constant.SUPER_ADMIN.equals(user.getUserId())){
            dataIds = deptMapper.queryAllDeptIds();
        }else {
            //分配给用户的部门数据权限id列表
            Set<Long> userDeptDataIds = userMapper.queryDeptIdsByUserId(user.getUserId());
            if (userDeptDataIds!=null && !userDeptDataIds.isEmpty()){
                dataIds.addAll(userDeptDataIds);
            }
            //用户所在的部门及其子部门id列表~递归实现
            dataIds.add(user.getDeptId());
            List<Long> subDeptIdList=deptService.getSubDeptIdList(user.getDeptId());
            dataIds.addAll(subDeptIdList);
        }
        return dataIds;
    }

    /**
     * 将部门数据ID列表转化为ID拼接的字符串
     * @author like
     * @date 2022/5/12 19:52
     * @return java.lang.String
     */
    @Override
    public String getCurrUserDataDeptStr() {
        String result = null;

        Set<Long> dataSet = this.getCurrUserDataDeptIds();
        if (dataSet!=null && !dataSet.isEmpty()){
            result = CommonUtil.concatStrToInt( Joiner.on(",").join(dataSet),",");

        }
        return result;
    }
}
