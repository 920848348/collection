package cn.sonrisa.mapper;

import cn.sonrisa.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserMapper {

    User queryUserById(@Param("userId")long userId);

    User queryUserByName(@Param("username") String username);

    Integer addUser(User user);

    void updatePasswordByUserId(Map<String,Object> map);

    void updateNicknameByUserId(Map<String,Object> map);

    User queryUserByNickname(@Param("nickname") String nickname);

    void updatePortraitPath(@Param("userId")long userId,@Param("portraitPath")String portraitPath);
}
