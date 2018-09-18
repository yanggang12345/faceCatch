package com.nala.faceCatch.util.netty.heart;

import com.nala.faceCatch.util.NumberUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * create by lizenn
 * create date 2018/8/15
 * description 心跳检测处理器
 */
@Component
public class HeartBeartHandler extends ChannelInboundHandlerAdapter {

    /**
     * 心跳命令
     */
    private static final String HEART_MSG = "A010bb551000b01a0000";

    /**
     * 心跳包头
     */
    private static final int heartHead = -1609516203;

    /**
     * 数据包头
     */
    private static final int dataHead = -1592738987;

    @Autowired
    private Client client;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("--- Server is active ---");
        //todo......
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("--- Server is inactive ---");
        //todo......
        // 10s 之后尝试重新连接服务器
        System.out.println("10s 之后尝试重新连接服务器...");
        Thread.sleep(10 * 1000);
        client.initClient("192.168.10.11",8102);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        //标记读指针位置
        byteBuf.markReaderIndex();
        int head = byteBuf.readInt();
        //只有心跳包
        if (head == heartHead) {
            System.out.println("HeartBeatClientHandler-read0-heartbeat");
            byte[] array = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(array);
            System.out.println("心跳检测正常！！");
            byteBuf.discardReadBytes();
            System.out.println("远程摄像机运转正常！！");
        }
        //数据包在前
        if(byteBuf.writerIndex() != 0){
            //重置读指针位置
            byteBuf.resetReaderIndex();
            //进入下一个handler
            ctx.fireChannelRead(byteBuf);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            //如果10秒钟读空闲，发送心跳命令
            if (state == IdleState.READER_IDLE) {
                System.out.println("state--->" + state);
                sendHeartbeatPacket(ctx);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("连接出现异常");
        System.out.println("10s 之后尝试重新连接服务器...");
        Thread.sleep(10 * 1000);
        new Client().initClient("192.168.10.11",8102);
    }

    /**
     * 发送心跳包
     * @param ctx
     */
    private void sendHeartbeatPacket(ChannelHandlerContext ctx) {
        // write heartbeat to server
      String heartMsg = "A010bb551000b01a0000";
        byte[] array = NumberUtil.toBytes(heartMsg);
//      byte[] array = HEART_MSG.getBytes();
//        ctx.writeAndFlush(array);
        ByteBuf byteBuf = ctx.alloc().buffer(4 * heartMsg.length());
        byteBuf.writeBytes(array);
        //写并冲刷到远端服务器
        ctx.writeAndFlush(byteBuf);
//      ctx.write(byteBuf);
//      ctx.flush();
    }
}
