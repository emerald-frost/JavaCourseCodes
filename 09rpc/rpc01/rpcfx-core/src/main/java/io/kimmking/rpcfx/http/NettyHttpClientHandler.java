package io.kimmking.rpcfx.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

public class NettyHttpClientHandler extends SimpleChannelInboundHandler<HttpObject> {

	private final StringBuilder sb;

	public NettyHttpClientHandler(StringBuilder sb) {
		this.sb = sb;
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
		if (msg instanceof HttpContent) {
			HttpContent content = (HttpContent) msg;

			sb.append(content.content().toString(CharsetUtil.UTF_8));

			if (content instanceof LastHttpContent) {
				ctx.close();
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
