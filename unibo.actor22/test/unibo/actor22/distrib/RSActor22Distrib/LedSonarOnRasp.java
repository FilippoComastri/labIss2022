package unibo.actor22.distrib.RSActor22Distrib;

import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import unibo.actor22.Qak22Context;
import unibo.actor22.annotations.ActorLocal;
import unibo.actor22.common.ApplData;
import unibo.actor22.common.ControllerActorLedSonar;
import unibo.actor22.common.LedActor;
import unibo.actor22.common.SonarActor;
import unibo.actor22.distrib.LedActorOnRasp;
import unibo.actor22comm.context.EnablerContextForActors;
import unibo.actor22comm.interfaces.ActionFun;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommSystemConfig;
import unibo.actor22comm.utils.CommUtils;

@ActorLocal(name = {
		ApplData.ledName, 
		ApplData.sonarName 
		}, 
		implement = {
				unibo.actor22.common.LedActor.class, 
				unibo.actor22.common.SonarActor.class 
				})

public class LedSonarOnRasp {
	
	private EnablerContextForActors ctx;
	private LedActor led;
	private SonarActor sonar;

	public void doJob(String domainConfig) {
		ColorsOut.outappl("LedSonarOnRasp | Start", ColorsOut.BLUE);
		setup(domainConfig);
		configure();
		CommUtils.aboutThreads("Before execute - ");
		//CommUtils.waitTheUser();
		execute();
		terminate();
	}
	
	public void setup( String domainConfig )  {
		if( domainConfig != null ) {
			DomainSystemConfig.setTheConfiguration(domainConfig);
		}
		if( domainConfig == null ) {
			
			DomainSystemConfig.simulation   = true;			
			DomainSystemConfig.ledGui       = true;			
			DomainSystemConfig.tracing      = false;
			DomainSystemConfig.testing		= false;
			DomainSystemConfig.DLIMIT 		= 30;
			
			CommSystemConfig.tracing        = false;

		}
 
	}
	
	protected void configure() {
		this.ctx = new EnablerContextForActors( "ctx",ApplData.ctxPort,ApplData.protocol);
 		
		//WITHOUT ANNOTATIONS
//		this.led = new LedActor( ApplData.ledName );
// 		this.sonar = new SonarActor (ApplData.sonarName);
		
		//WITH ANNOTATIONS
		Qak22Context.handleLocalActorDecl(this);
   	}
	
	protected void execute() {
		ColorsOut.outappl("LedSonaronRasp | execute", ColorsOut.MAGENTA);
		ctx.activate();
	} 

	public void terminate() {
		CommUtils.aboutThreads("Before exit - ");
// 	    CommUtils.delay(5000);
//		System.exit(0);
	}
	
	public static void main( String[] args) {
		CommUtils.aboutThreads("Before start - ");
		new LedSonarOnRasp().doJob("DomainSystemConfig.json");
		CommUtils.aboutThreads("Before end - ");
	}


}
