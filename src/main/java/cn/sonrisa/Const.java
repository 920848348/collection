/**
 * @ClassName Const
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/11 10:55
 */
package cn.sonrisa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class  Const {

    public static final String LoginUser = "LoginUser";

    public static final String Ajax_loginMsg = "login_msg";

    public static final String Ajax_registerMsg = "register_msg";

    public static String Tool_Path;

    @Autowired(required = true)
    public void setUrl(@Value("http://localhost:8080/")String path){
        Tool_Path = path;
    }
}
