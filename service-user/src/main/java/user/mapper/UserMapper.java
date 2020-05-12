package user.mapper;

import org.apache.ibatis.annotations.*;
import user.po.UserPO;

import java.util.List;

/**
 * UserMapper
 *
 * @author 22510
 * @version 1.0
 * 2020/5/9 18:08
 **/
@Mapper
public interface UserMapper {
    /**
     * 获取QQ号
     * @return
     */
    @Select("SELECT * FROM qq_number")
    List<String> getQqNumber();

    /**
     * APP登录
     */
    @Update("UPDATE doit_user set applastlogin = #{timestamp},applastip = #{ip}")
    void loginApp(@Param("timestamp") String timestamp,@Param("ip") String ip);

    /**
     * APP退出
     */
    @Update("UPDATE doit_user set applastlogout = #{timestamp}")
    void logoutApp(@Param("timestamp") String timestamp);

    /**
     * WEB登录
     */
    @Update("UPDATE doit_user set weblastlogin = #{timestamp},weblastip = #{ip}")
    void loginWeb(@Param("timestamp") String timestamp,@Param("ip") String ip);

    /**
     * WEB退出
     */
    @Update("UPDATE doit_user set weblastlogout = #{timestamp}")
    void logoutWeb(@Param("timestamp") String timestamp);

    /**
     * 获取用户信息
     * @param phone 手机号码
     * @return 用户信息
     */
    @Select("SELECT * FROM doit_user WHERE phone = #{phone} AND password = #{pwd}")
    UserPO getUser(@Param("phone") String phone, @Param("pwd") String pwd);

    /**
     * 鉴权
     * 是否管理员
     * @param uid
     * @return
     */
    @Select("SELECT (SELECT authority FROM doit_user WHERE uid = #{uid}) <=> 'admin'")
    boolean isAdmin(@Param("uid") int uid);

    /**
     * 获取所有用户
     * 仅管理员访问
     */
    @Select("SELECT * FROM doit_user")
    List<UserPO> getAllUser(@Param("uid") int uid);

    /**
     * 获取当前用户关注的用户
     * @param uid 用户ID
     * @return
     */
    @Select("SELECT uid,username,head FROM doit_user WHERE uid IN (SELECT touid FROM doit_user_attention_user WHERE uid = #{uid})")
    List<UserPO> getAttentionUser(@Param("uid") int uid);

    /**
     * 获取当前用户关注的标签
     * @param uid 用户ID
     * @return
     */
    @Select("SELECT title FROM doit_system_label WHERE id IN (SELECT id FROM doit_user_attention_label WHERE uid = #{uid})")
    List<String> getAttentionLabel(@Param("uid") int uid);

    /**
     * 更改密码
     * @param password
     * @param uid
     */
    @Update("UPDATE doit_user set password = #{password} WHERE uid = #{uid}")
    void setPassword(@Param("password") String password,@Param("uid") int uid);

    /**
     * 用户注册
     * @param uuid
     * @param username
     * @param password
     * @param qq_number
     * @param phone
     * @param addtype
     * @param addtime
     */
    @Insert("INSERT INTO doit_user(uuid,authority,username,password,qq_number,phone,addtype,addtime) VALUES(" +
            "#{uuid}," +
            "#{authority}," +
            "#{headuri}," +
            "#{username}," +
            "#{password}," +
            "#{qq_number}," +
            "#{phone}," +
            "#{addtype}," +
            "#{addtime})")
    void addUser(@Param("uuid") String uuid,
                 @Param("authority") String authority,
                 @Param("headuri") String headuri,
                 @Param("username") String username,
                 @Param("password") String password,
                 @Param("qq_number") String qq_number,
                 @Param("phone") String phone,
                 @Param("addtype") String addtype,
                 @Param("addtime") String addtime);
}
