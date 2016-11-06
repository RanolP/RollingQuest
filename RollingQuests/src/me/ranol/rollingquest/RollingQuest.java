package me.ranol.rollingquest;

import me.ranol.rollingquest.menu.RealMenuListener;
import me.ranol.rollingquest.quest.Npc;
import me.ranol.rollingquest.quest.Quest;
import me.ranol.rollingquest.quest.QuestDialog;
import me.ranol.rollingquest.quest.commands.QuestCommand;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RollingQuest extends JavaPlugin {
	Npc npc = new Npc("Marc");
	Quest quest = new Quest("Pizza").setGiver(npc);

	void abc() {
		QuestDialog first = new QuestDialog("marc1")
				.setMessage("§f야, 나 §c배고파§f.").setDisplay("§aMarc와 대화하기")
				.setSlot(20).setStackId(260).setVisible(true);
		QuestDialog second = new QuestDialog("marc2")
				.setMessage("§f여기 §c피자§f가 그렇게 맛있다면서?")
				.setDisplay("§aMarc와 대화하기").setSlot(20).setStackId(260);
		QuestDialog third = new QuestDialog("marc3")
				.setMessage("§c피자 §e10개§f만 사 와.").setDisplay("§aMarc와 대화하기")
				.setSlot(20).setStackId(260);
		first.addCommand(QuestCommand.createCommand("dialog marc2"));
		second.addCommand(QuestCommand.createCommand("dialog marc3"));
		quest.addDialog(first);
		quest.addDialog(second);
		quest.addDialog(third);
	}

	private static RollingQuest instance;

	@Override
	public void onEnable() {
		instance = this;
		Bukkit.getPluginManager().registerEvents(new RealMenuListener(), this);
		QuestCommand.initialize();
		abc();
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

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		if (!(s instanceof Player))
			return false;
		s.sendMessage(quest.toString());
		quest.openUI((Player) s);
		return true;
	}
}
