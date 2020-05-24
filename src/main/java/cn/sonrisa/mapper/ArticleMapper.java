/**
 * @ClassName ArticleMapper
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/11 19:09
 */
package cn.sonrisa.mapper;

import cn.sonrisa.pojo.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ArticleMapper {

    void addArticle(Article article);

    Article getArticleByUserIdAndUrl(long userId,String url);

    List<Article> getArticles();

    List<Article> getOtherArticles(@Param("userId") long userId);

    List<Article> queryUserArticles(@Param("userId")long userId);

    List<Article> getArticlesByUserId(@Param("userId") long userId);

    void updateArticleLike(Map<String,Object> map);

    List<Article> getArticlesByFolderName(@Param("userId")long userId,@Param("folderName")String folderName,@Param("isPrivate")Boolean isPrivate);

    void deleteById(@Param("id") long id);

    void updateArticle(@Param("articleId")long articleId,@Param("title")String title,@Param("description")String description,boolean isPrivate);
}
