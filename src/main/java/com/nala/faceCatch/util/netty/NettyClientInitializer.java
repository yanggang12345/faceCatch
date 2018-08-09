package com.nala.faceCatch.util.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

/**
 * Created by heshangqiu on 2017/1/18 15:18
 * update by lizenn
 */

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
//        ch.config().setReceiveBufferSize(150000);
        //bytebuf缓冲区默认大小为1024字节，为一次性获取全部包数据故将缓冲区大小调为204800
        ch.config().setRecvByteBufAllocator(new FixedRecvByteBufAllocator(204800));

        pipeline.addLast(new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, 900000000, 4, 4, 4, 76, true));
//        pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
//        pipeline.addLast(new NettyMsgEncoder());

        //设定IdleStateHandler心跳检测，5秒进行一次读检测
        pipeline.addLast(new IdleStateHandler(5,0,0, TimeUnit.SECONDS));
        pipeline.addLast(new ClientHandler());

    }

}