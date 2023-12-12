/**
 * <LICENSE/>
 */
package com.palmyralabs.palmyra.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author ksvraja
 *
 */
@JsonInclude(Include.NON_EMPTY)
public interface ResultSet<T> {

	public String getError();

	public List<T> getResult();

	public Integer getCount();

	public Integer getOffset();

	public Long getTotal();

}
