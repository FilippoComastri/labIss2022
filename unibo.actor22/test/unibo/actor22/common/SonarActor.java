package unibo.actor22.common;

import it.unibo.kactor.IApplMessage;
import it.unibo.kactor.MsgUtil;
import it.unibo.radarSystem22.domain.DeviceFactory;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import unibo.actor22.QakActor22;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;

public class SonarActor extends QakActor22{

	private ISonar sonar;

	public SonarActor(String name) {
		super(name);
		sonar = DeviceFactory.createSonar();
	}

	@Override
	protected void handleMsg(IApplMessage msg) {
		CommUtils.aboutThreads(getName()  + " |  Before doJob - ");
		ColorsOut.out( getName()  + " | doJob " + msg, ColorsOut.CYAN);
		if( msg.isRequest() ) elabRequest(msg);
		else elabCmd(msg);
	}

	protected void elabRequest(IApplMessage msg) {
		String msgReq = msg.msgContent();
		//ColorsOut.out( getName()  + " | elabRequest " + msgCmd, ColorsOut.CYAN);
		switch( msgReq ) {
		case ApplData.reqIsActiveSonar  :{
			boolean b = sonar.isActive();
			IApplMessage reply = MsgUtil.buildReply(getName(), ApplData.reqIsActiveSonar, ""+b, msg.msgSender());
			ColorsOut.out( getName()  + " | reply= " + reply, ColorsOut.CYAN);
			sendReply(msg, reply );				
			break;
		}
		case ApplData.reqDistanceSonar :{
			IDistance d = sonar.getDistance();
			System.out.println("SonarActor | msg content "+d.getVal());
			IApplMessage reply = MsgUtil.buildReply(getName(), ApplData.reqDistanceSonar, ""+d.getVal(), msg.msgSender());
			ColorsOut.out( getName()  + " | reply= " + reply, ColorsOut.CYAN);
			sendReply(msg, reply );				
			break;
		}
		default: ColorsOut.outerr(getName()  + " | unknown " + msgReq);
		}
	}

	protected void elabCmd(IApplMessage msg) {
		String msgCmd = msg.msgContent();
		switch( msgCmd ) {
		case ApplData.cmdActivate  : sonar.activate();break;
		case ApplData.cmdDectivate : sonar.deactivate();break;
		default: ColorsOut.outerr(getName()  + " | unknown " + msgCmd);
		}
	}

}