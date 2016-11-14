package me.ranol.rollingquest.quest.commands;

import java.util.List;

import me.ranol.rollingquest.api.MessageDialog;
import me.ranol.rollingquest.api.RollingCommand;
import me.ranol.rollingquest.quest.QuestMenu;

public class CmdHide implements RollingCommand {
	String dialog;

	@Override
	public void apply(List<String> args) {
		dialog = args.get(0);
	}

	@Override
	public void activate(QuestMenu menu) {
		MessageDialog dialog;
		if ((dialog = menu.getDialog(this.dialog)) != null) {
			menu.disableDialog(dialog);
		}
	}
}
