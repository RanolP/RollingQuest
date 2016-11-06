package me.ranol.rollingquest.util;

import java.util.ArrayList;
import java.util.List;

public class StringParser {
	public static List<String> parse(String real) {
		real = real + " ";
		List<String> result = new ArrayList<>();
		int last = 0;
		int index = -1;
		boolean opened = false;
		while ((index = real.indexOf(" ", index + 1)) != -1) {
			String parsed = real.substring(last, index).trim();
			if (!parsed.endsWith("\'")) {
				if (parsed.startsWith("\'")) {
					opened = true;
					continue;
				}
			} else if (opened) {
				opened = false;
				result.add(parsed.substring(1, parsed.length() - 1));
				last = index;
				continue;
			}
			result.add(parsed);
			last = index;
		}
		return result;
	}

	public static String parse(List<String> values) {
		StringBuilder builder = new StringBuilder();
		values.stream().map(s -> s + " ").forEach(builder::append);
		return builder.toString();
	}
}
