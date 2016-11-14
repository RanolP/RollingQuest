package me.ranol.rollingquest;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.ranol.rollingquest.api.RollingAction;
import me.ranol.rollingquest.api.RollingCommand;
import me.ranol.rollingquest.commands.CmdQuestview;
import me.ranol.rollingquest.commands.CmdRollingQuestAdmin;
import me.ranol.rollingquest.commands.CmdRollingQuestUser;
import me.ranol.rollingquest.events.StaticEvents;
import me.ranol.rollingquest.management.DialogManager;
import me.ranol.rollingquest.management.NpcManager;
import me.ranol.rollingquest.management.QuestManager;
import me.ranol.rollingquest.menu.RealMenuListener;
import me.ranol.rollingquest.quest.modifiers.RollingModifier;
import me.ranol.rollingquest.util.RYamlConfiguration;

public class RollingQuest extends JavaPlugin {
	private static RYamlConfiguration config;

	private static RollingQuest instance;

	@Override
	public void onEnable() {
		instance = this;
		Bukkit.getPluginManager().registerEvents(new RealMenuListener(), this);
		RollingCommand.initialize();
		RollingModifier.initialize();
		RollingAction.initialize();
		StaticEvents.initialize();
		Language.initialize();
		if (!getDataFolder().exists()) {
			saveResource("dialog-marc.yml", false);
			saveResource("dialog-marc_complete.yml", false);
			saveResource("npc-marc.yml", false);
			saveResource("quest-marc.yml", false);
		}
		saveDefaultConfig();
		config = RYamlConfiguration.loadConfiguration(this, "config.yml");
		DialogManager.loadDialogs();
		NpcManager.loadNpcs();
		QuestManager.loadQuests();

		CmdRollingQuestAdmin rqadmin = new CmdRollingQuestAdmin();
		getCommand("rqa").setExecutor(rqadmin);
		getCommand("rqa").setTabCompleter(rqadmin);
		getCommand("rqa").setPermission("*");
		getCommand("rqa").setPermissionMessage("§6[§c!§6] §e오피만 사용 가능한 명령어입니다! §a[§f/rq§a]");

		CmdRollingQuestUser rq = new CmdRollingQuestUser();
		getCommand("rq").setExecutor(rq);
		getCommand("rq").setTabCompleter(rq);

		CmdQuestview questview = new CmdQuestview();
		getCommand("questview").setExecutor(questview);
		getCommand("questview").setTabCompleter(questview);
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
	}

	public static RollingQuest getInstance() {
		return instance;
	}

	public static int addRepeatingTask(Runnable run, int delay) {
		return Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(), run, 0, delay);
	}

	public static void registerEvents(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, getInstance());
	}

	public static void cancelTask(int id) {
		Bukkit.getScheduler().cancelTask(id);
	}

	public static int addDelayedTask(Runnable run, int delay) {
		return Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), run, delay);
	}

	public static boolean isLoggingLoad() {
		return config.getBoolean("view-load", true);
	}

	public static int defaultItem() {
		return config.getInt("default-item", 260);
	}
}
