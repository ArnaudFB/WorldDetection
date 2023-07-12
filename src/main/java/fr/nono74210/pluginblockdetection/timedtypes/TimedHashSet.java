package fr.nono74210.pluginblockdetection.timedtypes;

import fr.nono74210.pluginblockdetection.PluginBlockDetection;
import org.bukkit.Bukkit;

import java.util.*;

public class TimedHashSet<T> implements Iterable<T> {
    private final HashSet<TimedItem<T>> hashset = new HashSet<>();

    public TimedHashSet()
    {
        Bukkit.getScheduler().runTaskTimer(PluginBlockDetection.getInstance(), () ->
            hashset.removeIf(TimedItem::isExpired), 20, 20);
    }

    public void add(T item, long timeoutMillis) {
        // Ajoute un objet dans le TimedHashSet
        hashset.add(new TimedItem<>(item, System.currentTimeMillis() + timeoutMillis));
    }

    public boolean isEmpty(){
        // Check si le TimedHashSet est vide
        return hashset.isEmpty();
    }

    public boolean contains(T item) {
        // Check si le TimedHashedSet contient l'objet voulu
        return get(item).isPresent();
    }

    private Optional<TimedItem<T>> get(T item) {
        return hashset.stream().filter(tTimedItem -> tTimedItem.item == item).findFirst();
    }

    public HashSet<TimedItem<T>> get() {
        return hashset;
    }

    public void clear() {
        hashset.forEach(TimedItem::setForceExpired);
    }

    public static final class TimedItem<T> {
        private final T item;
        private final long expireTime;
        private boolean forceExpired;

        public TimedItem(T item, long expireTime) {
            this.item = item;
            this.expireTime = expireTime;
            this.forceExpired = false;
        }

        public boolean isExpired() {
                // Check la date limite d'un objet
                return System.currentTimeMillis() > expireTime || forceExpired;
            }

        public T item() {
            return item;
        }

        public void setForceExpired() {
            this.forceExpired = true;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new TimedHashSetIterator<>(hashset.iterator());
    }

    private record TimedHashSetIterator<T>(Iterator<TimedItem<T>> iterator) implements Iterator<T> {
        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        public T next() {
            return iterator.next().item();
        }

        @Override
        public void remove() {
            iterator.remove();
        }
    }
}
