package com.nala.faceCatch.clientAlpha;

/**
 * create by lizenn
 * create date 2018/7/24
 * description
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientAlpha {

    public void connect(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new AlphaHandler());
                }
            });

            // Start the client.
//            ChannelFuture f = b.connect(host, port).sync();
            b.connect(host, port).sync();
            // Wait until the connection is closed.
//            f.channel().closeFuture().sync();
        } finally {
//            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        ClientAlpha client = new ClientAlpha();
        client.connect("192.168.10.10", 8102);
    }
}

