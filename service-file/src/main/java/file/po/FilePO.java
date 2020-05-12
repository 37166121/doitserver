package file.po;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * FilePO
 *
 * @author 22510
 * @version 1.0
 * 2020/5/10 5:30
 **/
@Data
public class FilePO {
//    用户ID
    int uid;
//    资源id
    String mediaid;
//    视频URI
    String videouri;
//    图片URI
    String imageuri;
//    其他资源URI
    String otheruri;
//    标签
    List<String> label;
//    标题
    String title;
//    内容
    String value;
//    是否公开
    boolean ispublic;
//    喜欢人数
    int like;
//    收藏人数
    int collect;
//    踩人数
    int dislike;
//    赞人数
    int great;
//    看过人数
    int seen;
//    添加时间
    Date addtime;
}
