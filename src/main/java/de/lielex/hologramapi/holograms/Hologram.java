package de.lielex.hologramapi.holograms;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class Hologram{

    protected Location location;
    protected final Player viewer;
    protected final double height;

    public Hologram(Location location,Player player,double height){
        this.location=location;
        this.viewer=player;
        this.height=height;
    }

    public Location getLocation(){
        return this.location;
    }

    public double getHeight(){
        return this.height;
    }

    public Player getViewer(){
        return this.viewer;
    }

    public void setLocation(Location location){
        this.location=location;
        this.move(location);
    }

    public abstract void create();

    public abstract void destroy();

    public abstract void update();

    public abstract void move(Location location);
}