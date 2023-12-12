package com.palmyralabs.palmyra.client;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.palmyralabs.palmyra.client.json.TupleDeserializer;
import com.palmyralabs.palmyra.client.json.TupleSerializer;
import com.palmyralabs.palmyra.client.pojo.TupleMetaInfo;

/**
 * The base class for all the data operations. This class will carry the
 * information from the json format and transfer all the way to the database
 * object.
 * 
 * @author ksvraja
 *
 */

@JsonInclude(Include.NON_EMPTY)
@JsonDeserialize(using = TupleDeserializer.class)
@JsonSerialize(using = TupleSerializer.class)

public interface Tuple {
	
	public String getId();
	
	public void setId(String id);

	public String getType();

	public void setType(String type);
	
	public HashMap<String, Tuple> getParent();
	
	public Tuple getParent(String key);
	
	public void setParent(HashMap<String, Tuple> parent);

	public void addParent(String key, Tuple value);
	
	public void removeParent(String key);

	public HashMap<String, List<Tuple>> getChildren();

	public void setChildren(HashMap<String, List<Tuple>> children);
	
	public void addChildren(String key, Tuple tuple);
	
	public void setChildren(String key, List<Tuple> tuples);

	@JsonAnyGetter
	public HashMap<String, Object> getAttributes();
	
	public void setAttributes(HashMap<String, Object> attributes);	

	@JsonAnySetter
	public void setAttribute(String key, Object value);

	@JsonIgnore
	public Object getAttribute(String key);
	
	public default boolean isIdEmpty() {
		return null == getId();
	}
		
	@JsonIgnore
	public boolean hasAttribute(String name);
	
	@JsonIgnore
	public default String getAttributeAsString(String name) {
		Object obj = getAttribute(name);
		if (null == obj)
			return null;
		else if (obj instanceof String)
			return (String) obj;
		else
			return obj.toString();
	}

	@JsonIgnore
	public void setRefAttribute(String field, Object value);
	
	@JsonIgnore
	public Object getRefAttribute(String field);
	
	/**
	 * @param value
	 */
	public default void setId(Object value) {
		if(null == value)
			setId(null);
		else if(value instanceof String)
			setId((String) value);
		else
			setId(value.toString());
			
	}

	public TupleMetaInfo getMetainfo();
}