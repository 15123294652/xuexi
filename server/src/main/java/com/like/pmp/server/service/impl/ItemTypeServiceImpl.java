package com.like.pmp.server.service.impl;

import com.like.pmp.model.entity.ItemType;
import com.like.pmp.model.mapper.ItemTypeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.pmp.server.service.IItemTypeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品类别 服务实现类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
@Service
public class ItemTypeServiceImpl extends ServiceImpl<ItemTypeMapper, ItemType> implements IItemTypeService {

}
