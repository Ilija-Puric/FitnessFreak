package GenerisanjePlanaTreninga;

import Baza.PomocnaKlasa;
import Entiteti.eClan;
import Entiteti.eClanovi;
import Entiteti.eMisicnaGrupa;
import Entiteti.ePlanTreninga;
import Entiteti.eTrening;
import Entiteti.eVezba;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projekatfinalfaks.cUnosClanova;

public class cGenerisiPlan implements Initializable {

    @FXML
    private GridPane gridVezbi;
    @FXML
    private TableView<eClan> tClan;
    @FXML
    private TableColumn<eClan, Integer> cID;
    @FXML
    private TableColumn<eClan, String> cJMBG;
    @FXML
    private TableColumn<eClan, String> cIme;
    @FXML
    private TableColumn<eClan, String> cPrezime;
    @FXML
    private TableColumn<eClan, Date> cDatRodjenja;
    @FXML
    private TableColumn<eClan, Float> cVisina;
    @FXML
    private TableColumn<eClan, Character> cPol;
    @FXML
    private TableColumn<eClan, String> cPlanFA;
    @FXML
    private TableColumn<eClan, Float> cBMI;
    @FXML
    private TableColumn<eClan, String> cStatusZdravlja;
    @FXML
    private TableColumn<eClan, String> cBrojTelefona;
    @FXML
    private TableColumn<eClan, Float> cTelesnaTezina;
    private ObservableList<eClan> listaClanova = FXCollections.observableArrayList();
    @FXML
    private TableColumn<eClan, Date> cDatumUclanjenja;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cJMBG.setCellValueFactory(new PropertyValueFactory<>("jmbg"));
        cID.setCellValueFactory(new PropertyValueFactory<>("id"));
        cIme.setCellValueFactory(new PropertyValueFactory<>("ime"));
        cPrezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));
        cDatRodjenja.setCellValueFactory(new PropertyValueFactory<>("datumRodjenja"));
        cVisina.setCellValueFactory(new PropertyValueFactory<>("visina"));
        cTelesnaTezina.setCellValueFactory(new PropertyValueFactory<>("telesnaTezina"));
        cPol.setCellValueFactory(new PropertyValueFactory<>("pol"));
        cPlanFA.setCellValueFactory(new PropertyValueFactory<>("planFizickeAktivnosti"));
        cBMI.setCellValueFactory(new PropertyValueFactory<>("BMI"));
        cStatusZdravlja.setCellValueFactory(new PropertyValueFactory<>("statusZdravlja"));
        cBrojTelefona.setCellValueFactory(new PropertyValueFactory<>("brojTelefona"));
        cDatumUclanjenja.setCellValueFactory(new PropertyValueFactory<>("datumUclanjenja"));

    }

    public static cGenerisiPlan generisiPlan() throws IOException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(cGenerisiPlan.class.getResource("/GenerisanjePlanaTreninga/bGenerisiPlan.fxml"));
        Parent root = loader.load();;
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Unos");
        stage.getIcons().add(new Image("/Slike/logoAplikacijaPNG.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        cGenerisiPlan c = loader.getController();

        return c;
    }

    //Preko ovoga prosledjujem clana ovom kontroleru!!!!
    public void primiClana(eClan clan) {

        ArrayList<eMisicnaGrupa> listaMisicnihGrupa = new ArrayList<>();

        listaMisicnihGrupa.addAll(eMisicnaGrupa.vratiMisicneGrupe());

        //Provera da li imam misicnih grupa u bazi
        if (listaMisicnihGrupa.isEmpty()) {
            PomocnaKlasa.nasilnoPrekiniProgram("Nisu vraćene misicne grupe iz baze");
        }

        ArrayList<eVezba> listaVezbi = new ArrayList<>();

        //Dodaj vezbe u listu/ Vrati vezbe
        //odredi broj ponavljanja,serija,vreme,kilazu za dizanje//
        //Dodaj misicnu grupu vezbi
        listaVezbi.addAll(eVezba.vratiVezbe(clan.getDatumRodjenja(), clan.getPol(), clan.getStatusZdravlja(), clan.getTelesnaTezina()));

        //Provera da li imam vezbi u bazi
        if (listaVezbi.isEmpty()) {
            PomocnaKlasa.nasilnoPrekiniProgram("Nisu vraćene vezbe iz baze");
        }

        ePlanTreninga planTreninga = new ePlanTreninga();
        //dodajPlanuTreninge       //dodaj treninge         //dodaj vezbe  
        planTreninga.dodajPlanuTreninge(eTrening.dodajTreninge(listaVezbi, clan.getPlanFizickeAktivnosti()));

        eClanovi clanovi = new eClanovi();

        clan.dodajClanuPlanTreninga(planTreninga);

//        //NE BRISI!!!!
        boolean uspesanUnosClana = clanovi.upisiClana(clan);

        if (uspesanUnosClana) {

            PomocnaKlasa.prikaziAlert(Alert.AlertType.INFORMATION, "Uspešan unos!!!", "",
                    ("Uspešno ste uneli člana " + clan.getIme() + " " + clan.getPrezime() + " sa ID brojem:" + clan.getId() + " u bazu"));

            ispisGenerisanogPlana(clan, planTreninga);

            listaClanova.add(clan);
            tClan.setItems(listaClanova);
        }
        
        else{
             PomocnaKlasa.prikaziAlert(Alert.AlertType.ERROR, "Neuspeh", "Neuspesan unos", "Niste uspesno generisali plan treninga!!!");
        }

    }

    public void ispisGenerisanogPlana(eClan clan, ePlanTreninga planTreninga) {
        ////GridPane dodavanje u njega labele
        //Za svaki dan mi treba label u odnosu na plan treninga toliko labela...
        StringBuilder vezbe = new StringBuilder();

        Label label1 = new Label();
        Label label2 = new Label();
        Label label3 = new Label();
        Label label4 = new Label();
        
        if (clan.getPlanFizickeAktivnosti().equals("Lagana: 1-3 puta nedeljno")) {
            int brojVezbe = 1;
            for (eTrening trening : planTreninga.getListaTreninga()) {

                vezbe.append("nedelja: " + trening.getNedelja() + "\n");
                vezbe.append("dan: " + trening.getBrDanaUNed() + "\n");
                for (eVezba vezba : trening.getListaVezbi()) {
                    vezbe.append("Vežba broj: " + (brojVezbe) + "\n");
                    vezbe.append("Naziv: " + vezba.getNaziv() + "\n");
                    vezbe.append("Broj ponavljanja: " + PomocnaKlasa.pretvori(vezba.getBrojPonavljanja()) + "\n");
                    vezbe.append("Broj serija: " + vezba.getBrojSerija() + "\n");
                    vezbe.append("Dodatno opterećenje(kg): " + PomocnaKlasa.pretvori(vezba.getDodatnoOpterecenje()) + "\n");
                    vezbe.append("Vreme trajanja(sekunde): " + PomocnaKlasa.pretvori(vezba.getVremeTrajanja()) + "\n");
                    vezbe.append("Misićne grupe:\n");

                    for (eMisicnaGrupa misicnaGrupa : vezba.getListaMisicnihGrupa()) {
                        vezbe.append("  -" + misicnaGrupa.getNazivGrupe());
                        vezbe.append("\n");
                    }
                    vezbe.append("------------------\n");

                    brojVezbe++;
                }

                if (trening.getNedelja() == 1 && trening.getBrDanaUNed() == 3) {
                    label1.setText(vezbe.toString());
                    vezbe.delete(0, vezbe.length());
                } else if (trening.getNedelja() == 2 && trening.getBrDanaUNed() == 3) {
                    label2.setText(vezbe.toString());
                    vezbe.delete(0, vezbe.length());
                } else if (trening.getNedelja() == 3 && trening.getBrDanaUNed() == 3) {
                    label3.setText(vezbe.toString());
                    vezbe.delete(0, vezbe.length());
                } else if (trening.getNedelja() == 4 && trening.getBrDanaUNed() == 3) {
                    label4.setText(vezbe.toString());
                    vezbe.delete(0, vezbe.length());
                }

                brojVezbe = 1;
            }

        } else if (clan.getPlanFizickeAktivnosti().equals("Umerena: 4-5 puta nedeljno")) {
            int brojVezbe = 1;
            for (eTrening trening : planTreninga.getListaTreninga()) {

                vezbe.append("nedelja: " + trening.getNedelja() + "\n");
                vezbe.append("dan: " + trening.getBrDanaUNed() + "\n");
                for (eVezba vezba : trening.getListaVezbi()) {
                    vezbe.append("Vežba broj: " + (brojVezbe) + "\n");
                    vezbe.append("Naziv: " + vezba.getNaziv() + "\n");
                    vezbe.append("Broj ponavljanja: " + PomocnaKlasa.pretvori(vezba.getBrojPonavljanja()) + "\n");
                    vezbe.append("Broj serija: " + vezba.getBrojSerija() + "\n");
                    vezbe.append("Dodatno opterećenje(kg): " + PomocnaKlasa.pretvori(vezba.getDodatnoOpterecenje()) + "\n");
                    vezbe.append("Vreme trajanja(sekunde): " + PomocnaKlasa.pretvori(vezba.getVremeTrajanja()) + "\n");
                    vezbe.append("Misićne grupe:\n");

                    for (eMisicnaGrupa misicnaGrupa : vezba.getListaMisicnihGrupa()) {
                        vezbe.append("  -" + misicnaGrupa.getNazivGrupe());
                        vezbe.append("\n");
                    }
                    vezbe.append("------------------\n");

                    brojVezbe++;
                }

                if (trening.getNedelja() == 1 && trening.getBrDanaUNed() == 5) {
                    label1.setText(vezbe.toString());
                    vezbe.delete(0, vezbe.length());
                } else if (trening.getNedelja() == 2 && trening.getBrDanaUNed() == 5) {
                    label2.setText(vezbe.toString());
                    vezbe.delete(0, vezbe.length());
                } else if (trening.getNedelja() == 3 && trening.getBrDanaUNed() == 5) {
                    label3.setText(vezbe.toString());
                    vezbe.delete(0, vezbe.length());
                } else if (trening.getNedelja() == 4 && trening.getBrDanaUNed() == 5) {
                    label4.setText(vezbe.toString());
                    vezbe.delete(0, vezbe.length());
                }

                brojVezbe = 1;
            }

        } else if (clan.getPlanFizickeAktivnosti().equals("Aktivna: Svakodnevno treniranje ")) {
            int brojVezbe = 1;
            for (eTrening trening : planTreninga.getListaTreninga()) {

                vezbe.append("nedelja: " + trening.getNedelja() + "\n");
                vezbe.append("dan: " + trening.getBrDanaUNed() + "\n");
                for (eVezba vezba : trening.getListaVezbi()) {
                    vezbe.append("Vežba broj: " + (brojVezbe) + "\n");
                    vezbe.append("Naziv: " + vezba.getNaziv() + "\n");

                    vezbe.append("Broj ponavljanja: " + PomocnaKlasa.pretvori(vezba.getBrojPonavljanja()) + "\n");
                    vezbe.append("Broj serija: " + vezba.getBrojSerija() + "\n");
                    vezbe.append("Dodatno opterećenje(kg): " + PomocnaKlasa.pretvori(vezba.getDodatnoOpterecenje()) + "\n");
                    vezbe.append("Vreme trajanja(sekunde): " + PomocnaKlasa.pretvori(vezba.getVremeTrajanja()) + "\n");
                    vezbe.append("Misićne grupe:\n");

                    for (eMisicnaGrupa misicnaGrupa : vezba.getListaMisicnihGrupa()) {
                        vezbe.append("  -" + misicnaGrupa.getNazivGrupe());
                        vezbe.append("\n");
                    }
                    vezbe.append("------------------\n");

                    brojVezbe++;
                }

                if (trening.getNedelja() == 1 && trening.getBrDanaUNed() == 7) {
                    label1.setText(vezbe.toString());
                    vezbe.delete(0, vezbe.length());
                } else if (trening.getNedelja() == 2 && trening.getBrDanaUNed() == 7) {
                    label2.setText(vezbe.toString());
                    vezbe.delete(0, vezbe.length());
                } else if (trening.getNedelja() == 3 && trening.getBrDanaUNed() == 7) {
                    label3.setText(vezbe.toString());
                    vezbe.delete(0, vezbe.length());
                } else if (trening.getNedelja() == 4 && trening.getBrDanaUNed() == 7) {
                    label4.setText(vezbe.toString());
                    vezbe.delete(0, vezbe.length());
                }
                brojVezbe = 1;
            }

        }
        gridVezbi.setPadding(new Insets(20, 20, 20, 200));

        gridVezbi.setHgap(15);

        //                //malo CSS-a//
        label1.setStyle("-fx-border-color: black; -fx-font-size: 13;");
        label2.setStyle("-fx-border-color: black; -fx-font-size: 13;");
        label3.setStyle("-fx-border-color: black; -fx-font-size: 13;");
        label4.setStyle("-fx-border-color: black; -fx-font-size: 13;");
        gridVezbi.add(label1, 0, 0, 1, 2);
        gridVezbi.add(label2, 1, 0, 1, 2);
        gridVezbi.add(label3, 2, 0, 1, 2);
        gridVezbi.add(label4, 3, 0, 1, 2);
    }

}
