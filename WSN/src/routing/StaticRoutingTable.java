package routing;

import node.StaticNode;

public class StaticRoutingTable {

	public StaticRoutingTable(){
	}
	
	public StaticNode getEntry( ){
		return this.entry; 
	}
	
	public int getIndex( ){
		return this.idx;
	}
	
	public void setIndex( int idx ){
		this.idx = idx;
	}
	
	public void setEntry( StaticNode entry ){
		this.entry = entry;
		//System.out.println( entry.getIndex() );
	}
	private int idx;
	private StaticNode entry;
}
