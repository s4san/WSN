package node;

import packet.WSNPacket;
import java.io.IOException;

public class StaticMaliciousNode extends StaticNode {
	public StaticMaliciousNode( int x, int y ) throws IOException{
		super( x, y );
	}
	
	@Override
	public void receive( WSNPacket aPacket ){
			aPacket.setMsg("Hacked you! Bitch!");
			super.receive( aPacket );
	}

}
