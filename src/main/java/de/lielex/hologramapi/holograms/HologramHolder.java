package de.lielex.hologramapi.holograms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.lielex.hologramapi.holograms.holograms.ItemHologram;
import de.lielex.hologramapi.holograms.holograms.TextHologram;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class HologramHolder{

    protected List rawLines=new ArrayList();
    protected List holograms=new ArrayList();
    protected final String name;
    protected Location location;
    protected final Player viewer;
    private final double height;
    protected final double heightAverage;

    public HologramHolder(String name,Location location,Player player,Object[] object){
        this.name=name;
        this.location=location;
        this.viewer=player;
        double height=0.0D;
        Location subtract=this.location.clone().subtract(0.0D,height,0.0D);

        for(Object o: object){
            if(o instanceof ItemStack){
                ItemStack itemStack=((ItemStack)o);
                this.rawLines.add(((ItemStack)o).getItemMeta().getDisplayName());
                this.holograms.add(new ItemHologram(subtract.subtract(0.0D,0.275D,0.0D),this.viewer,itemStack));
            }else if(o instanceof String){
                this.rawLines.add(((String)o));
                this.holograms.add(new TextHologram(subtract.subtract(0.0D,0.115D,0.0D),this.viewer,((String)o)));
            }
        }

        Hologram hologram;
        for(Iterator iterator=this.holograms.iterator();iterator.hasNext();height+=hologram.getHeight()){
            hologram=(Hologram)iterator.next();
        }

        this.height=height;
        this.heightAverage=this.height/(double)this.holograms.size();
    }

    public String getName(){
        return this.name;
    }

    public Location getLocation(){
        return this.location;
    }

    public List getHolograms(){
        return this.holograms;
    }

    public Player getViewer(){
        return this.viewer;
    }

    public double getHeight(){
        return this.height;
    }

    public void setLocation(Location location){
        this.location=location.add(0.0D,this.heightAverage/2.0D,0.0D);
        double v=0.0D;

        Hologram hologram;
        for(Iterator iterator=this.holograms.iterator();iterator.hasNext();v+=hologram.getHeight()){
            hologram=(Hologram)iterator.next();
            hologram.setLocation(location.clone().subtract(0.0D,v+hologram.getHeight()/2.0D,0.0D));
        }

    }

    public void create(){
        for(Object hologram: this.holograms){
            ((Hologram)hologram).create();
        }
    }

    public void remove(){
        for(Object hologram: this.holograms){
            ((Hologram)hologram).destroy();
        }
    }

    public void update(){
        this.onUpdate();
        for(Object hologram: this.holograms){
            ((Hologram)hologram).update();
        }
    }

    public abstract void onUpdate();
}