package com.herocraftonline.dev.heroes.util;

import org.bukkit.entity.Player;

public class Misc {
	public static void sendMessage(Player p, String m){
		p.sendMessage(Properties.cColor + "Heroes: " + m);
	}
}
