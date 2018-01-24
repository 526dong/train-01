package dong.utils.mynetty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Created by ${xzd} on 2018/1/8.
 * @Description
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server handler");
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        //接受并打印客户端的信息
        System.out.println("client write:"+new String(bytes));
        byteBuf.release();

        String response = "hello client";
        ByteBuf respByteBuf = ctx.alloc().buffer(4*response.length());
        respByteBuf.writeBytes(response.getBytes());
        ctx.write(respByteBuf);
        ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
