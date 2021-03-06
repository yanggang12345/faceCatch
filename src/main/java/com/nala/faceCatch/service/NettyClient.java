package com.nala.faceCatch.service;

/**
 * Created by heshangqiu on 2016/12/28 9:56
 */

import com.nala.faceCatch.util.netty.idle.ClientHandler;
import com.nala.faceCatch.util.netty.NettyClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {


    public void connect(String host, int port) throws Exception {

        // 创建EventLoopGroup，提供用于处理Channel事件的EventLoop
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        try {


            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
//            bootstrap.handler(new HeartBeatsClientInitializer());
            // 设置用于处理Channel数据和事件的ChannelInboundHandler
            bootstrap.handler(new NettyClientInitializer());
            // Start the client.
            ChannelFuture f = bootstrap.connect(host, port).sync();
            Channel c = f.channel();
            ClientHandler clientHandler = c.pipeline().get(ClientHandler.class);

//            clientHandler.sendRequest();
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
            System.out.println("客户端释放了资源>>>>>>>>>>>>>>>>>");
        }

    }

    private void reConnetServer() {

    }

    public static void main(String[] args) throws Exception {
        NettyClient client = new NettyClient();
        client.connect("192.168.10.11", 8102);
    }
}

