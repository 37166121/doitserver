package captcha.service;

import org.apache.ibatis.annotations.Select;

/**
 * CaptchaService
 *
 * @author 22510
 * @version 1.0
 * 2020/5/10 1:25
 **/
public interface CaptchaService {
    /**
     * 验证手机号是否存在
     * @param phone
     * @return
     */
    boolean isPhonePresence(String phone);

    /**
     * 获取AccessKeyID
     * @return
     */
    String getAK();

    /**
     * 获取AccessKeySecret
     * @return
     */
    String getAS();

    /**
     * 获取aliyunSMS
     * @return
     */
    String getSmsCode();
}
