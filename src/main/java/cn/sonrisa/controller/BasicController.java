package cn.sonrisa.controller;

import cn.sonrisa.service.Impl.FolderServiceImpl;
import cn.sonrisa.service.Impl.UserServiceImpl;
import cn.sonrisa.utils.BasicUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.NameNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static cn.sonrisa.Const.*;
@Controller
@Transactional
@RequestMapping
public class BasicController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private FolderServiceImpl folderService;

    private static HashMap<String, String> map = new HashMap<>();

    /**
     *@decribe ajax登录拦截
     *@param [username, password]
     *@return java.util.Map
     *@Author sonrisa
     *@date 2020/5/9
     */
    @PostMapping("/loginCheck")
    @ResponseBody
    public Map loginCheck(HttpServletRequest request,String username, String password){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        map.clear();
        try{
            subject.login(token);
            map.put(Ajax_loginMsg,"passed");
            userService.clearRepeatUser(username);
        }catch (UnknownAccountException e){
            map.put(Ajax_loginMsg,"wrong_name");
        }catch (IncorrectCredentialsException e){
            map.put(Ajax_loginMsg,"wrong_password");
        }
        return map;
    }

    /*
     *@decribe ajax检测用户名是否存在
     *@param [username]
     *@return java.util.Map
     *@Author sonrisa
     *@date 2020/5/10
     */
    @PostMapping("/isExistUsername")
    @ResponseBody
    public Map isExistUsername(String username){
        boolean q = userService.isEmptyUsername(username);
        map.clear();
        if(q){
            map.put(Ajax_registerMsg,"passed");
        } else map.put(Ajax_registerMsg,"wrong");
        return map;
    }

    @PostMapping("/registerSuccess")
    public String registerSuccess(String username,String password){
        userService.addUser(username,password);
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login(){
        if(BasicUtils.isLogining()) return "redirect:/manager";
        return "login";
    }

    @RequestMapping("/register")
    public String register(){
        if(BasicUtils.isLogining()) return "redirect:/manager";
        return "register";
    }

    @RequestMapping("/forget")
    public String forget(){
        if(BasicUtils.isLogining()) return "redirect:/manager";
        return "forget";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/";
    }


}
