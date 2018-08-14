package com.nala.faceCatch.util.netty.idle;

import com.nala.faceCatch.util.NumberUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

/**
 * 心跳状态检测handler
 */

@Sharable
public class ConnectorIdleStateTrigger extends ChannelInboundHandlerAdapter {

    private static ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("0xA00x100xbb0x550x100x00xb00x1a0x00x0",
            CharsetUtil.UTF_8));

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                System.out.println("state--->" + state);
                // write heartbeat to server
                String heartMsg = "A010bb551000b01a0000";
                byte[] array = NumberUtil.toBytes(heartMsg);

//                ctx.writeAndFlush(array);
                ByteBuf byteBuf = ctx.alloc().buffer(4 * heartMsg.length());
                byteBuf.writeBytes(array);
                ctx.write(byteBuf);
                ctx.flush();

            }

        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
