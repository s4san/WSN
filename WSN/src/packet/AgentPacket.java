package packet;

import node.StaticNode;

public class AgentPacket extends WSNPacket {
	
	public AgentPacket(){
		super ("Hello, I'm an Agent!");
	}
	
	public AgentPacket( String x ){
		super ( x );
	}
	
	public void roam( StaticNode startNode ){
		startNode.send( this );
	}
	
	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	private boolean secure;
}
