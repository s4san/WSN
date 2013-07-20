package topology;

import node.StaticNode;
import base.StaticBaseStation;
import packet.AgentPacket;

import java.util.Arrays;
import java.util.ArrayList;

public class StaticTopology {
	
	public StaticTopology( StaticBaseStation s, StaticNode... nodeList ){
		
		this.nodeArrayList = new ArrayList<StaticNode>( Arrays.asList( nodeList ) );
		for( StaticNode sn : this.nodeArrayList ){
			sn.setIndex(nodeArrayList.indexOf(sn));
		}
		
		this.agentPacketReference = new AgentPacket();
		this.sbsReference = s;
		
		s.setIndex(this.nodeArrayList.size());
		s.getAgent().setAgentMsg(this.agentPacketReference.getMsg());
					
		for( StaticNode sn : this.nodeArrayList ){
			sn.setBaseIndex(nodeArrayList.indexOf(sbsReference));
			sn.setSbsReference(this.sbsReference);
			sn.getAgent().setAgentMsg(this.agentPacketReference.getMsg());
		}
		
	}
	
	public void addNode( StaticNode aNode ){
		
		this.nodeArrayList.add( aNode );
		aNode.setIndex(this.nodeArrayList.indexOf(aNode));
		sbsReference.setIndex(aNode.getIndex()+1);
		aNode.setBaseIndex(sbsReference.getIndex());
		aNode.setKnowsBase(true);
		aNode.setSbsReference(this.sbsReference);
		aNode.getAgent().setAgentMsg(this.agentPacketReference.getMsg());
	}
	
	public ArrayList<StaticNode> getAllNodes(){
		return this.nodeArrayList;
	}
	
	public void generateRoutingTables( ){
		for( StaticNode sn : nodeArrayList ){
				
				int next = sn.getIndex() + 1;
				if( next != this.nodeArrayList.size() ){
					int temp = sn.getIndex() + 1;
					sn.setRoutingInfo( temp, nodeArrayList.get( temp ));
					sn.setNeighbor(nodeArrayList.get( temp ));
				}
				
		}
		
		int last = this.nodeArrayList.size() - 1;
		this.nodeArrayList.get( last ).setNeighbor( nodeArrayList.get( 0 ) );
		this.nodeArrayList.get( last ).setRoutingInfo( this.sbsReference.getIndex(), this.sbsReference );
	}
	
	public AgentPacket getAgentPacketReference() {
		return agentPacketReference;
	}

	public void setAgentPacketReference(AgentPacket agentPacketReference) {
		this.agentPacketReference = agentPacketReference;
	}

	private ArrayList<StaticNode> nodeArrayList;
	private StaticBaseStation sbsReference;
	private AgentPacket agentPacketReference;

}
