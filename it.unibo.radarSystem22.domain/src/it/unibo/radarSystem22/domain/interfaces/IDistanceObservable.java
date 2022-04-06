package it.unibo.radarSystem22.domain.interfaces;

import java.util.Observer;

public interface IDistanceObservable extends IDistance {
	
	public void setVal(IDistance val);
	public void addObserver(IObserver obs);
	public void removeObserver(IObserver obs);
}
