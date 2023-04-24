package hu.bme.mit.train.sensor;

import hu.bme.mit.train.controller.TrainControllerImpl;
import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.user.TrainUserImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;


public class TrainSensorTest {
    private TrainController controller;
    private TrainUser user;
    private TrainSensor sensor;

    @Before
    public void before() {
        controller = mock(TrainController.class);
        user = mock(TrainUser.class);
        //user = new TrainUserImpl(controller);
        sensor = new TrainSensorImpl(controller, user);
    }

   /* @Test
    public void DangerousSituation() {
        danger = true;
        sensor.dangerDetection(danger);
    }

    @Test
    public void Test7() {
        sensor.logTachograph();
        Assert.assertEquals(1, sensor.getLogSize());
    }*/

    //uj sebesseg korlatozas 0 alatti
    @Test
    public void alarmTest1(){
        sensor.overrideSpeedLimit(-2);
       // Assert.assertEquals(true, user.getAlarmState());
        verify(user, times(1)).setAlarmState(true);
    }

     //uj sebesseg korlatozas 500 feletti
     @Test
     public void alarmTest2(){
         sensor.overrideSpeedLimit(525);
        // Assert.assertEquals(true, user.getAlarmState());
         verify(user, times(1)).setAlarmState(true);
     }

      //uj sebesseg korlatozas tul nagy kulonbseg
      @Test
      public void alarmTest3(){
        when(controller.getReferenceSpeed()).thenReturn(150);
        sensor.overrideSpeedLimit(50);
        verify(controller, times(1)).getReferenceSpeed();
        verify(user, times(1)).setAlarmState(true);
      }

     //uj sebesseg korlatozas jo
     @Test
     public void alarmTest4(){
         when(controller.getReferenceSpeed()).thenReturn(70);
         sensor.overrideSpeedLimit(50);
         verify(controller, times(1)).getReferenceSpeed();
         verify(user, times(0)).setAlarmState(false);
         verify(user, times(0)).setAlarmState(true);
     }
}
