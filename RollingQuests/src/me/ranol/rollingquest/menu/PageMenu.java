package me.ranol.rollingquest.menu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class PageMenu<T> extends Menu {
	private int page = 1;
	protected int start;
	protected int end;
	protected List<T> pageItems = new ArrayList<>();
	private int maxPage;
	private List<PageModifizer<T>> modifizers = new ArrayList<>();

	public PageMenu(Player opener) {
		super(opener);
	}

	public T getByIndex(int index) {
		int i = index + (getPage() - 1) * (end - start);
		return pageItems.size() - 1 < i ? null : pageItems.get(i);
	}

	public void addModify(PageModifizer<T> modifizer) {
		modifizers.add(modifizer);
	}

	public void removeModify(PageModifizer<T> modifizer) {
		modifizers.remove(modifizer);
	}

	protected void update() {
		maxPage = (int) Math.ceil((double) pageItems.size() / (end - start));
	}

	protected void calculation() {
		int idx = start;
		for (int j = (getPage() - 1) * (end - start); j < pageItems.size() + end; j++) {
			if (idx > end)
				break;
			T obj = pageItems.size() > j ? pageItems.get(j) : null;
			ItemStack temp = obj != null ? getStack(obj) : null;
			if (obj != null && temp != null)
				modifizers.forEach(mod -> mod.modify(obj, temp));
			getInventory().setItem(idx++, temp);
		}
	}

	public abstract ItemStack getStack(T object);

	public int getPage() {
		return page;
	}

	public void setPage(int i) {
		page = i > maxPage ? maxPage : i < 1 ? 1 : i;
	}

	public void nextPage() {
		setPage(getPage() + 1);
	}

	public void backPage() {
		setPage(getPage() - 1);
	}

	public interface PageModifizer<T> {
		public void modify(T t, ItemStack stack);
	}

}
