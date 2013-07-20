package security;

import packet.AgentPacket;

public class NodeRoamerAgent {

	public NodeRoamerAgent( ){
		this.compromised = false;
		this.agentMsg = "";
	}
	
	public String getAgentMsg() {
		return agentMsg;
	}

	public void setAgentMsg(String agentMsg) {
		this.agentMsg = agentMsg;
	}

	public boolean isCompromised( AgentPacket ap ){
		compromised = !compare( ap );
		ap.setSecure(compare( ap ));
		return compromised;
	}
	
	protected boolean compare( AgentPacket aPacket ){
		return this.agentMsg == aPacket.getMsg();
	}
	
	public boolean isCompromised(){
		return compromised;
	}

	private String agentMsg;
	private boolean compromised;
}
