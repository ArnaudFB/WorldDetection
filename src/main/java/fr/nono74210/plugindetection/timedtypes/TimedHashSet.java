package fr.nono74210.plugindetection.timedtypes;

import java.util.*;

public class TimedHashSet<T> implements Iterable<T> {
    private final HashSet<TimedItem<T>> hashset = new HashSet<>();

    public TimedHashSet() {
        Timer timer = new Timer(true);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                hashset.removeIf(TimedItem::isExpired);
            }
        };

        timer.scheduleAtFixedRate(task, 0, 1000L);
    }

    public void add(T item, long timeoutMillis) {
        // Ajoute un objet dans le TimedHashSet
        hashset.add(new TimedItem<>(item, System.currentTimeMillis() + timeoutMillis));
    }
    public void remove(T item){
        // Retire un objet spécifique du TimedHashSet
        get(item).ifPresent(hashset::remove);
    }

    public boolean isEmpty(){
        // Check si le TimedHashSet est vide
        return hashset.isEmpty();

    }

    public boolean contains(T item) {
        // Check si le TimedHashedSet contient l'objet voulu
        return get(item).isPresent();
    }

    public record TimedItem<T> (T item, long expireTime){
        public boolean isExpired(){
            // Check la date limite d'un objet
            return System.currentTimeMillis() > expireTime;

        }

    }
    @Override
    public Iterator<T> iterator(){

        return new TimedHashSetIterator<>(hashset.iterator());

    }

    private final class TimedHashSetIterator<T> implements Iterator<T> {
        private final Iterator<TimedItem<T, I>> iterator;

        private TimedHashSetIterator(Iterator<TimedItem<T, I>> iterator) {
            this.iterator = iterator;
        }

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
