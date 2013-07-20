package node;

import packet.AgentPacket;
import packet.HelloPacket;
import routing.StaticRoutingTable;
import security.NodeRoamerAgent;
import packet.WSNPacket;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Random;
import java.io.IOException;

import base.StaticBaseStation;



public class StaticNode {
	
	public StaticNode( int xPoint, int yPoint ) throws IOException{
		this.x = xPoint;
		this.y = yPoint;
		this.receivedPackets = new CopyOnWriteArrayList<HelloPacket>();
		this.receiveCount = 0;
		this.sentCount = 0;
		this.rt = new StaticRoutingTable();
		this.agent = new NodeRoamerAgent();
	}
	
	public void setRoutingInfo( int idx, StaticNode n ){
		rt.setIndex(idx);
		rt.setEntry( n );
	}
	
	public void setBaseIndex( int n ){
		this.baseIndex = n;
		this.setKnowsBase(true);
	}
	public StaticRoutingTable getRoutingTable(){
		return this.rt;
	}
	
	public void setNeighbor( StaticNode staticNode ){
		this.neighborReference = staticNode;
	}
	
	public StaticNode getNeighbor( ) {
		return this.neighborReference;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	

	public int getBaseIndex(){
		return this.baseIndex;
	}
	
	public boolean senseHelloPacket(){
		Random abc = new Random( System.nanoTime() );
		
		if( abc.nextBoolean() ){
				//System.out.println( "I sensed a packet! Node: " + this.index);
				this.receiveCount += 1;
				HelloPacket hp = new HelloPacket();
				this.send( hp );
				hp.setSource(this.index);
				hp.setDest(this.rt.getIndex());
				hp.setPath( this.index );
				hp.setPath("-->"+String.valueOf(this.rt.getIndex()));
				if( this.rt.getIndex() == this.baseIndex ){
					hp.setPath( ": End Node" );
				}			
				return true;
		}
		
		return false;
		
	}
	
	public void send( WSNPacket p ) throws NullPointerException {
		
		//System.out.println(rt.getIndex());
		
		if( p instanceof HelloPacket ){
			rt.getEntry().receive(p);
			this.sentCount += 1;
		}
		else{
			try{
				this.receive(p);
			}
			catch( NullPointerException npe ){
				npe.printStackTrace();
				System.out.println("THIS INDEX: " + this.index + " NEXT: " + this.getRoutingTable().getIndex());
			}
		}
	}
	
	protected void singleHopSend( WSNPacket aPacket ){
		aPacket.setSource(index);
		aPacket.setDest(baseIndex);
		sbsReference.receive(aPacket);
	}
	
	public void receive( WSNPacket aPacket ){
		if( aPacket instanceof HelloPacket ){
			HelloPacket hp = ( HelloPacket ) aPacket;
			receivedPackets.add(hp);
			hp.incrementHopCount();
			this.receiveCount += 1;
		}
		else{
			if( agent.isCompromised( (AgentPacket) aPacket ) ){
				//System.out.println("COMPROMISED");
				singleHopSend( aPacket );
			}
			rt.getEntry().send( aPacket );
			
		}
	}
	
	public CopyOnWriteArrayList<HelloPacket> publishPackets(){
		return this.receivedPackets;
	}
	
	public void setKnowsBase( boolean val ){
		this.knowsBase = val;
	}
	
	public boolean getKnowsBase( ){
		return this.knowsBase;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public void sendReceivedPackets(){
		for( HelloPacket p : receivedPackets ){
			this.send( p );
			p.setDest(this.rt.getIndex());
			if( ! ( rt.getIndex() == this.baseIndex ) ){
				p.setPath( "-->"+String.valueOf(this.rt.getIndex()) );
			}
			else{
				p.setPath("-->"+String.valueOf(this.rt.getIndex())+": End Node");
			}
		}
	}
	

	public int getReceiveCount() {
		return receiveCount;
	}

	public void setReceiveCount(int receiveCount) {
		this.receiveCount = receiveCount;
	}

	public int getSentCount() {
		return sentCount;
	}

	public void setSentCount(int sentCount) {
		this.sentCount = sentCount;
	}
	
	public  float getNeighborThroughput(){
		if( neighborReference.getReceiveCount() != 0 && ( neighborReference.getReceiveCount() == neighborReference.getSentCount() ) ){
			return ( ( ( float ) this.neighborReference.getReceiveCount()  / ( float ) this.neighborReference.getSentCount() ) * 100 );
		}
		else if( neighborReference.getReceiveCount() == 0 ){
			return (float) 0.0;
		}
		HelloPacket hp = new HelloPacket( "Drop Node Present" );
		hp.setSource(index);
		hp.setDest(baseIndex);
		sbsReference.receive(hp);
		return (float) 1/ (float) 0;
	}
	
	public CopyOnWriteArrayList<HelloPacket> getReceivedPackets(){
		return receivedPackets;
	}
	
	public NodeRoamerAgent getAgent(){
		return this.agent;
	}
	
	public StaticBaseStation getSbsReference() {
		return sbsReference;
	}

	public void setSbsReference(StaticBaseStation sbsReference) {
		this.sbsReference = sbsReference;
	}
	
	@Override
	public String toString(){
		return "\n" + index + " ( " + x + " , " + y + " )" + " " + neighborReference.getIndex() + " " + receiveCount + " " + sentCount + " " + this.getNeighborThroughput() + " " + agent.isCompromised();
	}

	private int x;
	private	int y;
	private int index;
	private int baseIndex;
	private StaticBaseStation sbsReference;
	private StaticNode neighborReference;
	private StaticRoutingTable rt;
	private boolean knowsBase = false;
	private CopyOnWriteArrayList<HelloPacket> receivedPackets;
	private int receiveCount;
	private int sentCount;
	private NodeRoamerAgent agent;
}
