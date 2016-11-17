package me.ranol.rollingquest.util;

import java.util.List;

public class CompletionUtils {
	public static void complete(List<String> real, String arg) {
		real.removeIf(s -> !s.toLowerCase().startsWith(arg.toLowerCase()));
	}
}
