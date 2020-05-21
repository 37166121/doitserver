package captcha.controller;

import captcha.service.CaptchaService;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static org.apache.http.auth.AuthProtocolState.FAILURE;
import static org.apache.http.auth.AuthProtocolState.SUCCESS;

/**
 * CaptchaController
 *
 * @author 22510
 * @version 1.0
 * 2020/5/9 17:47
 **/
@RestController
public class CaptchaController {
    @Autowired
    CaptchaService captchaService;
    @Autowired
    StringRedisTemplate redisTemplate;

    private static JSONObject json = new JSONObject();

    /**
     * 获取短信验证码
     * @param phone
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/sendSms/{phone}")
    private JSONObject getCaptcha(@PathVariable String phone, HttpServletRequest request, HttpServletResponse response){
        if (captchaService.isPhonePresence(phone)) {
            SendSms sendSms = new SendSms(redisTemplate,captchaService,phone,request,response);
            sendSms.sendSms();
        }
        json = new JSONObject();
        json.put("msg",SUCCESS);
        return json;
    }

    /**
     * 实现获取短信验证码并保存到redis
     */
    public static class SendSms {
        private final String phone;
        private HttpServletRequest request;
        private HttpServletResponse response;
        private CaptchaService captchaService;
        private StringRedisTemplate redisTemplate;
        SendSms(StringRedisTemplate redisTemplate,CaptchaService captchaService,String phone,
                HttpServletRequest request, HttpServletResponse response){
            this.redisTemplate = redisTemplate;
            this.captchaService = captchaService;
            this.phone = phone;
            this.request = request;
            this.response = response;
        }
        public void sendSms() {
            DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", captchaService.getAK(), captchaService.getAS());
            IAcsClient client = new DefaultAcsClient(profile);
            CommonRequest request = new CommonRequest();
            String sendSms = String.format("%04d",new Random().nextInt(9999));
                String smsCode = captchaService.getSmsCode();
            request.setMethod(MethodType.POST);
            request.setDomain("dysmsapi.aliyuncs.com");
            request.setVersion("2017-05-25");
            request.setAction("SendSms");
            request.putQueryParameter("RegionId", "cn-hangzhou");
            request.putQueryParameter("PhoneNumbers", phone);
            request.putQueryParameter("SignName", "Doit");
            request.putQueryParameter("TemplateCode", smsCode);
            request.putQueryParameter("TemplateParam", "{\"code\":\"" + sendSms + "\"}");
            redisTemplate.opsForValue().set(phone, sendSms);
            try {
                CommonResponse response = client.getCommonResponse(request);
                System.out.println(response.getData());
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("set/{key}/{value}")
    public String set(@PathVariable("key")String key,@PathVariable("value") String value) {
        //注意这里的 key不能为null spring 内部有检验
        redisTemplate.opsForValue().set(key, value);
        return key + "," + value;
    }

    @RequestMapping("get/{key}")
    public String get(@PathVariable("key") String key) {

        return "key=" + key + ",value=" + redisTemplate.opsForValue().get(key);
    }

    /**
     * 验证APP发送的验证码是否正确
     * @param code 接收APP验证码
     * @param request
     * @param response
     * @return boolean
     */
    @RequestMapping(value="/isCaptcha/{phone}/{code}")
    public JSONObject isCaptcha(@PathVariable String code, @PathVariable String phone,
                                HttpServletRequest request, HttpServletResponse response){
        return isCode(Objects.requireNonNull(redisTemplate.opsForValue().get(phone)),code,phone);
    }
    private JSONObject isCode(String oldCode, String code,String phone){
        if (!oldCode.equals(code)){
            json.put("msg", FAILURE);
            return json;
        }
        json.put("msg", SUCCESS);
        redisTemplate.delete(phone);
        return json;
    }
}
