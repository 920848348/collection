/**
 * @ClassName Folder
 * @Description 文件夹
 * @Author Sonrisa
 * @Date 2020/5/12 16:44
 */
package cn.sonrisa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Folder {
    private Integer id;
    private long userId;
    private String name;

    public Folder() {
    }

    public Folder(long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                '}';
    }
}
