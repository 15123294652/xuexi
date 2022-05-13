package com.like.pmp.server.service;

import com.like.pmp.common.utils.PageUtil;
import com.like.pmp.model.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface ISysLogService extends IService<SysLog> {

    PageUtil queryPage(Map<String, Object> params);

    void truncate();
}
