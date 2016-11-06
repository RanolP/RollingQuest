package me.ranol.rollingquest.quest.commands;

import java.util.List;

import me.ranol.rollingquest.quest.QuestDialog;
import me.ranol.rollingquest.quest.QuestMenu;

public class CmdDialog implements QuestCommand {
	String dialog;

	@Override
	public void apply(List<String> args) {
		dialog = args.get(0);
	}

	@Override
	public void activate(QuestMenu menu) {
		QuestDialog dialog;
		if ((dialog = menu.getQuest().getDialog(this.dialog)) != null) {
			dialog.setVisible(true);
			menu.enableDialog(dialog);
		}
	}
}
