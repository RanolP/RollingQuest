package me.ranol.rollingquest.util;

import java.util.HashMap;

public class PlaceHolders {
	private static final HashMap<String, String> holders = new HashMap<>();

	public static String replaceAll(String real) {
		WrappedString ws = new WrappedString(Wrap.empty(real));
		replaceAll(ws);
		return ws.get();
	}

	public static void replaceAll(WrappedString wrap) {
		holders.forEach(wrap::replace);
	}

	public static void addHolder(String holder, String replace) {
		holders.put(holder, replace);
	}
}
