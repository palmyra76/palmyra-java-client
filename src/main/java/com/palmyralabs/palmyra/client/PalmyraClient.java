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
public interface PalmyraClient<T, ID> {

	public T findById(ID id) throws IOException;

	public ResultSet<T> query(FilterCriteria filter) throws IOException;

	public T save(T data) throws IOException;

	public List<T> save(List<T> data) throws IOException;

	public T save(T data, ID id) throws IOException;

	public T delete(ID id) throws IOException;

	public default List<T> list(FilterCriteria filter) throws IOException {
		ResultSet<T> result = query(filter);
		if (null != result)
			return result.getResult();
		return null;
	}

	public default T findUnique(FilterCriteria filter) throws IOException {
		ResultSet<T> result = query(filter);
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

	public default T findUnique(String key, String value) throws IOException {
		FilterCriteria filter = new FilterCriteria();
		filter.addCriteria(key, value);
		return findUnique(filter);
	}

}