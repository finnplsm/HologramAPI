package de.lielex.hologramapi.holograms.holograms;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import de.lielex.hologramapi.HologramAPIPlugin;
import de.lielex.hologramapi.holograms.Hologram;
import de.lielex.hologramapi.nms.ReflectionCache;
import de.lielex.hologramapi.nms.ReflectionUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemHologram extends Hologram{

    private final Object itemStack;
    private final Object armorStand;
    private final int id;
    private final Object packetPlayOutSpawnEntityLiving;
    private final Object packetPlayOutEntityDestroy;
    private final boolean isBlock;
    private float rotation;

    public ItemHologram(Location location,Player player,ItemStack itemStack){

        super(location,player,0.55D);
        this.rotation=(new Random()).nextFloat()*100.0F;
        this.isBlock=itemStack.getType().isBlock();

        try{
            this.armorStand=ReflectionCache.EntityArmorStandConstructor.newInstance(ReflectionUtil.getHandle(ReflectionCache.CraftWorld.cast(this.location.getWorld())),this.location.getX(),this.location.getY(),this.location.getZ());
            ReflectionCache.setInvisible.invoke(this.armorStand,true);
            ReflectionCache.setSmall.invoke(this.armorStand,true);

            this.id=(Integer)ReflectionCache.getID.invoke(this.armorStand);
            this.itemStack=ReflectionCache.asNMSCopy.invoke((Object)null,itemStack);
            this.packetPlayOutSpawnEntityLiving=ReflectionCache.PacketPlayOutSpawnEntityLivingConstructor.newInstance(this.armorStand);
            this.packetPlayOutEntityDestroy=ReflectionCache.PacketPlayOutEntityDestroyConstructor.newInstance(new int[]{this.id});
        }catch(InvocationTargetException|IllegalAccessException|InstantiationException|NoSuchMethodException e){
            throw new RuntimeException("An error occurred while creating the item hologram",e);
        }

    }

    private void sendItem(Object o){
        try{
            if(ReflectionCache.PacketPlayOutEntityEquipmentConstructor.getParameterTypes()[1].equals(Integer.TYPE)){
                ReflectionUtil.sendPacket(this.viewer,ReflectionCache.PacketPlayOutEntityEquipmentConstructor.newInstance(this.id,4,o));
            }else{
                ReflectionUtil.sendPacket(this.viewer,ReflectionCache.PacketPlayOutEntityEquipmentConstructor.newInstance(this.id,ReflectionUtil.getNMSClass("EnumItemSlot").getEnumConstants()[5],o));
            }

            this.updateMetadata();
        }catch(IllegalAccessException|InstantiationException|InvocationTargetException e){
            HologramAPIPlugin.getInstance().getLogger().severe("An error occurred while setting hologram item: "+e.getMessage());
        }
    }

    private void updateMetadata() throws InvocationTargetException, IllegalAccessException, InstantiationException{
        Object o=ReflectionCache.PacketPlayOutEntityMetadataConstructor.newInstance(this.id,ReflectionCache.getDataWatcher.invoke(this.armorStand),true);
        ReflectionUtil.sendPacket(this.viewer,o);
    }

    private void updateArmorStandLocation() throws InvocationTargetException, IllegalAccessException, InstantiationException{
        ReflectionCache.setLocation.invoke(this.armorStand,this.location.getX(),this.isBlock?this.location.getY()+0.75D:this.location.getY()+0.34D,this.location.getZ(),this.rotation,this.location.getPitch());
        ReflectionUtil.sendPacket(this.viewer,ReflectionCache.PacketPlayOutEntityTeleportConstructor.newInstance(this.armorStand));
    }

    public void create(){
        ReflectionUtil.sendPacket(this.viewer,this.packetPlayOutSpawnEntityLiving);
        this.sendItem(this.itemStack);
    }

    public void destroy(){
        ReflectionUtil.sendPacket(this.viewer,this.packetPlayOutEntityDestroy);
    }

    public void update(){
        this.rotation=(float)((double)this.rotation+1.8D);
        if(this.rotation >= 180.0F){
            this.rotation=-180.0F;
        }
    }

    public void move(Location location){
        try{
            this.updateArmorStandLocation();
        }catch(IllegalAccessException|InstantiationException|InvocationTargetException e){
            HologramAPIPlugin.getInstance().getLogger().severe("An error occurred while moving an item hologram: "+e.getMessage());
        }
    }
}