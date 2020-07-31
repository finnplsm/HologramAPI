package de.lielex.hologramapi.holograms;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HologramPlayersManager{

    private static HologramPlayersManager instance;
    private Map hologramPlayers=new HashMap();

    public static HologramPlayersManager getInstance(){
        if(instance==null){
            instance=new HologramPlayersManager();
        }
        return instance;
    }

    public HologramPlayer createHologramPlayer(UUID uuid){
        if(!this.hologramPlayers.containsKey(uuid)){
            HologramPlayer hologramPlayer=new HologramPlayer(uuid);
            this.hologramPlayers.put(uuid,hologramPlayer);
            return hologramPlayer;
        }else{
            return (HologramPlayer)this.hologramPlayers.get(uuid);
        }
    }

    public void deleteHologramPlayer(UUID uuid){
        if(this.existsHologramPlayer(uuid)){
            HologramPlayer hologramPlayer=this.getHologramPlayerFromUUID(uuid);
            hologramPlayer.destroy();
        }

        this.hologramPlayers.remove(uuid);
    }

    public HologramPlayer getHologramPlayerFromUUID(UUID uuid){
        return (HologramPlayer)this.hologramPlayers.get(uuid);
    }

    public boolean existsHologramPlayer(UUID uuid){
        return this.hologramPlayers.containsKey(uuid);
    }
}
