package cn.sonrisa.service;

import cn.sonrisa.pojo.User;
import org.springframework.transaction.annotation.Transactional;


public interface UserService {

    String queryUsernameById(long userId);

    void clearRepeatUser(String username);

    boolean isEmptyUsername(String name);

    long queryUserId(String username);

    User queryUserByName(String name);

    void addUser(String username,String password);

    void changePwd(String newPassword);

    void changePwd(String username,String newPassword);

    void changeNickname(String nickname);

    boolean isExistByNickname(String nickname);

    String getNickname();

    String getNickname(String username);

    long getUserIdByNickname(String nickname);

    String getUsernameByNickname(String nickname);

    void updatePortraitPath(String portraitPath);

    String getPortraitPath(long userId);

    String getPortraitPath(String username);
}
