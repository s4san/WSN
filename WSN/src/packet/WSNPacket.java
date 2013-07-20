package packet;

public class WSNPacket {
	
	public WSNPacket( String msg ){
		this.msg = msg;
		this.hopCount = 0;
	}
	public String echo(){
		return this.msg;
	}
	
	public void incrementHopCount(){
		this.hopCount += 1;
	}
	
	public void setHopCount( int hc ){
		this.hopCount = hc;
	}
	public int getHopCount(){
		return this.hopCount;
	}
	
	public int getSource(){
		return this.packetSource;
	}
	
	public int getDest(){
		return this.packetDest;
	}
	
	public void setSource( int source ){
		this.packetSource = source;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setDest( int dest ){
		this.packetDest = dest;
	}

	private String msg;
	private int packetSource;
	private int packetDest;
	private int hopCount;
}
