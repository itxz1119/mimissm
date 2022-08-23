package com.bjpowernode.controller;

import com.bjpowernode.pojo.Admin;
import com.bjpowernode.service.AdminService;
import com.bjpowernode.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequestMapping("/login")
    public String login(String name, String pwd, HttpServletRequest request){
        String md5 = MD5Util.getMD5(pwd);
        Admin admin = adminService.login(name, md5);
        if (admin != null){
            System.out.println("登陆成功！");
            request.setAttribute("admin", admin);
            return "main";
        }else {
            request.setAttribute("errmsg", "用户名或密码错误");
            return "login";
        }

    }

}
