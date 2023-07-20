package br.cadu.pro.master.npc;

import lombok.Builder;
import lombok.Getter;
import org.bukkit.Location;

@Builder
@Getter
public class RealmNPCOptions {
    private final String name;
    private final String texture;
    private final String signature;
    private final Location location;
    private final boolean hideNametag;
}