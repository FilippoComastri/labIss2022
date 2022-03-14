package it.unibo.radarSystem22.domain.concrete;

import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.models.SonarModel;

public class SonarConcrete extends SonarModel implements ISonar {
	
	public SonarConcrete() {}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IDistance getDistance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void sonarSetUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void sonarProduce() {
		// TODO Auto-generated method stub
		
	}

}
