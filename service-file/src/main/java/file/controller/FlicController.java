package file.controller;

import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.Authenticator;
import file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import static org.apache.http.auth.AuthProtocolState.FAILURE;
import static org.apache.http.auth.AuthProtocolState.SUCCESS;

/**
 * FlicController
 *
 * @author 22510
 * @version 1.0
 * 2020/5/10 4:51
 **/
@RestController
public class FlicController {
    private JSONObject jsonObject;
    public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    @Autowired
    FileService fileService;

    /**
     * 更改头像
     * @param request
     * @param response
     */
    @RequestMapping(value = "/setHead", method = RequestMethod.POST)
    public JSONObject setHead(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        getJSONObject(request, response);
        String headuri = "static\\media\\head\\" + jsonObject.getInteger("uid").toString();
        session.setAttribute("headuri",headuri);
        session.setAttribute("uid",jsonObject.getInteger("uid"));
        return jsonObject;
    }

    /**
     * 上传资源
     * @param request
     * @param response
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JSONObject uploadFile(HttpServletRequest request, HttpServletResponse response){
        getJSONObject(request, response);
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
        Date date = new Date();
        List<String> list = JSONObject.parseArray(jsonObject.getJSONArray("label").toJSONString(), String.class);
        String mediaid = formatter.format(date) + "-" + generateMixStr(7);
        HttpSession session = request.getSession();
        String uri = "static\\media\\" + jsonObject.getInteger("uid") + "\\" + mediaid;
        String videouri = uri + "\\video";
        String imageuri = uri + "\\image";
        String otheruri = uri + "\\other";
        List<File> dirs = new ArrayList<>();
        dirs.add(new File(videouri));
        dirs.add(new File(imageuri));
        dirs.add(new File(otheruri));
        File dir = new File(uri);
        session.setAttribute("dirs",dirs);
        JSONObject json = new JSONObject();
        //创建目录
        if (dir.exists()) {
            json.put("msg","创建目录" + uri + "失败，目标目录已经存在");
            return json;
        } else {
            if (!dir.mkdirs()) {
                json.put("msg","创建目录" + uri + "失败");
                return json;
            }
            for (File dir1 : dirs){
                if (!dir1.mkdirs()) {
                    json.put("msg","创建目录" + uri + "失败");
                    return json;
                }
                System.out.println("创建目录" + dir1.getPath() + "成功");
            }
        }
        fileService.uploadFile(
                jsonObject.getInteger("uid"),
                mediaid,
                videouri,
                imageuri,
                otheruri,
                listToString(list,','),
                jsonObject.getString("title"),
                jsonObject.getString("value"),
                jsonObject.getBoolean("ispublic"),
                0,
                0,
                0,
                0,
                0,
                date);
        return jsonObject;
    }

    /**
     * 获取文件流
     * @param request
     * @param response
     */
    @RequestMapping(value = "/saveFile/head", method = RequestMethod.POST)
    private void getFileStream(HttpServletRequest request, HttpServletResponse response) {
        try {
            InputStream is = request.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            HttpSession session = request.getSession();
            String result = "";
            result = saveFile(dis,session.getAttribute("headuri").toString(),(int)session.getAttribute("uid"));
            request.getSession().invalidate();
            response.setContentType("text/html;charset=UTF-8");
            ObjectOutputStream dos = new ObjectOutputStream(response.getOutputStream());
            dos.writeObject(result);
            dos.flush();
            dos.close();
            dis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存头像
     * @param dis
     * @return
     */
    private String saveFile(DataInputStream dis,String uri,int uid){
        File file = new File(uri);
        file.exists();
        file.mkdirs();
        try {
            uri += "\\" + generateMixStr(6) + ".jpg";
            file = new File(uri);
            if (file.createNewFile()){
                fileService.setHead(uri,uid);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fps = null;
        try {
//            System.out.println(file.getCanonicalPath());
            fps = new FileOutputStream(file);
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            while ((dis.read(buffer)) != -1) {
                fps.write(buffer);
            }
            fps.flush();
            fps.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SUCCESS.toString();
    }

    /**
     * 读取 InputStream 到 String字符串中
     */
    public static String readStream(InputStream in) {
        try {
            //<1>创建字节数组输出流，用来输出读取到的内容
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //<2>创建缓存大小
            byte[] buffer = new byte[1024]; // 1KB
            //每次读取到内容的长度
            int len = -1;
            //<3>开始读取输入流中的内容
            while ((len = in.read(buffer)) != -1) { //当等于-1说明没有数据可以读取了
                baos.write(buffer, 0, len);   //把读取到的内容写到输出流中
            }
            //<4> 把字节数组转换为字符串
            String content = baos.toString();
            //<5>关闭输入流和输出流
            in.close();
            baos.close();
            //<6>返回字符串结果
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return  e.getMessage();
        }
    }

    /**
     * 返回一个定长的随机纯字母字符串(只包含大小写字母)
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String generateMixStr(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(letterChar.charAt(random.nextInt(letterChar.length())));
        }
        return sb.toString();
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

    /**
     * List转String
     * @param list
     * @param separator
     * @return
     */
    public String listToString(List list, char separator) {
        return org.apache.commons.lang.StringUtils.join(list.toArray(),separator);
    }
}