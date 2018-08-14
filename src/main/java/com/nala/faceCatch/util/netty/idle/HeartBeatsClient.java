package com.nala.faceCatch.util.netty.idle;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;

import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

public class HeartBeatsClient {

    protected final HashedWheelTimer timer = new HashedWheelTimer();

    /**
     * 客户端引导 引导线程 引导可利用的管道实现（处理程序启动时逻辑，配置应用如ip port）
     */
    private Bootstrap bootstrap;

    private final ConnectorIdleStateTrigger idleStateTrigger = new ConnectorIdleStateTrigger();

    public void connect(int port, String host) throws Exception {
        //EventLoopGroup是Executor的实现类，EventLoop是EventLoopGroup的实现类
        EventLoopGroup group = new NioEventLoopGroup();

        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO));

        final ConnectionWatchdog watchdog = new ConnectionWatchdog(bootstrap, timer, port, host, true) {
            public ChannelHandler[] handlers() {
                return new ChannelHandler[]{
                        this,
                        //心跳检测handler
                        new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS),
                        //心跳状态handler
                        idleStateTrigger,
                        //心跳检测数据处理handler
                        new HeartBeatClientHandler(),
//                            new StringDecoder(CharsetUtil.UTF_8),
                        new StringEncoder(),
                        new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, 900000000,
                                4, 4, 4, 76, true),
                        //数据包处理handler
                        new ClientHandler()
                };
            }
        };

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
                        ChannelHandler[] ChannelHandlers = watchdog.handlers();
                        ch.pipeline().addLast(ChannelHandlers);
                    }
                });

                future = bootstrap.connect(host, port);
            }

            // 以下代码在synchronized同步块外面是安全的
            future.sync();
        } catch (Throwable t) {
            throw new Exception("connects to  fails", t);
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int port = 8102;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        new HeartBeatsClient().connect(port, "192.168.10.10");
    }

}
