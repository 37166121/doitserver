package user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import user.mapper.UserMapper;
import user.po.UserPO;
import user.service.UserService;

import java.util.List;

/**
 * UserServiceImpl
 *
 * @author 22510
 * @version 1.0
 * 2020/5/9 18:10
 **/
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 获取QQ号
     * @return
     */
    @Override
    public List<String> getQqNumber() {
        return userMapper.getQqNumber();
    }

    /**
     * APP登录
     * @param timestamp
     * @param ip
     */
    @Override
    public void loginApp(String timestamp, String ip) {
        userMapper.loginApp(timestamp, ip);
    }

    /**
     * APP退出
     * @param timestamp
     */
    @Override
    public void logoutApp(String timestamp) {
        userMapper.logoutApp(timestamp);
    }

    /**
     * WEB登录
     * @param timestamp
     * @param ip
     */
    @Override
    public void loginWeb(String timestamp, String ip) {
        userMapper.loginWeb(timestamp, ip);
    }

    /**
     * WEB退出
     * @param timestamp
     */
    @Override
    public void logoutWeb(String timestamp) {
        userMapper.logoutWeb(timestamp);
    }

    /**
     * 鉴权
     * @param uid
     * @return
     */
    @Override
    public boolean isAdmin(int uid) {
        return userMapper.isAdmin(uid);
    }

    /**
     * 获取所有用户
     * @return
     */
    @Override
    public List<UserPO> getAllUser(int uid) {
        return userMapper.getAllUser(uid);
    }

    /**
     * 获取用户信息
     * @param phone
     * @param pwd
     * @return
     */
    @Override
    public UserPO getUser(String phone, String pwd) {
        return userMapper.getUser(phone,pwd);
    }

    /**
     * 获取关注用户
     * @param uid
     * @return
     */
    @Override
    public List<UserPO> getAttentionUser(int uid) {
        return userMapper.getAttentionUser(uid);
    }

    /**
     * 获取关注标签
     * @param uid
     * @return
     */
    @Override
    public List<String> getAttentionLabel(int uid) {
        return userMapper.getAttentionLabel(uid);
    }

    /**
     * 更改密码
     * @param password
     * @param uid
     */
    @Override
    public void setPassword(String password, int uid) {
        userMapper.setPassword(password, uid);
    }

    /**
     * 用户注册
     * @param uuid
     * @param authority
     * @param headuri
     * @param username
     * @param password
     * @param qq_number
     * @param phone
     * @param addtype
     * @param addtime
     */
    @Override
    public void addUser(String uuid, String authority, String headuri, String username, String password, String qq_number, String phone, String addtype, String addtime) {
        userMapper.addUser(uuid, authority, headuri, username, password, qq_number, phone, addtype, addtime);
    }
}
