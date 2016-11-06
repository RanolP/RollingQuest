package me.ranol.rollingquest.util;

public class Wrap<T> {
	T obj;

	public Wrap<T> set(T obj) {
		this.obj = obj;
		return this;
	}

	public T get() {
		return obj;
	}

	public static <U> Wrap<U> empty() {
		return new Wrap<U>();
	}

	public static <U> Wrap<U> empty(U with) {
		return new Wrap<U>().set(with);
	}

	public boolean isEmpty() {
		return obj == null;
	}

	public void toEmpty() {
		obj = null;
	}
}
