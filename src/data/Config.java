package data;

import java.io.File;
import java.util.HashMap;

import static data.Values.defaultPath;

public class Config {
    
    public static Config instance;
    
    public static Config getInstance() {
        if ( instance == null )
            instance = new Config();
        return instance;
    }
    
    private final HashMap< String, String > locations;
    public Config() {
        locations = new HashMap<>();
        
        // Default path is a data value from Values.java
        locations.put( "ArcGIS", defaultPath + "ArcGIS SI" );
        locations.put( "CARIS HIPS SIPS", defaultPath + "Caris SI" );
        locations.put( "Cisco VPN", defaultPath + "Cisco SI" );
        locations.put( "CommVault Simpana", defaultPath + "CommVault SI" );
        locations.put( "GlobalMapper", defaultPath + "GlobalMapper SI" );
        locations.put( "Google Earth Pro", defaultPath + "Google Earth SI" );
        locations.put( "Hypack", defaultPath + "Hypack SI" );
        locations.put( "Matlab", defaultPath + "Matlab SI" );
        locations.put( "Ninite", defaultPath + "Ninite SI" );
        locations.put( "Office 2016", defaultPath + "Office SI" );
        locations.put( "QPS Qimera / Fledermaus", defaultPath + "QPS SI" );
        locations.put( "SonarWiz", defaultPath + "SonarWiz SI" );
        locations.put( "TeraTerm / Tortoise HG", defaultPath + "TTT SI" );
        locations.put( "VMWare Workstation Player", defaultPath + "VMWare SI" );
    }
    
    public File geInstallerFile( String app ) {
        return new File( locations.get( app ) );
    }
}
