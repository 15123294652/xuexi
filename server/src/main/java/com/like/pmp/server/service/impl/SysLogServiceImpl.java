package com.like.pmp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.like.pmp.common.utils.PageUtil;
import com.like.pmp.common.utils.QueryUtil;
import com.like.pmp.model.entity.SysLog;
import com.like.pmp.model.mapper.SysLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.pmp.server.service.ISysLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    /**
     * 清除日志
     * @author like
     * @date 2022/5/13 20:47
     */
    @Override
    public void truncate() {
        baseMapper.truncate();
    }
    /**
     * 查询日志
     * @author like
     * @date 2022/5/13 20:47
     * @param params
     * @return com.like.pmp.common.utils.PageUtil
     */
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");

        IPage queryPage=new QueryUtil<SysLog>().getQueryPage(params);

        QueryWrapper<SysLog> wrapper=new QueryWrapper<SysLog>()
                .like(StringUtils.isNotBlank(key),"username", key)
                .or(StringUtils.isNotBlank(key))
                .like(StringUtils.isNotBlank(key),"operation", key);
        IPage<SysLog> page=this.page(queryPage,wrapper);

        return new PageUtil(page);
    }
}
