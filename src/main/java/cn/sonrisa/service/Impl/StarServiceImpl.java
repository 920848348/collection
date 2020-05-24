/**
 * @ClassName starServiceImpl
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/18 18:02
 */
package cn.sonrisa.service.Impl;

import cn.sonrisa.mapper.StarMapper;
import cn.sonrisa.pojo.Star;
import cn.sonrisa.service.StarService;
import cn.sonrisa.utils.BasicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StarServiceImpl implements StarService {

    @Autowired
    private StarMapper starMapper;

    @Override
    public boolean isStar(long articleId) {
        return starMapper.queryStar(BasicUtils.getCurrentUserId(),articleId) != null;
    }

    @Override
    public void addStar(long articleId) {
        starMapper.addStar(new Star(BasicUtils.getCurrentUserId(), articleId));
    }

    @Override
    public void delStar(long articleId) {
        starMapper.delStar(new Star(BasicUtils.getCurrentUserId(),articleId));
    }

    public void delStarByArticleId(long articleId){
        starMapper.delStarByArticleId(articleId);
    }
}
