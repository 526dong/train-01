package dong.utils.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Created by ${xzd} on 2018/1/8.
 * @Description
 */
public class ServerChannel {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(7777));
        while(true){
            SocketChannel socketChannel = serverSocketChannel.accept();

            //do something with socketChannel...
            if(socketChannel != null){
                //do something with socketChannel...
                if (socketChannel.isConnected()) {
                    ByteBuffer buf = ByteBuffer.allocate(48);
                    buf.clear();
                    Socket socket = socketChannel.socket();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    System.out.println("客户端发过来的消息是："+bufferedReader.readLine());
                }
            }
        }

        /*ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket socket = serverSocketChannel.socket();

        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();

        SelectionKey register = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select() == 0) {
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectionKeys.iterator();

            while (it.hasNext()) {
                SelectionKey selectionKey = it.next();
                if (selectionKey.isAcceptable()) {
                    //是否可以接受连接

                } else if (selectionKey.isConnectable()) {
                    //是否可以连接

                } else if (selectionKey.isReadable()) {
                    //是否可以读取

                } else if (selectionKey.isWritable()) {
                    //是否可以写入
                }

                it.remove();
            }
        }*/
    }
}
