package me.ranol.rollingquest.util;

import java.util.List;
import java.util.stream.Collectors;

public class CompletionUtils {
	public static void complete(List<String> real, String arg) {
		List<String> filtered = real.stream().filter(r -> r.toLowerCase().startsWith(arg.toLowerCase()))
				.collect(Collectors.toList());
		real.clear();
		real.addAll(filtered);
	}
}
