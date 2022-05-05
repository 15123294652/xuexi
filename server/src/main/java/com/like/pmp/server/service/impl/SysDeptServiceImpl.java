package com.like.pmp.server.service.impl;

import com.like.pmp.model.entity.SysDept;
import com.like.pmp.model.mapper.SysDeptMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.pmp.server.service.ISysDeptService;
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
    @Override
    public List<SysDept> queryAll(Map<String, Object> map) {
        List<SysDept> sysDepts = deptMapper.queryAll(map);
        return sysDepts;
    }

    @Override
    public List<Long> queryDeptIds(Long parentId) {
        return deptMapper.queryDeptIds(parentId);
    }
}
