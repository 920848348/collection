package cn.sonrisa.service;

import cn.sonrisa.pojo.Article;

import java.util.List;

public interface ArticleService {

    void addArticle(Article article);

    Article getArticle(long userId,String url);

    long getArticleId(long userId,String url);

    List<Article> getArticles();

    List<Article> getOtherArticles();

    List<Article> getUserArticles(long userId);

    List<Article> getArticles(long userId);

    List<Article> getArticlesByFolderName(long userId,String folderName,Boolean isPrivate);

    void getStar(long articleId);

    void loseStar(long articleId);

    boolean isExist(String url);

    void deleteByArticleId(long articleId);

    void updateArticle(long articleId,String title,String description,boolean isPrivate);
}
