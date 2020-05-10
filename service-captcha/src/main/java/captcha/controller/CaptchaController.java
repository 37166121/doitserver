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

    private static String AK = null;
    private static String AS = null;
    private static String SMSCODE = null;
    private static JSONObject json = new JSONObject();

    public Map<String,String> getMap(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        Map<String,String> map = new HashMap<>();
        if (session.getAttribute("map") != null){
            map = (HashMap<String,String>)session.getAttribute("map");
        }
        return map;
    }
    public void setMap(Map<String,String> map, HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.setAttribute("map", map);
        System.out.println("map.size()：" + map.size());
    }

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
            AK = captchaService.getAK();
            AS = captchaService.getAS();
            SMSCODE = captchaService.getSmsCode();
            SendSms sendSms = new SendSms(phone,getMap(request, response),request,response);
            sendSms.sendSms();
        }
        json = new JSONObject();
        json.put("msg",SUCCESS);
        return json;
    }
    public static class SendSms {
        private final String phone;
        private Map<String,String> map = new HashMap<>();
        private HttpServletRequest request;
        private HttpServletResponse response;
        SendSms(String phone,Map<String,String> map,HttpServletRequest request, HttpServletResponse response){
            this.phone = phone;
            this.map = map;
            this.request = request;
            this.response = response;
        }
        public void sendSms() {
            DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", AK, AS);
            IAcsClient client = new DefaultAcsClient(profile);
            CommonRequest request = new CommonRequest();
            String sendSms = String.format("%04d",new Random().nextInt(9999));
            request.setMethod(MethodType.POST);
            request.setDomain("dysmsapi.aliyuncs.com");
            request.setVersion("2017-05-25");
            request.setAction("SendSms");
            request.putQueryParameter("RegionId", "cn-hangzhou");
            request.putQueryParameter("PhoneNumbers", phone);
            request.putQueryParameter("SignName", "Doit");
            request.putQueryParameter("TemplateCode", SMSCODE);
            request.putQueryParameter("TemplateParam", "{\"code\":\"" + sendSms + "\"}");
            map.put(phone,sendSms);
            new CaptchaController().setMap(map,this.request,response);
            try {
                CommonResponse response = client.getCommonResponse(request);
                System.out.println(response.getData());
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
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
        Map<String,String> map = getMap(request, response);
        if (map.size() == 0){
            json.put("msg", FAILURE);
            return json;
        }
        return isCode(map.get(phone).toUpperCase(),code.toUpperCase());
    }
    private JSONObject isCode(String oldCode, String code){
        if (!oldCode.equals(code)){
            json.put("msg", FAILURE);
            return json;
        }
        json.put("msg", SUCCESS);
        return json;
    }
}
