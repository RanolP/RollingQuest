package me.ranol.rollingquest.util;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class Util {
	public static void msg(CommandSender s, String message) {
		s.sendMessage("§8§l[§aRollingQuests§8§l] §a" + message);
	}

	public static void con(String message) {
		msg(Bukkit.getConsoleSender(), message);
	}

	public static boolean sendMessageList(CommandSender s, List<String> list, int i, String l) {
		if ((i * 8 - (8 - 1) > list.size()) || (i <= 0)) {
			msg(s, "§c목록을 찾을 수 없습니다.");
			return false;
		}
		if (list.size() * 8 == 0)
			msg(s, "§6" + list.size() + "개를 찾았습니다. §e" + i + "§f/§a" + list.size() / 8);
		else {
			msg(s, "§6" + list.size() + "개를 찾았습니다. §e" + i + "§f/§a" + (list.size() / 8 + 1));
		}
		for (int j = (i - 1) * 8; j < i * 8; j++) {
			s.sendMessage("§a" + (j + 1) + ". §6" + list.get(j));
			if (list.size() == j + 1) {
				break;
			}
			if ((i * 8 - 1 == j) && (list.size() > j + 1))
				msg(s, "§b다음 목록을 보려면 §c/" + l + " " + (i + 1));
		}
		return true;
	}

	public static void warn(String msg) {
		con("§6[§c!§6] " + msg.replace("§r", "§6"));
	}
}
