package me.ranol.rollingquest.api;

import java.util.HashMap;
import java.util.List;

import me.ranol.rollingquest.quest.QuestMenu;
import me.ranol.rollingquest.quest.commands.CmdChatMessage;
import me.ranol.rollingquest.quest.commands.CmdCloseInv;
import me.ranol.rollingquest.quest.commands.CmdGiveItem;
import me.ranol.rollingquest.quest.commands.CmdGiveQuest;
import me.ranol.rollingquest.quest.commands.CmdHide;
import me.ranol.rollingquest.quest.commands.CmdIssueCommand;
import me.ranol.rollingquest.quest.commands.CmdIssueCommandConsole;
import me.ranol.rollingquest.quest.commands.CmdIssueCommandOp;
import me.ranol.rollingquest.quest.commands.CmdOpenSet;
import me.ranol.rollingquest.quest.commands.CmdShow;
import me.ranol.rollingquest.quest.commands.CmdTeleport;
import me.ranol.rollingquest.util.StringParser;

public interface RollingCommand {
	static final HashMap<String, Class<? extends RollingCommand>> commands = new HashMap<>();

	public static void initialize() {
		register("show", CmdShow.class);
		register("hide", CmdHide.class);
		register("close", CmdCloseInv.class);
		register("chat", CmdChatMessage.class);
		register("cmd", CmdIssueCommand.class);
		register("cmdop", CmdIssueCommandOp.class);
		register("cmdcon", CmdIssueCommandConsole.class);
		register("tp", CmdTeleport.class);
		register("gquest", CmdGiveQuest.class);
		register("openset", CmdOpenSet.class);
		register("gitem", CmdGiveItem.class);
	}

	public static void register(String name, Class<? extends RollingCommand> command) {
		commands.put(name, command);
	}

	public static RollingCommand createCommand(String args) {
		List<String> data = StringParser.parse(args);
		if (data.isEmpty())
			return null;
		RollingCommand command = getCommand(data.get(0));
		if (command != null)
			command.apply(data.subList(1, data.size()));
		return command;
	}

	public static RollingCommand getCommand(String name) {
		try {
			return commands.get(name).newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	public void apply(List<String> args);

	public void activate(QuestMenu menu);
}
