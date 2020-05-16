package socket.po;

import lombok.Data;

/**
 * RequestMessage
 * 浏览器向服务端请求的消息
 * @author 22510
 * @version 1.0
 * 2020/5/12 23:13
 **/
@Data
public class RequestMessage {
    private String name;
}
