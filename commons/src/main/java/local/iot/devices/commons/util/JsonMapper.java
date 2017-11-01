package local.iot.devices.commons.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper
{
	private ObjectMapper mapper = new ObjectMapper();

	public <T> T fromJson(String json, Class<T> clazz) throws IOException
	{
		return mapper.readValue(json, clazz);
	}

	public <T> String toJson(T obj) throws JsonProcessingException
	{
		return mapper.writeValueAsString(obj);
	}
}
