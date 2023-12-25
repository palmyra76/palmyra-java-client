/**
 * <LICENSE/>
 */
package com.palmyralabs.palmyra.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.palmyralabs.palmyra.client.pojo.ResultSetImpl;

/**
 * @author ksvraja
 *
 */
@JsonInclude(Include.NON_EMPTY)
@JsonDeserialize(as = ResultSetImpl.class)
public interface ResultSet<T> {

	public String getError();

	public List<T> getResult();

	public Integer getCount();

	public Integer getOffset();

	public Long getTotal();

}
