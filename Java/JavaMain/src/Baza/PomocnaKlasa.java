package Baza;

import java.sql.SQLException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class PomocnaKlasa {

    public static void greska(Exception e) {
        prikaziAlert(Alert.AlertType.ERROR, "Greška", "",
                "Poruka:" + e.getMessage());
    }

    public static void greska(SQLException e) {
        prikaziAlert(Alert.AlertType.ERROR, "Greška", "",
                "Poruka:" + e.getMessage() + "\n Kod greške: " + e.getErrorCode());
    }

    public static void greska(String e) {
        prikaziAlert(Alert.AlertType.ERROR, "Greška", "", e);
    }

    public static void prikaziAlert(Alert.AlertType aType, String title, String headerTekst, String contentText) {
        Alert alert = new Alert(aType);
        alert.setTitle(title);
        alert.setHeaderText(headerTekst);
        alert.setContentText(contentText);
        alert.show();
    }

    public static void nasilnoPrekiniProgram(Exception e, String stringGreske) {
        Alert prikazGreske = new Alert(Alert.AlertType.ERROR);
        prikazGreske.setHeaderText("GRESKA!!!");
        prikazGreske.setContentText(e + "\n" + stringGreske);
        if (prikazGreske.showAndWait().get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        }
    }

    public static void nasilnoPrekiniProgram(String stringGreske) {
        Alert prikazGreske = new Alert(Alert.AlertType.ERROR);
        prikazGreske.setHeaderText("GRESKA!!!");
        prikazGreske.setContentText(stringGreske);
        if (prikazGreske.showAndWait().get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        }
    }

    // Za / umesto 0
    public static String pretvori(int broj) {
        if (broj == 0) {
            return "/";
        } else {
            return Integer.toString(broj);
        }
    }
}
