/**
 * @ClassName FileUtils
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/23 18:00
 */
package cn.sonrisa.utils;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtils {

    public static void uploadFile(byte[] fileBytes, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(fileBytes);
        out.flush();
        out.close();
    }
}
