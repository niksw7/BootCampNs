package fun.parking;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by nikeshshetty on 10/02/15.
 */
public class ParkingTest {
    ParkingLot parkingLot;
    final int CAPACITY = 10;
    final float FASTFILLINGFACTOR = 0.8F;
    final int FASTFILLINGTHRESHOLD = (int)(FASTFILLINGFACTOR*CAPACITY);
    @Before
    public void setupParkingLot() throws Exception{
       parkingLot = new ParkingLot(CAPACITY);
    }
    @Test
    public void checkThresholdValue(){

        assertEquals(8, parkingLot.getFastFillingThreshold());
    }
    @Test(expected = Exception.class)
    public void parkingLotShouldNotBeInitializedWithNegativeCapacity() throws Exception{
        new ParkingLot(-1);
    }
    @Test(expected = Exception.class)
    public void parkingLotShouldNotBeInitializedWithZeroCapacity() throws Exception{
        new ParkingLot(0);
    }
    @Test
    public void shouldParkaCarIfNotFull(){
        assertTrue(parkingLot.parkCar());
    }
    @Test
    public void shouldNotParkACarIfTheParkingLotIsFull() {
        makeParkingLotFull();
        assertFalse(parkingLot.parkCar());
    }
    @Test
    public void shouldReturnTrueWhenParkingLotIsFull(){
        makeParkingLotFull();
        assertTrue(parkingLot.isFull());
    }
    @Test
    public void shouldReturnFalsewhenParkingLotIsNotFull(){
        assertFalse(parkingLot.isFull());
    }
    @Test
    public void shouldNotifyFillingFastWhenReachingTheStage_FromAvailable(){
        fillParkingLotOneSlotBeforeFastFilling();
        ParkingLotListener parkingLotListener = mock(ParkingLotListener.class);


        parkingLot.addListener(parkingLotListener);
        parkingLot.parkCar();

        verify(parkingLotListener, only()).notifyParkingFillingFast();


    }
    @Test
    public void shouldNotifyFillingFastWhenReachingTheStage_FromFull(){
        fillParkingLot(CAPACITY);
        ParkingLotListener parkingLotListener = mock(ParkingLotListener.class);

        parkingLot.addListener(parkingLotListener);
        parkingLot.unpark();

        verify(parkingLotListener, only()).notifyParkingFillingFast();

    }

    @Test
    public void shouldReturnFalseOnUnParkIfEmpty() {
        assertFalse(parkingLot.unpark());
    }

    @Test
    public void shouldIncreaseNumberOfFreeSlotsOnUnparking() {
        makeParkingLotFull();
        parkingLot.unpark();
        assertEquals(CAPACITY-1,parkingLot.getBookedSlotsCount());
    }
@Test
public void shouldNotNotifyFastFillingListenerOnChangeWithinThresholdAndMaxCapacityRange(){
    makeParkingLotFull();
    ParkingLotListener parkingLotListener = mock(ParkingLotListener.class);
    parkingLot.addListener(parkingLotListener);
    parkingLot.unpark();
    parkingLot.unpark();
    verify(parkingLotListener,times(1)).notifyParkingFillingFast();
    verify(parkingLotListener, only()).notifyParkingFillingFast();
}
    public void fillParkingLotOneSlotBeforeFastFilling(){
        int fastFillingCapacity1Before = FASTFILLINGTHRESHOLD-1;
        fillParkingLot(fastFillingCapacity1Before);
    }

    public void makeParkingLotFull(){
        fillParkingLot(CAPACITY);
    }

    public void fillParkingLot(int toExtent) {
        for(int i = 0 ; i < toExtent ; i++) {
            parkingLot.parkCar();
        }
    }

}
