/**
 * @ClassName Response
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/16 14:41
 */
package cn.sonrisa.result;

public class Response {

    private String rspCode = "100";
    private String rspMsg = "操作成功";

    public Response(){

    }
    public Response(ExceptionMsg msg){
        this.rspCode = msg.getCode();
        this.rspMsg = msg.getMsg();
    }

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    @Override
    public String toString() {
        return "Response{" +
                "rspCode='" + rspCode + '\'' +
                ", rspMsg='" + rspMsg + '\'' +
                '}';
    }
}
