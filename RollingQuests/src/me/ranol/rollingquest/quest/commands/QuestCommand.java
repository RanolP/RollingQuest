package me.ranol.rollingquest.quest.commands;

import java.util.HashMap;
import java.util.List;

import me.ranol.rollingquest.quest.QuestMenu;
import me.ranol.rollingquest.util.StringParser;

public interface QuestCommand {
	static final HashMap<String, QuestCommand> commands = new HashMap<>();

	public static void initialize() {
		register("dialog", new CmdDialog());
	}

	public static void register(String name, QuestCommand command) {
		commands.put(name, command);
	}

	public static QuestCommand createCommand(String args) {
		List<String> data = StringParser.parse(args);
		if (data.size() == 0)
			return null;
		QuestCommand command = commands.get(data.get(0));
		command.apply(data.subList(1, data.size()));
		return command;
	}

	public static QuestCommand getCommand(String name) {
		return commands.get(name);
	}

	public void apply(List<String> args);

	public void activate(QuestMenu menu);
}
