package com.like.pmp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.like.pmp.common.utils.PageUtil;
import com.like.pmp.common.utils.QueryUtil;
import com.like.pmp.model.entity.SysDict;
import com.like.pmp.model.mapper.SysDictMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.pmp.server.service.ISysDictService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        IPage queryPage=new QueryUtil<SysDict>().getQueryPage(params);

        //查询包装器
        QueryWrapper<SysDict> wrapper=new QueryWrapper<SysDict>()
                .like(StringUtils.isNotBlank(name),"name", name);
        IPage<SysDict> page=this.page(queryPage,wrapper);

        return new PageUtil(page);
    }
}
