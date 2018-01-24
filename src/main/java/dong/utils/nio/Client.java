package dong.utils.nio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Created by xzd on 2018/1/8.
 * @Description
 */
public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("启动客户端");
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 7777));
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        pw.write("客户端说：我想连接你");
        pw.close();
        socket.close();
    }
}
