package me.ranol.rollingquest.management;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import me.ranol.rollingquest.RollingQuest;
import me.ranol.rollingquest.completableactions.CompletableAction;
import me.ranol.rollingquest.quest.Quest;
import me.ranol.rollingquest.quest.modifiers.RollingModifier;
import me.ranol.rollingquest.util.PlaceHolders;
import me.ranol.rollingquest.util.RYamlConfiguration;
import me.ranol.rollingquest.util.Util;

public class QuestManager {

	private static List<Quest> quests = new ArrayList<>();
	private static HashMap<UUID, List<Quest>> hasQuests = new HashMap<>();

	public static void loadQuests() {
		File[] questFiles = RollingQuest.getInstance().getDataFolder()
				.listFiles(f -> f.isFile() && f.getName().startsWith("quest"));
		for (File file : questFiles) {
			RYamlConfiguration cfg = RYamlConfiguration.loadConfiguration(file);
			Quest quest = new Quest(cfg.getString("name", ""));
			quest.setDisplayName(cfg.getString("visible", quest.getName()));
			cfg.getStringList("modifiers").stream().map(RollingModifier::createModifier).collect(Collectors.toList())
					.forEach(quest::addModifiers);
			quest.setStackId(cfg.getInt("item", RollingQuest.defaultItem()));
			quest.setCompleteAction(CompletableAction.createComplete(cfg.getString("complete-action", "click"))
					.setQuest(quest).bindPlayer(quest::complete));
			quest.setDialog(DialogManager.getDialogSet(cfg.getString("completion-dialog", "")));
			quests.add(quest);
			PlaceHolders.addHolder("<quest:" + quest.getName() + ">", quest.getDisplayName());
			if (RollingQuest.isLoggingLoad()) {
				Util.con("퀘스트 \'" + quest.getName() + "\'을(를) 로드하였습니다.");
			}
		}
	}

	public static List<Quest> getQuests() {
		return quests;
	}

	public static Quest getQuest(String name) {
		List<Quest> filtered = quests.stream().filter(npc -> npc.getName().equals(name)).collect(Collectors.toList());
		return filtered.isEmpty() ? null : filtered.get(0);
	}

	public static void giveQuest(Player p, String name) {
		if (!hasQuests.containsKey(p.getUniqueId()))
			hasQuests.put(p.getUniqueId(), new ArrayList<>());
		hasQuests.get(p.getUniqueId()).removeIf(q -> name.equals(q.getName()));
		Quest quest = getQuest(name);
		if (quest != null)
			hasQuests.get(p.getUniqueId()).add(quest);
	}

	public static void takeQuest(Player p, String name) {
		if (!hasQuests.containsKey(p.getUniqueId()))
			hasQuests.put(p.getUniqueId(), new ArrayList<>());
		Quest quest = getQuest(name);
		if (quest != null)
			hasQuests.get(p.getUniqueId()).remove(quest);
	}

	public static boolean availableQuest(String name) {
		return quests.stream().filter(npc -> npc.getName().equals(name)).count() > 0;
	}

	public static List<Quest> hasQuests(Player player) {
		return hasQuests.containsKey(player.getUniqueId()) ? hasQuests.get(player.getUniqueId())
				: Collections.emptyList();
	}
}
