package com.nala.faceCatch.util.netty.heart;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

/**
 * create by lizenn
 * create date 2018/8/15
 * description
 */
public class Client {

    private static Channel ch;

    private static Bootstrap bootstrap;

    public static void main(String[] args) throws Exception{

        new Client().initClient("192.168.10.10",8102);
    }


    public void initClient(String ip,Integer port) throws Exception{
        //非阻塞线程组 组内线程个数默认为 CPU核心数*2
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
            //引导  非线程安全
            bootstrap = new Bootstrap();
            bootstrap
                    .group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO));
            // 连接服务器
//            doConnect("192.168.10.10",8102);
            ChannelFuture future;
            //进行连接
            try {
                synchronized (bootstrap) {
                    /**
                     *ChannelInitializer是一个特殊的 ChannelHandler，通道被注册到
                     * EventLoop 后就会调用ChannelInitializer，并允许将 ChannelHandler
                     * 添加到CHannelPipeline；完成初始化通道后，这个特殊的 ChannelHandler
                     * 初始化器会从 ChannelPipeline 中自动删除。
                     */
                    bootstrap.handler(new ChannelInitializer<Channel>() {
                        //初始化channel
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.config().setRecvByteBufAllocator(new FixedRecvByteBufAllocator(204800));
                            ChannelPipeline pipeline = ch.pipeline();
                            //心跳检测
                            pipeline.addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS));
                            //心跳handler
                            pipeline.addLast(new HeartBeartHandler());
                            //基于长度协议解码器
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, 900000000,
                                    4, 4, 4, 76, true));
                            //业务数据handler
                            pipeline.addLast(new DataHandler());
                        }
                    });
                    //connect方法本身是线程安全的
                    future = bootstrap.connect(ip, port);
                }

                // 以下代码在synchronized同步块外面是安全的
                future.sync();
            } catch (Throwable t) {
                throw new Exception("connects to  fails", t);
            }
    }
}
