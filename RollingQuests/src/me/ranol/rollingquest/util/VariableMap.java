package me.ranol.rollingquest.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Optional;

public class VariableMap implements Serializable {
	private static final long serialVersionUID = -7404454490355987716L;
	HashMap<String, Object> map = new HashMap<>();

	public Optional<Object> get(String name) {
		if (map.containsKey(name))
			return Optional.of(map.get(name));
		return Optional.empty();
	}

	public void set(String name, Object value) {
		map.put(name, value);
	}

	public void remove(String key) {
		map.remove(key);
	}
}
