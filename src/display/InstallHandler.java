package display;


import data.Config;
import javafx.concurrent.Task;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import messaging.StatusMessenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class InstallHandler {
    private int currentlyDone = 0;
    private long start;
    
    private static InstallHandler instance;
    
    public static InstallHandler getInstance() {
        if ( instance == null )
            instance = new InstallHandler();
        return instance;
    }
    
    public void install( Stream< CheckBox > apps, int count, ProgressBar b ) {
        start = System.currentTimeMillis();
    
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern( "HH:mm:ss" );
        LocalDateTime now = LocalDateTime.now();
    
        StatusMessenger.sendStatusMessage( "Started install at " + dtf.format( now ) );
    
        String mes = "are";
        if ( count == 1 )
            mes = "is";
        System.out.println( "There " + mes + " " + count + " checked." );
        
        Task< Boolean > t = new Task<> () {
            @Override
            protected Boolean call() {
                apps.forEach( selectedItem -> {
                    String appName = selectedItem.getText();
                    System.out.println( "Attempting to install " + appName );
                    System.out.println( "Location of installer: " + Config.getInstance().getInstallerFile( appName ) );
                    try {
                        Process p = Runtime.getRuntime().exec( "powershell -File \"" + Config.getInstance().getInstallerFile( appName ) + "\"" );
        
                        BufferedReader in = new BufferedReader( new InputStreamReader( p.getInputStream() ) );
                        OutputStreamWriter out = new OutputStreamWriter( p.getOutputStream() );
                        BufferedReader err = new BufferedReader( new InputStreamReader( p.getErrorStream() ) );
                        
                        String line, error = null;
                        while ( ( line = in.readLine() ) != null || ( error = err.readLine() ) != null ) {
                            System.out.println( "LINE: " + line );
                            if ( error != null && !error.isEmpty() ) {
                                if ( error.contains( "does not exist" ) )
                                    StatusMessenger.sendStatusMessage( "- Error: Check the file location" );
                                System.err.println( "ERROR: " + error );
                            }
                        }
        
                        out.close();
                        in.close();
                        
                        p.waitFor();
                        System.out.println( "The process is done. Moving on to the next install." );
                    } catch ( IOException | InterruptedException ioe ) {
                        StatusMessenger.sendStatusMessage( "IOE Error in install(). Report this to Chris." );
                        System.err.println( ioe.getMessage() );
                    }
                    
                    StatusMessenger.sendStatusMessage( "- Finished " + appName);
    
                    currentlyDone += 1;
                    
                    updateProgress( currentlyDone, count );
                } );
                
                return true;
            }
        };
        
        t.setOnSucceeded( e -> {
            //indicatorWorking.setVisible( false );
    
            currentlyDone = 0;
            long minutes = ( System.currentTimeMillis() - start ) / 1000 / 60;
            StatusMessenger.sendStatusMessage( "Done! Install took " + minutes + " minutes" );
        } );
        t.setOnFailed( e -> StatusMessenger.sendStatusMessage( "There was an error that caused a complete failure: " + e.getSource().getMessage() ) );
        
        b.progressProperty().bind( t.progressProperty() );
        
        Thread thread = new Thread( t );
        thread.setDaemon( true );
        thread.start();
    }
}
