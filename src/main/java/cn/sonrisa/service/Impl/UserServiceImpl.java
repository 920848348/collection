package cn.sonrisa.service.Impl;

import cn.sonrisa.mapper.FolderMapper;
import cn.sonrisa.mapper.UserMapper;
import cn.sonrisa.pojo.Folder;
import cn.sonrisa.pojo.User;
import cn.sonrisa.service.UserService;
import cn.sonrisa.utils.BasicUtils;
import cn.sonrisa.utils.SnowflakeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FolderMapper folderMapper;

    @Autowired
    private SessionDAO sessionDAO;

    private final static Map<String,Object> map = new HashMap<>();

    @Value("${commonHeadPortraitPath}")
    private String commonHeadPortraitPath;


    @Override
    public String queryUsernameById(long userId) {
        return userMapper.queryUserById(userId).getUsername();
    }

    @Override
    public void clearRepeatUser(String username) {
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            String s = String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));
            if(s == "null") {
                sessionDAO.delete(session);
            }
            else {
                String loginUsername = s.split(",")[2].split("=")[1].split("\'")[1];
                if (username.equals(loginUsername) && !(SecurityUtils.getSubject().getSession().getId().equals(session.getId()))) {
                    sessionDAO.delete(session);
                }
            }
        }
    }

    public long queryUserId(String username){
        return userMapper.queryUserByName(username).getId();
    }

    @Override
    public User queryUserByName(String name) {
        return userMapper.queryUserByName(name);
    }

    @Override
    public boolean isEmptyUsername(String name) {
        return queryUserByName(name) == null;
    }

    @Override
    public void addUser(String username, String password) {
        long NewUserId = SnowflakeUtils.nextId();
        userMapper.addUser(new User(NewUserId,username,password,commonHeadPortraitPath));
        folderMapper.addFolder(new Folder(NewUserId,"未分类"));
    }

    @Override
    public void changePwd(String newPassword) {
        map.clear();
        map.put("userId", BasicUtils.getCurrentUserId());
        map.put("password",newPassword);
        userMapper.updatePasswordByUserId(map);
    }

    @Override
    public void changePwd(String username,String newPassword) {
        map.clear();
        map.put("userId", userMapper.queryUserByName(username).getId());
        map.put("password",newPassword);
        userMapper.updatePasswordByUserId(map);
    }

    @Override
    public void changeNickname(String nickname) {
        map.clear();
        map.put("userId",BasicUtils.getCurrentUserId());
        map.put("nickname",nickname);
        userMapper.updateNicknameByUserId(map);
    }

    @Override
    public boolean isExistByNickname(String nickname) {
        return userMapper.queryUserByNickname(nickname) != null;
    }

    @Override
    public String getNickname() {
        return getNickname(BasicUtils.getCurrentUsername());
    }

    @Override
    public String getNickname(String username){
        User user = userMapper.queryUserByName(username);
        return user.getNickname();
    }

    @Override
    public long getUserIdByNickname(String nickname){
        return userMapper.queryUserByNickname(nickname).getId();
    }

    @Override
    public String getUsernameByNickname(String nickname){
        return userMapper.queryUserByNickname(nickname).getUsername();
    }

    @Override
    public void updatePortraitPath(String portraitPath) {
        userMapper.updatePortraitPath(BasicUtils.getCurrentUserId(),portraitPath);
    }

    @Override
    public String getPortraitPath(long userId) {
        return userMapper.queryUserById(userId).getPortraitPath();
    }

    @Override
    public String getPortraitPath(String username){
        return userMapper.queryUserByName(username).getPortraitPath();
    }
}
