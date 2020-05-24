/**
 * @ClassName StarMapper
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/18 18:00
 */
package cn.sonrisa.mapper;

import cn.sonrisa.pojo.Star;
import org.springframework.stereotype.Repository;

@Repository
public interface StarMapper {

    Star queryStar(long userId,long articleId);

    void addStar(Star star);

    void delStar(Star star);

    void delStarByArticleId(long articleId);
}
