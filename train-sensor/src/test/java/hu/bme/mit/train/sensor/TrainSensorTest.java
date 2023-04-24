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
    TrainController controller;
    private TrainUser user = new TrainUserImpl(controller);
    private int speedLimit = 5;
    private boolean danger = false;
    TrainSensor sensor = new TrainSensorImpl(controller, user);

    @Before
    public void before() {
        controller = mock(TestController.class);
    }

    @Test
    public void DangerousSituation() {
        danger = true;
        sensor.dangerDetection(danger);
    }

    @Test
    public void Test7() {
        sensor.logTachograph();
        Assert.assertEquals(1, sensor.getLogSize());
    }

    //uj sebesseg korlatozas 0 alatti
    @Test
    public void alarmTest1(){
        sensor.overrideSpeedLimit(-2);
        Assert.assertEquals(true, user.getAlarmState());
    }

     //uj sebesseg korlatozas 500 feletti
     @Test
     public void alarmTest2(){
         sensor.overrideSpeedLimit(525);
         Assert.assertEquals(true, user.getAlarmState());
     }

      //uj sebesseg korlatozas tul nagy kulonbseg
      @Test
      public void alarmTest3(){
         controller.setJoystickPosition(150);
         controller.followSpeed();
         sensor.overrideSpeedLimit(50);
         Assert.assertEquals(true, user.getAlarmState());
      }

     //uj sebesseg korlatozas jo
     @Test
     public void alarmTest4(){
        controller.setJoystickPosition(70);
        controller.followSpeed();
        sensor.overrideSpeedLimit(50);
        Assert.assertEquals(false, user.getAlarmState());
     }
}
