package br.cadu.pro.master.npc;

import br.cadu.pro.master.nms.NMSHelper;
import br.cadu.pro.master.nms.ServerVersion;
import br.cadu.pro.master.npc.events.NPCClickAction;
import br.cadu.pro.master.npc.events.NPCInteractionEvent;
import br.cadu.pro.master.npc.version.v1_16_R3;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RealmNPCManager {
    private final JavaPlugin plugin;
    private final boolean useReflection;

    private final Set<RealmNPC> registeredNPCs = new HashSet<>();

    public RealmNPCManager(JavaPlugin plugin, boolean useReflection) {
        this.plugin = plugin;
        this.useReflection = useReflection;

        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(plugin, PacketType.Play.Client.USE_ENTITY) {
                    @Override
                    public void onPacketReceiving(PacketEvent event) {
                        EnumWrappers.EntityUseAction useAction = event.getPacket().getEntityUseActions().read(0);
                        int entityId = event.getPacket().getIntegers().read(0);
                        handleEntityClick(event.getPlayer(), entityId, NPCClickAction.fromProtocolLibAction(useAction));
                    }
                }
        );
    }

    private final Cache<Player, RealmNPC> clickedNPCCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1L, TimeUnit.SECONDS)
            .build();

    private void handleEntityClick(Player player, int entityId, NPCClickAction action) {
        registeredNPCs.stream()
                .filter(npc -> npc.getId() == entityId)
                .forEach(npc -> Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
                    RealmNPC previouslyClickedNPC = clickedNPCCache.getIfPresent(player);
                    if (previouslyClickedNPC != null && previouslyClickedNPC.equals(npc)) return; // If they've clicked this same NPC in the last 0.5 seconds ignore this click
                    clickedNPCCache.put(player, npc);

                    NPCInteractionEvent event = new NPCInteractionEvent(npc, player, action);
                    Bukkit.getPluginManager().callEvent(event);
                }, 2));
    }

    public RealmNPC newNPC(RealmNPCOptions options) {
        ServerVersion serverVersion = NMSHelper.getInstance().getServerVersion();
        RealmNPC npc = null;

        if (useReflection) {
            serverVersion = ServerVersion.REFLECTED;
        }

        switch (serverVersion) {
            case REFLECTED:
                npc = new RealmNPCReflection(plugin, options);
                break;
            case v1_16_R3:
                npc = new v1_16_R3(plugin, options);
                break;

        }

        if (npc == null) {
            throw new IllegalStateException("Versão inválida do servidor " + serverVersion + ". Este plugin precisa ser atualizado!");
        }

        registeredNPCs.add(npc);
        return npc;
    }

    public Optional<RealmNPC> findNPC(String name) {
        return registeredNPCs.stream()
                .filter(npc -> npc.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public void deleteNPC(RealmNPC npc) {
        npc.delete();
        registeredNPCs.remove(npc);
    }

    public void deleteAllNPCs() {
        // Copy the set to prevent concurrent modification exception
        Set<RealmNPC> npcsCopy = new HashSet<>(registeredNPCs);
        npcsCopy.forEach(this::deleteNPC);
    }
}