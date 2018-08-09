package com.nala.faceCatch.service;

/**
 * Created by heshangqiu on 2016/12/28 9:56
 */

import com.nala.faceCatch.util.netty.ClientHandler;
import com.nala.faceCatch.util.netty.NettyClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

public class NettyClient {

//    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    private  NioEventLoopGroup workGroup = new NioEventLoopGroup(4);

    private   Channel channel;

    private   Bootstrap bootstrap;

    public void initClient(){
        bootstrap = new Bootstrap();
        bootstrap.group(workGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new NettyClientInitializer());
        connect("192.168.10.10", 8102);
    }

    public void connect(String host, int port){

        if(channel != null && channel.isActive()){return;}
            ChannelFuture future = bootstrap.connect(host, port);
            channel = future.channel();
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){
                        channel = channelFuture.channel();
                        System.out.println("Connect to server successfully!");
                    }else {
                        System.out.println("Failed to connect to server, try connect after 10s");
                        channelFuture.channel().eventLoop().schedule(new Runnable() {
                            @Override
                            public void run() {
                                connect("192.168.10.10",8102);
                            }
                        },5, TimeUnit.SECONDS);
                    }
                }
            });
    }

    public static void main(String[] args) throws Exception {
        NettyClient client = new NettyClient();
        client.initClient();
    }
}

