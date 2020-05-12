package user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import user.po.UserPO;
import user.service.UserService;
import user.util.MD5Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * UserController
 *
 * @author 22510
 * @version 1.0
 * 2020/5/9 18:08
 **/
@RestController
public class UserController {
    MD5Util md5Util = new MD5Util();
    JSONObject jsonObject = new JSONObject();
    @Autowired
    UserService userService;

    /**
     * 获取QQ号
     */
    @RequestMapping(value = "/getQqNumberList")
    public JSONObject getQqNumber(){
        List<String> qqNumberList = new ArrayList<>();
        qqNumberList = userService.getQqNumber();
        jsonObject = new JSONObject();
        jsonObject.put("qqNumberList",JSONArray.parseArray(JSON.toJSONString(qqNumberList)));
        return jsonObject;
    }

    /**
     * 登录
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject login(HttpServletRequest request, HttpServletResponse response){
        getJSONObject(request, response);
        //更改APP上次登录的时间戳、上一次登录的APP版本号、APP上次登录的IP

        //对比设备ID

        return jsonObject;
    }

    /**
     * 获取用户信息
     */
    @RequestMapping(value = "/getUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject getUser(HttpServletRequest request, HttpServletResponse response){
        getJSONObject(request, response);
        UserPO userPo;
        userPo = userService.getUser(jsonObject.getString("phone"),md5Util.crypt(jsonObject.getString("pwd")));
        if (userPo != null){
            userPo.setPassword("");
            userPo.setAttentionUser(userService.getAttentionUser(userPo.getUid()));
            userPo.setAttentionLabel(userService.getAttentionLabel(userPo.getUid()));
        }
        jsonObject = new JSONObject();
        jsonObject.put("userinfo",userPo);
        return jsonObject;
    }

    /**
     * 鉴权
     * @return
     */
    public boolean isAdmin(int uid){
        return userService.isAdmin(uid);
    }

    /**
     * 获取所有用户信息
     * 仅管理员访问
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getAllUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject getAllUser(HttpServletRequest request, HttpServletResponse response){
        getJSONObject(request, response);
        if (isAdmin(jsonObject.getInteger("uid"))){
            List<UserPO> userPoList = userService.getAllUser(jsonObject.getInteger("uid"));
            jsonObject = new JSONObject();
            jsonObject.put("userinfoList",userPoList);
        }
        return jsonObject;
    }

    /**
     * 更改密码
     */
    @RequestMapping(value = "/setPassword", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public void setPassword(HttpServletRequest request, HttpServletResponse response){
        getJSONObject(request, response);
        userService.setPassword(jsonObject.getString("password"),jsonObject.getInteger("uid"));
    }

    /**
     * 用户注册
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject addUser(HttpServletRequest request, HttpServletResponse response){
        getJSONObject(request, response);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //头像uri：#{uid}/head/XXXXXXX.jpg
        String headuri = "static\\media\\" + jsonObject.getInteger("uid") + "\\head";
        File dir = new File(headuri);
        JSONObject json = new JSONObject();
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                json.put("msg","创建目录" + dir.getPath() + "失败");
                return json;
            }
            System.out.println("创建目录" + dir.getPath() + "成功");
            return json;
        } else {
            json.put("msg","创建目录" + dir.getPath() + "失败，目标目录已经存在");
        }
        userService.addUser(jsonObject.getString("uuid"),
                "user",
                headuri,
                jsonObject.getString("username"),
                md5Util.crypt(jsonObject.getString("password")),
                jsonObject.getString("qqNumber"),
                jsonObject.getString("phone"),
                jsonObject.getString("addtype"),
                formatter.format(date));
        return json;
    }
    /**
     * 解析客户端JSON
     * @param request
     * @param response
     * @return
     */
    private void getJSONObject(HttpServletRequest request, HttpServletResponse response){
        String json = null;
        try {
            InputStreamReader reader = new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8);
            char [] buff=new char[1024];
            int length = 0;
            while((length=reader.read(buff)) != -1){
                json = new String(buff,0,length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        jsonObject = JSONObject.parseObject(json);
    }
}