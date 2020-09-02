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
        locations.put( "ArcGIS", defaultPath + "ArcGIS SI.ps1" );
        locations.put( "CARIS HIPS SIPS", defaultPath + "Caris SI.ps1" );
        locations.put( "Cisco VPN", defaultPath + "Cisco SI.ps1" );
        locations.put( "CommVault Simpana", defaultPath + "CommVault SI.ps1" );
        locations.put( "GlobalMapper", defaultPath + "GlobalMapper SI.ps1" );
        locations.put( "Google Earth Pro", defaultPath + "Google Earth SI.ps1" );
        locations.put( "Hypack", defaultPath + "Hypack SI.ps1" );
        locations.put( "Matlab", defaultPath + "Matlab SI.ps1" );
        locations.put( "Ninite", defaultPath + "Ninite SI.ps1" );
        locations.put( "Office 2016", defaultPath + "Office SI.ps1" );
        locations.put( "QPS Qimera / Fledermaus", defaultPath + "QPS SI.ps1" );
        locations.put( "SonarWiz", defaultPath + "SonarWiz SI.ps1" );
        locations.put( "TeraTerm / Tortoise HG", defaultPath + "TTT SI.ps1" );
        locations.put( "VMWare Workstation Player", defaultPath + "VMWare SI.ps1" );
        locations.put( "Firewall / Group Policy", "\\\\Seacow\\Storage\\Scripts\\PowerShell\\CCOM Installer\\Reference Scripts\\Update Firewall and Group Policy.ps1" );
        locations.put( "Test", "C:\\Users\\Schwar7z\\Documents\\Loose files lmao\\Test.ps1" );
    }
    
    public String getInstallerFile( String app ) {
        return locations.get( app );
    }
}
