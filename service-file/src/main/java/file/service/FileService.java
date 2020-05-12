package file.service;

import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * FileService
 *
 * @author 22510
 * @version 1.0
 * 2020/5/10 4:49
 **/
public interface FileService {
    /**
     * 更换头像
     */
    void setHead(String uri, int uid);

    /**
     * 上传文件
     */
    void uploadFile(int uid, String mediaid, String videouri, String imageuri, String otheruri, String label, String title, String value, boolean ispublic, int like, int collect, int dislike, int great, int seen, Date addtime);
}
