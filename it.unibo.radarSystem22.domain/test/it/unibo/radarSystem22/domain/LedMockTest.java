package it.unibo.radarSystem22.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.mock.LedMock;

public class LedMockTest {
	
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
		
		ILed led = new LedMock();  // in seguito qua ci dovremmo mettere il LedConcrete
		assertTrue(led.getState()==false);
		
		led.turnOn();
		assertTrue(led.getState()==true);
	}
	
	@Test
	public void testLedMockOff() {
		
		System.out.println("TEST LedMock Off");
		
		ILed led = new LedMock();  // in seguito qua ci dovremmo mettere il LedConcrete
		assertTrue(led.getState()==false);
		
		led.turnOn();
		assertTrue(led.getState()==true);
		
		led.turnOff();
		assertTrue(led.getState()==false);
	}
	
	@Test
	public void failedTest() {
		
		System.out.println("TEST LedMock Off");
		
		ILed led = new LedMock();  // in seguito qua ci dovremmo mettere il LedConcrete
		assertTrue(led.getState()==false);
		
		led.turnOn();
		assertTrue(led.getState()==true);
		
		led.turnOff();
		assertTrue(led.getState()==true);
	}
	

}
