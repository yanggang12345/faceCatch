package com.nala.faceCatch.util.netty.idle;

import com.nala.faceCatch.util.NumberUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * create by lizenn
 * create date 2018/8/15
 * description
 */
public class HeartBeatsOutboundHandler extends ChannelOutboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HeartBeatsOutboundHandler.class);
    private static final String HEART_MSG = "A010bb551000b01a0000";

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise){
        // write heartbeat to server
//                String heartMsg = "A010bb551000b01a0000";
        byte[] array = NumberUtil.toBytes(HEART_MSG);

        ctx.writeAndFlush(array);
        ByteBuf byteBuf = ctx.alloc().buffer(4 * HEART_MSG.length());
        byteBuf.writeBytes(array);
//        ctx.writeAndFlush(byteBuf);
        ctx.write(byteBuf);
        ctx.flush();
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
