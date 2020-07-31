package de.lielex.hologramapi.listener;

import de.lielex.hologramapi.holograms.HologramPlayersManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerQuitListener implements Listener{

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p=e.getPlayer();
        UUID uuid=p.getUniqueId();
        HologramPlayersManager hologramPlayersManager=HologramPlayersManager.getInstance();
        hologramPlayersManager.deleteHologramPlayer(uuid);
    }
}
