package de.lielex.hologramapi.nms;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import de.lielex.hologramapi.HologramAPIPlugin;
import org.bukkit.inventory.ItemStack;

public class ReflectionCache{

    public static final Class CraftWorld=ReflectionUtil.getOBCClass("CraftWorld");
    public static final Class CraftItemStack=ReflectionUtil.getOBCClass("inventory.CraftItemStack");
    public static final Class World=ReflectionUtil.getNMSClass("World");
    public static final Class ItemStack=ReflectionUtil.getNMSClass("ItemStack");
    public static final Class EntityArmorStand=ReflectionUtil.getNMSClass("EntityArmorStand");
    public static final Class PacketPlayOutEntityEquipment=ReflectionUtil.getNMSClass("PacketPlayOutEntityEquipment");
    public static final Class PacketPlayOutSpawnEntityLiving=ReflectionUtil.getNMSClass("PacketPlayOutSpawnEntityLiving");
    public static final Class PacketPlayOutEntityDestroy=ReflectionUtil.getNMSClass("PacketPlayOutEntityDestroy");
    public static final Class PacketPlayOutEntityMetadata=ReflectionUtil.getNMSClass("PacketPlayOutEntityMetadata");
    public static final Class PacketPlayOutEntityTeleport=ReflectionUtil.getNMSClass("PacketPlayOutEntityTeleport");
    public static final Class Entity=ReflectionUtil.getNMSClass("Entity");
    public static final Class DataWatcher=ReflectionUtil.getNMSClass("DataWatcher");
    public static final Class EntityLiving=ReflectionUtil.getNMSClass("EntityLiving");
    public static Constructor EntityArmorStandConstructor=null;
    public static Constructor PacketPlayOutEntityEquipmentConstructor=null;
    public static Constructor PacketPlayOutSpawnEntityLivingConstructor=null;
    public static Constructor PacketPlayOutEntityDestroyConstructor=null;
    public static Constructor PacketPlayOutEntityMetadataConstructor=null;
    public static Constructor PacketPlayOutEntityTeleportConstructor=null;
    public static Method setInvisible;
    public static Method setCustomNameVisible;
    public static Method setMarker;
    public static Method setCustomName;
    public static Method getID;
    public static Method getDataWatcher;
    public static Method setLocation;
    public static Method setSmall;
    public static Method asNMSCopy;

    static{
        try{
            EntityArmorStandConstructor=EntityArmorStand.getConstructor(World,Double.TYPE,Double.TYPE,Double.TYPE);
            PacketPlayOutSpawnEntityLivingConstructor=PacketPlayOutSpawnEntityLiving.getConstructor(EntityLiving);
            PacketPlayOutEntityDestroyConstructor=PacketPlayOutEntityDestroy.getConstructor(int[].class);
            PacketPlayOutEntityMetadataConstructor=PacketPlayOutEntityMetadata.getConstructor(Integer.TYPE,DataWatcher,Boolean.TYPE);
            PacketPlayOutEntityTeleportConstructor=PacketPlayOutEntityTeleport.getConstructor(Entity);
            setInvisible=EntityArmorStand.getMethod("setInvisible",Boolean.TYPE);
            setCustomNameVisible=EntityArmorStand.getMethod("setCustomNameVisible",Boolean.TYPE);

            try{
                setMarker=EntityArmorStand.getMethod("setMarker",Boolean.TYPE);
            }catch(NoSuchMethodException var3){
                setMarker=null;
            }

            setLocation=Entity.getMethod("setLocation",Double.TYPE,Double.TYPE,Double.TYPE,Float.TYPE,Float.TYPE);
            setSmall=EntityArmorStand.getMethod("setSmall",Boolean.TYPE);
            getID=EntityArmorStand.getMethod("getId");
            getDataWatcher=Entity.getMethod("getDataWatcher");
            asNMSCopy=CraftItemStack.getMethod("asNMSCopy",ItemStack.class);

            try{
                setCustomName=EntityArmorStand.getMethod("setCustomName",String.class);
            }catch(NoSuchMethodException var2){
                setCustomName=EntityArmorStand.getMethod("setCustomName",IChatBaseComponent.IChatBaseComponent);
            }

            if(ReflectionUtil.exists("EnumItemSlot")){
                try{
                    PacketPlayOutEntityEquipmentConstructor=PacketPlayOutEntityEquipment.getConstructor(Integer.TYPE,ReflectionUtil.getNMSClass("EnumItemSlot"),ItemStack);
                }catch(NoSuchMethodException var1){
                    PacketPlayOutEntityEquipmentConstructor=PacketPlayOutEntityEquipment.getConstructor(Integer.TYPE,Integer.TYPE,ItemStack);
                }
            }else{
                PacketPlayOutEntityEquipmentConstructor=PacketPlayOutEntityEquipment.getConstructor(Integer.TYPE,Integer.TYPE,ItemStack);
            }
        }catch(NoSuchMethodException var4){
            HologramAPIPlugin.getInstance().getLogger().severe("Error while getting method/constructor:"+var4.getMessage());
        }
    }
}