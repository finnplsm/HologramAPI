package de.lielex.hologramapi.holograms;

import de.lielex.hologramapi.holograms.holograms.EmptyHologram;
import de.lielex.hologramapi.holograms.holograms.ItemHologram;
import de.lielex.hologramapi.holograms.holograms.TextHologram;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HologramFactory{
    private static HologramFactory instance;

    private HologramFactory(){
    }

    public TextHologram buildTextHologram(Location location,Player player,String rawLine){
        return new TextHologram(location,player,rawLine);
    }

    public EmptyHologram buildEmptyHologram(Location location,Player player){
        return new EmptyHologram(location,player);
    }

    public ItemHologram buildItemHologram(Location location,Player player,ItemStack itemStack){
        return new ItemHologram(location,player,itemStack);
    }

    public static HologramFactory getInstance(){
        if(instance==null){
            instance=new HologramFactory();
        }
        return instance;
    }
}
