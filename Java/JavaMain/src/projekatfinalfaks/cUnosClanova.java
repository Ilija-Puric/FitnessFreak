/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekatfinalfaks;

import Baza.Database;
import Entiteti.eClan;
import Entiteti.eClanovi;
import Entiteti.eTrening;
import Entiteti.eVezba;
import GenerisanjePlanaTreninga.cGenerisiPlan;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Ilija
 */
public class cUnosClanova implements Initializable {

    @FXML
    private TextField tfIme;
    @FXML
    private TextField tfPrezime;
    @FXML
    private TextField tfKilaza;
    @FXML
    private TextField tfVisina;
    @FXML
    private RadioButton rbM;
    @FXML
    private ToggleGroup Pol;
    @FXML
    private RadioButton rbZ;
    @FXML
    private Button btUnos;
    @FXML
    private ComboBox<String> cbAktivnost;
    @FXML
    private DatePicker dpDatRodjenja;
    @FXML
    private TextField tfBrojTelefona;
    @FXML
    private TextField tfJmbg;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> aktivnost = FXCollections.observableArrayList();

        aktivnost.add("Lagana: 1-3 puta nedeljno");
        aktivnost.add("Umerena: 4-5 puta nedeljno");
        aktivnost.add("Aktivna: Svakodnevno treniranje ");
        cbAktivnost.setItems(aktivnost);

    }

    public static cUnosClanova prikaziFormuZaUnos() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        //menjaj cKlasa i bKlasaView u kontrolere i viewe iz zadatka
        loader.setLocation(cUnosClanova.class.getResource("bUnosClanova.fxml"));
        Parent root = loader.load();;
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Fitness aplikacija");
        stage.getIcons().add(new Image("/Slike/logoAplikacijaPNG.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        cUnosClanova c = loader.getController();
        return c;
    }

    //Ovo treba samo proveru da sadrzi
    @FXML
    private void unesiClana(ActionEvent event) throws IOException {

        String jmbg = null, ime = null, prezime = null, planFizickeAktivnosti = null, brojTelefona = null;
        Date datumRodjenja = null;
        float visina = 0, telesnaTezina = 0;
        char pol = 0;

        StringBuilder ispisGresaka = new StringBuilder("Greske su:\n");
        int brojSlova = ispisGresaka.length();

        if (tfJmbg.getText().equals("")) {
            ispisGresaka.append("--- Niste uneli jmbg\n");
        } else {

            boolean greska = false;
            int brojac = 0;
            for (int i = 0; i < tfJmbg.getText().length(); i++) {
                greska = Character.isDigit(tfJmbg.getText().charAt(i));
                if (!greska) {
                    ispisGresaka.append("--- Uneli ste string umesto jmbg-a\n");
                    break;
                }
                brojac++;
            }

            if (brojac > 13) {
                ispisGresaka.append("--- JMBG ne moze da ima vise od 13 karaktera\n");
            } else if (brojac < 13) {
                ispisGresaka.append("--- JMBG ne moze da ima manje od 13 karaktera\n");
            }

            //daLiClanPostoji
            //VEZA SA BAZOM PROVERA JMBG-a
            if (eClanovi.daLiClanPostoji(tfJmbg.getText().toString())) {
                ispisGresaka.append("--- Uneli ste clana koji ima dati JMBG\n");
            } else {
                jmbg = tfJmbg.getText();
            }

        }

        if (tfIme.getText().equals("")) {
            ispisGresaka.append("--- Niste uneli ime\n");
        } else if (tfIme.getText().length() < 3) {
            ispisGresaka.append("--- Niste uneli ime sa minimalnim brojem karaktera\n");
        } //Proverava da li postoji broj u imenu
        else if ((tfIme.getText().matches(".*\\d.*"))) {
            ispisGresaka.append("--- Uneli ste broj umesto imena\n");
        } else {
            ime = tfIme.getText();
        }

        if (tfPrezime.getText().equals("")) {
            ispisGresaka.append("--- Niste uneli prezime\n");
        } else if (tfPrezime.getText().length() < 3) {
            ispisGresaka.append("--- Niste uneli prezime sa minimalnim brojem karaktera\n");

        } else if ((tfPrezime.getText().matches(".*\\d.*"))) {

            ispisGresaka.append("--- Uneli ste broj umesto prezimena\n");
        } else {
            prezime = tfPrezime.getText();
        }

        if (tfKilaza.getText().equals("")) {
            ispisGresaka.append("--- Niste uneli kilazu\n");
        } else {
            try {

                telesnaTezina = Float.parseFloat(tfKilaza.getText() + "f");
                if (telesnaTezina > 260 || telesnaTezina < 40) {
                    ispisGresaka.append("--- Niste uneli validnu kilazu\n");
                }

            } catch (NumberFormatException e) {
                ispisGresaka.append("--- Uneli ste string umesto telesne težine\n");
            }
        }

        if (tfVisina.getText().equals("")) {
            ispisGresaka.append("--- Niste uneli visinu\n");
        } else {
            try {
                visina = Float.parseFloat(tfVisina.getText() + "f");
                if (visina > 290 || visina < 45.6) {
                    ispisGresaka.append("--- Niste uneli visinu u opsegu od 45.6 do 290 cm\n");
                }

            } catch (NumberFormatException e) {
                ispisGresaka.append("--- Uneli ste string umesto visine\n");
            }
        }
        
        //DATUM
        if (dpDatRodjenja.getValue() == null) {
            ispisGresaka.append("--- Niste selektovali datum rodjenja\n");
        } else if (dpDatRodjenja.getValue().getYear() > 2003 && dpDatRodjenja.getValue().getYear() <= LocalDate.now().getYear()) {
            ispisGresaka.append("--- Nije moguć upis maloletnih lica\n");

        } else if (dpDatRodjenja.getValue().getYear() < 1920) {
            ispisGresaka.append("--- Nije moguć unos lica zbog zdravstvenih razloga\n");
        } else if (dpDatRodjenja.getValue().getYear() > LocalDate.now().getYear()) {
            ispisGresaka.append("--- Niste uneli validan datum\n");

        } else {
            LocalDate datum = dpDatRodjenja.getValue();
            datumRodjenja = java.util.Date.from(datum.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        }

        if (cbAktivnost.getSelectionModel().getSelectedIndex() == -1) {
            ispisGresaka.append("--- Niste selektovali plan aktivnosti\n");
        } else {
            planFizickeAktivnosti = cbAktivnost.getSelectionModel().getSelectedItem();
        }

        if (tfBrojTelefona.getText().equals("")) {
            ispisGresaka.append("--- Niste uneli broj telefona\n");
        } else {

            boolean jesteBroj = false;
            int brojac = 0;
            for (int i = 0; i < tfBrojTelefona.getText().length(); i++) {
                jesteBroj = Character.isDigit(tfBrojTelefona.getText().charAt(i));
                // 6 -true, s-false ako je false generise error
                if (!jesteBroj) {
                    ispisGresaka.append("--- Uneli ste string umesto broj telefona\n");
                    break;
                }
                brojac++;
                if (i == tfBrojTelefona.getText().length() - 1) {
                    if (brojac > 10) {
                        ispisGresaka.append("--- Broj telefona ne moze da ima vise od 10 karaktera\n");
                    } else if (brojac < 10) {
                        ispisGresaka.append("--- Broj telefona ne moze da ima manje od 10 karaktera\n");
                    } else if (eClanovi.daLiBrojTelefonaPostoji(tfBrojTelefona.getText().toString())) {
                        ispisGresaka.append("--- Postoji vec clan sa datim brojem telefona\n");
                    }
                }
            }

            if (jesteBroj) {
                brojTelefona = tfBrojTelefona.getText();

            }

        }

        if (rbM.isSelected()) {
            pol = 'M';
        } else {
            pol = 'Z';
        }

        if (ispisGresaka.length() == brojSlova) {

            eClan clan = new eClan(jmbg, ime, prezime, datumRodjenja, visina, telesnaTezina, pol, planFizickeAktivnosti, brojTelefona);

            cGenerisiPlan cG = cGenerisiPlan.generisiPlan();
            cG.primiClana(clan);

            //clear nakon prosledjivanja kontroleru
            tfJmbg.clear();
            tfIme.clear();
            tfPrezime.clear();
            tfKilaza.clear();
            tfVisina.clear();
            cbAktivnost.getSelectionModel().clearSelection();
            tfBrojTelefona.clear();
            dpDatRodjenja.setValue(null);

        } else {

            Alert neuspesanUpis = new Alert(Alert.AlertType.ERROR);
            neuspesanUpis.setTitle("FitnessFreak");
            neuspesanUpis.setContentText(ispisGresaka.toString() + "\n");
            neuspesanUpis.show();
        }

    }

}
