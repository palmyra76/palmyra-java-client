/**
 * <LICENSE/>
 */
package com.palmyralabs.palmyra.client.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.palmyralabs.palmyra.client.ResultSet;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ksvraja
 *
 */
@JsonInclude(Include.NON_EMPTY)
@Getter
@Setter
public class ResultSetImpl<T> implements ResultSet<T> {
	List<T> result;
	private TupleImpl relation;
	private Integer offset;
	private Long total;

	private String Error;

	public TupleImpl getRelation() {
		return relation;
	}

	public void setRelation(TupleImpl relation) {
		this.relation = relation;
	}

	public String getError() {
		return Error;
	}

	public void setError(String error) {
		Error = error;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public Integer getCount() {
		if (null != result)
			return result.size();
		else
			return 0;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
