package cn.sonrisa.mapper;

import cn.sonrisa.pojo.Folder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FolderMapper {

    Folder getFolderById(Integer id);

    void addFolder(Folder folder);

    List<Folder> getFoldersByUserId(long userId);

    List<Folder> getRealFoldersByUserId(long userId);

    List<Folder> getFoldersByUserIdNotId(long userId);

    List<String> getFoldersNameByUserId(long userId);

    List<String> getRealFoldersNameByUserId(long userId);

    Integer getIdByUserIdAndName(Folder folder);

    Folder queryFolderByUserIdAndName(Map<String,Object> map);

    void updateFolderName(@Param("userId")long userId,@Param("oldName")String oldName,@Param("newName")String newName);

    void deleteFolder(@Param("folderId")Integer folderId);

    Folder queryFolderByArticleId(@Param("articleId") long articleId);
}
