package it.unibo.radarSystem22.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.mock.LedMock;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class LedTest {
	
	@Before
	public void up() {
		System.out.println("up");
	}
	
	@After
	public void down() {
		System.out.println("down");		
	}
	
	@Test
	public void testLedMockOn() {
		
		System.out.println("TEST LedMock On");
		
		DomainSystemConfig.simulation = true ;
		ILed led = DeviceFactory.createLed();  // in seguito qua ci dovremmo mettere il LedConcrete
		
		assertTrue(led.getState()==false);
		
		led.turnOn();
		assertTrue(led.getState()==true);
	}
	
	@Test
	public void testLedMockOff() {
		
		System.out.println("TEST LedMock Off");
		
		DomainSystemConfig.simulation = true ;
		ILed led = DeviceFactory.createLed(); // in seguito qua ci dovremmo mettere il LedConcrete
		
		assertTrue(led.getState()==false);
		
		led.turnOn();
		assertTrue(led.getState()==true);
		
		led.turnOff();
		assertTrue(led.getState()==false);
	}
	

}
