package cn.sonrisa.service;

import cn.sonrisa.pojo.Folder;

import java.util.List;

public interface FolderService {

    List<String> getFolderName(long userId);

    List<String> getRealFolderName(long userId);

    List<Folder> getRealFolders(long userId);

    List<Folder> getFolders(long userId);

    void addFolder(Folder folder);

    Integer getCurrentFolderId(long userId,String name);

    String getFolderName(Integer id);

    boolean isExist(String folderName);

    boolean isExist(long userId,String folderName);

    void updateFolderName(String folderName,String newFolderName);

    Folder getFolder(long userId,String folderName);

    void deleteFolder(Integer folderId);

    Folder getFolderByArticleId(long articleId);

    String getFolderNameByArticleId(long articleId);
}
