package main;

import javafx.scene.control.*;
import messaging.StatusMessenger;
import messaging.StatusSubscriber;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Stream;

public class InstallerController implements StatusSubscriber{
    
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
    public TextArea txtUpdates;
    
    private boolean last = false;
    private int currentlyDone = 0;
    private long start;
    
    private final boolean[] ccomLoad = {true, true, false, true, true, true, true, true, true, true, true, true, true, true};
    private final boolean[] noaaLoad = {true, true, false, true, false, false, false, false, true, true, true, false, true, true};
    private final boolean[] clearLoad = {false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    
    public void initialize() {
        
        // Easy array to cleanup the code down below.
        all = new CheckBox[]{
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
            } else
                chkbxCisco.setSelected( last );
        } );
        
        chkbxCisco.setOnAction( e -> last = chkbxCisco.isSelected() );
        
        btnInstall.setOnAction( e -> {
            start = System.currentTimeMillis();
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            
            updateStatus( "Started install at " + dtf.format( now ) );
            
            indicatorWorking.setVisible( true );
            
            Stream< CheckBox > selected = Arrays.stream( all ).filter( CheckBox::isSelected );
            
            int checked = ( int ) Arrays.stream( all ).filter( CheckBox::isSelected ).count();
            System.out.println( "There are " + checked + " checked." );
            
            selected.forEach( selectedItem -> {
                String appName = selectedItem.getText();
                
                if ( InstallHandler.install( appName ) ) {
                    currentlyDone += 1;
                    updateStatusBar( currentlyDone, checked );
                    updateStatus( "Finished " + appName );
                }
                
            } );
            
            currentlyDone = 0;
            indicatorWorking.setVisible( false );
            
            long minutes = ( System.currentTimeMillis() - start ) / 1000 / 60;
            updateStatus( "Install took " + minutes + " minutes" );
        } );
    
        StatusMessenger.subscriber( this );
    }
    
    private void updateStatusBar( double current, double total ) {
        installProgress.setProgress( current / total );
    }
    
    private void setBoxes( boolean[] values ) {
        for ( int index = 0; index < all.length; index += 1 )
            all[ index ].setSelected( values[ index ] );
    }
    
    @Override
    public void updateStatus( String s ) {
        txtUpdates.appendText( s + "\n" );
    }
}
