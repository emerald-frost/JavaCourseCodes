package io.github.emeraldfrost.gateway.outbound.okhttp;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import io.github.emeraldfrost.gateway.filter.HttpRequestFilter;
import io.github.emeraldfrost.gateway.router.HttpEndpointRouter;
import io.github.emeraldfrost.gateway.router.RoundRibbonHttpEndpointRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@Slf4j
public class OkhttpOutboundHandler {

	private List<String> backendUrls;

	private OkHttpClient client;

	private HttpEndpointRouter router;

	public OkhttpOutboundHandler(List<String> backendUrls) {
		this.backendUrls = new ArrayList<>(backendUrls);

		//创建okhttp client
		this.client = new OkHttpClient.Builder()
				.connectTimeout(Duration.ofSeconds(1L))
				.readTimeout(Duration.ofSeconds(2L))
				.build();

		//创建路由实例
		this.router = new RoundRibbonHttpEndpointRouter();
	}

	public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, HttpRequestFilter filter) {
		String backendUrl = router.route(this.backendUrls);
		final String url = backendUrl + fullRequest.uri();
		filter.filter(fullRequest, ctx);
		fetchGet(fullRequest, ctx, url);
	}

	private void fetchGet(final FullHttpRequest fullHttpRequest, final ChannelHandlerContext ctx, final String url) {
		//创建请求
		Request request = new Request.Builder()
				.url(url)
				.build();
		//入队
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull IOException e) {
				log.warn("无法访问后端服务, url: {}", url);
				final FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, INTERNAL_SERVER_ERROR);
				writeResponse(fullHttpRequest, ctx, fullHttpResponse);
			}

			@Override
			public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
				final FullHttpResponse fullHttpResponse;
				if (response.isSuccessful()) {
					final ResponseBody body = response.body();
					fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body.bytes()));
					fullHttpResponse.headers()
							.set(HttpHeaderNames.CONTENT_TYPE.toString(), body.contentType().toString())
							.setInt(HttpHeaderNames.CONTENT_LENGTH.toString(), (int) body.contentLength())
					;
				}
				else {
					log.warn("请求后端服务失败, code: {}", response.code());
					fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, INTERNAL_SERVER_ERROR);
				}

				// Todo
				//filter.filter(fullHttpResponse);

				writeResponse(fullHttpRequest, ctx, fullHttpResponse);
			}
		});
	}

	private void writeResponse(final FullHttpRequest fullHttpRequest, final ChannelHandlerContext ctx, FullHttpResponse fullHttpResponse) {
		if (fullHttpRequest != null) {
			if (!HttpUtil.isKeepAlive(fullHttpRequest)) {
				ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
			}
			else {
				ctx.write(fullHttpResponse);
			}
		}
		ctx.flush();
		ctx.close();
	}
}
