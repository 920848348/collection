/**
 * @ClassName FolderServiceImpl
 * @Description TODO
 * @Author Sonrisa
 * @Date 2020/5/12 17:03
 */
package cn.sonrisa.service.Impl;

import cn.sonrisa.mapper.FolderMapper;
import cn.sonrisa.pojo.Article;
import cn.sonrisa.pojo.Folder;
import cn.sonrisa.service.FolderService;
import cn.sonrisa.utils.BasicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FolderServiceImpl implements FolderService {
    @Autowired
    private FolderMapper folderMapper;

    private Map<String,Object> map = new HashMap<>();

    @Override
    public List<String> getFolderName(long userId) {
        return folderMapper.getFoldersNameByUserId(userId);
    }

    @Override
    public List<String> getRealFolderName(long userId) {
        return folderMapper.getRealFoldersNameByUserId(userId);
    }

    @Override
    public List<Folder> getRealFolders(long userId) {
        return folderMapper.getRealFoldersByUserId(userId);
    }

    @Override
    public List<Folder> getFolders(long userId) {
        return folderMapper.getFoldersByUserIdNotId(userId);
    }


    @Override
    public void addFolder(Folder folder) {
        folderMapper.addFolder(folder);
    }

    @Override
    public Integer getCurrentFolderId(long userId, String name) {
        return folderMapper.getIdByUserIdAndName(new Folder(userId,name));
    }

    @Override
    public String getFolderName(Integer id){
        return folderMapper.getFolderById(id).getName();
    }

    @Override
    public boolean isExist(String folderName) {
        map.clear();
        map.put("userId", BasicUtils.getCurrentUserId());
        map.put("folderName",folderName);
        Folder folder = folderMapper.queryFolderByUserIdAndName(map);
        return folder != null;
    }

    @Override
    public boolean isExist(long userId,String folderName) {
        map.clear();
        map.put("userId", userId);
        map.put("folderName",folderName);
        Folder folder = folderMapper.queryFolderByUserIdAndName(map);
        return folder != null;
    }

    @Override
    public void updateFolderName(String folderName, String newFolderName) {
        folderMapper.updateFolderName(BasicUtils.getCurrentUserId(),folderName,newFolderName);
    }

    @Override
    public Folder getFolder(long userId,String folderName){
        map.clear();
        map.put("userId", userId);
        map.put("folderName",folderName);
        return folderMapper.queryFolderByUserIdAndName(map);
    }

    @Override
    public void deleteFolder(Integer folderId){
        folderMapper.deleteFolder(folderId);
    }

    @Override
    public Folder getFolderByArticleId(long articleId){
        return folderMapper.queryFolderByArticleId(articleId);
    }

    @Override
    public String getFolderNameByArticleId(long articleId){
        return getFolderByArticleId(articleId).getName();
    }
}
