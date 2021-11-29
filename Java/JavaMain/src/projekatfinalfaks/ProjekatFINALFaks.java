
package projekatfinalfaks;

import Baza.Database;
import Entiteti.eTrening;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ProjekatFINALFaks extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        cUnosClanova cU=cUnosClanova.prikaziFormuZaUnos();
        
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}
