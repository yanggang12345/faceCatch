package com.nala.faceCatch.util.netty.idle;

import com.nala.faceCatch.util.NumberUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 心跳状态检测handler
 */

@Sharable
public class ConnectorIdleStateTrigger extends ChannelInboundHandlerAdapter {
    //心跳命令
    private static final String HEART_MSG = "A010bb551000b01a0000";
//    private static final String HEART_MSG = "A0";

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                System.out.println("state--->" + state);

                // write heartbeat to server
//                String heartMsg = "A010bb551000b01a0000";
                byte[] array = NumberUtil.toBytes(HEART_MSG);
//                byte[] array = HEART_MSG.getBytes();

                ctx.writeAndFlush(array);
                ByteBuf byteBuf = ctx.alloc().buffer(4 * HEART_MSG.length());
                byteBuf.writeBytes(array);
                ctx.writeAndFlush(byteBuf);
//                ctx.write(byteBuf);
//                ctx.flush();
            }

        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
