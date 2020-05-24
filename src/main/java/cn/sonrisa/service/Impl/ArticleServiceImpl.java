/**
 * @ClassName ArticleServiceImpl
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/12 19:09
 */
package cn.sonrisa.service.Impl;

import cn.sonrisa.mapper.ArticleMapper;
import cn.sonrisa.mapper.ProfileMapper;
import cn.sonrisa.pojo.Article;
import cn.sonrisa.pojo.Profile;
import cn.sonrisa.service.ArticleService;
import cn.sonrisa.utils.BasicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class
ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ProfileMapper profileMapper;

    @Override
    public void addArticle(Article article) {
        articleMapper.addArticle(article);
    }

    @Override
    public long getArticleId(long userId, String url) {
        Article article = articleMapper.getArticleByUserIdAndUrl(userId, url);
        if(article == null) return 0;
        return article.getId();
    }

    @Override
    public Article getArticle(long userId,String url){
        return articleMapper.getArticleByUserIdAndUrl(userId,url);
    }

    @Override
    public List<Article> getArticles() {
        return articleMapper.getArticles();
    }

    @Override
    public List<Article> getOtherArticles() {
        return articleMapper.getOtherArticles(BasicUtils.getCurrentUserId());
    }

    @Override
    public List<Article> getUserArticles(long userId){
        return articleMapper.queryUserArticles(userId);
    }

    @Override
    public List<Article> getArticles(long userId) {
        return articleMapper.getArticlesByUserId(userId);
    }

    @Override
    public List<Article> getArticlesByFolderName(long userId,String folderName,Boolean isPrivate) {
        return articleMapper.getArticlesByFolderName(userId,folderName,isPrivate);
    }

    @Override
    public void getStar(long articleId) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",articleId);
        map.put("operator",Integer.valueOf(1));
        articleMapper.updateArticleLike(map);
    }

    @Override
    public void loseStar(long articleId) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",articleId);
        map.put("operator",Integer.valueOf(-1));
        articleMapper.updateArticleLike(map);
    }

    @Override
    public boolean isExist(String url) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId", BasicUtils.getCurrentUserId());
        map.put("url",url);
        Profile profile = profileMapper.queryProfile(map);
        if(profile == null) return false;
        return true;
    }

    @Override
    public void deleteByArticleId(long articleId) {
        articleMapper.deleteById(articleId);
    }

    @Override
    public void updateArticle(long articleId,String title,String description,boolean isPrivate){
        articleMapper.updateArticle(articleId,title,description,isPrivate);
    }

}
