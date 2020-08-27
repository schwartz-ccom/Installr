package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start( Stage primaryStage ) throws Exception {
        Parent root = FXMLLoader.load( getClass().getResource( "installer_view.fxml" ) );
        primaryStage.setTitle( "CCOM Installer" );
        
        Scene primaryScene = new Scene( root, 400, 450 );
        
        primaryStage.setResizable( false );
        primaryStage.setScene( primaryScene );
        primaryStage.show();
    }
    
    public static void main( String[] args ) {
        launch( args );
    }
}
