package dong.utils.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Created by xzd on 2018/1/9.
 * @Description
 */
public class ClientDemo {
    public static void main(String[] args) throws Exception {
        client();
    }

    public static void zhanClient() {

    }

    public static void myClient() throws IOException {
        SocketChannel socketChannel = null;

        try {
            socketChannel = SocketChannel.open();
            //将通道设置为非阻塞形式
            socketChannel.configureBlocking(false);
            //设置连接端口
            socketChannel.connect(new InetSocketAddress(7777));
            //创建多路复用选择器
            Selector selector = Selector.open();
            //通道绑定选择器中的连接属性
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            while (true) {
                if (selector.select() > 0) {
                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();
                        SocketChannel channel = (SocketChannel) key.channel();
                        //判断是否连接
                        if (key.isConnectable()) {
                            channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, new Integer(1));
                            channel.finishConnect();
                        }
                        //向服务器发出请求/或写入数据
                        if (key.isWritable()) {
                            key.attach(new Integer(1));
                            channel.write(ByteBuffer.wrap("哈哈，我来了。。。。".getBytes()));
                        }
                        //从服务器读取数据
                        if (key.isReadable()) {
                            key.attach(new Integer(1));
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            //设置1024的缓冲区
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            int len = 0;
                            while ((len = channel.read(byteBuffer)) != 0) {
                                byteBuffer.flip();
                                byte[] bytes = new byte[byteBuffer.remaining()];
                                byteBuffer.get(bytes);
                                out.write(bytes);
                                byteBuffer.clear();
                            }
                            System.out.println(new String(out.toByteArray()));
                            out.close();
                        }
                        channel.close();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socketChannel.close();
        }


    }

    public static void client() {
        SocketChannel channel=null;
        try {
            Selector selector=Selector.open();
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(8020));
            channel.register(selector, SelectionKey.OP_CONNECT);
            while(true){
                if(selector.select()>0){
                    Iterator<SelectionKey> set=selector.selectedKeys().iterator();
                    while(set.hasNext()){
                        SelectionKey key=set.next();
                        set.remove();

                        SocketChannel ch=(SocketChannel) key.channel();
                        if(key.isConnectable()){
                            ch.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE,new Integer(1));
                            ch.finishConnect();
                        }

                        if(key.isReadable()){
                            key.attach(new Integer(1));
                            ByteArrayOutputStream output=new ByteArrayOutputStream();
                            ByteBuffer buffer=ByteBuffer.allocate(1024);
                            int len=0;
                            while((len=ch.read(buffer))!=0){
                                buffer.flip();
                                byte by[]=new byte[buffer.remaining()];
                                buffer.get(by);
                                output.write(by);
                                buffer.clear();
                            }
                            System.out.println(new String(output.toByteArray()));
                            output.close();
                        }

                        if(key.isWritable()){
                            key.attach(new Integer(1));
                            ch.write(ByteBuffer.wrap((("client say:hi")).getBytes()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class ClientRunnable implements Runnable{
        private SocketChannel ch;
        private ClientRunnable(SocketChannel ch){
            this.ch=ch;
        }

        @Override
        public void run() {
            try {
                while(true){
                    ch.write(ByteBuffer.wrap((("client say:hi")).getBytes()));
                    Thread.sleep(5000);
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    ch.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


}
