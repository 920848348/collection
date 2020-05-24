package cn.sonrisa;

import cn.sonrisa.service.EmailService;
import cn.sonrisa.service.Impl.ArticleServiceImpl;
import cn.sonrisa.service.Impl.FolderServiceImpl;
import cn.sonrisa.service.Impl.UserServiceImpl;
import cn.sonrisa.utils.SnowflakeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SnowflakeUtils snowflakeUtils;

    @Autowired
    private FolderServiceImpl folderService;

    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired
    private EmailService emailService;

    @Test
    void test1(){
   }
}
