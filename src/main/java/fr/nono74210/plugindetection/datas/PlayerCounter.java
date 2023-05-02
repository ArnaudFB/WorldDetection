package fr.nono74210.plugindetection.datas;

import java.util.UUID;

public final class PlayerCounter {
    private final UUID uuid;
    private int counter;

    public PlayerCounter(UUID uuid, int counter) {
        this.uuid = uuid;
        this.counter = counter;
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
