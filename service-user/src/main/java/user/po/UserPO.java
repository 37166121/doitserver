package user.po;

import java.util.Base64;
import java.util.List;

/**
 * UserPo
 *
 * @author 22510
 * @version 1.0
 * 2020/5/9 20:17
 **/
public class UserPO {
//    用户ID
    private String uid;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Base64 getHead() {
        return head;
    }

    public void setHead(Base64 head) {
        this.head = head;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getQqNumber() {
        return qq_number;
    }

    public void setQqNumber(String qq_number) {
        this.qq_number = qq_number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<UserPO> getAttentionUser() {
        return attentionUser;
    }

    public void setAttentionUser(List<UserPO> attentionUser) {
        this.attentionUser = attentionUser;
    }

    public List<String> getAttentionLabel() {
        return attentionLabel;
    }

    public void setAttentionLabel(List<String> attentionLabel) {
        this.attentionLabel = attentionLabel;
    }
}
