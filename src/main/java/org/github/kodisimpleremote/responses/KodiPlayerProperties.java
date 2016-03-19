package org.github.kodisimpleremote.responses;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class KodiPlayerProperties {
	private JsonObject result;

	private KodiPlayerProperties() {

	}

	public String getPropertyValue(String propertyName) {
		JsonElement info = result.get(propertyName);
		if (info == null)
			return null;
		return info.getAsString();
	}

	public static KodiPlayerProperties fromJson(String content) {
		KodiPlayerProperties props = new KodiPlayerProperties();
		JsonObject json = new JsonParser().parse(content).getAsJsonObject();
		props.result = json.getAsJsonObject("result");
		return props;
	}
}
