package base;

import packet.AgentPacket;
import packet.HelloPacket;
import packet.WSNPacket;
import node.StaticNode;
import topology.StaticTopology;

import java.io.IOException;

public class StaticBaseStation extends StaticNode{
	
	public StaticBaseStation( int xPos, int yPos ) throws IOException{
		super( xPos, yPos );
		secure = true;
		log = "";
	}
	
	public void createTopology( StaticNode... staticNodes ){
		
		this.topology = new StaticTopology( this,  staticNodes );
		topology.generateRoutingTables();
	}
	
	public void addNodeToTopology( StaticNode sn ){
		this.topology.addNode(sn);
		topology.generateRoutingTables();
	}
	
	public  StaticTopology getStaticTopology(){
		return topology;
	}
	
	public void announceBaseLocation(){
		for( StaticNode sn : topology.getAllNodes() ){
			//System.out.println(this.getIndex());
			sn.setBaseIndex(this.getIndex());
		}
		
	}
	
	@Override
	public void receive( WSNPacket aPacket ){
		if( aPacket instanceof HelloPacket ){
			HelloPacket hp = ( HelloPacket ) aPacket;
			this.getReceivedPackets().add(hp);
			hp.incrementHopCount();
			this.setReceiveCount ( this.getReceiveCount() + 1 );
			this.topology.getAgentPacketReference().roam( this.topology.getAllNodes().get(0) );
		}
		else {
			secure = ((AgentPacket) aPacket).isSecure();
			
			if( ! secure ){
				aPacket.incrementHopCount();
				if( aPacket.getSource() != topology.getAllNodes().size()-1){
					
					StringBuilder sb = new StringBuilder();
					sb.append(log);
					sb.append(aPacket.getSource());
					sb.append(" ");
					sb.append(aPacket.getHopCount());
					sb.append(" ");
					sb.append(aPacket.echo());
					sb.append(System.getProperty( "line.separator" ));
					this.log = sb.toString();
				}
						
				aPacket.setHopCount(0);
			}
			//System.out.println( secure );
			
		}
	}

	
	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	@Override
	public String toString(){
		return log;
	}

	private StaticTopology topology;
	private boolean secure;
	private String log;
}
