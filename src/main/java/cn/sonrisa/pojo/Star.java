/**
 * @ClassName Star
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/18 18:02
 */
package cn.sonrisa.pojo;

public class Star {
    private long userId;
    private long articleId;

    public Star() {
    }

    public Star(long userId, long articleId) {
        this.userId = userId;
        this.articleId = articleId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "Star{" +
                "userId=" + userId +
                ", articleId=" + articleId +
                '}';
    }
}
