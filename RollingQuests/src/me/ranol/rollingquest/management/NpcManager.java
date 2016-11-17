package me.ranol.rollingquest.management;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.ranol.rollingquest.RollingQuest;
import me.ranol.rollingquest.exceptions.UnknownDialogException;
import me.ranol.rollingquest.exceptions.UnknownNpcException;
import me.ranol.rollingquest.quest.Npc;
import me.ranol.rollingquest.util.RYamlConfiguration;
import me.ranol.rollingquest.util.Util;

public class NpcManager {
	public static final Npc UNNAMED = new Npc("RollingQuest_UnnamedNpc").setVisibleName("Unnamed");
	private static List<Npc> npcs = new ArrayList<>();

	public static void loadNpcs() {
		File[] npcFiles = RollingQuest.getInstance().getDataFolder()
				.listFiles(f -> f.isFile() && f.getName().startsWith("npc"));
		for (File file : npcFiles) {
			RYamlConfiguration cfg = RYamlConfiguration.loadConfiguration(file);
			Npc npc = new Npc(file.getName().substring(0, file.getName().lastIndexOf(".")).replaceFirst("npc[-]?", ""));
			npc.setVisibleName(cfg.getString("name", npc.getName()));
			List<String> hasDialogs = cfg.getStringList("has-dialogs");
			hasDialogs.forEach(s -> {
				try {
					npc.addDialogSet(DialogManager.getDialogSet(s));
				} catch (UnknownDialogException e) {
					Util.warn("Npc §f" + npc.getName() + " (" + npc.getVisibleName() + ") §r에서 알 수 없는 다이얼로그 " + s
							+ "를 로드했습니다.");
				}
			});
			npcs.add(npc);
			if (RollingQuest.isLoggingLoad()) {
				Util.con("NPC \'" + npc.getName() + "\'을(를) 로드하였습니다.");
			}
		}
	}

	public static List<Npc> getNpcs() {
		return npcs;
	}

	public static Npc getNpc(String name) throws UnknownNpcException {
		for (Npc npc : npcs) {
			if (npc.getName().equals(name))
				return npc;
		}
		throw new UnknownNpcException("Npc " + name + " is not exists.");
	}

	public static boolean availableNpc(String name) {
		return npcs.stream().filter(npc -> npc.getName().equals(name)).count() > 0;
	}
}
