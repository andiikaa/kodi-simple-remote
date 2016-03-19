package org.github.kodisimpleremote.responses;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class KodiActivePlayers {
	private List<PlayerInfo> result = new ArrayList<PlayerInfo>();
	
	private KodiActivePlayers(){}
	
	public static KodiActivePlayers fromJson(String content){
		return new Gson().fromJson(content, KodiActivePlayers.class);	
	}

	public List<PlayerInfo> getResult() {
		return result;
	}
}
