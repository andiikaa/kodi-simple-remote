package org.github.kodisimpleremote;

import java.util.Base64;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentProvider;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Response.CompleteListener;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.github.kodisimpleremote.methods.JsonRpc;
import org.github.kodisimpleremote.methods.KodiGui;
import org.github.kodisimpleremote.methods.KodiJsonRpc;
import org.github.kodisimpleremote.methods.KodiPlayer;
import org.github.kodisimpleremote.methods.KodiPlayer.PlayerOpenType;
import org.github.kodisimpleremote.methods.Xbmc;
import org.github.kodisimpleremote.responses.KodiInfoLabels;
import org.github.kodisimpleremote.responses.KodiJsonRpcVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//For post
//http://<your-ip>:<your-port>/jsonrpc
//For get
//http://<your-ip>:<your-port>/jsonrpc?request=<url-encoded-request>

//http://kodi.wiki/view/JSON-RPC_API

/**
 * Kodi remote, to control Kodi via the JSON-RPC API. This Remote uses a jetty client for accessing
 * Kodi via http.
 *
 * @author André Kühnert
 *
 */
public class KodiRemote {
	public static final String MEDIA_TYPE_JSON = "application/json";

	private static final Logger logger = LoggerFactory.getLogger(KodiRemote.class);

	// Default authentication
	private static final String defaultAuth = "kodi:kodi";
	private static final String jsonRpc = "/jsonrpc";

	private final String auth;
	private final String serverBaseUri;
	private final String creds;

	private HttpClient client;

	/**
	 * This creates a new Kodi Remote. The default password and username is used.
	 * 
	 * @param serverBaseUri
	 * @throws Exception
	 */
	public KodiRemote(String serverBaseUri) throws Exception {
		this(serverBaseUri, null, null);
	}

	/**
	 * This creates a new Kodi Remote. The given username and password is used.
	 * 
	 * @param serverBaseUri
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	public KodiRemote(String serverBaseUri, String username, String password) throws Exception {
		if (username == null || password == null)
			auth = defaultAuth;
		else
			auth = username.concat(":").concat(password);
		this.serverBaseUri = serverBaseUri;
		client = new HttpClient();
		byte[] out = Base64.getEncoder().encode(auth.getBytes());
		creds = new String(out);
		client.start();
	}

	/**
	 * Sends a small notification to Kodi
	 * 
	 * @param title
	 * @param message
	 * @param displayTime
	 *            how long should the message display (milliseconds)
	 */
	public void sendNotification(String title, String message, int displayTime) {
		KodiGui.ShowNotification notification = new KodiGui.ShowNotification(title, message, displayTime);
		postKodiJsonRpcAsync(notification);
	}

	/**
	 * Path of a folder, or the item (movie, sound file, picture)
	 */
	public void open(String itemPath, PlayerOpenType type) {
		KodiPlayer.Open open = new KodiPlayer.Open(itemPath, type);
		postKodiJsonRpcAsync(open);
	}

	/**
	 * Stops the playing of music, videos or the diashow
	 * 
	 * @param playerId
	 *            e.g. 1
	 */
	public void stop(int playerId) {
		KodiPlayer.Stop stop = new KodiPlayer.Stop(playerId);
		postKodiJsonRpcAsync(stop);
	}

	/**
	 * Sends a KodiJsonRpc and return the response.
	 * 
	 * @param kodiJsonRpc
	 * @return
	 */
	public ContentResponse postKodiJsonRpc(KodiJsonRpc kodiJsonRpc) {
		logger.debug("JSON-RPC: {}", kodiJsonRpc.getAsJsonString());
		ContentProvider content = new StringContentProvider(kodiJsonRpc.getAsJsonString());

		ContentResponse response = null;

		try {
			response = client.POST(serverBaseUri).path(jsonRpc).header("Authorization", "Basic ".concat(creds))
					.content(content, MEDIA_TYPE_JSON).send();
		} catch (InterruptedException | TimeoutException | ExecutionException e) {
			e.printStackTrace();
			return null;
		}
		logger.debug("JSON-RPC Response Status: '{}'", response.getStatus());
		logger.debug("Response: {}", response.getContentAsString());
		return response;
	}

	/**
	 * Posts a async jsonRpc to Kodi
	 * 
	 * @param kodiJsonRpc
	 */
	public void postKodiJsonRpcAsync(final KodiJsonRpc kodiJsonRpc) {
		logger.debug("JSON-RPC: {}", kodiJsonRpc.getAsJsonString());
		ContentProvider content = new StringContentProvider(kodiJsonRpc.getAsJsonString());

		client.POST(serverBaseUri).path(jsonRpc).header("Authorization", "Basic ".concat(creds))
				.content(content, MEDIA_TYPE_JSON).send(new CompleteListener() {
					@Override
					public void onComplete(Result result) {
						logger.debug("Async Response Status for Kodi Method '{}': {}", kodiJsonRpc.getMethodName(),
								result.getResponse().getStatus());
					}
				});
	}

	public KodiJsonRpcVersion getJsonRpcVersion() {
		JsonRpc.Version rpc = new JsonRpc.Version();
		ContentResponse response = postKodiJsonRpc(rpc);
		String content = response.getContentAsString();
		return KodiJsonRpcVersion.fromJson(content);
	}

	/**
	 * Get the Info Lables from <a href=http://kodi.wiki/index.php?title=InfoLabels>Kodi Wiki</a>
	 * 
	 * @param labels
	 * @return
	 */
	public KodiInfoLabels getKodiInfos(String... labels) {
		Xbmc.GetInfoLabels getLabels = new Xbmc.GetInfoLabels(labels);
		ContentResponse response = postKodiJsonRpc(getLabels);
		String content = response.getContentAsString();
		return KodiInfoLabels.fromJson(content);
	}

	/**
	 * Stop this Client
	 * 
	 * @throws Exception
	 */
	public void stop() throws Exception {
		client.stop();
	}
}
