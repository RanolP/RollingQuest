package me.ranol.rollingquest.util;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerVariable {
	HashMap<UUID, VariableMap> variable = new HashMap<>();

	public VariableMap getMap(Player p) {
		if (!variable.containsKey(p.getUniqueId()))
			variable.put(p.getUniqueId(), new VariableMap());
		return variable.get(p.getUniqueId());
	}

	public Optional<Object> getVariable(Player p, String key) {
		return Optional.ofNullable(getMap(p).get(key));
	}

	public void setVariable(Player p, String key, Object value) {
		getMap(p).set(key, value);
	}
}
