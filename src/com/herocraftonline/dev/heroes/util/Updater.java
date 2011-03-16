package com.herocraftonline.dev.heroes.util;



import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;




public class Updater {

	public static void updateLatest(){
		URL url;
		try {
			url = new URL("http://109.153.159.46:8081/artifactory/plugins-release-local/Heroes/Heroes/latest/Heroes-latest.jar");
			ReadableByteChannel rbc = Channels.newChannel(url.openStream());
			FileOutputStream fos = new FileOutputStream("plugins" + File.pathSeparator + "Heroes.jar");
			fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateVersion(String v){
		URL url;
		try {
			url = new URL("http://109.153.159.46:8081/artifactory/plugins-release-local/Heroes/Heroes/" + v + "/Heroes-0.0.1.jar");
			ReadableByteChannel rbc = Channels.newChannel(url.openStream());
			FileOutputStream fos = new FileOutputStream("plugins" + File.pathSeparator + "Heroes.jar");
			fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
