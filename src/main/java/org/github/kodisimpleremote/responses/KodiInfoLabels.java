package org.github.kodisimpleremote.responses;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class KodiInfoLabels {
	private JsonObject result;

	private KodiInfoLabels() {

	}

	/**
	 * Gets the Info with the given label name. <br>
	 * Get the Info Lables from <a href=http://kodi.wiki/index.php?title=InfoLabels>Kodi Wiki</a>
	 * 
	 * @param infoName
	 * @return null if the label name is not in this object.
	 */
	public String getInfo(String infoName) {
		JsonElement info = result.get(infoName);
		if (info == null)
			return null;
		return info.getAsString();
	}

	public static KodiInfoLabels fromJson(String content) {
		KodiInfoLabels labels = new KodiInfoLabels();
		JsonObject json = new JsonParser().parse(content).getAsJsonObject();
		labels.result = json.getAsJsonObject("result");
		return labels;
	}

}
