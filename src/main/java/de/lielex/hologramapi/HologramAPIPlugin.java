package de.lielex.hologramapi;

import de.lielex.hologramapi.holograms.HologramPlayersManager;
import de.lielex.hologramapi.listener.EntityDamageByEntityListener;
import de.lielex.hologramapi.listener.PlayerJoinListener;
import de.lielex.hologramapi.listener.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class HologramAPIPlugin extends JavaPlugin implements Listener{

    private static de.lielex.hologramapi.HologramAPIPlugin instance;

    @Override
    public void onEnable(){
        instance=this;
        HologramPlayersManager playersManager=HologramPlayersManager.getInstance();
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(),this);
        Bukkit.getOnlinePlayers().forEach((player)->playersManager.createHologramPlayer(player.getUniqueId()));
    }

    public static HologramAPIPlugin getInstance(){
        return instance;
    }

    @Override
    public void onDisable(){
        HologramPlayersManager hologramPlayersManager=HologramPlayersManager.getInstance();
        Bukkit.getOnlinePlayers().forEach((player)->hologramPlayersManager.deleteHologramPlayer(player.getUniqueId()));
    }
}
