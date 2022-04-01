package it.unibo.radarSystem22.sprint1;

import java.util.Observable;

import it.unibo.radarSystem22.domain.DeviceFactory;
import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.interfaces.IObserver;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.interfaces.ISonarObservable;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.ColorsOut;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import it.unibo.radarSystem22.sprint1.usecases.LedAlarmUsecase;
import it.unibo.radarSystem22.sprint1.usecases.RadarGuiUsecase;

public class ControllerObserver implements IObserver {
	
	private ILed led;
	private ISonarObservable sonar;
	private IRadarDisplay radar;
	private ActionFunction endFun;
	private int distance;
	
	public static ControllerObserver create(ILed led, ISonarObservable sonar,IRadarDisplay radar ) {
		return new ControllerObserver( led,  sonar, radar  );
	}
	public static ControllerObserver create(ILed led, ISonarObservable sonar ) {
		return new ControllerObserver( led,  sonar, DeviceFactory.createRadarGui()  );
	}
	
	private ControllerObserver( ILed led, ISonarObservable sonar,IRadarDisplay radar ) {
		this.led    = led;
		this.sonar  = sonar;
		this.radar  = radar;
	}
		
	public void start( ActionFunction endFun, int limit ) {
		this.endFun = endFun;
		ColorsOut.outappl("Controller | start with endFun=" + endFun , ColorsOut.GREEN);
		sonar.register(this);
		sonar.activate();
		activate( limit );
	}
	

	/*
	 * Il Controller riceve dati dal sonar e attiva gli use cases
	 */
	protected void activate( int limit ) {
 		new Thread() {
			public void run() { 
				try {
					BasicUtils.aboutThreads("Controller activate | ");
  					boolean sonarActive = sonar.isActive();
					if( sonarActive ) {
						for( int i=1; i<=limit; i++) {
						//while( sonar.isActive() ) {
							IDistance d = new Distance(distance);
							ColorsOut.outappl("Controller | d="+d +" i=" + i, ColorsOut.GREEN  );
							if( radar != null) RadarGuiUsecase.doUseCase( radar,d  );	//
	 						LedAlarmUsecase.doUseCase( led,  d  );   
	 						BasicUtils.delay(DomainSystemConfig.sonarDelay);  //Al ritmo della generazione ...
	 					}
					}				
					//ColorsOut.outappl("Controller | BYE", ColorsOut.BLUE  );					 
					sonar.deactivate();
					endFun.run("Controller | BYE ");
					//System.exit(0);
				} catch (Exception e) {
		 			ColorsOut.outerr("ERROR"+e.getMessage());
				}					
			}
		}.start();
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String d) {
		this.distance = Integer.parseInt(d);
		System.out.println("ControllerObserver | updated distance to "+ d);
	}

}
