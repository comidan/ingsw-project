package it.polimi.ingsw.base;

import it.polimi.ingsw.sagrada.game.base.utility.Picker;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class PickerTest {

    @Test
    public void testPicker() {
        List<Integer> integers = new ArrayList<>();
        List<Integer> readed = new ArrayList<>();
        for(int i=0; i<20; i++) {
            integers.add(i);
        }
        Iterator<Integer> picker = new Picker<>(integers).pickerIterator();

        while(picker.hasNext()) {
            int j = picker.next();
            assertFalse(readed.contains(j));
            readed.add(j);
        }
    }
}
