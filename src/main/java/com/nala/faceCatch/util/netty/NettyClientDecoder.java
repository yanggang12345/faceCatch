package com.nala.faceCatch.util.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**
 * Created by heshangqiu on 2017/3/30 16:08
 * update by lizenn
 */
public class NettyClientDecoder extends LengthFieldBasedFrameDecoder {
    /**
     * @param byteOrder
     * @param maxFrameLength      字节最大长度,大于此长度则抛出异常
     * @param lengthFieldOffset   开始计算长度位置,这里使用0代表放置到最开始
     * @param lengthFieldLength   描述长度所用字节数
     * @param lengthAdjustment    长度补偿,这里由于命令码使用2个字节.需要将原来长度计算加2
     * @param initialBytesToStrip 开始计算长度需要跳过的字节数
     * @param failFast
     */
    public NettyClientDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset,
                              int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip,
                              boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength,
                lengthAdjustment, initialBytesToStrip, failFast);
    }

//    public NettyClientDecoder() {
//        this(ByteOrder.LITTLE_ENDIAN, 1000000000, 4, 4, 4, 76, true);
//    }

    /**
     * 根据构造方法自动处理粘包,半包.然后调用此decode
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {

        int buflen = byteBuf.readableBytes();
        byte[] bufArray = new byte[buflen];
        byteBuf.readBytes(bufArray);

        super.decode(ctx,byteBuf);

        int len =byteBuf.readableBytes();
        byte[] array = new byte[len];
        byteBuf.readBytes(array);

        return byteBuf;
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
