package file.mapper;

import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * FileMapper
 *
 * @author 22510
 * @version 1.0
 * 2020/5/10 4:52
 **/
@Mapper
public interface FileMapper {
    /**
     * 更换头像
     */
    @Update("UPDATE doit_user set head = #{uri} WHERE uid = #{uid}")
    void setHead(@Param("uri") String uri,@Param("uid") int uid);

    /**
     * 上传资源
     * @param uid 用户ID
     * @param mediaid 资源id
     * @param videouri 视频URI
     * @param imageuri 图片URI
     * @param label 标签
     * @param title 标题
     * @param value 内容
     * @param ispublic 是否公开
     * @param like 喜欢人数
     * @param collect 收藏人数
     * @param dislike 踩人数
     * @param great 赞人数
     * @param seen 看过人数
     * @param addtime 添加时间
     */
    @Insert("INSERT INTO doit_user_upload VALUES(" +
            "#{uid}," +
            "#{mediaid}," +
            "#{videouri}," +
            "#{imageuri}," +
            "#{otheruri}," +
            "#{label}," +
            "#{title}," +
            "#{value}," +
            "#{ispublic}," +
            "#{like}," +
            "#{collect}," +
            "#{dislike}," +
            "#{great}," +
            "#{seen}," +
            "#{addtime})")
    void uploadFile(
            @Param("uid") int uid,
            @Param("mediaid") String mediaid,
            @Param("videouri") String videouri,
            @Param("imageuri") String imageuri,
            @Param("otheruri") String otheruri,
            @Param("label") String label,
            @Param("title") String title,
            @Param("value") String value,
            @Param("ispublic") boolean ispublic,
            @Param("like") int like,
            @Param("collect") int collect,
            @Param("dislike") int dislike,
            @Param("great") int great,
            @Param("seen") int seen,
            @Param("addtime") Date addtime);

    /**
     * 删除资源
     */
    void deleteFile();
}