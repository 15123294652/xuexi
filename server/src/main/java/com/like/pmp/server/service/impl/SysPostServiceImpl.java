package com.like.pmp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.base.Joiner;
import com.like.pmp.common.response.BaseResponse;
import com.like.pmp.common.response.StatusCode;
import com.like.pmp.common.utils.CommonUtil;
import com.like.pmp.common.utils.PageUtil;
import com.like.pmp.common.utils.QueryUtil;
import com.like.pmp.model.entity.SysPost;
import com.like.pmp.model.mapper.SysPostMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.pmp.server.service.ISysPostService;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 * 岗位信息表 服务实现类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService {
    @Autowired
    private SysPostMapper postMapper;
    //分页模糊查询
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        String search = (String) (params.get("search") == null ?"":params.get("search").toString());
        IPage<SysPost> page = new QueryUtil<SysPost>().getPage(params);
        QueryWrapper<SysPost> wrapper = new QueryWrapper<>();


        wrapper.like(StringUtils.isNotBlank(search),"post_code",search.trim())
                .or(StringUtils.isNotBlank(search))
                .like(StringUtils.isNotBlank(search),"post_name",search.trim());
        IPage<SysPost> resPage = postMapper.selectPage(page, wrapper);
        return  new PageUtil(resPage);
    }
    //新增岗位
    @Override
    public void savePost(SysPost post) {
        if(getOne(new QueryWrapper<SysPost>().eq("post_code",post.getPostCode()))!=null){
            throw new RuntimeException(StatusCode.PostCodeHasExist.getMsg());
        }
        post.setCreateTime(DateTime.now().toDate());
        save(post);
    }
    //修改岗位信息
    @Override
    public void updatePost(SysPost post) {
        SysPost old = getById(post.getPostId());
        if (old != null && !old.getPostCode().equals(post.getPostCode())){
            if (getOne(new QueryWrapper<SysPost>().eq("post_code",post.getPostCode()))!=null){
                throw new RuntimeException(StatusCode.PostCodeHasExist.getMsg());
            }
        }
        post.setUpdateTime(DateTime.now().toDate());
        updateById(post);
    }
    //批量删除岗位信息
    @Override
    public void deletepath(Long[] ids) {
        //第一种方式使用-mybatis-plus
      //  removeByIds(Arrays.asList(ids));

        //第二种写法
        //ids=[1,2,3,4,5,];------> '1,2,3,4,5'
        String delIds = Joiner.on(",").join(ids);
        String s = CommonUtil.concatStrToInt(delIds, ",");
        postMapper.deleteBatchById(s);

    }
}
