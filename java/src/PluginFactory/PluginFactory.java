package PluginFactory;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginFactory {
	
	public IPlugin CreatePlugin(String arg) {
		String jarFilePath = "Plugins" + File.separator + arg + ".jar";
		try {
			URL jarURL = new URL(jarFilePath);
			URLClassLoader child = new URLClassLoader(new URL[] {jarURL}, this.getClass().getClassLoader());
			Class classToLoad = Class.forName(arg + "Plugin", true, child);
			IPlugin plugin = (IPlugin)classToLoad.newInstance(); //if errors here, try getConstructor().newInstance()
			return plugin;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
