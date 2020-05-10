package user.service;

import user.po.UserPO;

import java.util.List;

/**
 * UserService
 *
 * @author 22510
 * @version 1.0
 * 2020/5/9 18:09
 **/
public interface UserService {
    List<String> getQqNumber();
    void loginApp(String timestamp, String ip);
    void logoutApp(String timestamp);
    void loginWeb(String timestamp, String ip);
    void logoutWeb(String timestamp);
    boolean isAdmin(String uid);
    List<UserPO> getAllUser(String uid);
    UserPO getUser(String phone, String pwd);
    List<UserPO> getAttentionUser(String uid);
    List<String> getAttentionLabel(String uid);
    void setPassword(String password,String uid);
    void addUser(String uuid,String authority,String username,String password,String qq_number,String phone,String addtype,String addtime);
}