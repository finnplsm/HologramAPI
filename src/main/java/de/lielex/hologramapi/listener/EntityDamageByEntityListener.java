package de.lielex.hologramapi.listener;

import de.lielex.hologramapi.holograms.HologramPlayer;
import de.lielex.hologramapi.holograms.HologramPlayersManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener{

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e){
        Entity d=e.getDamager();
        Entity p=e.getEntity();
        if(d instanceof Player&&p instanceof Player){
            HologramPlayersManager playersManager=HologramPlayersManager.getInstance();
            HologramPlayer damagerHologramPlayer=playersManager.getHologramPlayerFromUUID(d.getUniqueId());
            HologramPlayer entityHologramPlayer=playersManager.getHologramPlayerFromUUID(p.getUniqueId());
            if(damagerHologramPlayer!=null){
                damagerHologramPlayer.updateCombatTag();
            }
            if(entityHologramPlayer!=null){
                entityHologramPlayer.updateCombatTag();
            }
        }
    }
}
