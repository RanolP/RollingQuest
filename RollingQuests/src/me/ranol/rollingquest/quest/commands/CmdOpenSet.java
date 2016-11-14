package me.ranol.rollingquest.quest.commands;

import java.util.List;

import me.ranol.rollingquest.api.DialogSet;
import me.ranol.rollingquest.api.RollingCommand;
import me.ranol.rollingquest.exceptions.UnknownDialogException;
import me.ranol.rollingquest.management.DialogManager;
import me.ranol.rollingquest.quest.QuestMenu;
import me.ranol.rollingquest.util.Util;

public class CmdOpenSet implements RollingCommand {
	DialogSet dialog;

	@Override
	public void apply(List<String> args) {
		try {
			dialog = DialogManager.getDialogSet(args.get(0));
		} catch (UnknownDialogException e) {
			Util.warn(args.get(0) + "을 찾을 수 없습니다.");
		}
	}

	@Override
	public void activate(QuestMenu menu) {
		dialog.openUI(menu.getPlayer());
	}
}
