package cn.sonrisa.service;

public interface StarService {

    boolean isStar(long article);

    void addStar(long articleId);

    void delStar(long articleId);

    void delStarByArticleId(long articleId);

}
