package fr.nono74210.plugindetection.timedtypes;

import java.util.*;

public class TimedHashSet<T, I, timeoutMillis> implements Iterable<T> {

    private final HashSet<TimedItem<T, I>> hashset = new HashSet<>();

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
    public void add(T item1, I item2, long timeoutMillis){

        hashset.add(new TimedItem<>(item1, item2, System.currentTimeMillis()+timeoutMillis));

    }
    public void remove(T item){

        hashset.remove(item);

    }
    public void clear(){

        hashset.clear();

    }
    public int size(){

        return hashset.size();

    }
    public boolean isEmpty(){

        return hashset.isEmpty();

    }
    public boolean contains(T item) {

        return hashset.contains(item);

    }

    public I getItemI(){

        return hashset.iterator().next().item2;

    }
    public record TimedItem<T, I> (T item1, I item2, long expireTime){
        public boolean isExpired(){

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

                return iterator.next().item1;

            }

        @Override
            public void remove() {

                iterator.remove();

            }

        public Iterator<TimedItem<T, I>> iterator() {
            return iterator;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (TimedHashSetIterator) obj;
            return Objects.equals(this.iterator, that.iterator);
        }

        @Override
        public int hashCode() {
            return Objects.hash(iterator);
        }

        @Override
        public String toString() {
            return "TimedHashSetIterator[" +
                    "iterator=" + iterator + ']';
        }


        }

}
