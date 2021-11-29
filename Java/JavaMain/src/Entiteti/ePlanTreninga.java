package Entiteti;

import Baza.Database;
import Baza.PomocnaKlasa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ePlanTreninga {

    private int idPlana;
    private ArrayList<eTrening> listaTreninga = new ArrayList();

    public ePlanTreninga() {

    }

    public int getIdPlana() {
        return idPlana;
    }

    public ArrayList<eTrening> getListaTreninga() {
        return listaTreninga;
    }

    //setter
    public void dodajPlanuTreninge(ArrayList<eTrening> listaTreninga) {
        this.listaTreninga = listaTreninga;
    }

    public boolean upisiPlanTreninga(eClan clan) {
        try {
            String sql = "INSERT INTO eplantreninga (IDCLANA) VALUES (?)";
            Connection konekcija = Database.connectToDatabase();

            if (konekcija != null) {
                try {

                    PreparedStatement prepStat = konekcija.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    prepStat.setInt(1, clan.getId());
                    int i = prepStat.executeUpdate();

                    if (i == 1) {
                        ResultSet primarniKljuc = prepStat.getGeneratedKeys();
                        primarniKljuc.next();
                        int idPlana = primarniKljuc.getInt(1);
                        this.idPlana = idPlana;

                        //Upis treninga 
                        int idTreninga = 0;
                        int brojacTreninga = 0;
                        for (eTrening trening : clan.getListaPlanaTreninga().get(0).getListaTreninga()) {

                            idTreninga = trening.upisiTrening(clan, trening, idPlana);
                            if (idTreninga == -1) {
                                PomocnaKlasa.greska("Greska kod unosa treninga!!!");
                                return false;
                            }

                            // //upis u cini
                            for (eVezba vezba : clan.getListaPlanaTreninga().get(0).getListaTreninga().get(brojacTreninga).getListaVezbi()) {
                                if (trening.upisiCini(vezba.getIdVezbe(), trening, idTreninga) == false) {
                                    PomocnaKlasa.greska("Greska kod unosa u tabelu cini!!!");
                                    return false;
                                }
                            }
                            brojacTreninga++;

                        }

                        return true;
                    } else {
                        PomocnaKlasa.greska("Doslo je do greske prilikom unosa plana treninga");
                        return false;
                    }

                } catch (SQLException ex) {
                    PomocnaKlasa.greska(ex);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(eClanovi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void ispisiPodatke() {
        for (eTrening trening : listaTreninga) {
            trening.ispisPodatke();
        }
    }

}
