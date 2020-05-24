package cn.sonrisa.service;

import cn.sonrisa.pojo.Article;
import cn.sonrisa.pojo.Profile;

public interface ProfileService {

    void ConnectFolderArticle(Integer folderId, long articleId);

    Profile getProfileByUrl(String url);

    Profile getProfileById(long articleId);

    void deleteProfile(Integer folderId);

    void updateFolderId(long articleId,Integer folderId);

    void deleteProfileByArticleId(long articleId);
}
