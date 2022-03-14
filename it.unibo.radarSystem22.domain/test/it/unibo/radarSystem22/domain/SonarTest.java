package it.unibo.radarSystem22.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class SonarTest {

	@Before
	public void up() {
		System.out.println("up");
	}

	@After
	public void down() {
		System.out.println("down");		
	}
	
	@Test
	public void testSonarMockIsActive() {
		
		System.out.println("TEST IsActive Sonar");
		
		DomainSystemConfig.simulation = true ;
		ISonar sonar = DeviceFactory.createSonar(); 
		
		
		sonar.activate();
		
		assertTrue(sonar.isActive() == true);
		
	}
	
	@Test
	public void testSonarMockInitVal() {
		
		System.out.println("TEST InitVal Sonar");
		
		DomainSystemConfig.simulation = true ;
		ISonar sonar = DeviceFactory.createSonar(); 
			
		assertTrue(sonar.getDistance().getVal() == 90);
		
	}
	
	@Test
	public void testSonarMockDistance() {
		
		System.out.println("TEST Distance Sonar");
		
		DomainSystemConfig.simulation = true ;
		ISonar sonar = DeviceFactory.createSonar(); 
		
		int initVal = sonar.getDistance().getVal();
		int delay = 5000;
		
		sonar.activate();
		BasicUtils.delay(5000);
		int expectedVal = initVal - delay/DomainSystemConfig.sonarDelay ;
		System.out.println("Expected "+expectedVal+" ; Val = "+sonar.getDistance().getVal());
		assertTrue(sonar.getDistance().getVal() == expectedVal);
		sonar.deactivate();
		
	}
	
	
}
