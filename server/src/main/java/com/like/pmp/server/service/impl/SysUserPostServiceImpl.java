package com.like.pmp.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Joiner;
import com.like.pmp.model.entity.SysUserPost;
import com.like.pmp.model.mapper.SysUserPostMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.pmp.server.service.ISysUserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户与岗位关联表 服务实现类
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
@Service
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements ISysUserPostService {
    @Autowired
    private SysUserPostMapper userPostMapper;
    /**
     * 根据用户ID获取岗位名称
     * 如果有多个用','拼接
     * @author like
     * @date 2022/5/10 20:15
     * @param userId
     * @return java.lang.String
     */
    @Override
    public String getPostNameByUserId(Long userId) {
        Set<String> set = userPostMapper.getPostNameByUserId(userId);
        if (set.size() > 0 ){
            return Joiner.on(",").join(set);
        }else {
            return "";
        }
    }

    /**
     * 维护用户和岗位之间的关联关系
     * @author like
     * @date 2022/5/10 21:12
     * @param userId
     * @param postIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void postSaveOrUpdate(Long userId, List<Long> postIds) {
        //删除旧的用户与岗位关联关系
        this.remove(new QueryWrapper<SysUserPost>().eq("user_id",userId));
        if (postIds.size()>0){
            SysUserPost sysUserPost;
            for (Long postId:postIds) {
                sysUserPost = new SysUserPost();
                sysUserPost.setPostId(postId);
                sysUserPost.setUserId(userId);
                this.save(sysUserPost);
            }
        }

    }

    /**
     * 获取用户和岗位的关联信息
     * @author like
     * @date 2022/5/10 22:11
     * @param userId
     * @return java.util.List<java.lang.Long>
     */
    @Override
    public List<Long> queryRoleIdList(Long userId) {
        return userPostMapper.queryRoleIdList(userId);
    }
}
