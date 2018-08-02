package com.nala.faceCatch.util.netty;

/**
 * Created by heshangqiu on 2016/12/28 9:55
 */

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends SimpleChannelInboundHandler<String> {
    private static Logger logger = LoggerFactory
            .getLogger(ClientHandler.class);


    private Channel channel;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {


    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("接收的数据::"+(String)msg);

    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        channel = ctx.channel();
    }


    @Override
         public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

