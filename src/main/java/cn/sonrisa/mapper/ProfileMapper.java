/**
 * @ClassName ProfileMapper
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/11 19:09
 */
package cn.sonrisa.mapper;

import cn.sonrisa.pojo.Profile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProfileMapper {

    void addProfile(Profile profile);

    Profile queryProfile(Map<String,Object> map);

    Profile queryProfileById(long articleId);

    void updateProfile(Profile profile);

    void deleteProfile(@Param("folderId") Integer folderId);

    void deleteProfileByArticleId(@Param("articleId")long articleId);

    List<Profile> queryProfileByFolderId(Integer folderId);

    void updateFolderId(@Param("articleId") long articleId,@Param("folderId") Integer folderId);
}
