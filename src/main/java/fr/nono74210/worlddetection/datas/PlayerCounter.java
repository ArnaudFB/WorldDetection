package fr.nono74210.worlddetection.datas;

import org.bukkit.World;

import java.util.UUID;

public final class PlayerCounter {
    private final UUID uuid;
    private int counter;

    private final World world;

    public PlayerCounter(UUID uuid, World world, int counter) {
        this.uuid = uuid;
        this.counter = counter;
        this.world = world;
    }

    public World world() {
        return world;
    }

    public UUID uuid() {
        return uuid;
    }

    public int counter() {
        return counter;
    }

    public void incrementCount() {
        counter++;
    }

    @Override
    public String toString() {
        return "PlayerCounter[" +
                "uuid=" + uuid + ", " +
                "counter=" + counter + ']';
    }
}
