/**
 * @ClassName ProfileServiceImpl
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/12 19:38
 */
package cn.sonrisa.service.Impl;

import cn.sonrisa.mapper.ProfileMapper;
import cn.sonrisa.pojo.Article;
import cn.sonrisa.pojo.Profile;
import cn.sonrisa.service.ProfileService;
import cn.sonrisa.utils.BasicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileMapper profileMapper;

    @Override
    public void ConnectFolderArticle(Integer folderId, long articleId) {
        profileMapper.addProfile(new Profile(BasicUtils.getCurrentUserId(),articleId,folderId));
    }

    @Override
    public Profile getProfileByUrl(String url) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",BasicUtils.getCurrentUserId());
        map.put("url",url);
        return profileMapper.queryProfile(map);
    }

    @Override
    public Profile getProfileById(long articleId) {
        return profileMapper.queryProfileById(articleId);
    }


    @Override
    public void deleteProfile(Integer folderId) {
        profileMapper.deleteProfile(folderId);
    }

    public List<Profile> getProfileByFolderId(Integer folderId){
        return profileMapper.queryProfileByFolderId(folderId);
    }

    @Override
    public void updateFolderId(long articleId,Integer folderId){
        profileMapper.updateFolderId(articleId,folderId);
    }

    @Override
    public void deleteProfileByArticleId(long articleId){
        profileMapper.deleteProfileByArticleId(articleId);
    }
}
