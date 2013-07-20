package log;

//import java.nio.file.StandardOpenOption.*;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class LogNode {
	public LogNode( String path ) throws IOException{
		
		logFile = Paths.get(path);
		try {
			out = new BufferedOutputStream( Files.newOutputStream( logFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND));
		}
		catch( IOException ioe ){
			ioe.printStackTrace();
		}
	}
	
	public void write( String s ) throws IOException{
		byte data[] = s.getBytes();
		out.write( data, 0, data.length );	
		out.write(System.getProperty("line.separator").getBytes());
		out.flush();
	}
	
	public void close() throws IOException{
		out.close();
	}
	private Path logFile;
	private OutputStream out;
}
