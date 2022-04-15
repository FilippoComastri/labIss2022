package unibo.actor22.distrib.RSActor22OnRasp;

import it.unibo.kactor.IApplMessage;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import unibo.actor22.Qak22Util;
import unibo.actor22.common.ApplData;
import unibo.actor22.common.ControllerActorLedSonar;
import unibo.actor22.common.LedActor;
import unibo.actor22.common.SonarActor;
import unibo.actor22.local.UsingLedAndControllerOnPc;
import unibo.actor22comm.context.EnablerContextForActors;
import unibo.actor22comm.interfaces.ActionFun;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommSystemConfig;
import unibo.actor22comm.utils.CommUtils;

public class SingleContextWithAll {
	private LedActor led;
	private SonarActor sonar;
	private ControllerActorLedSonar controller;
	private EnablerContextForActors ctx;
	 
		public void doJob(String domainConfig) {
			ColorsOut.outappl("UsingLedSonarController | Start", ColorsOut.BLUE);
			setup(domainConfig);
			configure();
			BasicUtils.aboutThreads("Before execute - ");
			//BasicUtils.waitTheUser();
			execute();
			//terminate();
		}
		
		public void setup( String domainConfig )  {
			if( domainConfig != null ) {
				DomainSystemConfig.setTheConfiguration(domainConfig);
			}
			if( domainConfig == null ) {
				/*DomainSystemConfig.simulation  = true;
		    	DomainSystemConfig.testing     = false;			
		    	DomainSystemConfig.tracing     = false;			
				DomainSystemConfig.sonarDelay  = 200;
		    	DomainSystemConfig.ledGui      = true;		//se siamo su PC	
		    	
			    CommSystemConfig.tracing       = true;*/
				
				DomainSystemConfig.simulation   = true;			
				DomainSystemConfig.ledGui       = true;			
				DomainSystemConfig.tracing      = false;
				DomainSystemConfig.testing		= false;
				DomainSystemConfig.DLIMIT 		= 30;
				
				CommSystemConfig.tracing        = false;

			}
	 
		}
		
		protected void configure() {
			ctx = new EnablerContextForActors( "ctx",ApplData.ctxPort,ApplData.protocol);
	 		this.led = new LedActor( ApplData.ledName );
	 		this.sonar = new SonarActor (ApplData.sonarName);
	 		ActionFun endFun = (n) -> { System.out.println(n); terminate(); };
	 		controller = new ControllerActorLedSonar(ApplData.controllerName,90,endFun,false);
	   	}
		
		protected void execute() {
			ctx.activate();
	  		Qak22Util.sendAMsg( ApplData.activateCrtl );
		} 

		public void terminate() {
			CommUtils.aboutThreads("Before exit - ");
			CommUtils.delay(3000);
			System.exit(0);
		}

		

		public static void main( String[] args) {
			CommUtils.aboutThreads("Before start - ");
			new SingleContextWithAll().doJob("DomainSystemConfig.json");
			CommUtils.aboutThreads("Before end - ");
		}
}
