package fr.nono74210.plugindetection.timedtypes;

import org.checkerframework.checker.units.qual.Time;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import static javax.swing.UIManager.get;

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
    public void add(T item, long timeoutMillis){

        hashset.add(new TimedItem<>(item, System.currentTimeMillis()+timeoutMillis));

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
    public record TimedItem<T> (T item, long expireTime){
        public boolean isExpired(){
            return System.currentTimeMillis() > expireTime;
        }

    }
    @Override
    public Iterator<T> iterator(){

        return new TimedHashSetIterator<>(hashset.iterator());

    }
    public record TimedHashSetIterator<T> (Iterator<TimedItem<T>> iterator) implements Iterator<T> {
        @Override
        public boolean hasNext(){
            return iterator.hasNext();
        }
        @Override
        public T next(){
            return iterator.next().item();
        }
        @Override
        public void remove(){
            iterator.remove();
        }
    }

    private record TimedHashSetIterator<T>(Iterator<TimedItem<T>> iterator) implements Iterator<T>{
        @Override
        public boolean hasNext(){

            return iterator.hasNext();

        }
        @Override
        public  T next(){

            return iterator.next().item();

        }
        @Override
        public void remove(){
            iterator.remove();
        }

    }
}
