package fr.nono74210.plugindetection;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class MessageUtils {

	public static String colorize(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
}
