package de.lielex.hologramapi.holograms.hologramholders;

import de.lielex.hologramapi.holograms.HologramHolder;
import de.lielex.hologramapi.holograms.HologramPlayer;
import de.lielex.hologramapi.holograms.HologramPlayersManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class HologramHUD extends HologramHolder{

    private long duration=0L;
    private final long lifetime;

    public HologramHUD(Location location,Player player,Object[] objects,long lifetime){
        super("hud-"+player.getUniqueId(),location,player,objects);
        this.lifetime=lifetime;
        HologramPlayersManager hologramPlayersManager=HologramPlayersManager.getInstance();
        HologramPlayer hologramPlayer=hologramPlayersManager.getHologramPlayerFromUUID(player.getUniqueId());
    }

    public long getLifetime(){
        return this.lifetime;
    }

    public void onUpdate(){
        ++this.duration;
        if(this.duration>this.lifetime){
            HologramPlayersManager hologramPlayersManager=HologramPlayersManager.getInstance();
            HologramPlayer hologramPlayer=hologramPlayersManager.getHologramPlayerFromUUID(this.viewer.getUniqueId());
            hologramPlayer.deleteHUD();
        }else{
            this.setLocation(this.viewer.getLocation().add(this.viewer.getEyeLocation().getDirection().normalize().multiply(3)).add(0.0D,(this.getHeight()-this.heightAverage)/2.0D,0.0D));
            Location location=this.viewer.getLocation().add(this.viewer.getEyeLocation().getDirection().normalize().multiply(3));

            location.add(0.0D,(this.getHeight()-this.heightAverage)/2.0D,0.0D);
            this.setLocation(location);
        }
    }
}
