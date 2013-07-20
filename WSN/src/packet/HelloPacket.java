package packet;

public class HelloPacket extends WSNPacket {
	
	public HelloPacket( String content ){
		super( content );
		
		this.path = "Start Node: ";
	}
	
	public HelloPacket(){
		super( "Hello, I'm Hello Packet!" );
		this.path = "Start Node: ";
	}
	
	public void setPath( int x ){
		//System.out.println("STRING VALUE OF :" + String.valueOf( x ));
		this.path = this.path + String.valueOf( x ) ;
	}
	
	public void setPath( String x ){
		this.path = this.path + x;
	}
	
	public String getPath( ){
		return this.path;
	}
		
	private String path;

}
