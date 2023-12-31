package com.palmyralabs.palmyra.client.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.palmyralabs.palmyra.client.Tuple;

public class TupleSerializer extends StdSerializer<Tuple> {
	private static final long serialVersionUID = 1L;

	public TupleSerializer() {
		this(null);
	}

	public TupleSerializer(Class<Tuple> vc) {
		super(vc);
	}

	@Override
	public void serialize(Tuple value, JsonGenerator gen, SerializerProvider provider)
	        throws IOException{		
		writeTuple(value, gen, provider);
	}
	
	private void writeTuple(Tuple value, JsonGenerator gen, SerializerProvider provider) throws IOException{
		gen.writeStartObject();
        
		if(null != value.getId())
        	gen.writeStringField("id", value.getId());
        if(null != value.getMetainfo())
        	gen.writeObjectField("_metainfo", value.getMetainfo());
    
        HashMap<String, Object> attributes = value.getAttributes();
        for(Entry<String, Object> attribute : attributes.entrySet()) {
        	if(null != attribute.getValue()) {
        		provider.defaultSerializeField(attribute.getKey(), attribute.getValue(), gen);
        	}
        }
        
        HashMap<String, Tuple> parent = value.getParent();
        for(Entry<String, Tuple> attribute : parent.entrySet()) {
        	gen.writeFieldName(attribute.getKey());
        	writeTuple(attribute.getValue(), gen, provider);	
        }

        HashMap<String, List<Tuple>> children = value.getChildren();
        for(Entry<String, List<Tuple>> attribute : children.entrySet()) {
        	List<Tuple> list = attribute.getValue();
        	gen.writeFieldName(attribute.getKey());
        	gen.writeStartArray();
        	for(Tuple child:list) {
        		writeTuple(child, gen, provider);
        	}
        	gen.writeEndArray();
        }
        
		gen.writeEndObject();
	}
}

