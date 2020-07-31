package de.lielex.hologramapi.listener;

import de.lielex.hologramapi.holograms.HologramPlayer;
import de.lielex.hologramapi.holograms.HologramPlayersManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener{

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p=e.getPlayer();
        UUID uuid=p.getUniqueId();
        HologramPlayersManager hologramPlayersManager=HologramPlayersManager.getInstance();
        HologramPlayer hologramPlayer=hologramPlayersManager.createHologramPlayer(uuid);
    }
}
