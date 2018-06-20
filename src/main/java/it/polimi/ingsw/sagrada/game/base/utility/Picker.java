package it.polimi.ingsw.sagrada.game.base.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;


/**
 * Utility class that allows you to pick an element without repetitions from a list of elements.
 *
 * @param <T> the generic type
 */

public class Picker<T> {
    
    /** The elements. */
    private List<T> elements;

    /**
     * Instantiates a new picker.
     *
     * @param elements the elements
     */
    public Picker(List<T> elements) {
        this.elements = elements;
    }

    /**
     * Picker iterator.
     *
     * @return the pick iterator
     */
    public PickIterator pickerIterator() {
        return new PickIterator();
    }

    /**
     * Inner class to iterate over listed elements
     */
    private class PickIterator implements Iterator<T> {
        
        /** The index. */
        private List<Integer> index = new ArrayList<>();

        /**
         * Instantiates a new pick iterator.
         */
        private PickIterator() {
            IntStream.range(0, elements.size()).forEach(index::add);
        }

        /* (non-Javadoc)
         * @see java.util.Iterator#hasNext()
         */
        @Override
        public boolean hasNext() {
            return !index.isEmpty();
        }

        /* (non-Javadoc)
         * @see java.util.Iterator#next()
         */
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
