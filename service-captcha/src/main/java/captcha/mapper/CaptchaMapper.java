package captcha.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Intellij IDEA.
 *
 * @author 22510
 * @create 2019/11/12 11:51
 */
@Mapper
public interface CaptchaMapper {
    /**
     * 验证手机号是否存在
     * @param phone
     * @return
     */
    @Select("SELECT phone FROM doit_user WHERE phone = #{phone}")
    String isPhonePresence(@Param("phone") String phone);

    /**
     * 获取AccessKeyID
     * @return
     */
    @Select("SELECT AccessKeyID FROM doit_system_config")
    String getAK();

    /**
     * 获取AccessKeySecret
     * @return
     */
    @Select("SELECT AccessKeySecret FROM doit_system_config")
    String getAS();

    /**
     * 获取aliyunSMS
     * @return
     */
    @Select("SELECT aliyunSMS FROM doit_system_config")
    String getSmsCode();
}
