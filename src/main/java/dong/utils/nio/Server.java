package dong.utils.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Created by ${xzd} on 2018/1/8.
 * @Description
 */
public class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("启动服务器端");
        ServerSocket serverSocket = new ServerSocket(7777);
        Socket accept = serverSocket.accept();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
        System.out.println("客户端发过来的消息是："+bufferedReader.readLine());
        serverSocket.close();
        accept.close();
        bufferedReader.close();
    }
}
