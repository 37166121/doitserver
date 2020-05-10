package captcha.service.impl;

import captcha.mapper.CaptchaMapper;
import captcha.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CaptchaServiceImpl
 *
 * @author 22510
 * @version 1.0
 * 2020/5/10 1:26
 **/
@Service
@Transactional
public class CaptchaServiceImpl implements CaptchaService {
    @Autowired
    CaptchaMapper captchaMapper;
    @Override
    public boolean isPhonePresence(String phone) {
        return captchaMapper.isPhonePresence(phone) == null;
    }

    @Override
    public String getAK() {
        return captchaMapper.getAK();
    }

    @Override
    public String getAS() {
        return captchaMapper.getAS();
    }

    @Override
    public String getSmsCode() {
        return captchaMapper.getSmsCode();
    }
}
