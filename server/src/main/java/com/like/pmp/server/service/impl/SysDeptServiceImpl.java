package com.like.pmp.server.service.impl;

import com.google.common.collect.Lists;
import com.like.pmp.common.utils.Constant;
import com.like.pmp.model.entity.SysDept;
import com.like.pmp.model.mapper.SysDeptMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.pmp.server.service.ISysDeptService;
import com.like.pmp.server.shiro.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门管理 服务实现类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private CommonDataServiceImpl commonDataService;
    /**
     * 查询所有部门列表~设计带部门数据权限的控制
     * @author like
     * @date 2022/5/12 20:20
     * @param map
     * @return java.util.List<com.like.pmp.model.entity.SysDept>
     */
    @Override
    public List<SysDept> queryAll(Map<String, Object> map) {
        if (!ShiroUtil.getUserId().equals(Constant.SUPER_ADMIN)){
            String deptDataIds = commonDataService.getCurrUserDataDeptStr();
            map.put("deptDataIds",deptDataIds);
        }
        return deptMapper.queryAll(map);
    }

    /**
     * 根据父级部门id查询子部门的id列表
     * @author like
     * @date 2022/5/12 20:19
     * @param parentId
     * @return java.util.List<java.lang.Long>
     */
    @Override
    public List<Long> queryDeptIds(Long parentId) {
        return deptMapper.queryDeptIds(parentId);
    }

    /**
     * 获取当前部门子部门
     * @author like
     * @date 2022/5/12 20:55
     * @param deptId
     * @return java.util.List<java.lang.Long>
     */
    @Override
    public List<Long> getSubDeptIdList(Long deptId) {
        List<Long> deptIdList = Lists.newLinkedList();
        //第一级部门id列表
        List<Long> subIdList = deptMapper.queryDeptIds(deptId);
        getDeptTreeList(subIdList,deptIdList);
        return deptIdList;
    }
   /**
    * 递归
    * @author like
    * @date 2022/5/12 21:01
    * @param subIdList 第一级部门数据id列表
    * @param deptIdList 每次递归时循环存储的结果数据id列表
    */
    private void getDeptTreeList(List<Long> subIdList,List<Long> deptIdList){
        List<Long> list;
        for (Long subId:subIdList) {
            list = deptMapper.queryDeptIds(subId);
            if (list != null && !list.isEmpty()){
                //调用自己
                getDeptTreeList(list,deptIdList);
            }
            //当前递归结算
            deptIdList.add(subId);
        }
    }
}
