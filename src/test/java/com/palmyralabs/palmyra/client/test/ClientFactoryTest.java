package com.palmyralabs.palmyra.client.test;

import java.io.IOException;

import com.palmyralabs.palmyra.client.PalmyraClientFactory;
import com.palmyralabs.palmyra.client.ResultSet;
import com.palmyralabs.palmyra.client.Tuple;
import com.palmyralabs.palmyra.client.TupleRestClient;
import com.palmyralabs.palmyra.client.pojo.FilterCriteria;

public class ClientFactoryTest {

	private static String baseURL = "http://localhost:6060";
	private static String context = "/api/palmyra/";

	public static void main(String[] args) throws IOException {
		PalmyraClientFactory factory = new PalmyraClientFactory(baseURL, context);
		TupleRestClient restClient = factory.getTupleClient("masterdata/vehicleYear");
		FilterCriteria criteria = new FilterCriteria();

		// Tuple data = restClient.findById(2);
		Tuple data = restClient.findUnique("year", "2021");
		ResultSet<Tuple> result = restClient.query(criteria);
		
		for(Tuple t : result.getResult()) {
			
		}
		
		System.out.println(result.getCount());
	}
}
