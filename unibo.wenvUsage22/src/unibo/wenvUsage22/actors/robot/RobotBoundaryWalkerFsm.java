package unibo.wenvUsage22.actors.robot;
import org.json.JSONObject;
import it.unibo.kactor.IApplMessage;
import unibo.actor22.QakActor22Fsm;
import unibo.actor22comm.interfaces.Interaction2021;
import unibo.actor22comm.interfaces.StateActionFun;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.ws.WsConnSysObserver;
import unibo.actor22comm.ws.WsConnection;
import unibo.wenvUsage22.common.ApplData;
import unibo.wenvUsage22.common.VRobotMoves;

public  class RobotBoundaryWalkerFsm extends QakActor22Fsm {  
	private Interaction2021 conn;
	private int numIter ;
	
	public RobotBoundaryWalkerFsm(String name) {
		super(name);
		this.numIter = 0 ;
 	}
	
  	 
	@Override
	protected void declareTheStates( ) {
		
		
		declareState("start", new StateActionFun() {
			@Override
			public void run(IApplMessage msg) {
				outInfo(""+msg);	
				//Inizializzo la connessione con WEnv
				conn = WsConnection.create("localhost:8091" );				 
				//Aggiungo un observer dei messaggi inviati da WEnv  
				((WsConnection)conn).addObserver(new WsConnSysObserver(getName()));
				addTransition( "goingAhead", ApplData.robotCmdId );
				nextState();
			}			
		});
		declareState("goingAhead", new StateActionFun() {
			@Override
			public void run(IApplMessage msg) {
				outInfo(""+msg);	
 				VRobotMoves.step(getName(),conn);
				addTransition( "checkResult",  "wsEvent" );
 				nextState();
			}			
		});
		declareState("checkResult", new StateActionFun() {
			@Override
			public void run(IApplMessage msg) {
				outInfo(""+msg);	
				String payload = msg.msgContent().replaceAll("'","");//remove ' ' 
				JSONObject json = new JSONObject(payload);
				outInfo(""+json);
				boolean b = false;
				if( json.has("endmove") )  b = json.getBoolean("endmove");
				if(b) {
					VRobotMoves.step(getName(),conn);
					addTransition( "checkResult",  "wsEvent" );
				}else {
					VRobotMoves.turnLeft(getName(), conn);
					addTransition("turnedLeft","wsEvent");
				}
				nextState();
 			}			
		});
		
		
		declareState("turnedLeft", new StateActionFun() {
			@Override
			public void run(IApplMessage msg) {
				outInfo(""+msg);
				numIter ++ ;
				System.out.println("Iterazioni "+numIter);
				if (numIter < 4) {
					VRobotMoves.step(getName(),conn);
					addTransition( "checkResult",  "wsEvent" );
					nextState();
				} else {
					System.out.println("BYE");
				}
  			}			
		});
		
		declareState("end", new StateActionFun() {
			@Override
			public void run(IApplMessage msg) {
				outInfo(""+msg);
				System.out.println("BYE");
  			}			
		});
	}
	
	@Override
	protected void setTheInitialState( ) {
		declareAsInitialState( "start" );
	}
	
	protected void doMove(String move) {
		try {
 			conn.forward( move );
		}catch( Exception e) {
			ColorsOut.outerr("robotMOve ERROR:" + e.getMessage() );
		}
	}

 

}
