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
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @version 1.0
 * @created 11-May-2021 13:17:21
 */
public class eTrening {

    private int nedelja;
    private int brDanaUNed;
    private ArrayList<eVezba> listaVezbi;

    public eTrening() {

    }

    public eTrening(ArrayList<eVezba> listaVezbi) {
        this.listaVezbi = listaVezbi;
    }

    public eTrening(int nedelja, int brDanaUNed, ArrayList<eVezba> listaVezbi) {
        this.nedelja = nedelja;
        this.brDanaUNed = brDanaUNed;
        this.listaVezbi = listaVezbi;
    }

    public int getNedelja() {
        return nedelja;
    }

    public void setNedelja(int nedelja) {
        this.nedelja = nedelja;
    }

    public int getBrDanaUNed() {
        return brDanaUNed;
    }

    public void setBrDanaUNed(int brDanaUNed) {
        this.brDanaUNed = brDanaUNed;
    }

    public ArrayList<eVezba> getListaVezbi() {
        return listaVezbi;
    }

    public void setListaVezbi(ArrayList<eVezba> listaVezbi) {
        this.listaVezbi = listaVezbi;
    }

    public void finalize() throws Throwable {

    }

    public void ispisPodatke() {

        System.out.println("Nedelja: " + nedelja);
        System.out.println("Dan: " + brDanaUNed);
        for (eVezba vezba : listaVezbi) {
            vezba.ispisiPodatke();
        }
        System.out.println("------");
    }

    public static ArrayList<eTrening> dodajTreninge(ArrayList<eVezba> listaVezbi, String planFizickeAktivnosti) {
        ArrayList<eTrening> listaTreninga = new ArrayList<>();
        int nedelja = 1;
        int dan = 0;

        //moram dodati jos 3 nedelje i dana!!!! da bude full generisan trening
        if (planFizickeAktivnosti.equals("Lagana: 1-3 puta nedeljno")) {

            for (int x = 0; x < 4; x++) {
                for (int i = 0; i < 3; i++) {
                    ArrayList<eVezba> listaZaDodavanje = new ArrayList<>();
                    /*Promesa listu*/
                    Collections.shuffle(listaVezbi, new Random());

                    int brojacNoge = 0;
                    int brojacRuke = 0;
                    int brojacStomak = 0;

                    for (eVezba vezba : listaVezbi) {
                        if ((vezba.getNaziv().equals("Mrtva dizanja") || vezba.getNaziv().equals("Cucanj") || vezba.getNaziv().equals("Bugarski cucanj")
                                || vezba.getNaziv().equals("Rumunska mrtva dizanja") || vezba.getNaziv().equals("Iskoraci"))) {
                            if (brojacNoge < 3) {
                                listaZaDodavanje.add(vezba);
                                brojacNoge++;
                            }

                        }
                        if ((vezba.getNaziv().equals("Zgibovi") || vezba.getNaziv().equals("Propadanja") || vezba.getNaziv().equals("Bench press")
                                || vezba.getNaziv().equals("Sklekovi") || vezba.getNaziv().equals("Skullcrushers")) && brojacRuke < 3) {
                            listaZaDodavanje.add(vezba);
                            brojacRuke++;
                        }

                        if ((vezba.getNaziv().equals("Plank") || vezba.getNaziv().equals("Mountain climbers")) && brojacStomak < 1) {
                            listaZaDodavanje.add(vezba);
                            brojacStomak++;
                        }

                    }

                    dan++;

//              Dodavanje vezbi
                    eTrening trening = new eTrening(nedelja, dan, listaZaDodavanje);
                    listaTreninga.add(trening);

                    if (dan == 3) {
                        nedelja++;
                        dan = 0;
                    }
                }

            }
            return listaTreninga;
        } else if (planFizickeAktivnosti.equals("Umerena: 4-5 puta nedeljno")) {

            for (int i = 0; i < 5; i++) {
                for (int x = 0; x < 4; x++) {
                    ArrayList<eVezba> listaZaDodavanje = new ArrayList<>();
                    /*Promesa listu*/
                    Collections.shuffle(listaVezbi);

                    int brojacNoge = 0;
                    int brojacRuke = 0;
                    int brojacStomak = 0;

                    for (eVezba vezba : listaVezbi) {
                        if ((vezba.getNaziv().equals("Mrtva dizanja") || vezba.getNaziv().equals("Cucanj") || vezba.getNaziv().equals("Bugarski cucanj")
                                || vezba.getNaziv().equals("Rumunska mrtva dizanja") || vezba.getNaziv().equals("Iskoraci"))) {
                            if (brojacNoge < 2) {
                                listaZaDodavanje.add(vezba);
                                brojacNoge++;
                            }

                        }
                        if ((vezba.getNaziv().equals("Zgibovi") || vezba.getNaziv().equals("Propadanja") || vezba.getNaziv().equals("Bench press")
                                || vezba.getNaziv().equals("Sklekovi") || vezba.getNaziv().equals("Skullcrushers")) && brojacRuke < 2) {
                            listaZaDodavanje.add(vezba);
                            brojacRuke++;
                        }

                        if ((vezba.getNaziv().equals("Plank") || vezba.getNaziv().equals("Mountain climbers")) && brojacStomak < 1) {
                            listaZaDodavanje.add(vezba);
                            brojacStomak++;
                        }

                    }

                    dan++;
//              Dodavanje vezbi                
                    eTrening trening = new eTrening(nedelja, dan, listaZaDodavanje);
                    listaTreninga.add(trening);

                    if (dan == 5) {
                        nedelja++;
                        dan = 0;
                    }

                }
            }
            return listaTreninga;

        } else if (planFizickeAktivnosti.equals("Aktivna: Svakodnevno treniranje ")) {
            for (int x = 0; x < 4; x++) {
                for (int i = 0; i < 7; i++) {
                    ArrayList<eVezba> listaZaDodavanje = new ArrayList<>();
                    /*Promesa listu*/
                    Collections.shuffle(listaVezbi);

                    int brojacNoge = 0;
                    int brojacRuke = 0;
                    int brojacStomak = 0;

                    for (eVezba vezba : listaVezbi) {
                        if ((vezba.getNaziv().equals("Mrtva dizanja") || vezba.getNaziv().equals("Cucanj") || vezba.getNaziv().equals("Bugarski cucanj")
                                || vezba.getNaziv().equals("Rumunska mrtva dizanja") || vezba.getNaziv().equals("Iskoraci"))) {
                            if (brojacNoge < 2) {
                                listaZaDodavanje.add(vezba);
                                brojacNoge++;
                            }

                        }
                        if ((vezba.getNaziv().equals("Zgibovi") || vezba.getNaziv().equals("Propadanja") || vezba.getNaziv().equals("Bench press")
                                || vezba.getNaziv().equals("Sklekovi") || vezba.getNaziv().equals("Skullcrushers")) && brojacRuke < 2) {
                            listaZaDodavanje.add(vezba);
                            brojacRuke++;
                        }

                        if ((vezba.getNaziv().equals("Plank") || vezba.getNaziv().equals("Mountain climbers")) && brojacStomak < 1) {
                            listaZaDodavanje.add(vezba);
                            brojacStomak++;
                        }

                    }

                    dan++;
//              Dodavanje vezbi                
                    eTrening trening = new eTrening(nedelja, dan, listaZaDodavanje);

                    listaTreninga.add(trening);

                    if (dan == 7) {
                        nedelja++;
                        dan = 0;
                    }

                }
            }
            return listaTreninga;

        }
        return null;
    }

    public int upisiTrening(eClan clan, eTrening trening, int idPlana) {
        try {

            Connection konekcija = Database.connectToDatabase();

            if (konekcija != null) {
                try {

                    String sql = "INSERT INTO etrening (NEDELJA,BRDANAUNED,IDPLANA) VALUES (?,?,?)";
                    PreparedStatement prepStat = konekcija.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    prepStat.setInt(1, trening.getNedelja());
                    prepStat.setInt(2, trening.getBrDanaUNed());
                    prepStat.setInt(3, idPlana);

                    //Vraca 0 ili 1
                    //1 uspeh ,0 ne
                    int i = prepStat.executeUpdate();

                    if (i == 1) {
                        ResultSet primarniKljuc = prepStat.getGeneratedKeys();
                        primarniKljuc.next();

                        //Upis u odradjuje tabelu... to je moja eVezba tabela za bazu...                                            
                        for (eVezba vezba : trening.getListaVezbi()) {
                            if (vezba.upisiSveVezbeZaClana(clan.getId(), vezba) == false) {
                                PomocnaKlasa.greska("Greska kod unosa u tabelu odradjuje");
                                return -1;
                            }

                        }

                        return primarniKljuc.getInt(1);
                    } else {
                        return -1;
                    }

                } catch (SQLException ex) {
                    PomocnaKlasa.greska(ex);

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(eClanovi.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public boolean upisiCini(int idVezbe, eTrening trening, int idTreninga) {
        try {
            String sql = "INSERT INTO cini (IDVEZBE,IDTRENINGA,NEDELJA,BRDANAUNED) VALUES (?,?,?,?)";
            Connection konekcija = Database.connectToDatabase();

            if (konekcija != null) {
                try {

                    PreparedStatement prepStat = konekcija.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    prepStat.setInt(1, idVezbe);
                    prepStat.setInt(2, idTreninga);
                    prepStat.setInt(3, trening.getNedelja());
                    prepStat.setInt(4, trening.getBrDanaUNed());
                    int i = prepStat.executeUpdate();

                    if (i == 1) {
                        return true;
                    } else {
                        return false;
                    }

                } catch (SQLException ex) {
                    PomocnaKlasa.greska(ex);

                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(eClanovi.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
