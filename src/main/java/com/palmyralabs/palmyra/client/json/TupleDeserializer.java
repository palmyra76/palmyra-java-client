package com.palmyralabs.palmyra.client.json;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.palmyralabs.palmyra.client.pojo.TupleImpl;
import com.palmyralabs.palmyra.client.pojo.TupleMetaInfo;

public class TupleDeserializer extends StdDeserializer<TupleImpl> {
	private static final long serialVersionUID = 1L;
	private static final String TUPLE_ID = "id";
	private static final ObjectMapper objectMapper = ObjectMapperFactory.getMapper();

	public TupleDeserializer() {
		this(null);
	}

	public TupleDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public TupleImpl deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode root = jp.getCodec().readTree(jp);
		TupleImpl tuple = getTuple(root, jp);
		return tuple;
	}

	protected TupleImpl create() {
		return new TupleImpl();
	}

	protected TupleImpl getTuple(JsonNode node, JsonParser jp) throws IOException {
		TupleImpl tuple = create();
		ObjectReader reader = objectMapper.readerFor(TupleMetaInfo.class);
		Iterator<Entry<String, JsonNode>> fields = node.fields();
		while (fields.hasNext()) {
			Entry<String, JsonNode> entry = fields.next();
			String key = entry.getKey();
			JsonNode value = entry.getValue();

			switch (value.getNodeType()) {
			case NUMBER:
				if (TUPLE_ID.equals(key)) {
					tuple.setId(value.asText());
					break;
				} else
					tuple.setAttribute(key, value.numberValue());
				break;
			case STRING:
				if (TUPLE_ID.equals(key)) {
					tuple.setId(value.asText());
					break;
				}
				tuple.setAttribute(key, value.asText());
				break;
			case BOOLEAN:
				tuple.setAttribute(key, value.asBoolean());
				break;
			case NULL:
				tuple.setAttribute(key, null);
				break;
			case OBJECT:
				if (key.equals("_metainfo")) {
					TupleMetaInfo info = reader.readValue(value);
					tuple.setMetainfo(info);
				} else {
					tuple.addParent(key, getTuple(value, jp));
				}
				break;
			case ARRAY:
				Iterator<JsonNode> children = value.elements();
				JsonNode child;
				while (children.hasNext()) {
					child = children.next();
					tuple.addChildren(key, getTuple(child, jp));
				}
				break;

			default:
				break;
			}

		}
		return tuple;
	}
}
