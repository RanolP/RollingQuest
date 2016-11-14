package me.ranol.rollingquest.util;

public class WrappedString extends Wrap<String> {
	public WrappedString(Wrap<? extends String> wrap) {
		set(wrap.get());
	}

	public void replace(String oldStr, String newStr) {
		set(get().replace(oldStr, newStr));
	}
}
