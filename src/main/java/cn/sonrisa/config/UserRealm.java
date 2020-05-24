package cn.sonrisa.config;

import cn.sonrisa.pojo.User;
import cn.sonrisa.service.Impl.UserServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class UserRealm extends AuthorizingRealm{

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SessionDAO sessionDAO;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.queryUserByName(token.getUsername());
        return user == null ? null : new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
