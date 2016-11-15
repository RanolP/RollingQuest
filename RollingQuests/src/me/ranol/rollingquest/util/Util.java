package me.ranol.rollingquest.util;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

	public static int hasItem(Player p, ItemStack stack) {
		return Arrays.stream(p.getInventory().getContents()).filter(stack::isSimilar).mapToInt(ItemStack::getAmount)
				.sum();
	}

	public static boolean takeItem(Player p, ItemStack stack, int count) {
		int rem = 0;
		ItemStack[] stacks = p.getInventory().getContents();
		for (int i = 0; i < stacks.length; i++) {
			ItemStack temp;
			if (stack.isSimilar(temp = stacks[i])) {
				int cnt;
				if ((cnt = temp.getAmount() - (count - rem)) > 0) {
					temp.setAmount(cnt);
					rem += count - rem;
					break;
				}
				rem += temp.getAmount();
				temp.setAmount(0);
			}
		}
		if (rem == count) {
			p.getInventory().setContents(stacks);
			return true;
		}
		return false;
	}

	public static boolean giveItem(Player p, ItemStack item) {
		if (item.getAmount() > 64) {
			giveItem(p, item, 64);
			return giveItem(p, item, item.getAmount() - 64);
		}
		if (p.getInventory().firstEmpty() != -1) {
			p.getInventory().addItem(new ItemStack[] { item });
			return false;
		}
		p.getWorld().dropItem(p.getLocation(), item);
		return true;
	}

	public static boolean giveItem(Player p, ItemStack item, int count) {
		ItemStack i = item.clone();
		i.setAmount(count);
		return giveItem(p, i);
	}

	public static String display(ItemStack item) {
		return item.hasItemMeta() && item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName()
				: item.getType().name();
	}
}
