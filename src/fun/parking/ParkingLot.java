package fun.parking;


import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by nikeshshetty on 10/02/15.
 */
public class ParkingLot {
    private int capacity;
    private int availableSlots;
    private int fastFillingThreshold;
    final float FASTFILLINGFACTOR = 0.8F;
    private HashSet<ParkingLotListener> listeners;
    private ParkingSlotsCollection parkingSlotsCollection;
    private HashMap<Integer,Boolean> slotsMap = new HashMap();
    public enum CurrentState{
        Available,
        FastFilling,
        Full
    };

    int getFastFillingThreshold() {
        return fastFillingThreshold;
    }

    public ParkingLot(int capacity) throws Exception{
        if(capacity <= 0){
            throw new Exception("Capacity should be greater than 0");
        }
        this.capacity = capacity;
        this.availableSlots = capacity;
        this.fastFillingThreshold = (int)(FASTFILLINGFACTOR * capacity);
        this.listeners = new HashSet<ParkingLotListener>();

    }

    public boolean parkCar() {
        if(!isFull()){
            CurrentState oldState = getCurrentState();
            availableSlots--;
            notifyIfNeeded(oldState);
            return true;
        }
        return false;
    }

    public boolean isFull() {
        if(availableSlots == 0)
            return true;
        else
            return false;
    }

    private void notifyIfNeeded(CurrentState oldState) {
        System.out.println("oldstate="+oldState);
        if(getBookedSlotsCount() >= fastFillingThreshold &&  CurrentState.FastFilling != oldState)
        {
            for (ParkingLotListener listener :listeners){
                listener.notifyParkingFillingFast();
            }
        }

    }

    private CurrentState getCurrentState() {
        CurrentState cState= CurrentState.Available;
        if (getBookedSlotsCount() == capacity) {
            cState = CurrentState.Full;
        }
        else if(getBookedSlotsCount() >=fastFillingThreshold) {
            cState = CurrentState.FastFilling;
        }

        return cState;
    }

    public int getBookedSlotsCount(){
        return capacity - availableSlots;
    }

    public void addListener(ParkingLotListener parkingLotListener) {
        listeners.add(parkingLotListener);
    }

    public boolean unpark() {
        if (getBookedSlotsCount()==0)
            return false;
        else {
            CurrentState oldState = getCurrentState();
            availableSlots++;
            notifyIfNeeded(oldState);
            return true;

        }

    }
}
