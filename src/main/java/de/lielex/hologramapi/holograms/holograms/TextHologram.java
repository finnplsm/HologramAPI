package de.lielex.hologramapi.holograms.holograms;

import de.lielex.hologramapi.holograms.Hologram;
import de.lielex.hologramapi.nms.IChatBaseComponent;
import de.lielex.hologramapi.nms.ReflectionCache;
import de.lielex.hologramapi.nms.ReflectionUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class TextHologram extends Hologram {

   private final String rawLine;
   private final Object armorStand;
   private final int id;
   private final Object packetPlayOutSpawnEntityLiving;
   private final Object packetPlayOutEntityDestroy;
   private final boolean marker;

   public TextHologram(Location location, Player player, String rawLine) {
      super(location, player, 0.23D);
      this.marker = ReflectionCache.setMarker != null;
      this.rawLine = rawLine;

      try {
         this.armorStand = ReflectionCache.EntityArmorStandConstructor.newInstance(ReflectionUtil.getHandle(ReflectionCache.CraftWorld.cast(this.location.getWorld())), this.location.getX(), this.location.getY(), this.location.getZ());
         ReflectionCache.setInvisible.invoke(this.armorStand, true);
         ReflectionCache.setCustomNameVisible.invoke(this.armorStand, true);
         if (this.marker) {
            ReflectionCache.setMarker.invoke(this.armorStand, true);
         }

         if (ReflectionCache.setCustomName.getParameterTypes()[0].equals(String.class)) {
            ReflectionCache.setCustomName.invoke(this.armorStand, "");
         } else {
            ReflectionCache.setCustomName.invoke(this.armorStand, IChatBaseComponent.of(""));
         }

         this.id = (Integer)ReflectionCache.getID.invoke(this.armorStand);
         this.packetPlayOutSpawnEntityLiving = ReflectionCache.PacketPlayOutSpawnEntityLivingConstructor.newInstance(this.armorStand);
         this.packetPlayOutEntityDestroy = ReflectionCache.PacketPlayOutEntityDestroyConstructor.newInstance(new int[]{this.id});
      } catch (InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
         throw new RuntimeException("An error occurred while creating the hologram.", e);
      }
   }

   public String getRawLine() {
      return this.rawLine;
   }

   private void setText(String s) {
      try {
         if (ReflectionCache.setCustomName.getParameterTypes()[0].equals(String.class)) {
            ReflectionCache.setCustomName.invoke(this.armorStand, s);
         } else {
            ReflectionCache.setCustomName.invoke(this.armorStand, IChatBaseComponent.of(s));
         }

         this.updateMetadata();
      } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
         throw new RuntimeException("An error occurred while changing hologram text.", e);
      }
   }

   private void updateMetadata() throws InvocationTargetException, IllegalAccessException, InstantiationException {
      Object o = ReflectionCache.PacketPlayOutEntityMetadataConstructor.newInstance(this.id, ReflectionCache.getDataWatcher.invoke(this.armorStand), true);
      ReflectionUtil.sendPacket(this.viewer, o);
   }

   private void updateArmorStandLocation() throws InvocationTargetException, IllegalAccessException, InstantiationException {
      ReflectionCache.setLocation.invoke(this.armorStand, this.location.getX(), this.marker ? this.location.getY() + 1.25D : this.location.getY() - 0.75D, this.location.getZ(), this.location.getYaw(), this.location.getPitch());
      ReflectionUtil.sendPacket(this.viewer, ReflectionCache.PacketPlayOutEntityTeleportConstructor.newInstance(this.armorStand));
   }

   public void create() {
      try {
         ReflectionUtil.sendPacket(this.viewer, this.packetPlayOutSpawnEntityLiving);
         this.updateMetadata();
      } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
         throw new RuntimeException("An error occurred while spawning the hologram.", e);
      }
   }

   public void destroy() {
      ReflectionUtil.sendPacket(this.viewer, this.packetPlayOutEntityDestroy);
   }

   public void update() {
      String rawLine = this.rawLine;

      if (rawLine == null || rawLine.length() < 1) {
         rawLine = " ";
      }

      this.setText(rawLine);
   }

   public void move(Location location) {
      try {
         this.updateArmorStandLocation();
      } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
         throw new RuntimeException("An error occurred while moving the hologram.", e);
      }
   }
}