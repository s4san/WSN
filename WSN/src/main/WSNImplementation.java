package main;

import log.LogNode;
import node.StaticNode;
import node.StaticMaliciousDropNode;
//import node.StaticMaliciousNode;
import base.StaticBaseStation;
import packet.HelloPacket;


import java.util.ArrayList;
import java.io.IOException;

public class WSNImplementation {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws NullPointerException, IOException{
		// TODO Auto-generated method stub
		StaticBaseStation sbs = new StaticBaseStation( 25, 25 );
				
		StaticNode[] sn = new StaticNode[5];
		for( int i = 0, j = 0; i < 5; i++, j++ ){
			sn[i] = new StaticNode( i, j );
		}
		
		sbs.createTopology( sn[0], sn[1], sn[2], sn[3], sn[4] );
		sbs.addNodeToTopology( new StaticNode( 5, 5 ));
	//	sbs.addNodeToTopology(new StaticMaliciousNode( 24, 24 ));
		sbs.addNodeToTopology( new StaticMaliciousDropNode( 24, 24 ) );
		
		ArrayList<StaticNode> nodes = sbs.getStaticTopology().getAllNodes();
			
		int count = 0;
		
		for( StaticNode node : nodes ){
			if( node.getIndex() != nodes.size() - 1 ){
				if( node.senseHelloPacket() ){
				count++;
				}
			}
		}
		
		for( StaticNode node : nodes ){
			node.sendReceivedPackets();
		}
		for( StaticNode node : nodes ){
			node.getNeighborThroughput();
		}
		System.out.printf("\nSensed %d Hello Packets\n", count);
		
		if( sbs.publishPackets().isEmpty() ){
			System.out.println("No Pakets Present! What Went Wrong??!!");
		}
		else{
			LogNode tracert = new LogNode("./" + System.currentTimeMillis() + "tracert.log");
			
			for( HelloPacket hp : sbs.publishPackets() ){
				tracert.write( hp.echo() + " Hop Count: " + hp.getHopCount() + " Source: " + hp.getSource() + " Destination: " + hp.getDest() + " Path: " + hp.getPath() );
			}
			tracert.close();
		}
		
		LogNode logger = new LogNode( "./" + System.currentTimeMillis() +"Node.trace" );
		LogNode packet = new LogNode( "./" + System.currentTimeMillis() +"PacketInfo.log" );
		for( StaticNode node : nodes ){
			logger.write(node.toString());
			packet.write( "Throughput ( Node: " + node.getNeighbor().getIndex() + " ) "+ node.getNeighborThroughput() + "%");
		}
		if( sbs.toString() != "" ){
			LogNode base = new LogNode( "./" + System.currentTimeMillis() +"BaseStation.trace");
			base.write( sbs.toString());
			base.close();
		}
		
		logger.close();
		packet.close();
	}

}
