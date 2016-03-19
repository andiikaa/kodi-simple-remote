package org.github.kodisimpleremote.responses;

import com.google.gson.Gson;

public class PlayerInfo {
	private String type;
	private int playerId;
	
	private PlayerInfo(){}
	
	public static PlayerInfo fromJson(String content){
		return new Gson().fromJson(content, PlayerInfo.class);
	}	
	
	public String getType() {
		return type;
	}

	public int getPlayerId() {
		return playerId;
	}
}
