package unibo.wenvUsage22.wshttp;

import java.util.Observable;

import org.json.JSONObject;

import it.unibo.is.interfaces.IObserver;
import unibo.actor22comm.interfaces.Interaction2021;
import unibo.actor22comm.utils.CommUtils;
import unibo.actor22comm.ws.WsConnection;
import unibo.wenvUsage22.common.ApplData;

public class BoundaryWalkerUsingWs implements unibo.actor22comm.interfaces.IObserver {
	
	private Interaction2021 conn;
	private String answer = "";
	private int count = 0 ;
	
	private void doStop() throws Exception {
		System.out.println("FINE ! ");
		this.conn.close();
	}
	
	private void doMove() throws Exception {
		if(answer.contains("collision")) {
			System.out.println("Obstacle!");
			conn.forward(ApplData.turnLeft(300));
			count ++ ;
		}
		else {
			conn.forward(ApplData.moveForward(300));
		}	
	}
	
	private void doJob() throws Exception {
		conn = WsConnection.create("localhost:8091" );
		((WsConnection)conn).addObserver( this);
		
		this.doMove();
	}
	
	public static void main(String[] args) throws Exception   {
		CommUtils.aboutThreads("Before start - ");
 		new BoundaryWalkerUsingWs().doJob();
		CommUtils.aboutThreads("At end - ");
	}

	@Override
	public void update(Observable o, Object data) {
		CommUtils.delay(500);
		JSONObject d = new JSONObject(""+data);
		this.answer = d.toString();
		System.out.println("UPDATE : "+this.answer);
		if (count < 4 )
			try {
				this.doMove();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			try {
				this.doStop();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}

	@Override
	public void update(String value) {
		
	}


}
