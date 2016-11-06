package me.ranol.rollingquest.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.ArrayUtils;

public class WordManager {
	private static char[] 초성 = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ".toCharArray();
	private static char[] 중성 = "ㅏㅐㅑㅒㅓㅔㅕㅖㅗㅘㅙㅚㅛㅜㅝㅞㅟㅠㅡㅢㅣ".toCharArray();
	private static char[] 종성 = " ㄱㄲㄳㄴㄵㄶㄷㄹㄺㄻㄼㄽㄾㄿㅀㅁㅂㅄㅅㅆㅇㅈㅊㅋㅌㅍㅎ".toCharArray();
	private static HashMap<Character, char[]> back = new HashMap<>();

	static {
		back.put('ㄱ', new char[] { 'ㄳ' });
		back.put('ㄴ', new char[] { 'ㄵ', 'ㄶ' });
		back.put('ㄹ', new char[] { 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ' });
		back.put('ㅂ', new char[] { 'ㅄ' });

		back.put('ㅗ', new char[] { 'ㅘ', 'ㅙ', 'ㅚ' });
		back.put('ㅜ', new char[] { 'ㅝ', 'ㅞ', 'ㅟ' });
		back.put('ㅡ', new char[] { 'ㅢ' });
	}

	public static List<String> typingAll(String real, boolean smoothKorea) {
		List<String> texts = new ArrayList<>();
		StringBuilder now = new StringBuilder();
		for (int i = 0; i < real.length(); i++) {
			String get = real.substring(i, i + 1);
			if (get.matches("[가-힣]") && smoothKorea) {
				toType(get.charAt(0)).stream().map(s -> now + s)
						.forEach(texts::add);
				now.delete(0, now.length());
				now.append(texts.get(texts.size() - 1));
			} else if (get.equals("§") || get.equals(" ")) {
				int found = find(i, real);
				get = real.substring(i, i + found + 1);
				i += found;
				texts.add(now + get);
				now.append(get);
			} else {
				texts.add(now + get);
				now.append(get);
			}
		}
		return texts;
	}

	public static int find(int from, String s) {
		int find = 0;
		for (int i = from; i < s.length(); i++) {
			switch (s.substring(i, i + 1)) {
			case "§":
				find++;
				break;
			case " ":
				find++;
				break;
			}
		}
		return find;
	}

	public static List<String> toType(char c) {
		List<Character> types = new ArrayList<>();
		char[] arr = uncraft(c);
		types.add(arr[0]);
		if (isDuplicate(arr[1])) {
			types.add(craft(arr[0], getBack(arr[1]), ' '));
		}
		types.add(craft(arr[0], arr[1], ' '));
		if (arr[2] != ' ') {
			if (isDuplicate(arr[2])) {
				types.add(craft(arr[0], arr[1], getBack(arr[2])));
			}
			types.add(craft(arr[0], arr[1], arr[2]));
		}
		return types.stream().map(String::valueOf).collect(Collectors.toList());
	}

	private static char getBack(char c) {
		char result = c;
		for (char key : back.keySet()) {
			if (ArrayUtils.contains(back.get(key), c)) {
				result = key;
				break;
			}
		}
		return result;
	}

	private static boolean isDuplicate(char c) {
		boolean result = false;
		for (char[] chars : back.values()) {
			if (ArrayUtils.contains(chars, c)) {
				result = true;
				break;
			}
		}
		return result;
	}

	private static char craft(char 초, char 중, char 종) {
		return (char) (44032 + (ArrayUtils.indexOf(초성, 초) * 588)
				+ (ArrayUtils.indexOf(중성, 중) * 28) + (ArrayUtils.indexOf(종성, 종)));
	}

	private static char[] uncraft(char all) {
		int code = all - 44032;
		char[] temp = { 초성[code / 588], 중성[code % 588 / 28], ' ' };
		temp[2] = 종성[(code % 588) - (28 * ArrayUtils.indexOf(중성, temp[1]))];
		return temp;
	}
}
