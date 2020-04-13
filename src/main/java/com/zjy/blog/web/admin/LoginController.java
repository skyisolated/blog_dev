package com.zjy.blog.web.admin;

import com.zjy.blog.entity.User;
import com.zjy.blog.service.UserService;
import com.zjy.blog.service.UserServiceImpl;
import com.zjy.blog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping
    public String toLogin(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Map<String,Object> map){
        User user = userService.checkUser(username, MD5Utils.code(password));
        if(user != null){
            session.setAttribute("user",user);
            return "admin/index";
        }else{
            map.put("msg","用户名或密码错误！");
            return "admin/login";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        /**
         * 此处只能到admin，到admin后，再跳转到登录界面
         * 如果设成admin/login，则是直接去登录了；
         */
        return "redirect:/admin";
    }
}
