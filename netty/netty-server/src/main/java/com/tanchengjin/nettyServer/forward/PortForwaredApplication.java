package com.tanchengjin.nettyServer.forward;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
public class PortForwaredApplication {
    private final int localPort;

    private final String remoteHost;
    private final int remotePort;

    public PortForwaredApplication(int localPort, String remoteHost, int remotePort) {
        this.localPort = localPort;
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
    }

    public void start() throws InterruptedException {
        //接收数据
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        //处理数据
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        Bootstrap b = bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new PortForwardHandler());
                    }
                });

        try {
            //绑定本地端口
            Channel localChannel = b.bind(localPort).sync().channel();

            System.out.println("port forward listening on " + localPort);
            //连接远程主机
            Channel remoteChannel = b.connect(remoteHost, remotePort).sync().channel();
            System.out.println("connect to " + remoteHost + ":" + remotePort);
            //等待本地端口关闭
            localChannel.closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PortForwaredApplication portForwaredApplication = new PortForwaredApplication(5588, "127.0.0.1", 6666);
        portForwaredApplication.start();
    }
}
