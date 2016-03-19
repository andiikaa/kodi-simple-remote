package org.github.kodisimpleremote.responses;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class KodiJsonRpcVersion {
	private final int minor;
	private final int patch;
	private final int major;

	private KodiJsonRpcVersion(int minor, int patch, int major) {
		this.minor = minor;
		this.patch = patch;
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public int getPatch() {
		return patch;
	}

	public int getMajor() {
		return major;
	}

	@Override
	public String toString() {
		return minor + "." + patch + "." + major;
	}

	/**
	 * Creates the Object from a json response. Response looks like this:
	 * {"id":1,"jsonrpc":"2.0","result":{"version":{"major":6,"minor":21,"patch":2}}}
	 * 
	 * @param json
	 * @return
	 */
	public static KodiJsonRpcVersion fromJson(String content) {
		JsonObject json = new JsonParser().parse(content).getAsJsonObject();
		JsonObject version = json.getAsJsonObject("result").getAsJsonObject("version");
		int major = version.get("major").getAsInt();
		int minor = version.get("minor").getAsInt();
		int patch = version.get("patch").getAsInt();
		return new KodiJsonRpcVersion(minor, patch, major);
	}

}
