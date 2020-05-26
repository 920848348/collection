/**
 * @ClassName ManagerController
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/11 12:47
 */
package cn.sonrisa.controller;

import cn.sonrisa.result.ExceptionMsg;
import cn.sonrisa.result.Response;
import cn.sonrisa.Const;
import cn.sonrisa.pojo.Article;
import cn.sonrisa.pojo.Folder;
import cn.sonrisa.pojo.Profile;
import cn.sonrisa.service.EmailService;
import cn.sonrisa.service.Impl.*;
import cn.sonrisa.utils.BasicUtils;
import cn.sonrisa.utils.CodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Transactional
@RequestMapping("/manager")
public class ManagerController extends BaseController{

    @Autowired
    private FolderServiceImpl folderService;

    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired
    private ProfileServiceImpl profileService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private StarServiceImpl starService;

    @Autowired
    private EmailServiceImpl emailService;


    @RequestMapping("")
    public String toManager(Model model){
        model.addAttribute("nickname",userService.getNickname());
        model.addAttribute("folders",folderService.getRealFolders(BasicUtils.getCurrentUserId()));
        model.addAttribute("portraitPath",userService.getPortraitPath(BasicUtils.getCurrentUserId()));
        return "manager/index";
    }

    @RequestMapping("/tool")
    public String getTool(Model model) {
        String path="javascript:(function()%7Bvar%20description;var%20desString=%22%22;var%20metas=document.getElementsByTagName('meta');for(var%20x=0,y=metas.length;x%3Cy;x++)%7Bif(metas%5Bx%5D.name.toLowerCase()==%22description%22)%7Bdescription=metas%5Bx%5D;%7D%7Dif(description)%7BdesString=%22&amp;description=%22+encodeURIComponent(description.content);%7Dvar%20win=window.open(%22"
                + Const.Tool_Path
                +"manager/collect?from=webtool&url=%22+encodeURIComponent(document.URL)+desString+%22&title=%22+encodeURIComponent(document.title)+%22&charset=%22+document.charset,'_blank');win.focus();%7D)();";
        model.addAttribute("path",path);
        return "manager/tool";
    }

    /**
     *@decribe 处理收藏请求
     *@param [url, title]
     *@return java.lang.String
     *@Author sonrisa
     *@date 2020/5/11
     */
    @GetMapping("/collect")
    public String collect(String url, String title,String description,Model model){
        model.addAttribute("url",url);
        model.addAttribute("title",title);
        model.addAttribute("folders",folderService.getFolderName(BasicUtils.getCurrentUserId()));
        return "manager/collect";
    }

    /**
     *@decribe 创建收藏，关联三表
     *@param [request]
     *@return java.lang.String
     *@Author sonrisa
     *@date 2020/5/12
     */
    @PostMapping("/saveArticle")
    @ResponseBody
    public Response saveArticle(HttpServletRequest request) throws RuntimeException{
        String url = request.getParameter("url");
        if(articleService.isExist(url)) return result(ExceptionMsg.isExistTheArticle);
        String folderNew = request.getParameter("folderNew");
        if(!"".equals(folderNew) && folderService.isExist(folderNew)) return result(ExceptionMsg.folderNameUsed);
        String folder = request.getParameter("folder");
        if(!"".equals(folder) && !folderService.isExist(folder)) return result(ExceptionMsg.isNotExistTheFolder);
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        boolean isPrivate = "on".equals(request.getParameter("isPrivate"));
        if(StringUtils.isEmpty(description)) description = title;
        Article article = new Article(
                title,
                url,
                description,
                isPrivate);
        articleService.addArticle(article);
        if((StringUtils.isEmpty(folder) && StringUtils.isEmpty(folderNew)) || (!StringUtils.isEmpty(folder) && !StringUtils.isEmpty(folderNew))){
            throw new RuntimeException("The Folder Is Wrong!!!");
        }
        final Integer folderId;
        if(!StringUtils.isEmpty(folderNew)) {
            Folder newFolder = new Folder(BasicUtils.getCurrentUserId(), folderNew);
            folderService.addFolder(newFolder);
            folder = folderNew;
        }
        folderId = folderService.getCurrentFolderId(BasicUtils.getCurrentUserId(),folder);
        profileService.ConnectFolderArticle(folderId,article.getId());
        return result();
    }

    @RequestMapping("/home")
    public String home(Model model){
        long currentUserId = BasicUtils.getCurrentUserId();
        List<Article> articles = articleService.getArticles(currentUserId);
        List<Boolean> isStars = new ArrayList<>();
        List<String> foldersName = new ArrayList<>();
        Profile profile;
        for (Article article : articles) {
            profile = profileService.getProfileById(article.getId());
            foldersName.add(folderService.getFolderName(profile.getFolderId()));
            isStars.add(starService.isStar(article.getId()));
        }
        model.addAttribute("articles",articles);
        model.addAttribute("isStars",isStars);
        model.addAttribute("foldersName",foldersName);
        model.addAttribute("nickname",BasicUtils.getCurrentNickname());
        model.addAttribute("portraitPath",userService.getPortraitPath(BasicUtils.getCurrentUserId()));
        return "manager/home";
    }

    @RequestMapping("/openFolder/{folderName}")
    public String folderArticles(@PathVariable String folderName,Model model){
        model.addAttribute("folderName",folderName);
        List<Article> articles = articleService.getArticlesByFolderName(BasicUtils.getCurrentUserId(),folderName,true);
        List<Boolean> isStars = new ArrayList<>();
        for (Article article : articles) {
            isStars.add(starService.isStar(article.getId()));
        }
        model.addAttribute("articles",articles);
        model.addAttribute("isStars",isStars);
        model.addAttribute("nickname",BasicUtils.getCurrentNickname());
        model.addAttribute("articleTotal",articles.size());
        model.addAttribute("isMe",true);
        model.addAttribute("portraitPath",userService.getPortraitPath(BasicUtils.getCurrentUserId()));
        return "manager/folder";
    }

    @RequestMapping("/openFolder/{nickname}/{folderName}")
    public String folderArticles(@PathVariable String nickname,@PathVariable String folderName,Model model) {
        String username = userService.getUsernameByNickname(nickname);
        model.addAttribute("folderName",folderName);
        List<Article> articles = articleService.getArticlesByFolderName(userService.queryUserId(username),folderName,false);
        List<Boolean> isStars = new ArrayList<>();
        List<String> portraitPaths = new ArrayList<>();
        for (Article article : articles) {
            isStars.add(starService.isStar(article.getId()));
        }
        model.addAttribute("articles",articles);
        model.addAttribute("isStars",isStars);
        model.addAttribute("articleTotal",articles.size());
        model.addAttribute("isMe",false);
        model.addAttribute("nickname",userService.getNickname(username));
        model.addAttribute("portraitPath",userService.getPortraitPath(username));
        return "manager/folder";
    }


    @PostMapping("/getStar")
    @ResponseBody
    public Response getStar(String nickname,String url,HttpServletResponse response){
        String username = userService.getUsernameByNickname(nickname);
        long articleId = articleService.getArticleId(userService.queryUserId(username), url);
        if(articleId == 0) return result(ExceptionMsg.isNotExistTheArticle);
        if(starService.isStar(articleId)) response.setStatus(400);
        else {
            starService.addStar(articleId);
            articleService.getStar(articleId);
        }
        return result();
    }

    @PostMapping("/loseStar")
    @ResponseBody
    public Response loseStar(String nickname,String url,HttpServletResponse response){
        String username = userService.getUsernameByNickname(nickname);
        long articleId = articleService.getArticleId(userService.queryUserId(username), url);
        if(articleId == 0) return result(ExceptionMsg.isNotExistTheArticle);
        if(!starService.isStar(articleId)) response.setStatus(400);
        else {
            starService.delStar(articleId);
            articleService.loseStar(articleId);
        }
        return result();
    }

    @RequestMapping("/addFolder")
    public String addFolder(){
        return "user/addFolder";
    }

    @RequestMapping("/getFoldersName")
    @ResponseBody
    public List<String> getFoldersName(){
        return folderService.getFolderName(BasicUtils.getCurrentUserId());
    }

    @RequestMapping("/lookAround")
    public String lookAround(Model model){
        List<Article> articles = articleService.getOtherArticles();
        List<Boolean> isStars = new ArrayList<>();
        List<String> nicknames = new ArrayList<>();
        List<String> foldersName = new ArrayList<>();
        List<String> portraitPaths = new ArrayList<>();
        Profile profile;
        String username;
        for (Article article : articles) {
            profile = profileService.getProfileById(article.getId());
            isStars.add(starService.isStar(article.getId()));
            foldersName.add(folderService.getFolderName(profile.getFolderId()));
            username = userService.queryUsernameById(profile.getUserId());
            nicknames.add(userService.getNickname(username));
            portraitPaths.add(userService.getPortraitPath(username));
        }
        model.addAttribute("articles",articles);
        model.addAttribute("nicknames",nicknames);
        model.addAttribute("isStars",isStars);
        model.addAttribute("foldersName",foldersName);
        model.addAttribute("portraitPaths",portraitPaths);
        model.addAttribute("isMe",true);
        return "manager/lookAround";
    }

    @RequestMapping("/lookAround/{nickname}")
    public String lookAround(@PathVariable String nickname,Model model){
        String username = userService.getUsernameByNickname(nickname);
        long userId = userService.queryUserId(username);
        List<Article> articles = articleService.getUserArticles(userId);
        List<Boolean> isStars = new ArrayList<>();
        List<String> nicknames = new ArrayList<>();
        List<String> foldersName = new ArrayList<>();
        List<String> portraitPaths = new ArrayList<>();
        Profile profile;
        for (Article article : articles) {
            profile = profileService.getProfileById(article.getId());
            isStars.add(starService.isStar(article.getId()));
            foldersName.add(folderService.getFolderName(profile.getFolderId()));
        }
        String portraitPath = userService.getPortraitPath(userId);
        nicknames.add(nickname);
        portraitPaths.add(portraitPath);
        model.addAttribute("articles",articles);
        model.addAttribute("isStars",isStars);
        model.addAttribute("foldersName",foldersName);
        model.addAttribute("isMe",false);
        model.addAttribute("nicknames", nicknames);
        model.addAttribute("portraitPaths",portraitPaths);
        return "manager/lookAround";
    }

    @PostMapping("/isExistFolder")
    @ResponseBody
    public Response isExistFolder(String nickname, String folderName){
        boolean exist = folderService.isExist(userService.getUserIdByNickname(nickname), folderName);
        if(!exist) return result(ExceptionMsg.isNotExistTheFolder);
        return result();
    }

    @PostMapping("/getPortraitPath")
    @ResponseBody
    public String getPortraitPath(){
        return userService.getPortraitPath(BasicUtils.getCurrentUserId());
    }

    @PostMapping("/getCode")
    @ResponseBody
    public Map getCode(String username){
        Map<String,String> map = new HashMap<>();
        boolean q = userService.isEmptyUsername(username);
        if(q) {
            map.put("rspCode","107");
            map.put("rspMsg","用户邮箱不存在");
            return map;
        }
        String code = CodeUtils.getCode();
        emailService.sendSimpleMail(username,"Sonrisa云收藏","【Sonrisa云收藏】验证码：" + "<h4 style='color: blue'>"+ code +"</h4>" + " 您正在重置密码，为保证您的账户安全，请勿向任何人提供此验证码。如非本人操作，请忽略本邮件。");
        map.put("rspCode","100");
        map.put("rspMsg",code);
        return map;
    }
}
