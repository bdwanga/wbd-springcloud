package com.wbd.boot1xx.user.dao;

import com.wbd.boot1xx.user.model.UserBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户管理dao接口
 * <p>
 * 定义了用户的增删改查等接口
 *
 * @author wangbendong
 * @version 1.0
 * @date 2018.10.31
 * @since 1.8
 */
@Mapper
public interface IUserDao
{
    /**
     * 查询用户数据列表
     *
     * @param name 用户名，模糊查询
     * @return 用户信息列表
     */
    public List<UserBean> queryUsers();
}
