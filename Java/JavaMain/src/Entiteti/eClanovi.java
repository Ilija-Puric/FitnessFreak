package Entiteti;

import Baza.Database;
import Baza.PomocnaKlasa;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class eClanovi {

    private ArrayList<eClan> listaClanova = new ArrayList<>();

    public eClanovi() {

    }

    public ArrayList<eClan> getListaClanova() {
        return listaClanova;
    }

    public void setListaClanova(ArrayList<eClan> listaClanova) {
        this.listaClanova = listaClanova;
    }

    public void dodajClana(eClan clan) {
        this.listaClanova.add(clan);
    }

    public static boolean daLiClanPostoji(String jmbg) {

        try {
            String sql = "select * from eclan where jmbg=?";
            Connection konekcija = Database.connectToDatabase();

            if (konekcija != null) {
                try {
                    PreparedStatement prepStat = konekcija.prepareStatement(sql);
                    prepStat.setString(1, jmbg);
                    ResultSet rsClan = prepStat.executeQuery();

                    //Ako cursor pokazuje na nesto tj. ako ima sledeci red znaci ima taj jmbg
                    if (rsClan.next()) {
                        return true;
                    } //ne postoji clan sa tim jmbgom                   
                    else {
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

    public static boolean daLiBrojTelefonaPostoji(String brojTelefona) {

        try {
            String sql = "select * from eclan where brojtelefona=?";
            Connection konekcija = Database.connectToDatabase();

            if (konekcija != null) {
                try {
                    PreparedStatement prepStat = konekcija.prepareStatement(sql);
                    prepStat.setString(1, brojTelefona);
                    ResultSet rsClan = prepStat.executeQuery();
                    if (rsClan.next()) {
                        return true;
                    } else {
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

    public boolean upisiClana(eClan clan) {
        try {
            String sql = "INSERT INTO eclan (`JMBG`, `IME`, `PREZIME`, `DATUMRODJENJA`, `VISINA`, `TELESNATEZINA`, `POL`, `PLANFIZICKEAKTIVNOSTI`, `BROJTELEFONA`,`DATUMUCLANJENJA`) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?)";
            Connection konekcija = Database.connectToDatabase();

            if (konekcija != null) {
                try {

                    Date datumUclanjenja = Date.valueOf(LocalDate.now());
                    PreparedStatement prepStat = konekcija.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    
                    prepStat.setString(1, clan.getJmbg());
                    prepStat.setNString(2, clan.getIme());
                    prepStat.setNString(3, clan.getPrezime());
                    //drugacije nece...
                    prepStat.setDate(4, new java.sql.Date(clan.getDatumRodjenja().getTime()));
                    prepStat.setFloat(5, clan.getVisina());
                    prepStat.setFloat(6, clan.getTelesnaTezina());
                    prepStat.setString(7, String.valueOf(clan.getPol()));
                    prepStat.setNString(8, clan.getPlanFizickeAktivnosti());
                    prepStat.setString(9, clan.getBrojTelefona());
                    prepStat.setDate(10, datumUclanjenja);
                    clan.setDatumUclanjenja(datumUclanjenja);

                    int i = prepStat.executeUpdate();

                    //i==1 dodata je torka
                    if (i == 1) {
                        
                        //vraca vrednost id-a
                        ResultSet primarniKljuc = prepStat.getGeneratedKeys();
                        //posto je vraceni id pre prvog reda moram da vratim sledeci...
                        primarniKljuc.next();
                        //1 je broj kolone
                        int idClana = primarniKljuc.getInt(1);
                        clan.setId(idClana);
                        this.listaClanova.add(clan);

                        //upisiPlanTreninga
                        ePlanTreninga planTreninga = new ePlanTreninga();
                        
                        if(planTreninga.upisiPlanTreninga(clan)==false){  
                            return false;
                        }

                        return true;
                    } else {
                        PomocnaKlasa.greska("Unos clana nije izvrsen!!!");
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

//    public boolean upisiSveVezbeZaClana(int idClana, eVezba vezba) {
//        try {
//            String sql = "INSERT INTO odradjuje (IDCLANA,IDVEZBE,BROJPONAVLJANJA,BROJSERIJA,VREMETRAJANJA,DODATNOOPTERECENJE) VALUES (?,?,?,?,?,?)";
//
//            Connection konekcija = Database.connectToDatabase();
//
//            if (konekcija != null) {
//                try {
//
//                    PreparedStatement prepStat = konekcija.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//
//                    prepStat.setInt(1, idClana);
//                    prepStat.setInt(2, vezba.getIdVezbe());
//                    prepStat.setInt(3, vezba.getBrojPonavljanja());
//                    prepStat.setInt(4, vezba.getBrojSerija());
//                    prepStat.setInt(5, vezba.getVremeTrajanja());
//                    prepStat.setInt(6, vezba.getDodatnoOpterecenje());
//
//                    int i = prepStat.executeUpdate();
//
//                    if (i == 1) {
//                        return true;
//                    } else {
//                        System.out.println("Unos nije izvrsen");
//                        return false;
//                    }
//                } catch (SQLException ex) {
//                    PomocnaKlasa.greska(ex);
//                }
//
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(eClanovi.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return false;
//    }
    @Override
    public String toString() {
        return "eClanovi{" + "listaClanova=" + listaClanova + '}';
    }

}
