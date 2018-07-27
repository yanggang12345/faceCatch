package com.nala.faceCatch.util.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**
 * Created by heshangqiu on 2017/1/18 15:18
 */

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
// pipeline.addLast("LengthFieldBasedFrameDecoder", new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN, Integer.MAX_VALUE, 0, 4, 0, 4, true));
        pipeline.addLast("nettyClientDecoder", new NettyClientDecoder());
        ch.pipeline().addLast("framedecoder",new LengthFieldBasedFrameDecoder(1024*1024*1024, 0, 4,0,4));
//        pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast("nettyMsgEncode", new NettyMsgEncoder());
        pipeline.addLast(new DelimiterBasedFrameDecoder(65535,delimiter));
        pipeline.addLast(new ClientHandler());

    }

}