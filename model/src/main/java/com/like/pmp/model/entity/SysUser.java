package com.like.pmp.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author like
 * @since 2022-04-26
 */
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("user_id")
    private Long userId;

    /**
     * 姓名
     */
    @NotBlank(message="用户名不能为空")
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    @NotBlank(message="密码不能为空")
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 邮箱
     */
    @NotBlank(message="邮箱不能为空!")
    private String email;

    /**
     * 手机号
     */
    @NotBlank(message="手机号不能为空!")
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;

    /**
     * 部门ID
     */
    @NotNull(message="部门Id不能为空!")
    private Long deptId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String postName;

    /**
     * 用户所有的角色列表（@TableField：字段属性不为数据库表字段，但又是必须使用的）
     */
    @TableField(exist=false)
    private List<Long> roleIdList;

    /**
     * 用户所有的岗位列表（@TableField：字段属性不为数据库表字段，但又是必须使用的）
     */
    @TableField(exist=false)
    private List<Long> postIdList;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public List<Long> getPostIdList() {
        return postIdList;
    }

    public void setPostIdList(List<Long> postIdList) {
        this.postIdList = postIdList;
    }

    @Override
    public String toString() {
        return "SysUser{" +
            "userId=" + userId +
            ", name=" + name +
            ", username=" + username +
            ", password=" + password +
            ", salt=" + salt +
            ", email=" + email +
            ", mobile=" + mobile +
            ", status=" + status +
            ", deptId=" + deptId +
            ", createTime=" + createTime +
        "}";
    }
}
