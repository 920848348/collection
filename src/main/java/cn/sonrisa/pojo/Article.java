/**
 * @ClassName Arrticle
 * @Description 文章总类
 * @Author Sonrisa
 * @Date 2020/5/11 15:43
 */
package cn.sonrisa.pojo;

import cn.sonrisa.utils.SnowflakeUtils;
import javafx.scene.input.DataFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Article{
    private long id;
    private String title;
    private String url;
    private String description;
    private Integer likeTotal;
    private Integer collectionTotal;
    private String createTime;
    private boolean isPrivate;
    private MyDate myDate;

    private static SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");

    public Article() {
    }

    public Article(String title, String url, String description,boolean isPrivate) {
        this.id = SnowflakeUtils.nextId();
        this.title = title;
        this.url = url;
        this.description = description;
        this.likeTotal = 0;
        this.collectionTotal = 0;
        this.isPrivate = isPrivate;
        this.createTime = dft.format(new Date());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLikeTotal() {
        return likeTotal;
    }

    public void setLikeTotal(Integer likeTotal) {
        this.likeTotal = likeTotal;
    }

    public Integer getCollectionTotal() {
        return collectionTotal;
    }

    public void setCollectionTotal(Integer collectionTotal) {
        this.collectionTotal = collectionTotal;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
        this.myDate = new MyDate(this.createTime);
    }
    public MyDate getMyDate() {
        return myDate;
    }

    public void setMyDate(MyDate myDate) {
        this.myDate = myDate;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", likeTotal=" + likeTotal +
                ", collectionTotal=" + collectionTotal +
                ", createTime='" + createTime + '\'' +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
