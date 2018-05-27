package it.polimi.ingsw.sagrada.gui;


//TO BE REPLACED WHEN CONNECTING VIEW TO MODEL

import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PlayerModel {

    WindowModel windowModel;
    int id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerModel that = (PlayerModel) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }


    public WindowModel getWindowModel() {
        return windowModel;
    }


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
