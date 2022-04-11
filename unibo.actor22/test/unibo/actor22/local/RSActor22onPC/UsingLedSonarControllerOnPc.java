package unibo.actor22.local.RSActor22onPC;

import it.unibo.kactor.IApplMessage;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import unibo.actor22.Qak22Util;
import unibo.actor22.common.ApplData;
import unibo.actor22.common.LedActor;
import unibo.actor22.common.SonarActor;
import unibo.actor22.local.UsingLedAndControllerOnPc;
import unibo.actor22comm.interfaces.ActionFun;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommSystemConfig;
import unibo.actor22comm.utils.CommUtils;

public class UsingLedSonarControllerOnPc {
	private LedActor led;
	private SonarActor sonar;
	private ControllerActorLedSonar controller;
	 
		public void doJob() {
			ColorsOut.outappl("UsingLedSonarController | Start", ColorsOut.BLUE);
			configure();
			BasicUtils.aboutThreads("Before execute - ");
			//BasicUtils.waitTheUser();
			execute();
			//terminate();
		}
		
		protected void configure() {
			DomainSystemConfig.simulation   = true;			
			DomainSystemConfig.ledGui       = true;			
			DomainSystemConfig.tracing      = false;
			DomainSystemConfig.testing		= false;
			DomainSystemConfig.DLIMIT 		= 10;
			
			CommSystemConfig.tracing        = false;
			
	 		this.led = new LedActor( ApplData.ledName );
	 		this.sonar = new SonarActor (ApplData.sonarName);
	 		ActionFun endFun = (n) -> { System.out.println(n); terminate(); };
	 		controller = new ControllerActorLedSonar(ApplData.controllerName,90,endFun);
	   	}
		
		protected void execute() {
	  		Qak22Util.sendAMsg( ApplData.activateCrtl );
		} 

		public void terminate() {
			CommUtils.aboutThreads("Before exit - ");
			CommUtils.delay(3000);
			System.exit(0);
		}


		public static void main( String[] args) {
			CommUtils.aboutThreads("Before start - ");
			new UsingLedSonarControllerOnPc().doJob();
			CommUtils.aboutThreads("Before end - ");
		}
}
