package me.ranol.rollingquest.quest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.ranol.rollingquest.RollingQuest;
import me.ranol.rollingquest.quest.commands.QuestCommand;
import me.ranol.rollingquest.util.WordManager;
import me.ranol.rollingquest.util.Wrap;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class QuestDialog implements Cloneable {
	private String msg;
	private int slot;
	private int stackId;
	private String display;
	private Wrap<Integer> id = Wrap.empty();
	private boolean visible = false;
	private final String name;
	private Wrap<Boolean> skip = Wrap.empty(false);
	private ArrayList<QuestCommand> commands = new ArrayList<QuestCommand>();

	public QuestDialog(String name) {
		this.name = name;
	}

	public String getMessage() {
		return msg;
	}

	public QuestDialog setMessage(String s) {
		this.msg = s;
		return this;
	}

	@Override
	public String toString() {
		return "QuestDialog[message=" + msg + ", visible=" + isVisible() + "]";
	}

	public int getSlot() {
		return slot;
	}

	public QuestDialog setSlot(int slot) {
		this.slot = slot;
		return this;
	}

	public int getStackId() {
		return stackId;
	}

	public QuestDialog setStackId(int stackId) {
		this.stackId = stackId;
		return this;
	}

	public String getDisplay() {
		return display;
	}

	public QuestDialog setDisplay(String display) {
		this.display = display;
		return this;
	}

	public void visible(QuestMenu menu) {
		if (isVisible()) {
			Wrap<Integer> index = Wrap.empty(0);
			List<String> words = WordManager.typingAll(msg, true);
			id.set(RollingQuest.addRepeatingTask(() -> {
				if (!skip.isEmpty() && skip.get()) {
					RollingQuest.cancelTask(id.get());
					id.toEmpty();
					skip.toEmpty();
					ItemStack temp = getItemStack(words.get(words.size() - 1));
					menu.getInventory().setItem(getSlot(), temp);
					menu.getPlayer().updateInventory();
					return;
				}
				if (words.size() - 1 <= index.get()) {
					RollingQuest.cancelTask(id.get());
					id.toEmpty();
					return;
				}
				ItemStack temp = getItemStack(words.get(index.get()));
				menu.getInventory().setItem(getSlot(), temp);
				menu.getPlayer().updateInventory();
				index.set(index.get() + 1);
			}, 2));
		}
	}

	public void cancelTask() {
		if (isPlaying()) {
			RollingQuest.cancelTask(id.get());
			id.toEmpty();
		}
	}

	private ItemStack getItemStack(String... lores) {
		ItemStack stack = new ItemStack(stackId);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(display);
		meta.setLore(Arrays.asList(lores));
		stack.setItemMeta(meta);
		return stack;
	}

	public void addCommand(QuestCommand command) {
		if (command == null)
			return;
		commands.add(command);
	}

	public void activate(QuestMenu menu) {
		System.out.println(toString() + " 호출됨.");
		RollingQuest.addDelayedTask(
				() -> commands.forEach(c -> c.activate(menu)), 1);
	}

	public boolean isVisible() {
		return visible;
	}

	public boolean isPlaying() {
		return !id.isEmpty();
	}

	public QuestDialog setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}

	public String getName() {
		return name;
	}

	public void skip() {
		if (isPlaying()) {
			skip.set(true);
		}
	}

	@Override
	public QuestDialog clone() {
		QuestDialog dialog = new QuestDialog(this.name);
		dialog.commands = new ArrayList<>();
		this.commands.forEach(dialog.commands::add);
		dialog.display = this.display;
		dialog.id = this.id.clone();
		dialog.msg = this.msg;
		dialog.skip = this.skip.clone();
		dialog.slot = this.slot;
		dialog.stackId = this.stackId;
		dialog.visible = this.visible;
		return dialog;
	}
}
