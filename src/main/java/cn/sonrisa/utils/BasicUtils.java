/**
 * @ClassName BasicUtils
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/11 13:58
 */
package cn.sonrisa.utils;

import cn.sonrisa.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Shiro 安全框架下获取当前用户简单信息
 */
public class BasicUtils {

    public static boolean isLogining(){
        return SecurityUtils.getSubject().isAuthenticated();
    }
    public static long getCurrentUserId(){
        return ((User)SecurityUtils.getSubject().getPrincipal()).getId();
    }

    public static String getCurrentUsername(){
        return ((User)SecurityUtils.getSubject().getPrincipal()).getUsername();
    }

    public static String getCurrentNickname(){
        return ((User)SecurityUtils.getSubject().getPrincipal()).getNickname();
    }
    public static void isCorrectPassword(String password) throws Exception{
        SecurityUtils.getSecurityManager().authenticate(new UsernamePasswordToken(getCurrentUsername(),password));
    }

    public static void logout(){
        SecurityUtils.getSubject().logout();
    }
}
