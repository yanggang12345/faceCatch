package com.nala.faceCatch.util.netty.idle;

/**
 * 处理业务数据handler
 */

import com.nala.faceCatch.util.FileUtil;
import com.nala.faceCatch.util.ImageUtil;
import com.nala.faceCatch.util.OutUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static Logger logger = LoggerFactory
            .getLogger(ClientHandler.class);


    private Channel channel;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ClientHandler>>>>>>>>>>>>");

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

        int len = msg.readableBytes();
        if (len <= 10) {
            System.out.println("心跳或其它数据::" + msg);
        } else {
            byte[] array = new byte[len];
            msg.readBytes(array);
//        OutUtil.Out();
//        for (int i = 0; i < array.length ; i++) {
//            System.out.println(array[i]);
//        }
            System.out.println("接收的数据::" + msg);
//          图片写到硬盘
            Date date = new Date();
            FileUtil.byte2image(array, "/Users/lizengqi/Pictures/face_dev/"
                    + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date) + ".jpeg");
            //图像识别
            ImageUtil.faceMacth(array);
            System.out.println("ClientHandlerRead0>>>>>>>>>>>>");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

