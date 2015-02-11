package fun.parking;

/**
 * Created by nikeshshetty on 10/02/15.
 */
public interface ParkingLotListener {
    public void notifyParkingFull();
    public void notifyParkingFillingFast();
    public void notifyParkingAvailable();

}
