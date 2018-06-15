package it.polimi.ingsw.sagrada.gui.test;


//TO BE REPLACED WHEN CONNECTING VIEW TO MODEL

import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * The Class PlayerModel.
 */
public class PlayerModel {

    /** The window model. */
    WindowModel windowModel;
    
    /** The id. */
    int id;

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerModel that = (PlayerModel) o;
        return id == that.id;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        return Objects.hash(id);
    }


    /**
     * Gets the window model.
     *
     * @return the window model
     */
    public WindowModel getWindowModel() {
        return windowModel;
    }


    /**
     * To singleton.
     *
     * @param <T> the generic type
     * @return the collector
     */
    public static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }
}
