package me.ranol.rollingquest;

import me.ranol.rollingquest.menu.RealMenuListener;
import me.ranol.rollingquest.quest.commands.DialogCommand;
import me.ranol.rollingquest.util.RYamlConfiguration;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RollingQuest extends JavaPlugin {
	private static RYamlConfiguration config;

	private static RollingQuest instance;

	@Override
	public void onEnable() {
		instance = this;
		Bukkit.getPluginManager().registerEvents(new RealMenuListener(), this);
		DialogCommand.initialize();
		if (!getDataFolder().exists()) {
			saveResource("dialog-marc.yml", false);
			saveResource("npc-marc.yml", false);
			saveResource("quest-marc.yml", false);
		}
		saveDefaultConfig();
		config = RYamlConfiguration.loadConfiguration(this, "config.yml");
		DialogManager.loadDialogs();
		NpcManager.loadNpcs();

		CmdQuestlist questlist = new CmdQuestlist();
		getCommand("questlist").setExecutor(questlist);
		getCommand("questlist").setTabCompleter(questlist);

		CmdNpclist npclist = new CmdNpclist();
		getCommand("npclist").setExecutor(npclist);
		getCommand("npclist").setTabCompleter(npclist);

		CmdDialoglist dialoglist = new CmdDialoglist();
		getCommand("dialoglist").setExecutor(dialoglist);
		getCommand("dialoglist").setTabCompleter(dialoglist);

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
		return Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(),
				run, 0, delay);
	}

	public static void cancelTask(int id) {
		Bukkit.getScheduler().cancelTask(id);
	}

	public static int addDelayedTask(Runnable run, int delay) {
		return Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(),
				run, delay);
	}

	public static boolean isLoggingLoad() {
		return config.getBoolean("view-load", true);
	}

	public static int defaultItem() {
		return config.getInt("default-item", 260);
	}
}
