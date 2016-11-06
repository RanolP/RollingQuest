package me.ranol.rollingquest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.ranol.rollingquest.quest.Npc;
import me.ranol.rollingquest.util.RYamlConfiguration;
import me.ranol.rollingquest.util.Util;

public class NpcManager {
	private static List<Npc> npcs = new ArrayList<>();

	public static void loadNpcs() {
		File[] npcFiles = RollingQuest.getInstance().getDataFolder()
				.listFiles(f -> f.isFile() && f.getName().startsWith("npc"));
		for (File file : npcFiles) {
			RYamlConfiguration cfg = RYamlConfiguration.loadConfiguration(file);
			Npc npc = new Npc(file.getName()
					.substring(0, file.getName().lastIndexOf("."))
					.replaceFirst("npc[-]?", ""));
			npc.setVisibleName(cfg.getString("name", npc.getName()));
			npc.setDialogSet(DialogManager.getDialogSet(cfg
					.getString("dialog-set")));
			npcs.add(npc);
			if (RollingQuest.isLoggingLoad()) {
				Util.con("NPC \'" + npc.getName() + "\'을(를) 로드하였습니다.");
			}
		}
	}

	public static List<Npc> getNpcs() {
		return npcs;
	}
}
