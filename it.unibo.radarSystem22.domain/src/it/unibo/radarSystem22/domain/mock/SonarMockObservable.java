package it.unibo.radarSystem22.domain.mock;

import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.models.SonarModelObservable;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.ColorsOut;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class SonarMockObservable extends SonarModelObservable implements ISonar{
private int delta = 1;
	@Override
	protected void sonarSetUp() {
		curVal = new Distance(90);
		ColorsOut.out("SonarMockObservable | sonarSetUp curVal="+curVal);
	}
	
	@Override
	public IDistance getDistance() {
		return curVal;
	}	
	@Override
	protected void sonarProduce( ) {
		if( DomainSystemConfig.testing ) {	//produces always the same value
			updateDistance( DomainSystemConfig.testingDistance );			      
		}else {
			int v = curVal.getVal() - delta;
			//ColorsOut.out("SonarMockObservable | value = "+v);
			updateDistance( v );			    
			stopped = ( v <= 0 );
		}
		BasicUtils.delay(DomainSystemConfig.sonarDelay);  //avoid fast generation
 	}
}
