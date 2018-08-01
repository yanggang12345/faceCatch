package com.nala.faceCatch.util.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**
 * Created by heshangqiu on 2017/1/18 15:18
 * update
 */

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
//        ch.config().setReceiveBufferSize(150000);
        //bytebuf缓冲区默认大小为1024字节，为一次性获取全部包数据故将缓冲区大小调为204800
        ch.config().setRecvByteBufAllocator(new FixedRecvByteBufAllocator(204800));
        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
//        pipeline.addLast("LengthFieldBasedFrameDecoder", new LengthFieldBasedFrameDecoder(ByteOrder.BIG_ENDIAN, Integer.MAX_VALUE, 0, 4, 0, 4, true));
        pipeline.addLast("nettyClientDecoder", new NettyClientDecoder());
        ch.pipeline().addLast("framedecoder",new LengthFieldBasedFrameDecoder(1024*1024*1024, 0, 4,0,4));
//        pipeline.addLast("stringDecoder", new StringDecoder(CharsetUtil.UTF_8));
        ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
        pipeline.addLast("nettyMsgEncode", new NettyMsgEncoder());
//        pipeline.addLast(new DelimiterBasedFrameDecoder(65535,delimiter));
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(1024*100, Delimiters.lineDelimiter()));
        pipeline.addLast(new ClientHandler());

    }

}