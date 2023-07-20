package br.cadu.pro.master;

import br.cadu.pro.master.npc.RealmNPCManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyBlockCore extends JavaPlugin {

    private static SkyBlockCore instance;
    private RealmNPCManager npcManager;


    private final boolean USE_REFLECTION = true;


    private String active[] = {
            "   §a§lREALM NETWORK",
            "§aSkyBlock Core activated",
            ""
    };
    @Override
    public void onEnable() {
        instance = this;
        this.npcManager = new RealmNPCManager(this, USE_REFLECTION);

        getServer().getConsoleSender().sendMessage(active);
    }

    @Override
    public void onDisable() {
    }

    void listeners() {

    }

    void commands() {

    }

    public static SkyBlockCore getInstance() {
        return instance;
    }
}
