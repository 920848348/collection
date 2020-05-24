package cn.sonrisa.result;

public enum ExceptionMsg {

    folderNameUsed("101","收藏夹已存在"),

    isNotExistTheFolder("102","收藏夹不存在"),

    isExistTheArticle("103","该文章已收藏"),

    isNotExistTheArticle("104","文章不存在"),

    failedUploadHeadPortrait("105","头像上传失败"),

    isNotTheImageType("106","这并不是图片文件"),

    isNotExistTheUsername("107","用户邮箱不存在");

    private String code;
    private String msg;
    ExceptionMsg(String code,String msg ) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
