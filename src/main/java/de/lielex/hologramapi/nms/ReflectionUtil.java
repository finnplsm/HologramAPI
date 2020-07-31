package de.lielex.hologramapi.nms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReflectionUtil{

    public static String version;
    private static Method sendPacket=null;
    private static Map cached_classes=new HashMap();

    public static Class getNMSClass(String var0){
        if(cached_classes.containsKey(var0)){
            return (Class)cached_classes.get(var0);
        }else{
            try{
                Class var1=Class.forName("net.minecraft.server."+version+"."+var0);
                cached_classes.put(var0,var1);
                return var1;
            }catch(ClassNotFoundException var2){
                throw new RuntimeException("An error occurred while finding NMS class.",var2);
            }
        }
    }

    public static boolean exists(String var0){
        try{
            Class var1=Class.forName("net.minecraft.server."+version+"."+var0);
            cached_classes.put(var0,var1);
            return true;
        }catch(ClassNotFoundException var2){
            return false;
        }
    }

    public static Class getOBCClass(String var0){
        if(cached_classes.containsKey(var0)){
            return (Class)cached_classes.get(var0);
        }else{
            try{
                Class var1=Class.forName("org.bukkit.craftbukkit."+version+"."+var0);
                cached_classes.put(var0,var1);
                return var1;
            }catch(ClassNotFoundException var2){
                throw new RuntimeException("An error occurred while finding OBC class.",var2);
            }
        }
    }

    public static Object getHandle(Object var0) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        return var0.getClass().getMethod("getHandle").invoke(var0);
    }

    public static Object enumValueOf(Class var0,String var1){
        return Enum.valueOf(var0,var1.toUpperCase());
    }

    public static Optional getNMSOptionalClass(String var0){
        return optionalClass("net.minecraft.server."+version+"."+var0);
    }

    public static Optional getOBCOptionalClass(String var0){
        return optionalClass("org.bukkit.craftbukkit."+version+"."+var0);
    }

    public static Optional optionalClass(String var0){
        try{
            return Optional.of(Class.forName(var0));
        }catch(ClassNotFoundException var2){
            return Optional.empty();
        }
    }

    public static void sendPacket(Player var0,Object var1){
        try{
            Object var2=getHandle(var0);
            Object var3=var2.getClass().getField("playerConnection").get(var2);
            if(sendPacket==null){
                sendPacket=var3.getClass().getMethod("sendPacket",getNMSClass("Packet"));
            }
            sendPacket.invoke(var3,var1);
        }catch(NoSuchFieldException|IllegalAccessException|InvocationTargetException|NoSuchMethodException var4){
        }
    }

    static{
        String[] var0=Bukkit.getServer().getClass().getName().replace('.',',').split(",");
        if(var0.length >= 4){
            version=var0[3];
        }else{
            version="";
        }
    }
}