/**
 * @ClassName SafetyController
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/15 11:55
 */
package cn.sonrisa.controller;

import cn.sonrisa.pojo.Article;
import cn.sonrisa.pojo.Folder;
import cn.sonrisa.pojo.Profile;
import cn.sonrisa.result.ExceptionMsg;
import cn.sonrisa.result.Response;
import cn.sonrisa.service.Impl.*;
import cn.sonrisa.utils.BasicUtils;
import cn.sonrisa.utils.FileUtils;
import cn.sonrisa.utils.SnowflakeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Transactional
@RequestMapping("/safety")
public class SafetyController extends BaseController{

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private FolderServiceImpl folderService;

    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired
    private ProfileServiceImpl profileService;

    @Autowired
    private StarServiceImpl starService;

    @Value("${staticPath}")
    private String staticPath;

    @Value("${mavenPath}")
    private String mavenPath;

    @RequestMapping("/isCorrectPwd")
    @ResponseBody
    public String isCorrectPwd(String password){
        boolean q = true;
        try{
            BasicUtils.isCorrectPassword(password);
        }catch (Exception e){
            q = false;
        }
        if(q) return "passed";
        else return "wrong";
    }
    @RequestMapping("/changePwd")
    @ResponseBody
    public String changePwd(String newPassword){
        userService.changePwd(newPassword);
        BasicUtils.logout();
        return null;
    }

    @RequestMapping("/forgetPwd")
    @ResponseBody
    public Response forgetPwd(String username,String password){
        userService.changePwd(username,password);
        return result();
    }

    @RequestMapping("/changeNickname")
    @ResponseBody
    public String changeNickname(String nickname){
        boolean q = userService.isExistByNickname(nickname);
        if(q) return "isExist";
        userService.changeNickname(nickname);
        return "isOk";
    }

    @RequestMapping("/addFolder")
    @ResponseBody
    public Response addFolder(String folderName){
        if(folderService.isExist(folderName)) return result(ExceptionMsg.folderNameUsed);
        folderService.addFolder(new Folder(BasicUtils.getCurrentUserId(),folderName));
        return result();
    }

    @RequestMapping("/changeFolderName")
    @ResponseBody
    public Response changeFolderName(String name,String newName){
        if(folderService.isExist(newName)) return result(ExceptionMsg.folderNameUsed);
        folderService.updateFolderName(name,newName);
        return result();
    }

    @PostMapping("/delFolder")
    @ResponseBody
    public Response delFolder(String folderName){
        Folder folder = folderService.getFolder(BasicUtils.getCurrentUserId(), folderName);
        List<Profile> profiles = profileService.getProfileByFolderId(folder.getId());
        profileService.deleteProfile(folder.getId());
        folderService.deleteFolder(folder.getId());
        for (Profile profile : profiles) {
            starService.delStarByArticleId(profile.getArticleId());
        }
        for (Profile profile : profiles) {
            articleService.deleteByArticleId(profile.getArticleId());
        }
        return result();
    }

    @PostMapping("/getArticle")
    @ResponseBody
    public Map getArticle(String url){
        Article article = articleService.getArticle(BasicUtils.getCurrentUserId(), url);
        Map<String,Object> map = new HashMap<>();
        if(article == null){
            map.put("rspCode","104");
            return map;
        }
        map.put("description",article.getDescription());
        String s = article.isPrivate() ? "yes" : "no";
        map.put("isPrivate",s);
        map.put("folderName",folderService.getFolderNameByArticleId(article.getId()));
        map.put("url",url);
        map.put("title",article.getTitle());
        map.put("rspCode","100");
        return map;
    }

    @PostMapping("/editCollection")
    public String editArticle(HttpServletRequest request,Model model){
        List<String> folderNames = folderService.getFolderName(BasicUtils.getCurrentUserId());
        folderNames.remove(request.getParameter("folderName"));
        model.addAttribute("url",request.getParameter("url"));
        model.addAttribute("folders",folderNames);
        model.addAttribute("description",request.getParameter("description"));
        model.addAttribute("folderName",request.getParameter("folderName"));
        model.addAttribute("isPrivate",request.getParameter("isPrivate"));
        model.addAttribute("title",request.getParameter("title"));
        return "manager/editCollection";
    }

    @PostMapping("/changeCollection")
    @ResponseBody
    public Response changeCollection(HttpServletRequest request){
        boolean q = "on".equals(request.getParameter("isPrivate"));
        Article article = articleService.getArticle(BasicUtils.getCurrentUserId(), request.getParameter("url"));
        Folder folder = folderService.getFolder(BasicUtils.getCurrentUserId(), request.getParameter("folder"));
        if(article == null) return result(ExceptionMsg.isNotExistTheArticle);
        if(folder == null ) return result(ExceptionMsg.isNotExistTheFolder);
        articleService.updateArticle(
                article.getId(),
                request.getParameter("title"),
                request.getParameter("description"),
                q
            );
        profileService.updateFolderId(article.getId(),folder.getId());
        return result();
    }

    @PostMapping("/delCollection")
    @ResponseBody
    public Response delCollection(HttpServletRequest request){
        Article article = articleService.getArticle(BasicUtils.getCurrentUserId(), request.getParameter("url"));
        if(article == null) return result(ExceptionMsg.isNotExistTheArticle);
        profileService.deleteProfileByArticleId(article.getId());
        starService.delStarByArticleId(article.getId());
        articleService.deleteByArticleId(article.getId());
        return result();
    }

    @PostMapping("/uploadHeadPortrait")
    @ResponseBody
    public Response uploadHeadPortrait(String dataUrl){
        try{
            String savePath = staticPath + BasicUtils.getCurrentUserId() + "/";
            String fileName = SnowflakeUtils.nextId() + ".png";
            String image = dataUrl;
            String header = "data:image";
            String[] s = image.split(",");
            if(s[0].contains(header)){
                image = s[1];
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] decode = decoder.decode(image);
                FileUtils.uploadFile(decode,savePath,fileName);
                userService.updatePortraitPath(mavenPath + BasicUtils.getCurrentUserId() + "/" + fileName);
                return result();
            }else return result(ExceptionMsg.isNotTheImageType);
        }catch (Exception e){
            return result(ExceptionMsg.failedUploadHeadPortrait);
        }
    }
}
