/**
 * @ClassName Profile
 * @Description 用户类简类（供识别用户）
 * @Author Sonrisa
 * @Date 2020/5/9 13:58
 */
package cn.sonrisa.pojo;

public class User {
    private long id;
    private String nickname;
    private String username;
    private String password;
    private String portraitPath;


    public User() {
    }

    public User(long id,String username,String password,String portraitPath) {
        this.id = id;
        this.nickname = username;
        this.username = username;
        this.password = password;
        this.portraitPath = portraitPath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPortraitPath() {
        return portraitPath;
    }

    public void setPortraitPath(String portraitPath) {
        this.portraitPath = portraitPath;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", portraitPath='" + portraitPath + '\'' +
                '}';
    }
}
