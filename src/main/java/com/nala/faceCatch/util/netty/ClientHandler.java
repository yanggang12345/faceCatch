package com.nala.faceCatch.util.netty;

/**
 * Created by heshangqiu on 2016/12/28 9:55
 */

import com.nala.faceCatch.util.FileUtil;
import com.nala.faceCatch.util.ImageUtil;
import com.nala.faceCatch.util.OutUtil;
import com.nala.faceCatch.util.netty.heartBeats.ClientHeartBeatsHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler extends ClientHeartBeatsHandler {

    public ClientHandler(){
        super("client");
    }
    private static Logger logger = LoggerFactory
            .getLogger(ClientHandler.class);


    private Channel channel;

    /**
     * 此方法会在连接到服务器后被调用
     * @param ctx
     */
    public void channelActive(ChannelHandlerContext ctx) {
//        ctx.write(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }

    /**
     * 此方法会在接收到服务端数据后调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void handleData(ChannelHandlerContext ctx, ByteBuf msg){

        int len = msg.readableBytes();
        byte[] array = new byte[len];
        msg.readBytes(array);
        OutUtil.Out();
        for (int i = 0; i < array.length ; i++) {
            System.out.println(array[i]);
        }
        System.out.println("接收的数据::"+msg);
        //图片写到硬盘
        Date date = new Date();
        FileUtil.byte2image(array,"/Users/lizengqi/Pictures/face_dev/"
                +new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date)+".jpeg");
        //图像识别
        ImageUtil.faceMacth(array);

    }

    @Override
    protected void handleAllIdle(ChannelHandlerContext ctx) {
        super.handleAllIdle(ctx);
        sendPingMsg(ctx);
    }
}

