# kodi-simple-remote
A simple remote for accessing the media center kodi (http://kodi.wiki/) via json rpc

## Examples

```java
//new remote with uses the default authentication
KodiRemote remote = new KodiRemote("http://192.168.2.103:8080");

//get version info
KodiJsonRpcVersion version = remote.getJsonRpcVersion();
System.out.println("Connected to Kodi. Version: " + version.toString());

//play a file
remote.open("/sdcard/someMusic.mp3", PlayerOpenType.FILE);

//show a picture
remote.open("/sdcard/someImage.jpg", PlayerOpenType.FILE);

//stops player with id 0
remote.stop(0);
```
