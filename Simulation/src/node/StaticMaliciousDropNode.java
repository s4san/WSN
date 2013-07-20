package node;

import java.io.IOException;

import packet.WSNPacket;

public class StaticMaliciousDropNode extends StaticNode {
	public StaticMaliciousDropNode( int x, int y ) throws IOException{
		super( x, y );
	}
	
	@Override
	public void receive( WSNPacket aPacket ){
		this.setReceiveCount(this.getReceiveCount() + 1);
	}

}
