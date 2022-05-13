package com.like.pmp.server.service;

import com.like.pmp.common.utils.PageUtil;
import com.like.pmp.model.entity.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 数据字典表 服务类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
public interface ISysDictService extends IService<SysDict> {
    PageUtil queryPage(Map<String, Object> params);
}
