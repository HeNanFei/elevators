package com.zjt.elevator.test.eventloop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hyh.
 * @version 1.0
 * @Date: 2021/5/20 6:47
 */
public class EventLoop {
    public static void main(String[] args) {
      /*  io.netty.channel.EventLoopGroup eventLoop = new NioEventLoopGroup();
        io.netty.channel.EventLoop next = eventLoop.next();
        next.scheduleAtFixedRate(new Thread(() -> System.out.println("nothing")),20,20, TimeUnit.MILLISECONDS);
        //eventLoop.schedule(new Thread(() -> System.out.println("nothing")),20, TimeUnit.MILLISECONDS);
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor();
        new ServerBootstrap()
                .channel(NioServerSocketChannel.class)
                .group(new NioEventLoopGroup(),new NioEventLoopGroup())
                .childHandler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        
                    }
                });*/

    }
}
