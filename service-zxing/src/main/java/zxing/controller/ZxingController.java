package zxing.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zxing.uitl.ZxingUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * ZxingController
 *
 * @author 22510
 * @version 1.0
 * 2020/5/11 6:42
 **/
@RestController
public class ZxingController {
    @RequestMapping(value = "/getZxing/{content}")
    public void getZxing(@PathVariable String content, @RequestParam(defaultValue = "300", required = false) int width, @RequestParam(defaultValue = "300", required = false) int height, HttpServletResponse response){
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ZxingUtil.writeToStream(content, outputStream, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
