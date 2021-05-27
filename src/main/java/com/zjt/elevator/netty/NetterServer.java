package com.zjt.elevator.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author hyh.
 * @version 1.0
 * @Date: 2021/5/23 15:57
 */
public class NetterServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossEventGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerEventGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossEventGroup,workerEventGroup)
                .channel(NioServerSocketChannel.class)
                .bind(9950);


    }
}
