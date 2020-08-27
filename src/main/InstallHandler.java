package main;


import messaging.StatusMessenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class InstallHandler {
    
    public static boolean install( String app ) {
        try {
            Process p = Runtime.getRuntime().exec( "ping -4 google.com" );
    
            BufferedReader in = new BufferedReader( new InputStreamReader( p.getInputStream() ) );
            OutputStreamWriter out = new OutputStreamWriter( p.getOutputStream() );
            String line;
            while ( ( line  = in.readLine() ) != null ) {
                System.out.println( "LINE: " + line );
            }
           
            out.close();
            in.close();
            
        } catch ( IOException ioe ){
            StatusMessenger.sendStatusMessage( "IOE Error in install(). Report this to Chris." );
            System.err.println( ioe.getMessage() );
            return false;
        }
        return true;
    }
}
