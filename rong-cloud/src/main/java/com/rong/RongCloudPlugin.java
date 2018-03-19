package com.rong;

import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 插件的入口类，负责注入这个插件的功能
 *
 */
public class RongCloudPlugin implements Plugin {
	private static final Logger log = LoggerFactory.getLogger(RongCloudPlugin.class);
	
	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		System.out.println("rong Cloud plugin is running!");
		log.warn("rong Cloud plugin is running!");
	}

	

	@Override
	public void destroyPlugin() {
		
	}

	
}