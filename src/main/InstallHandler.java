package main;

import javafx.scene.control.ProgressBar;

public class InstallHandler {
    
    private static String[] apps = { "ARC", "CARIS", "CV", "GLOBALMAPPER" };
    
    public static boolean install( ProgressBar pb, boolean[] apps ) {
        
        // Get the total number of apps we need to install.
        int totalNumberToInstall = 0;
        for ( boolean b : apps ) {
            if ( b )
                totalNumberToInstall += 1;
        }
        
        return true;
    }
}
