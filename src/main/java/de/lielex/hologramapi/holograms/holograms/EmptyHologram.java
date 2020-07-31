package de.lielex.hologramapi.holograms.holograms;

import de.lielex.hologramapi.holograms.Hologram;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class EmptyHologram extends Hologram{

    public EmptyHologram(Location location,Player player){
        super(location,player,0.23D);
    }

    public void create(){
    }

    public void destroy(){
    }

    public void update(){
    }

    public void move(Location location){
    }
}