package messaging;

import java.util.ArrayList;

public class StatusMessenger {
    private final static ArrayList< StatusSubscriber > subs = new ArrayList<>();
    
    public static void subscriber( StatusSubscriber sub ) {
        subs.add( sub );
    }
    
    public static void sendStatusMessage( String s ) {
        subs.forEach( sub -> sub.updateStatus( s ) );
    }
}
