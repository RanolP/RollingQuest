package me.ranol.rollingquest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Optional;

import me.ranol.rollingquest.util.VariableMap;

public class StaticVariables implements Serializable{
	private static final long serialVersionUID = -732292596370222554L;
	private VariableMap map = new VariableMap();
	private static StaticVariables instance;
	private File file;

	public static StaticVariables getInstance() {
		if (instance == null)
			synchronized (StaticVariables.class) {
				instance = new StaticVariables();
			}
		return instance;
	}

	public void setFile(File f) {
		this.file = f;
	}

	public void i_save() {
		if (!this.file.exists()) {
			try {
				this.file.createNewFile();
			} catch (Exception e) {
			}
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void save() {
		getInstance().i_save();
	}

	public void i_load() {
		if (file.exists())
			try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file))) {
				StaticVariables temp = (StaticVariables) oos.readObject();
				this.map = temp.map;
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public static void load() {
		getInstance().i_load();
	}

	public void i_set(String key, Object val) {
		map.set(key, val);
		i_save();
	}

	public static void set(String key, Object val) {
		getInstance().i_set(key, val);
	}

	public <T> Optional<T> i_get(String key) {
		return (Optional<T>) map.get(key);
	}

	public static <T> Optional<T> get(String key) {
		return getInstance().i_get(key);
	}

	public int i_getInt(String key) {
		return (int) i_get(key).orElse(0);
	}

	public static int getInt(String key) {
		return getInstance().i_getInt(key);
	}

	public String i_getString(String key) {
		return (String) i_get(key).orElse("");
	}

	public static String getString(String key) {
		return getInstance().i_getString(key);
	}

	public boolean i_getBoolean(String key) {
		return (boolean) i_get(key).orElse(false);
	}

	public static boolean getBoolean(String key) {
		return getInstance().i_getBoolean(key);
	}

	public double i_getDouble(String key) {
		return (double) i_get(key).orElse(0.0d);
	}

	public static double getDouble(String key) {
		return getInstance().i_getDouble(key);
	}

	public void i_remove(String key) {
		map.remove(key);
	}

	public static void remove(String key) {
		getInstance().i_remove(key);
	}
}
