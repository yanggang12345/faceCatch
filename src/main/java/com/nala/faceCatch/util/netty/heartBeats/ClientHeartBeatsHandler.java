package com.nala.faceCatch.util.netty.heartBeats;


import com.nala.faceCatch.service.NettyClient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;


/**
 * create by lizenn
 * create date 2018/8/8
 * description
 */
public abstract class ClientHeartBeatsHandler extends SimpleChannelInboundHandler<ByteBuf> {

    public static final byte PING_MSG = 1;
    public static final byte PONG_MSG = 2;
    public static final byte CUSTOM_MSG = 3;
    protected String name;
    private int heartbeatCount = 0;

    public ClientHeartBeatsHandler(String name) {
        this.name = name;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, ByteBuf byteBuf){
        if (byteBuf.getByte(4) == PING_MSG) {
            sendPingMsg(context);
        } else if (byteBuf.getByte(4) == PONG_MSG){
            System.out.println(name + " get pong msg from " + context.channel().remoteAddress());
        } else {
            handleData(context, byteBuf);
        }
    }

    protected void sendPingMsg(ChannelHandlerContext context) {
        ByteBuf buf = context.alloc().buffer(10);
        //心跳命令
        byte[] ping = new byte[10];
        ping[0] = (byte) 0xA0;
        ping[1] = (byte) 0x10;
        ping[2] = (byte) 0xbb;
        ping[3] = (byte) 0x55;
        ping[4] = (byte) 0x10;
        ping[5] = (byte) 0x0;
        ping[6] = (byte) 0xb0;
        ping[7] = (byte) 0x1a;
        ping[8] = (byte) 0x0;
        ping[9] = (byte) 0x0;

        buf.readBytes(ping);
        context.writeAndFlush(buf);
        heartbeatCount++;
        System.out.println(name + " sent ping msg to " + context.channel().remoteAddress() + ", count: " + heartbeatCount);
    }

//    private void sendPongMsg(ChannelHandlerContext context) {
//        ByteBuf buf = context.alloc().buffer(5);
//        buf.writeInt(5);
//        buf.writeByte(PONG_MSG);
//        context.channel().writeAndFlush(buf);
//        heartbeatCount++;
//        System.out.println(name + " sent pong msg to " + context.channel().remoteAddress() + ", count: " + heartbeatCount);
//    }

    protected abstract void handleData(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // IdleStateHandler 所产生的 IdleStateEvent 的处理逻辑.
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    handleReaderIdle(ctx);
                    sendPingMsg(ctx);
                    break;
                case WRITER_IDLE:
                    handleWriterIdle(ctx);
                    break;
                case ALL_IDLE:
                    handleAllIdle(ctx);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("---" + ctx.channel().remoteAddress() + " is active---");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("---" + ctx.channel().remoteAddress() + " is inactive---");
        NettyClient client = new NettyClient();
        //断线重连
        client.connect("192.168.10.10",8102);
    }

    protected void handleReaderIdle(ChannelHandlerContext ctx) {

        System.err.println("---READER_IDLE---");
    }

    protected void handleWriterIdle(ChannelHandlerContext ctx) {
        System.err.println("---WRITER_IDLE---");
    }

    protected void handleAllIdle(ChannelHandlerContext ctx) {
        System.err.println("---ALL_IDLE---");
    }


}
