package it.polimi.ingsw.sagrada.game.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

/**
 *Utility class that allows you to pick an element without repetitions from a list of elements
 */

public class Picker<T> {
    private List<T> elements;

    public Picker(List<T> elements) {
        this.elements = elements;
    }

    public PickIterator pickerIterator() {
        return new PickIterator();
    }

    private class PickIterator implements Iterator<T> {
        private List<Integer> index = new ArrayList<>();

        private PickIterator() {
            for(int i=0; i<elements.size(); i++) {
                index.add(i);
            }
        }

        @Override
        public boolean hasNext() {
            return !index.isEmpty();
        }

        @Override
        public T next() {
            int randomNum = ThreadLocalRandom.current().nextInt(0, index.size());
            if(randomNum>=elements.size()) throw new NoSuchElementException();
            T e = elements.get(index.get(randomNum));
            index.remove(randomNum);
            return e;
        }
    }
}
