package dong.utils.mynetty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Created by ${xzd} on 2018/1/8.
 * @Description
 */
public class MyClientHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf info = (ByteBuf) msg;
        byte[] bytes = new byte[info.readableBytes()];
        info.readBytes(bytes);
        System.out.println("server write:"+new String(bytes));
        info.release();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg = "hello server";
        ByteBuf buffer = ctx.alloc().buffer(4 * msg.length());
        buffer.writeBytes(msg.getBytes());
        ctx.write(buffer);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
