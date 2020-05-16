package socket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SocketServer
 *
 * @author 22510
 * @version 1.0
 * 2020/5/12 17:53
 **/
@SpringBootApplication
public class DoitWebSocketServer {
    public static void main(String[] args) {
        SpringApplication.run(DoitWebSocketServer.class,args);
    }
}