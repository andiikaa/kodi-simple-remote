package org.github.kodisimpleremote.methods;

//example
//{"jsonrpc": "2.0", "method": "Player.PlayPause", "params": { "playerid": 0 }, "id": 1}
public class KodiGui {

	public static class ShowNotification extends KodiJsonRpc {
		public static final String NOTIFACTION_TYPE = "GUI.ShowNotification";

		public ShowNotification(String title, String message, int displayTime) {
			super(NOTIFACTION_TYPE);
			addParam("title", title);
			addParam("message", message);
			addParam("displaytime", displayTime);
			addProperty("id", 1);			
		}
	}
}
