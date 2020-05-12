package user.po;

import lombok.Data;

import java.util.Base64;
import java.util.List;

/**
 * UserPo
 *
 * @author 22510
 * @version 1.0
 * 2020/5/9 20:17
 **/
@Data
public class UserPO {
//    用户ID
    private int uid;
//    头像
    private Base64 head;
//    昵称
    private String username;
//    密码
    private String password;
//    令牌
    private String token;
//    QQ号
    private String qq_number;
//    手机号码
    private String phone;
//    关注的用户
    private List<UserPO> attentionUser;
//    关注的标签
    private List<String> attentionLabel;
}