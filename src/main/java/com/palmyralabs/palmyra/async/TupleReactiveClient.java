/**
 * <LICENSE/>
 */
package com.palmyralabs.palmyra.async;

import java.io.IOException;
import java.util.List;

import com.palmyralabs.palmyra.client.ResultSet;
import com.palmyralabs.palmyra.client.Tuple;
import com.palmyralabs.palmyra.client.pojo.FilterCriteria;

/**
 * @author ksvraja
 *
 */
public interface TupleReactiveClient {

	public void findById(Object id, ResponseHandler<Tuple> handler) throws IOException;

	public void query(FilterCriteria filter, ResponseHandler<ResultSet<Tuple>> handler) throws IOException;

	public void save(Tuple data, ResponseHandler<Tuple> handler) throws IOException;

	public void save(List<Tuple> data, ResponseHandler<List<Tuple>> handler) throws IOException;

	public void delete(Object id, ResponseHandler<Tuple> handler) throws IOException;

}
