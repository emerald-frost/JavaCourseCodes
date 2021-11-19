package io.kimmking.rpcfx.http;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import lombok.extern.slf4j.Slf4j;

public class NettyHttpClient {

	public static String postJson(String url, String jsonString) throws Exception {
		URI uri = new URI(url);
		String host = uri.getHost() == null ? "127.0.0.1" : uri.getHost();
		int port = uri.getPort();
		if (port == -1) {
			//该项目中不考虑https
			port = 80;
		}

		//用这个接收内容
		final StringBuilder sb = new StringBuilder();

		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(Holder.EVENT_LOOP_GROUP)
				.channel(NioSocketChannel.class)
				.handler(new NettyHttpClientInitializer(sb));
		Channel ch = bootstrap.connect(host, port).sync().channel();
		FullHttpRequest request = new DefaultFullHttpRequest(
				HttpVersion.HTTP_1_1,
				HttpMethod.POST,
				uri.getRawPath(),
				jsonString != null ? Unpooled.wrappedBuffer(jsonString.getBytes(StandardCharsets.UTF_8)) : Unpooled.EMPTY_BUFFER
		);
		request.headers().set(HttpHeaderNames.HOST, host);
		request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
		request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);
		request.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
		request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());

		ch.writeAndFlush(request);
		ch.closeFuture().sync();

		return sb.toString();
	}

	@Slf4j
	private static class Holder {

		private static final EventLoopGroup EVENT_LOOP_GROUP;

		static {
			log.info("创建EventLoopGroup实例");
			EVENT_LOOP_GROUP = new NioEventLoopGroup();
		}
	}
}
