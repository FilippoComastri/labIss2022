package unibo.actor22.common;

import it.unibo.kactor.IApplMessage;
import it.unibo.radarSystem22.domain.DeviceFactory;
import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.concrete.RadarDisplay;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import unibo.actor22.QakActor22;
import unibo.actor22comm.interfaces.ActionFun;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;

public class ControllerActorLedSonar extends QakActor22{
	
	private IDistance distanceFromSonar ;
	private IRadarDisplay radarDisplay;
	private int limit ;
	private int numIter = 1 ;
	private ActionFun endFun;
	private boolean radar ;

	public ControllerActorLedSonar(String name, int limit, ActionFun endFun, boolean radar) {
		super(name);
		this.radar = radar ;
		this.limit = limit ;
		this.endFun = endFun ;
		if (radar) this.radarDisplay = DeviceFactory.createRadarGui();
	}

	@Override
	protected void handleMsg(IApplMessage msg) {  
		if( msg.isReply() ) {
			elabReply(msg);
		}else { 
			elabCmd(msg) ;	
		}
 	}
	
	protected void elabCmd(IApplMessage msg) {
		String msgCmd = msg.msgContent();
		ColorsOut.outappl( getName()  + " | elabCmd=" + msgCmd + " obs=" + RadarSystemConfig.sonarObservable, ColorsOut.BLUE);
		if( msgCmd.equals(ApplData.cmdActivate)  ) {
 				sendMsg( ApplData.activateSonar);
 				request(ApplData.askDistance);
 		}		
	}

	
	public void elabReply(IApplMessage msg) {
		ColorsOut.outappl( getName()  + " | elabAnswer , distance from Sonar = "+ msg, ColorsOut.MAGENTA);
		this.distanceFromSonar = new Distance(msg.msgContent());
		doControllerWork(this.limit);
	}
	
	private void doControllerWork(int limit) {
		
		if( numIter++ < limit ) {
			if(this.radar) this.radarDisplay.update(""+this.distanceFromSonar.getVal(), "30");
 			if (this.distanceFromSonar.getVal()<DomainSystemConfig.DLIMIT) {
 				System.out.println("Accendo il led perchè distanza vale "+this.distanceFromSonar.getVal());
 				forward(ApplData.turnOnLed);
 			} else {
 				forward(ApplData.turnOffLed);
 			}
 			request (ApplData.askDistance);
 			BasicUtils.delay(DomainSystemConfig.sonarDelay);
	       }else {
	    	   this.endFun.run("Controller | Bye");
	    	   forward(ApplData.deactivateSonar);
	      }
	}

}
