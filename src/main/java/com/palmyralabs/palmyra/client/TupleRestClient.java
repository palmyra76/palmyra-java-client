/**
 * <LICENSE/>
 */
package com.palmyralabs.palmyra.client;

import java.io.IOException;
import java.util.List;

import com.palmyralabs.palmyra.client.exception.MultipleRecordsFoundException;
import com.palmyralabs.palmyra.client.pojo.FilterCriteria;

/**
 * @author ksvraja
 *
 */
public interface TupleRestClient {

	public Tuple findById(Object id) throws IOException;

	public ResultSet<Tuple> query(FilterCriteria filter) throws IOException;

	public Tuple save(Tuple data) throws IOException;
	
	public Tuple update(Tuple data, Object id) throws IOException;
	
	public Tuple create(Tuple data) throws IOException;

	public List<Tuple> save(List<Tuple> data) throws IOException;

	public default Tuple findUnique(FilterCriteria filter) throws IOException {
		ResultSet<Tuple> result = query(filter);
		int count = result.getCount();
		switch (count) {
		case 1:
			return result.getResult().get(0);
		case 0:
			return null;
		default:
			throw new MultipleRecordsFoundException(result.getResult());
		}
	}

	public default Tuple findUnique(String key, String value) throws IOException {
		FilterCriteria filter = new FilterCriteria();
		filter.addCriteria(key, value);
		return findUnique(filter);
	}

	public default List<Tuple> list(FilterCriteria filter) throws IOException {
		ResultSet<Tuple> result = query(filter);
		if (null != result)
			return result.getResult();
		return null;
	}

	public Tuple delete(Object id) throws IOException;

}
