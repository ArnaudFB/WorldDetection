package fr.nono74210.plugindetection;

import org.bukkit.ChatColor;

public class MessageUtils {

	public static String colorize(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
}
