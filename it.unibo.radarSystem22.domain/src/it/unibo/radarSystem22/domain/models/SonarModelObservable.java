package it.unibo.radarSystem22.domain.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.concrete.SonarConcrete;
import it.unibo.radarSystem22.domain.concrete.SonarConcreteObservable;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.IObserver;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.interfaces.ISonarObservable;
import it.unibo.radarSystem22.domain.mock.SonarMock;
import it.unibo.radarSystem22.domain.mock.SonarMockObservable;
import it.unibo.radarSystem22.domain.utils.ColorsOut;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public abstract class SonarModelObservable implements ISonarObservable {
	
	protected  IDistance curVal = new Distance(90);	 
	protected boolean stopped   = true;
	List<IObserver> observers = new ArrayList<>();
	
	public static ISonar create() {
		if( DomainSystemConfig.simulation )  return createSonarMockObservable();
		else  return createSonarConcreteObservable();		
	}

	public static ISonar createSonarMockObservable() {
		ColorsOut.out("createSonarMock", ColorsOut.BLUE);
		return new SonarMockObservable();
	}	
	public static ISonar createSonarConcreteObservable() {
		ColorsOut.out("createSonarConcrete", ColorsOut.BLUE);
		return new SonarConcreteObservable();
	}	
	
	protected SonarModelObservable() {//hidden costructor, to force setup
		ColorsOut.out("SonarModelObservable | calling (specialized) sonarSetUp ", ColorsOut.BLUE );
		sonarSetUp();   
	}
	
	protected void updateDistance( int d ) {
		curVal = new Distance( d );
		//ColorsOut.out("SonarModelObservable | updateDistance "+ d, ColorsOut.BLUE);
		System.out.println("SonarModelObservable | updateDistance "+ d);
	}	
	
	protected abstract void sonarSetUp() ;
 	protected abstract void sonarProduce() ;

 	@Override
	public boolean isActive() {
		//ColorsOut.out("SonarModel | isActive "+ (! stopped), ColorsOut.GREEN);
		return ! stopped;
	}
	
	@Override
	public IDistance getDistance() {
		return curVal;
	}
	
	@Override
	public void activate() {
		curVal = new Distance( 90 );
 		ColorsOut.out("SonarModelObservable | activate" );
		stopped = false;
		new Thread() {
			public void run() {
				while( ! stopped  ) {
					//Colors.out("SonarModel | call produce", Colors.GREEN);
					sonarProduce(  );
					// se il valore rilevato dal sonar è maggiore-uguale della soglia allora aggiorno gli observer
					if(curVal.getVal() >= DomainSystemConfig.limit_observable) {
						for (IObserver obs : observers) {
							obs.update(curVal.toString());
						}
					}
					//Utils.delay(RadarSystemConfig.sonarDelay);
				}
				ColorsOut.out("SonarModelObservable | ENDS" );
		    }
		}.start();
	}
 	
	@Override
	public void deactivate() {
		ColorsOut.out("SonarModelObservable | deactivate", ColorsOut.BgYellow );
		stopped = true;
	}

	@Override
	public void register(IObserver obs) {
		//ColorsOut.out("SonarModelObservable | adding observer", ColorsOut.YELLOW);
		System.out.println("SonarModelObservable | adding observer");
		observers.add(obs);
	}

	@Override
	public void unregister(IObserver obs) {
		//ColorsOut.out("SonarModelObservable | removing observer", ColorsOut.YELLOW);
		System.out.println("SonarModelObservable | removing observer");
		observers.remove(obs);
		
	}

}
