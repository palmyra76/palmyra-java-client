package com.palmyralabs.palmyra.client.test;

import java.io.IOException;
import java.time.Duration;

import com.palmyralabs.palmyra.async.RequestConfig;
import com.palmyralabs.palmyra.async.handler.AbstractResponseHandler;
import com.palmyralabs.palmyra.async.net.ReactiveRestClient;
import com.palmyralabs.palmyra.client.ResultSet;
import com.palmyralabs.palmyra.client.auth.AuthClient;

public class ClientFactoryTest {

	private static String baseURL = "http://localhost:6060";
	private static String context = "api/palmyra/";

	public static void main(String[] args) throws IOException, InterruptedException {
		ReactiveRestClient client = new ReactiveRestClient(baseURL, AuthClient.NoopAuthClient);
		
		client.get(context + "params/definition", new AbstractResponseHandler<ResultSet<Parameter>>() {

			@Override
			public void accept(ResultSet<Parameter> o) {
				System.out.println(o.getCount());				
			}

		}, RequestConfig.of("example:"));

		Thread.sleep(Duration.ofMinutes(5));
	}
}

class NationalityResponseHandler extends AbstractResponseHandler<Parameter> {

	@Override
	public void accept(Parameter o) {
		System.out.println(o.getName());
	}
}