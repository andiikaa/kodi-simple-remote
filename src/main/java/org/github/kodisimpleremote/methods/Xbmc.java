package org.github.kodisimpleremote.methods;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

public class Xbmc {

	/**
	 * Get the Info Lables from <a href=http://kodi.wiki/index.php?title=InfoLabels>Kodi Wiki</a>
	 * 
	 * <br>
	 * <br>
	 * {"params":{ "labels" : ["System.FriendlyName"]
	 * },"jsonrpc":"2.0","method":"XBMC.GetInfoLabels", "id" : 1}
	 * 
	 *
	 */
	public static class GetInfoLabels extends KodiJsonRpc {
		public static final String XBMC_GETINFOLABELS = "XBMC.GetInfoLabels";

		public GetInfoLabels(String... labels) {
			super(XBMC_GETINFOLABELS);
			addProperty("id", 1);
			addLabels(labels);
		}
		
		private void addLabels(String[] labels){
			JsonArray array = new JsonArray();
			for (int i = 0; i < labels.length; i++)
				array.add(new JsonPrimitive(labels[i]));
			addParam("labels", array);
		}

	}

}
