package unibo.actor22.distrib.RSActor22Distrib;

import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import unibo.actor22.Qak22Context;
import unibo.actor22.Qak22Util;
import unibo.actor22.annotations.ActorLocal;
import unibo.actor22.annotations.ActorRemote;
import unibo.actor22.common.ApplData;
import unibo.actor22.common.ControllerActorLedSonar;
import unibo.actor22.common.LedActor;
import unibo.actor22.common.SonarActor;
import unibo.actor22comm.context.EnablerContextForActors;
import unibo.actor22comm.interfaces.ActionFun;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommSystemConfig;
import unibo.actor22comm.utils.CommUtils;

@ActorLocal(name = {ApplData.controllerName}, 
		implement = { unibo.actor22.common.ControllerActorLedSonar.class })

@ActorRemote(
		name =   {ApplData.ledName,ApplData.sonarName}, 
		host=    {ApplData.raspAddr,ApplData.raspAddr}, 
		port=    { ""+ApplData.ctxPort, ""+ApplData.ctxPort}, 
		protocol={ "TCP" , "TCP" })


public class ControllerAndRadarOnPc {
	private ControllerActorLedSonar controller;

	public void doJob(String domainConfig) {
		ColorsOut.outappl("ControllerRadarOnPC | Start", ColorsOut.BLUE);
		setup(domainConfig);
		configure();
		CommUtils.aboutThreads("Before execute - ");
		//CommUtils.waitTheUser();
		execute();
		terminate();
	}
	
	public void setup( String domainConfig)  {
		if( domainConfig != null ) {
			DomainSystemConfig.setTheConfiguration(domainConfig);
		}
		if( domainConfig == null ) {
			
			DomainSystemConfig.simulation   = false;			
			DomainSystemConfig.ledGui       = false;			
			DomainSystemConfig.tracing      = false;
			DomainSystemConfig.testing		= false;
			DomainSystemConfig.DLIMIT 		= 30;
			
			CommSystemConfig.tracing        = false;

		}
 
	}
	
	protected void configure() {
		ActionFun endFun = (n) -> { System.out.println(n); terminate(); };
		// WITHOUT ANNOTATIONS
//		Qak22Context.setActorAsRemote( 
//				ApplData.ledName, ""+ApplData.ctxPort, ApplData.raspAddr, ApplData.protocol);
//		Qak22Context.setActorAsRemote(
//				ApplData.sonarName, ""+ApplData.ctxPort, ApplData.raspAddr, ApplData.protocol);
// 		controller = new ControllerActorLedSonar(ApplData.controllerName,10000,endFun,true);
		
		// WITH ANNOTATIONS
		Qak22Context.myHandleLocalActorActorDecl(this, 10000, endFun, true);
		Qak22Context.handleRemoteActorDecl(this);
   	}
	
	protected void execute() {
		ColorsOut.outappl("ControllerSonarOnPc | execute", ColorsOut.MAGENTA);
		Qak22Util.sendAMsg( ApplData.activateCrtl );
	} 

	public void terminate() {
		CommUtils.aboutThreads("Before exit - ");
// 	    CommUtils.delay(5000);
//		System.exit(0);
	}
	
	public static void main( String[] args) {
		CommUtils.aboutThreads("Before start - ");
		new ControllerAndRadarOnPc().doJob(null);
		CommUtils.aboutThreads("Before end - ");
	}
}
