package de.lielex.hologramapi.api;

import de.lielex.hologramapi.holograms.HologramPlayer;
import de.lielex.hologramapi.holograms.HologramPlayersManager;
import org.bukkit.entity.Player;

public class HologramAPI{
    public static void sendHologram(Player player,Object[] objects,long seconds){
        HologramPlayersManager hologramPlayersManager=HologramPlayersManager.getInstance();
        HologramPlayer hologramPlayer=hologramPlayersManager.createHologramPlayer(player.getUniqueId());
        hologramPlayer.showHUD(objects,(20*seconds));
    }
}
