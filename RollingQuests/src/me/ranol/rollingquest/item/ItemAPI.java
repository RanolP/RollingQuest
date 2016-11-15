package me.ranol.rollingquest.item;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import me.ranol.rollingquest.util.RYamlConfiguration;

public class ItemAPI {
	private static HashMap<String, NamedItem> map = new HashMap<>();

	public static void register(RYamlConfiguration config) {
		config.getKeys(false).forEach(key -> map.put(key + ":?" + config.getCurrentPath(), new NamedItem(config, key)));
	}

	public static List<NamedItem> getItem(String name) {
		return map.keySet().stream().filter(s -> s.startsWith(name)).map(map::get).collect(Collectors.toList());
	}

	public static NamedItem getItem(String name, RYamlConfiguration cfg) {
		List<NamedItem> collected = map.keySet().stream().filter((name + ":?" + cfg.getCurrentPath())::equals)
				.map(map::get).collect(Collectors.toList());
		return collected.isEmpty() ? null : collected.get(0);
	}
}
