package com.gmail.justbru00.nethercube.elytra.map;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.justbru00.nethercube.elytra.enums.MapDifficulty;
import com.gmail.justbru00.nethercube.elytra.enums.MapLength;
import com.gmail.justbru00.nethercube.elytra.main.NetherCubeElytra;
import com.gmail.justbru00.nethercube.elytra.utils.ItemBuilder;

/**
 * This class handles importing maps from the config.
 * @author Justin Brubaker
 *
 */
public class MapManager {
	
	private static ArrayList<Map> maps = new ArrayList<Map>();
	
	public static void init() {	
		FileConfiguration c = NetherCubeElytra.getInstance().getConfig();
		Set<String> mapKeys = c.getConfigurationSection("maps").getKeys(false);		
		
		for (String mapKey : mapKeys) {
			Map m = new Map(mapKey);
			
			String prePath = "maps." + mapKey + ".";
			
			m.setGuiItem(new ItemBuilder(Material.valueOf(c.getString("maps." + mapKey + ".item.material"))).setDataValue(c.getInt("maps." + mapKey + ".item.data")).setName("maps." + mapKey + ".item.displayname").build());
			m.setDifficulty(MapDifficulty.fromString(c.getString("maps." + mapKey + "difficulty")));
			m.setCreatorName(c.getString("maps." + mapKey + ".creatorname"));
			m.setInternalName(mapKey);
			m.setLength(MapLength.fromString(c.getString("maps." + mapKey +".length")));
			m.setPurchaseCost(c.getInt(prePath + "purchasecost"));
			m.setRewardAmount(c.getInt(prePath + "rewardamount"));
			Location start;
			Location end;
			
			start = new Location(Bukkit.getWorld(c.getString(prePath + "startlocation.world")), c.getInt(prePath + "startlocation.x"), c.getInt(prePath + "startlocation.y"), c.getInt(prePath + "startlocation.z"));
			m.setStartPlateLocation(start);
			
			end = new Location(Bukkit.getWorld(c.getString(prePath + "endinglocation.world")), c.getInt(prePath + "endinglocation.x"), c.getInt(prePath + "endinglocation.y"), c.getInt(prePath + "endinglocation.z"));
			m.setEndingPlateLocation(end);
			
			maps.add(m);
		}
	}
	
	/**
	 * Searches all maps to see if one has the location registered as a plate location.
	 * @param loc The location to check.
	 * @return The map the plate belongs to, otherwise returns null.
	 */
	public static Map getMapFromPlateLocation(Location loc) {
		for (Map m : maps) {
			if (m.getStartPlateLocation().equals(loc) || m.getEndingPlateLocation().equals(loc)) {
				return m;
			}
		}
		
		return null;
	}

}
