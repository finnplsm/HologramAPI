package de.lielex.hologramapi.holograms;

import java.util.UUID;

import de.lielex.hologramapi.HologramAPIPlugin;
import de.lielex.hologramapi.holograms.hologramholders.HologramHUD;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class HologramPlayer{
    private final UUID uuid;
    private HologramHUD hologramHUD;
    private final BukkitTask hologramUpdateTask;
    private long lastTag=System.currentTimeMillis()-(long)(10*1000)-1L;

    public HologramPlayer(UUID uuid){
        this.uuid=uuid;

        this.hologramUpdateTask=(new BukkitRunnable(){
            public void run(){
                if(HologramPlayer.this.hologramHUD!=null){
                    HologramPlayer.this.hologramHUD.update();
                }
            }
        }).runTaskTimerAsynchronously(HologramAPIPlugin.getInstance(),0L,1L);
    }

    public void showHUD(Object[] objects,long duration){
        this.deleteHUD();
        if(duration==-1L){
            duration=Long.MAX_VALUE;
        }

        Player player=Bukkit.getPlayer(this.uuid);

        assert player!=null;

        this.hologramHUD=new HologramHUD(player.getLocation(),player,objects,duration);
        Bukkit.getScheduler().runTaskAsynchronously(HologramAPIPlugin.getInstance(),()->this.hologramHUD.create());

    }

    public void deleteHUD(){
        if(this.hasHUD()){

            Player player=Bukkit.getPlayer(this.uuid);

            assert player!=null;

            this.hologramHUD.remove();
            this.hologramHUD=null;
        }

    }

    public boolean hasHUD(){
        return this.hologramHUD!=null;
    }

    public void clearHolograms(){
        this.deleteHUD();
    }

    public void destroy(){
        this.clearHolograms();
        this.hologramUpdateTask.cancel();
    }

    public void updateCombatTag(){
        this.lastTag=System.currentTimeMillis();
    }
}