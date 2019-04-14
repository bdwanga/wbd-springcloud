package com.wbd.boot1xx.user.controller;

import com.wbd.boot1xx.user.model.UserBean;
import com.wbd.boot1xx.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户管理Controller
 * <p>
 * 提供用户的增删改查和用户登陆等接口
 *
 * @author wangbendong
 * @version 1.0
 * @date 2018.10.31
 * @since 1.8
 */
@Controller("userController")
@RequestMapping("/api/users")
public class UserController
{
    @Autowired
    private UserServiceImpl userService;

    /**
     * 查询用户信息列表
     *
     * @return 分页数据
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<UserBean> queryUsers()
    {
        System.out.println("queryUsers");
        return userService.queryUsers();
    }




}
