package me.ranol.rollingquest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerData implements Serializable {
	private static final long serialVersionUID = -4004287378843181301L;
	private HashMap<UUID, List<String>> questSet = new HashMap<>();
	File file;

	public HashMap<UUID, List<String>> questMap() {
		return questSet;
	}

	public void giveQuest(Player p, String name) {
		if (!questSet.containsKey(p.getUniqueId()))
			questSet.put(p.getUniqueId(), new ArrayList<>());
		if (!questSet.get(p.getUniqueId()).contains(name))
			questSet.get(p.getUniqueId()).add(name);
		save();
	}

	public void takeQuest(Player p, String name) {
		if (!questSet.containsKey(p.getUniqueId()))
			return;
		questSet.get(p.getUniqueId()).remove(name);
		if (questSet.get(p.getUniqueId()).isEmpty())
			questSet.remove(p.getUniqueId());
		save();
	}

	public PlayerData(String file) {
		this.file = new File(RollingQuest.getInstance().getDataFolder(), file);
	}

	public void save() {
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

	public void load() {
		if (file.exists())
			try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(file))) {
				PlayerData temp = (PlayerData) oos.readObject();
				this.questSet = temp.questSet;
				System.out.println(temp.questSet);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
