package me.ranol.rollingquest.management;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.ranol.rollingquest.RollingQuest;
import me.ranol.rollingquest.quest.DialogSet;
import me.ranol.rollingquest.quest.MessageDialog;
import me.ranol.rollingquest.quest.commands.RollingCommand;
import me.ranol.rollingquest.util.RYamlConfiguration;
import me.ranol.rollingquest.util.Util;

public class DialogManager {
	private static List<DialogSet> dialogs = new ArrayList<>();

	public static void loadDialogs() {
		File[] dialogFiles = RollingQuest.getInstance().getDataFolder()
				.listFiles(f -> f.isFile() && f.getName().startsWith("dialog"));
		for (File file : dialogFiles) {
			DialogSet set = new DialogSet(
					file.getName().substring(0, file.getName().lastIndexOf(".")).replaceFirst("dialog[-]?", ""));
			RYamlConfiguration cfg = RYamlConfiguration.loadConfiguration(file);
			cfg.getKeys(false).forEach(key -> {
				MessageDialog dialog = new MessageDialog(key);
				dialog.setMessage(cfg.getString(key + ".text"));
				dialog.setDisplay(cfg.getString(key + ".display"));
				dialog.setSlot(cfg.getInt(key + ".slot", 0));
				dialog.setStackId(cfg.getInt(key + ".item", RollingQuest.defaultItem()));
				dialog.setVisible(cfg.getBoolean(key + ".visible", true));
				cfg.getStringList(key + ".commands").forEach(s -> {
					try {
						dialog.addCommand(RollingCommand.createCommand(s));
					} catch (Exception e) {
					}
				});
				set.addDialog(dialog);
			});
			dialogs.add(set);
			if (RollingQuest.isLoggingLoad()) {
				Util.con("다이얼로그 \'" + set.getName() + "\'을(를) 로드하였습니다.");
			}
		}
	}

	public static List<DialogSet> getDialogs() {
		return dialogs;
	}

	public static DialogSet getDialogSet(String name) {
		List<DialogSet> filtered = dialogs.stream().filter(d -> d.getName().equals(name)).collect(Collectors.toList());
		return filtered.size() > 0 ? filtered.get(0) : null;
	}
}
