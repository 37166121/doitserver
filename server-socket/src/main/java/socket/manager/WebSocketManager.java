package socket.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocketManager
 * socket管理器
 * @author 22510
 * @version 1.0
 * 2020/5/23 2:07
 **/
@Slf4j
public class WebSocketManager {
    private static ConcurrentHashMap<String, WebSocketSession> manager = new ConcurrentHashMap<String, WebSocketSession>();

    public static void add(String key, WebSocketSession webSocketSession) {
        log.info("新添加webSocket连接 {} ", key);
        manager.put(key, webSocketSession);
    }

    public static void remove(String key) {
        log.info("移除webSocket连接 {} ", key);
        manager.remove(key);
    }

    public static WebSocketSession get(String key) {
        log.info("获取webSocket连接 {}", key);
        return manager.get(key);
    }
}
