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
import me.ranol.rollingquest.api.Quest;
import me.ranol.rollingquest.api.RollingAction;
import me.ranol.rollingquest.exceptions.UnknownDialogException;
import me.ranol.rollingquest.exceptions.UnknownQuestException;
import me.ranol.rollingquest.quest.Npc;
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
			quest.setCompleteAction(RollingAction.createComplete(cfg.getString("complete-action", "click"), quest));
			String dialog = cfg.getString("completion-dialog", "");
			try {
				quest.setDialogSet(DialogManager.getDialogSet(dialog));
			} catch (UnknownDialogException e) {

			}
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

	public static Quest getQuest(String name) throws UnknownQuestException {
		List<Quest> filtered = quests.stream().filter(npc -> npc.getName().equals(name)).collect(Collectors.toList());
		if (filtered.isEmpty())
			throw new UnknownQuestException("Quest " + name + " is not exists.");
		return filtered.get(0);
	}

	public static void giveQuest(Player p, String name) throws UnknownQuestException {
		giveQuest(p, name, NpcManager.UNNAMED);
	}

	public static void takeQuest(Player p, String name) throws UnknownQuestException {
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

	public static void giveQuest(Player p, String name, Npc npc) throws UnknownQuestException {
		if (!hasQuests.containsKey(p.getUniqueId()))
			hasQuests.put(p.getUniqueId(), new ArrayList<>());
		hasQuests.get(p.getUniqueId()).removeIf(q -> name.equals(q.getName()));
		Quest quest = getQuest(name);
		quest.setGiver(npc);
		hasQuests.get(p.getUniqueId()).add(quest);
	}
}
