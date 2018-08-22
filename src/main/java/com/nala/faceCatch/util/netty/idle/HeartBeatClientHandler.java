package com.nala.faceCatch.util.netty.idle;

import com.nala.faceCatch.util.FileUtil;
import com.nala.faceCatch.util.ImageUtil;
import com.nala.faceCatch.util.OutUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledHeapByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 心跳检测handler
 */

@Sharable
public class HeartBeatClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("激活时间是：" + new Date());
        System.out.println("HeartBeatClientHandler channelActive");
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("停止时间是：" + new Date());
        System.out.println("HeartBeatClientHandler channelInactive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        if (byteBuf.readableBytes() <= 10) {
            System.out.println("HeartBeatClientHandler-read0-heartbeat");
            byte[] array = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(array);
            System.out.println("心跳检测正常！！");
        }else {
            ctx.fireChannelRead(msg);
        }
    }
}
