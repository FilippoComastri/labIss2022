package unibo.actor22.local.RSActor22onPC;

import it.unibo.kactor.IApplMessage;
import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.concrete.RadarDisplay;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.IRadarDisplay;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import unibo.actor22.QakActor22;
import unibo.actor22.common.ApplData;
import unibo.actor22comm.interfaces.ActionFun;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;

public class ControllerActorLedSonar extends QakActor22{
	
	private IApplMessage getDistance;
	private IDistance distanceFromSonar ;
	private IRadarDisplay radarDisplay;
	private int limit ;
	private int numIter = 1 ;
	private ActionFun endFun;

	public ControllerActorLedSonar(String name, int limit, ActionFun endFun) {
		super(name);
		this.getDistance = ApplData.buildRequest(name,"ask", ApplData.reqDistanceSonar, ApplData.sonarName);
		this.radarDisplay = RadarDisplay.getRadarDisplay();
		this.limit = limit ;
		this.endFun = endFun ;
	}

	@Override
	protected void handleMsg(IApplMessage msg) {  
		if( msg.isReply() ) {
			elabAnswer(msg);
		}else { 
			elabCmd(msg) ;	
		}
 	}
	
	protected void elabCmd(IApplMessage msg) {
		String msgCmd = msg.msgContent();
		ColorsOut.outappl( getName()  + " | elabCmd=" + msgCmd, ColorsOut.GREEN);
		switch( msgCmd ) {
			case ApplData.cmdActivate : {
				start(this.limit);
	 			break;
			}
			default:break;
		}		
	}
	
	public void elabAnswer(IApplMessage msg) {
		ColorsOut.outappl( getName()  + " | elabAnswer , distance from Sonar = "+ msg, ColorsOut.MAGENTA);
		this.distanceFromSonar = new Distance(msg.msgContent());
		doControllerWork(this.limit);
	}
	
	private void start(int limit) {
		// TODO funzione di callback alla fine
		forward (ApplData.activateSonar);
		request (this.getDistance);
	}
	
	private void doControllerWork(int limit) {
		
		if( numIter++ < limit ) {
			this.radarDisplay.update(""+this.distanceFromSonar.getVal(), "30");
 			if (this.distanceFromSonar.getVal()<DomainSystemConfig.DLIMIT) {
 				forward(ApplData.turnOnLed);
 			} else {
 				forward(ApplData.turnOffLed);
 			}
 			request (this.getDistance);
 			BasicUtils.delay(DomainSystemConfig.sonarDelay);
	       }else {
	    	   this.endFun.run("Controller | Bye");
	    	   forward(ApplData.deactivateSonar);
	      }
	}

}
