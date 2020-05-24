/**
 * @ClassName MyDate
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/14 14:29
 */
package cn.sonrisa.pojo;

public class MyDate {
    private String year;
    private String mouth;
    private String day;

    public MyDate() {
    }
    public MyDate(String date){
        String[] s = date.split(" ")[0].split("-");
        year = s[0];
        mouth = s[1];
        day = s[2];
    }

    public String getYear() {
        return year;
    }

    public String getMouth() {
        return mouth;
    }

    public String getDay() {
        return day;
    }

    @Override
    public String toString() {
        return "MyDate{" +
                "year='" + year + '\'' +
                ", mouth='" + mouth + '\'' +
                ", day='" + day + '\'' +
                '}';
    }
}
