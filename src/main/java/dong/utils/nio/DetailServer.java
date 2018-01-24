package dong.utils.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Created by ${xzd} on 2018/1/13.
 * @Description
 */
public class DetailServer {
    public static void main(String[] args) throws Exception {
        server();
    }

    private static void server() throws Exception {
        //ServerSocketChannel通道引用
        ServerSocketChannel channel=null;

        try {
            //创建selector
            Selector selector=Selector.open();
            //open方法来打开一个未绑定的ServerSocketChannel实例
            channel = ServerSocketChannel.open();
            //把channel设置成非阻塞
            channel.configureBlocking(false);
            //timewait状态时，就可复用其端口,对于大量连接且经常有timewait时适用
            channel.socket().setReuseAddress(true);
            //与端口绑定
            channel.bind(new InetSocketAddress(8082));

            //在SelectionKey对象的有效期间，Selector会一直监控与SelectionKey对象相关的事件，如果事件发生，就会把SelectionKey对象加入到selected-keys集合中
            //将selector对象绑定到监听信道上,并在注册过程中指出该信道进行OP_ACCEPT操作
            channel.register(selector, SelectionKey.OP_ACCEPT,new Integer(1));
            /*SelectionKey.OP_ACCEPT —— 接收连接继续事件，表示服务器监听到了客户连接，服务器可以接收这个连接了
            SelectionKey.OP_CONNECT —— 连接就绪事件，表示客户与服务器的连接已经建立成功
            SelectionKey.OP_READ —— 读就绪事件，表示通道中已经有了可读的数据，可以执行读操作了（通道目前有数据，可以进行读操作了）
            SelectionKey.OP_WRITE —— 写就绪事件，表示已经可以向通道写数据了（通道目前可以用于写操作）*/

            //反复循环,等待IO
            while(true){
                //select方法会阻塞等待，直到至少有一个注册信道中有感兴趣的操作准备就绪,返回值大于0,表明有一个或更多个通道就绪了
                if(selector.select()>0){
                    //将就绪的对象放到set集合中
                    Set<SelectionKey> sets=selector.selectedKeys();
                    //通过迭代器遍历,依次处理selector上的每个已选择的SelectionKey
                    Iterator<SelectionKey> keys=sets.iterator();
                    while(keys.hasNext()){
                        //获取具体的selectionkey
                        SelectionKey key=keys.next();
                        //Selector不会自己从已选择键集中移除SelectionKey实例,必须在处理完通道时自己移除。
                        //下次该通道变成就绪时，Selector会再次将其放入已选择键集中。
                        keys.remove();

                        //selectionKey对应的通道是包含客户端的连接请求
                        if(key.isAcceptable()){
                            key.attach(new Integer(1));
                            //调用accept方法接受连接，产生服务器端对应的SocketChannel
                            SocketChannel socketChannel=((ServerSocketChannel) key.channel()).accept();
                            //同样设置为非阻塞
                            socketChannel.configureBlocking(false);
                            //同样将socketChannel注册到selector上,socketChannel设置成准备接受其他请求
                            socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
                        }
                        //selectionKey对应的通道有数据需要读取
                        if(key.isReadable()){
                            //获取该SelectionKey对应的Channel，该Channel中有可读的数据
                            SocketChannel socketChannel=(SocketChannel) key.channel();
                            //定义准备执行读取数据的ByteBuffer,NIO通信是基于Buffer块的
                            ByteBuffer buf=ByteBuffer.allocate(1024);
                            ByteArrayOutputStream output=new ByteArrayOutputStream();
                            int len=0;
                            while((len=socketChannel.read(buf))!=0){
                                buf.flip();
                                //remaining()返回剩余的可用长度，此长度为实际读取的数据长度
                                byte[] b=new byte[buf.remaining()];
                                buf.get(b);
                                output.write(b);
                                buf.clear();
                            }
                            String str=new String(output.toByteArray());
                            key.attach(str);

                        }
                        //selectionKey对应的通道是否可以写入数据
                        if(key.isWritable()){
                            Object object=key.attachment();
                            String attach=object!=null?"server replay: "+object.toString():"server replay: ";
                            //获取该SelectionKey对应的Channel，在该Channel中写入数据
                            SocketChannel socketChannel=(SocketChannel) key.channel();
                            socketChannel.write(ByteBuffer.wrap(attach.getBytes()));
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(channel!=null){
                try {
                    //关闭通道
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
