package Entiteti;

import Baza.Database;
import Baza.PomocnaKlasa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class eMisicnaGrupa {

    private String nazivGrupe;

    public eMisicnaGrupa() {

    }

    public eMisicnaGrupa(String nazivGrupe) {
        this.nazivGrupe = nazivGrupe;
    }

    public String getNazivGrupe() {
        return nazivGrupe;
    }

    public void setNazivGrupe(String nazivGrupe) {
        this.nazivGrupe = nazivGrupe;
    }

    public void finalize()
            throws Throwable {

    }

    public void ispisiPodatke() {

        System.out.println("Misicna grupa: " + nazivGrupe);
    }

    public static ArrayList<eMisicnaGrupa> vratiMisicneGrupe() {

        ArrayList<eMisicnaGrupa> listaGrupa = new ArrayList<>();
        try {
            ResultSet rsMisicneGrupe = Database.getResultSet("select * from emisicnagrupa");

            while (rsMisicneGrupe.next()) {

                eMisicnaGrupa grupa = new eMisicnaGrupa();
                grupa.setNazivGrupe(rsMisicneGrupe.getNString("NAZIVGRUPE"));
                listaGrupa.add(grupa);
            }

            return listaGrupa;
        } catch (SQLException ex) {
            PomocnaKlasa.greska(ex);
            return null;   
        }

    }

    public static ArrayList<eMisicnaGrupa> dodajMisicneGrupeVezbi(int idVezbe) {

        ArrayList<eMisicnaGrupa> listaGrupa = new ArrayList<>();
        try {
            String sql = "select NAZIVGRUPE from sadrzi where IDVEZBE=?";
            Connection konekcija = Database.connectToDatabase();
            if (konekcija != null) {
                try {
                    PreparedStatement prep = konekcija.prepareStatement(sql);
                    prep.setInt(1, idVezbe);
                    ResultSet rsMisicneGrupe = prep.executeQuery();

                    while (rsMisicneGrupe.next()) {
                        eMisicnaGrupa grupa = new eMisicnaGrupa();
                        grupa.setNazivGrupe(rsMisicneGrupe.getNString("NAZIVGRUPE"));
//                        System.out.println(grupa.getNazivGrupe());
                        listaGrupa.add(grupa);
                    }
//                    if(listaGrupa.isEmpty()){
////                        PomocnaKlasa.nasilnoPrekiniProgram("Nije popunjena tabela sadrzi ,nedostaju strani kljucevi IDVEZBE i NAZIVGRUPE!!!");
//                    }   
                    return listaGrupa;

                } catch (SQLException ex) {
                      PomocnaKlasa.greska(ex);
//                    PomocnaKlasa.nasilnoPrekiniProgram(ex, "Greska kod dodavanja misicne grupe u vezbu");
                    return null;
                }
            }

        } catch (SQLException ex) {
            PomocnaKlasa.greska(ex);
            return null;
        }
        return null;
    }

}
