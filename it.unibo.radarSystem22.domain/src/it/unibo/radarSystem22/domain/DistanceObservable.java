package it.unibo.radarSystem22.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.IDistanceObservable;
import it.unibo.radarSystem22.domain.interfaces.IObserver;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class DistanceObservable implements IDistanceObservable {
	
	protected IDistance distance;
	protected List<IObserver> observers = new ArrayList<>();

	@Override
	public int getVal() {
		return distance.getVal();
	}

	@Override
	public void setVal(IDistance val) {
		if(val.getVal()>=DomainSystemConfig.limit_observable) {
			System.out.println("DistanceObservable | Updating all observers");
			int c = 0;
			for (IObserver obs : observers) {
				obs.update(""+val.getVal());
				c++;
			}
			System.out.println("DistanceObservable | updated "+c+" observers");
		}
		
	}

	@Override
	public void addObserver(IObserver obs) {
		System.out.println("DistanceObservable | adding observer");
		observers.add(obs);
		
	}

	@Override
	public void removeObserver(IObserver obs) {
		System.out.println("DistanceObservable | removing observer");
		observers.remove(obs);
		
	}

}
