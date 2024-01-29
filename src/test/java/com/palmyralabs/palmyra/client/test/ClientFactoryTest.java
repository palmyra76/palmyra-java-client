package com.palmyralabs.palmyra.client.test;

import java.io.IOException;
import java.util.function.Consumer;

import com.palmyralabs.palmyra.async.RequestConfig;
import com.palmyralabs.palmyra.async.handler.ResultConsumerHandler;
import com.palmyralabs.palmyra.async.net.ReactiveRestClient;
import com.palmyralabs.palmyra.client.ResultSet;
import com.palmyralabs.palmyra.client.auth.AuthClient;

public class ClientFactoryTest {

	private static String baseURL = "http://localhost:6060";
	private static String context = "api/palmyra/";

	public static void main(String[] args) throws IOException, InterruptedException {
		ReactiveRestClient client = new ReactiveRestClient(baseURL, AuthClient.NoopAuthClient);
		Consumer<ResultSet<Parameter>> consumer = new Consumer<ResultSet<Parameter>>() {

			@Override
			public void accept(ResultSet<Parameter> sdf) {
				System.out.println(sdf.getCount());
			}
		};
		client.get(context + "params/definition", new ResultConsumerHandler<Parameter>(consumer, Parameter.class),
				RequestConfig.of("example:"));

		Thread.sleep(300 * 1000);
	}
}
