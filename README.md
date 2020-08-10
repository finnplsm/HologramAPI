# HologramAPI
[![](https://jitpack.io/v/HttpLielex/HologramAPI.svg)](https://jitpack.io/#HttpLielex/HologramAPI)
![](https://img.shields.io/badge/Version-1.1.0-brightgreen.svg)

Create a hologram that follows the players movements

## SpigotMC
[![HologramAPI](https://static.spigotmc.org/img/spigot.png "HologramAPI")](https://www.spigotmc.org/resources/hologramapi.81345/ "HologramAPI")

## Details
#### English
With this API, you as developer have all ways to create a hologram that is 100% async and does not cause laggs. The special thing about this API is that it tracks the movements of the  player and can be used not only for texts but also for items.
#### German
Mit dieser API stehen dir als Entwickler alle Wege offen, ein Hologramm zu erstellen, welches 100% asynchron ist und keine Laggs verursacht. Das besondere an dieser API ist zudem, dass es die Bewegungen des Spielers verfolgt und nicht nur für Texte sondern auch für Items verwendet werden kann.

## API
```java
ArrayList<Object> objects=new ArrayList<>(); //create a object arraylist
objects.add(new ItemStack(Material.BEACON)); //add a itemstack to the arraylist
objects.add("Example"); //add a string to the arraylist

HologramAPI.sendHologram(player,objects.toArray(),30); //send the hologram to the player (player, objects, seconds)
```
## Download
#### Maven
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.HttpLielex</groupId>
    <artifactId>HologramAPI</artifactId>
    <version>1.1.0</version>
</dependency>
```
#### Gradle
```gradle
repositories {
    ...
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.HttpLielex:HologramAPI:v1.1.0'
}
```

