package fun.parking;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nikeshshetty on 10/02/15.
 */
public class ParkingSlotsCollection extends HashMap<Integer, Boolean> {

    public enum ParkingLotState {
        Available,
        FillingFast,
        Full,
    }

    private int occupiedSlots;
    private float fastFillingThreshold = 0.8f;

    public void ParkingSlotsCollection(int capacity) {
        for (int ii = 1; ii <= capacity; ii++) {
            this.put(ii, false);
        }
    }

    public Boolean isFull() {
        if (currentState() == ParkingLotState.Full)
            return true;
        else
            return false;
    }

    public ParkingLotState currentState() {
        ParkingLotState currentState;
        int occupiedSlots = 0;
        for (Boolean available: this.values()) {
            occupiedSlots += available?1:0;
        }

        if (occupiedSlots == this.keySet().size())
            currentState = ParkingLotState.Full;
        else if (occupiedSlots >= (int)(fastFillingThreshold*this.keySet().size()))
            currentState = ParkingLotState.FillingFast;
        else
            currentState = ParkingLotState.Available;

        return currentState;
    }

}
