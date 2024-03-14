/**
 * <LICENSE/>
 */
package com.palmyralabs.palmyra.client.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ksvraja
 *
 */
@JsonInclude(Include.NON_EMPTY)
@Getter
@Setter
public class ItemResult<T>  {
	T result;
	private Integer status;
}
