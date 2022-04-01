package it.unibo.radarSystem22.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.interfaces.ISonarObservable;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class TestSonarMockObservable {

	@Before
	public void up() {
		System.out.println("up");
	}
	
	@After
	public void down() {
		System.out.println("down");		
	}	
	
	@Test 
	public void testSonarMockObservable() {
		DomainSystemConfig.simulation = true;
		DomainSystemConfig.testing    = false;
		DomainSystemConfig.sonarDelay = 10;		//quite fast generation ...
		int delta = 1;
		
		ISonarObservable sonar = DeviceFactory.createSonarObservable();	
		SonarObserverFortesting obs = new SonarObserverFortesting("obs1",false);
		sonar.register(obs);
		sonar.activate();
 		while( sonar.isActive() ) { BasicUtils.delay(1000);} //to avoid premature exit
 		sonar.unregister(obs);
 	}
}
