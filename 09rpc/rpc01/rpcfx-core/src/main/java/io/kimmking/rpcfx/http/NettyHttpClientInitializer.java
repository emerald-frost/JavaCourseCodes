package io.kimmking.rpcfx.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;

public class NettyHttpClientInitializer extends ChannelInitializer<SocketChannel> {

	private final StringBuilder sb;

	public NettyHttpClientInitializer(StringBuilder sb) {
		this.sb = sb;
	}

	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline p = ch.pipeline();

		p.addLast(new HttpClientCodec());
		p.addLast(new HttpContentDecompressor());
		p.addLast(new NettyHttpClientHandler(sb));
	}
}
