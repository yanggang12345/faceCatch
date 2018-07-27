package com.nala.faceCatch.util.netty;

import com.nala.faceCatch.util.JsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端这里继承<code>MessageToByteEncoder</code>更加方便
 */
public class NettyMsgEncoder extends MessageToByteEncoder<Object> {
    private static Logger logger = LoggerFactory.getLogger(NettyMsgEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf byteBuf) throws Exception {
        String jsonStr = JsonUtil.toJson(msg);
        logger.error("\n发送的数据:" + jsonStr);
        byte[] jsonByte = jsonStr.getBytes();
        int dataLength = jsonByte.length;
        byteBuf.ensureWritable(4 + dataLength);
        byteBuf.writeInt(dataLength);
        if (dataLength > 0) {
            byteBuf.writeBytes(jsonByte);
        }
    }

}
