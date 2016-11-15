package me.ranol.rollingquest.item;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class NamedItem {
	private String name;
	private String yamlFile;
	private ItemStack stack;

	public NamedItem(YamlConfiguration cfg, String key) {
		this.name = key;
		this.yamlFile = cfg.getCurrentPath();
		stack = new ItemStack(cfg.getInt(key + ".type", 1));
		stack.setDurability((short) cfg.getInt(key + ".durability", 0));
		stack.setAmount(cfg.getInt(key + ".count", 1));
		ItemMeta meta = stack.getItemMeta();
		if (cfg.contains(key + ".name"))
			meta.setDisplayName(cfg.getString(key + ".name"));
		if (cfg.contains(key + ".lores"))
			meta.setLore(cfg.getStringList(key + ".lores"));
		if (stack.getType() == Material.SKULL_ITEM && cfg.contains(key + ".skullowner"))
			((SkullMeta) meta).setOwner(cfg.getString(key + ".skullowner"));
		stack.setItemMeta(meta);
	}

	public NamedItem(ItemStack stack) {
		this.stack = stack;
	}

	public String getName() {
		return name;
	}

	public String getYAML() {
		return yamlFile;
	}

	public ItemStack getStack() {
		return stack;
	}

	public int getAmount() {
		return getStack().getAmount();
	}
}
