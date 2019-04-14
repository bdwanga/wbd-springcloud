package com.wbd.boot1xx.user.service;

import com.wbd.boot1xx.user.dao.IUserDao;
import com.wbd.boot1xx.user.model.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户管理service实现类
 * <p>
 * 实现用户的增删改查和用户登陆功能
 *
 * @author wangbendong
 * @version 1.0
 * @date 2018.10.31
 * @since 1.8
 */
@Service("usersMangerService")
public class UserServiceImpl
{

    @Autowired
    private IUserDao userDao;


    /**
     * 查询用户信息列表
     *
     * @return 分页数据
     */
    public List<UserBean> queryUsers()
    {
        return userDao.queryUsers();
    }



}
