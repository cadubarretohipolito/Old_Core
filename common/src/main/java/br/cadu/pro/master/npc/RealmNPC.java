package br.cadu.pro.master.npc;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface RealmNPC {
    String getName();
    void showTo(Player player);
    void hideFrom(Player player);
    void delete();
    Location getLocation();
    int getId();
}
