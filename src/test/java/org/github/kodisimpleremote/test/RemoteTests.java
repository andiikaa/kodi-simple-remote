package org.github.kodisimpleremote.test;

import org.github.kodisimpleremote.KodiRemote;
import org.github.kodisimpleremote.methods.KodiPlayer.PlayerOpenType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RemoteTests {
		private static final String serverPath = "http://192.168.3.103:8080";
		private static final String itemToOpenPath = "/sdcard/hilfe.png";
		private static final String imageToOpenPath = "/sdcard/sensoren_geht_es_ihnen_gut.wav";
		
		private static KodiRemote remote;
		
		@BeforeClass
		public static void connect() throws Exception{
			remote = new KodiRemote(serverPath);
		}
		
		@AfterClass
		public static void disconnect() throws Exception{
			remote.stop();
		}
		
		/**
		 * This simple tests the playing of some music file on kodi.
		 * You should here something. No automated test.
		 */
		@Test
		public void playOpenTest() throws Exception {
			remote.open(itemToOpenPath, PlayerOpenType.FILE);
		}
		
		@Test
		public void playOpenImageTest()throws Exception {
			remote.open(imageToOpenPath, PlayerOpenType.FILE);
		}
		
		/**
		 * Shows a notification in the kodi gui. You should read it from the display.
		 * No automated test.
		 */
		@Test
		public void guiNotificationTest()throws Exception {
			remote.sendNotification("Test Notification", "You should read this", 20000);
		}
		
		/**
		 * Stops the playing of something. There should a file playing before running this test
		 */
		@Test
		public void playerStopTest()throws Exception {
			remote.stop(0);
		}
}
