package de.lielex.hologramapi.nms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IChatBaseComponent{

    private static final Logger logger=Logger.getLogger(IChatBaseComponent.class.getName());
    public static final Class IChatBaseComponent=ReflectionUtil.getNMSClass("IChatBaseComponent");
    private static Method newIChatBaseComponent=null;

    public static Object of(String var0) throws InvocationTargetException, IllegalAccessException{
        return newIChatBaseComponent.invoke(null,"{\"text\": \""+var0+"\"}");
    }

    static{
        try{
            newIChatBaseComponent=IChatBaseComponent.getDeclaredClasses()[0].getMethod("a",String.class);
        }catch(NoSuchMethodException var1){
            logger.log(Level.SEVERE,"An error occurred while initializing IChatBaseComponent.");
        }
    }
}
