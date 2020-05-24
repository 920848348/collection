/**
 * @ClassName Profile
 * @Description 用户总类
 * @Author Sonrisa
 * @Date 2020/5/11 15:42
 */
package cn.sonrisa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Profile {
    private long userId;
    private long articleId;
    private Integer folderId;

    public Profile() {
    }

    public Profile(long userId, long articleId,Integer folderId) {
        this.userId = userId;
        this.articleId = articleId;
        this.folderId = folderId;
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

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "userId=" + userId +
                ", articleId=" + articleId +
                ", folderId='" + folderId + '\'' +
                '}';
    }
}
