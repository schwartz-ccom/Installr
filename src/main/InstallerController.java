package main;

import javafx.scene.control.*;

public class InstallerController {
    
    public CheckBox chkbxArcGis;
    public CheckBox chkbxCaris;
    public CheckBox chkbxCisco;
    public CheckBox chkbxCV;
    public CheckBox chkbxGM;
    public CheckBox chkbxGE;
    public CheckBox chkbxHypack;
    public CheckBox chkbxMatlab;
    public CheckBox chkbxNinite;
    public CheckBox chkbxOffice;
    public CheckBox chkbxQPS;
    public CheckBox chkbxSonarWiz;
    public CheckBox chkbxTTT;
    public CheckBox chkbxVMWare;
    
    private CheckBox[] all;
    
    public Button btnCCOMLoad;
    public Button btnNOAALoad;
    public Button btnClearLoad;
    public Button btnInstall;
    
    public ProgressBar installProgress;
    public ProgressIndicator indicatorWorking;
    
    public CheckBox isLaptop;
    
    private boolean last = false;
    
    private final boolean[] ccomLoad = { true, true, false, true, true, true, true, true, true, true, true, true, true, true };
    private final boolean[] noaaLoad = { true, true, false, true, false, false, false, false, true, true, true, false, true, true };
    private final boolean[] clearLoad = { false, false, false, false, false, false, false, false, false, false, false, false, false, false };
    
    public void initialize() {
        
        // Easy array to cleanup the code down below.
        all = new CheckBox[] {
                chkbxArcGis,
                chkbxCaris,
                chkbxCisco,
                chkbxCV,
                chkbxGE,
                chkbxGM,
                chkbxHypack,
                chkbxMatlab,
                chkbxNinite,
                chkbxOffice,
                chkbxQPS,
                chkbxSonarWiz,
                chkbxTTT,
                chkbxVMWare
        };
        
        btnCCOMLoad.setOnAction( e -> setBoxes( ccomLoad ) );
        
        btnNOAALoad.setOnAction( e -> setBoxes( noaaLoad ) );
        
        btnClearLoad.setOnAction( e -> setBoxes( clearLoad ) );
        
        isLaptop.setOnAction( e -> {
            if ( isLaptop.isSelected() ) {
                last = chkbxCisco.isSelected();
                chkbxCisco.setSelected( true );
                chkbxCV.setSelected( false );
            }
            else
                chkbxCisco.setSelected( last );
        } );
        
        chkbxCisco.setOnAction( e -> last = chkbxCisco.isSelected() );
        
        btnInstall.setOnAction( e -> {
            if ( InstallHandler.install( installProgress, getSelectedApps() ) )
                System.out.println( "Done " + chkbxCisco.getText() );
        } );
    
        
    }
    
    private boolean[] getSelectedApps() {
        boolean[] toInstall = new boolean[ 14 ];
        int count = -1;
        for ( CheckBox appBox: all ) {
            toInstall[ ++count ] = appBox.isSelected();
         }
        
        return toInstall;
    }
    
    private void setBoxes( boolean[] values ) {
        for ( int index = 0; index < all.length; index += 1 )
            all[ index ].setSelected( values[ index ] );
    }
}
