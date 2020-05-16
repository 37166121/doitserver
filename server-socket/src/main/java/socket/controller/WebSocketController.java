package socket.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import socket.po.ResponseMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebSocketController
 *
 * @author 22510
 * @version 1.0
 * 2020/5/12 19:20
 **/
@RestController
public class WebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    // 收到消息记数
    private AtomicInteger count = new AtomicInteger(0);
//    @MessageMapping(GlobalConsts.HELLO_MAPPING)
//    @SendTo(GlobalConsts.TOPIC)
//    public ServerMessage greeting(ClientMessage message) throws Exception {
//        // 模拟延时
//        Thread.sleep(1000);
//        return new ServerMessage("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
//    }
    @RequestMapping(value="/broadcast/index")
    public String broadcastIndex(HttpServletRequest req){
        System.out.println(req.getRemoteHost());
        return "websocket/simple/ws-broadcast";
    }
    @MessageMapping("/receive")
    @SendTo("/topic/getResponse")
    public ResponseMessage  broadcast(RequestMapping requestMessage){
        logger.info("receive message = {}" , JSONObject.toJSONString(requestMessage));
        ResponseMessage responseMessage = new ResponseMessage ();
        responseMessage.setResponseMessage("BroadcastCtl receive [" + count.incrementAndGet() + "] records");
        return responseMessage;
    }
}