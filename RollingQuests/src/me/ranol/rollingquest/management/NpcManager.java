package me.ranol.rollingquest.management;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.ranol.rollingquest.RollingQuest;
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
			Npc npc = new Npc(file.getName().substring(0, file.getName().lastIndexOf(".")).replaceFirst("npc[-]?", ""));
			npc.setVisibleName(cfg.getString("name", npc.getName()));
			npc.setDialogSet(DialogManager.getDialogSet(cfg.getString("dialog-set")));
			npcs.add(npc);
			if (RollingQuest.isLoggingLoad()) {
				Util.con("NPC \'" + npc.getName() + "\'을(를) 로드하였습니다.");
			}
		}
	}

	public static List<Npc> getNpcs() {
		return npcs;
	}

	public static Npc getNpc(String name) {
		List<Npc> filtered = npcs.stream().filter(npc -> npc.getName().equals(name)).collect(Collectors.toList());
		return filtered.isEmpty() ? null : filtered.get(0);
	}

	public static boolean availableNpc(String name) {
		return npcs.stream().filter(npc -> npc.getName().equals(name)).count() > 0;
	}
}
