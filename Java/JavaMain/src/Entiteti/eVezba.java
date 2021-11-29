package Entiteti;

import Baza.Database;
import Baza.PomocnaKlasa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @created 11-May-2021 13:17:21
 */
public class eVezba {

    private int idVezbe;
    private String naziv;
    private int brojPonavljanja;
    private int brojSerija;
    private int vremeTrajanja;
    private int dodatnoOpterecenje;
    private ArrayList<eMisicnaGrupa> listaMisicnihGrupa = new ArrayList<>();

    public eVezba() {

    }

    public eVezba(String naziv) {
        this.naziv = naziv;
    }

    public eVezba(String naziv, int brojPonavljanja, int brojSerija, int vremeTrajanja, int dodatnoOpterecenje) {
        this.naziv = naziv;
        this.brojPonavljanja = brojPonavljanja;
        this.brojSerija = brojSerija;
        this.vremeTrajanja = vremeTrajanja;
        this.dodatnoOpterecenje = dodatnoOpterecenje;
    }

    public eVezba(String naziv, int brojPonavljanja, int brojSerija, int vremeTrajanja, int dodatnoOpterecenje, ArrayList<eMisicnaGrupa> listaMisicnihGrupa) {
        this.naziv = naziv;
        this.brojPonavljanja = brojPonavljanja;
        this.brojSerija = brojSerija;
        this.vremeTrajanja = vremeTrajanja;
        this.dodatnoOpterecenje = dodatnoOpterecenje;
        this.listaMisicnihGrupa = listaMisicnihGrupa;
    }

    public int getIdVezbe() {
        return idVezbe;
    }

    public void setIdVezbe(int idVezbe) {
        this.idVezbe = idVezbe;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getBrojPonavljanja() {
        return brojPonavljanja;
    }

    public void setBrojPonavljanja(int brojPonavljanja) {
        this.brojPonavljanja = brojPonavljanja;
    }

    public int getBrojSerija() {
        return brojSerija;
    }

    public void setBrojSerija(int brojSerija) {
        this.brojSerija = brojSerija;
    }

    public int getVremeTrajanja() {
        return vremeTrajanja;
    }

    public void setVremeTrajanja(int vremeTrajanja) {
        this.vremeTrajanja = vremeTrajanja;
    }

    public int getDodatnoOpterecenje() {
        return dodatnoOpterecenje;
    }

    public void setDodatnoOpterecenje(int dodatnoOpterecenje) {
        this.dodatnoOpterecenje = dodatnoOpterecenje;
    }

    public ArrayList<eMisicnaGrupa> getListaMisicnihGrupa() {
        return listaMisicnihGrupa;
    }

    public void setListaMisicnihGrupa(ArrayList<eMisicnaGrupa> listaMisicnihGrupa) {
        //bez ponavljanja lista
        this.listaMisicnihGrupa = (ArrayList<eMisicnaGrupa>) listaMisicnihGrupa.stream().distinct().collect(Collectors.toList());
    }

    public void ispisiPodatke() {
        System.out.println("ID vezbe: " + idVezbe);

        System.out.println("Naziv vezbe: " + naziv);
        if (brojPonavljanja == 0) {
            System.out.println("Broj ponavljanja:/");
        } else {
            System.out.println("Broj ponavljanja: " + brojPonavljanja);
        }
        System.out.println("Broj serija: " + brojSerija);
        if (vremeTrajanja == 0) {
            System.out.println("Vreme trajanja: /");
        } else {
            System.out.println("Vreme trajanja: " + vremeTrajanja);
        }

        if (dodatnoOpterecenje == 0) {
            System.out.println("Dodatno opterecenje: /");
        } else {
            System.out.println("Dodatno opterecenje: " + dodatnoOpterecenje);
        }

        for (eMisicnaGrupa misicnaGrupa : listaMisicnihGrupa) {
            misicnaGrupa.ispisiPodatke();
        }

        System.out.println("----------");
    }

    public void odrediBrojPonavljanja(String statusZdravlja, String nazivVezbe) {

        if (nazivVezbe.equals("Zgibovi") || nazivVezbe.equals("Propadanja")) {
            if ("Smanjena telesna težina".equals(statusZdravlja)) {
                this.brojPonavljanja = 5;
            } else if ("Normalna težina".equals(statusZdravlja)) {
                this.brojPonavljanja = 8;
            } else if ("Mala gojaznost".equals(statusZdravlja)) {
                this.brojPonavljanja = 10;
            } else if ("Gojaznost".equals(statusZdravlja)) {
                this.brojPonavljanja = 10;
            } else if ("Jaka gojaznost".equals(statusZdravlja)) {
                this.brojPonavljanja = 12;
            }
        } else if (nazivVezbe.equals("Plank") || nazivVezbe.equals("Mountain climbers")) {
            this.brojPonavljanja = 0;
        } else {
            if ("Smanjena telesna težina".equals(statusZdravlja)) {
                this.brojPonavljanja = 8;
            }
            if ("Normalna težina".equals(statusZdravlja)) {
                this.brojPonavljanja = 10;
            }
            if ("Mala gojaznost".equals(statusZdravlja)) {
                this.brojPonavljanja = 10;
            }
            if ("Gojaznost".equals(statusZdravlja)) {
                this.brojPonavljanja = 12;
            }
            if ("Jaka gojaznost".equals(statusZdravlja)) {
                this.brojPonavljanja = 12;
            }

        }

    }

    public void odrediBrojSerija(String statusZdravlja) {
        if ("Smanjena telesna težina".equals(statusZdravlja)) {
            this.brojSerija = 3;
        } else if ("Normalna težina".equals(statusZdravlja)) {
            this.brojSerija = 3;
        } else if ("Mala gojaznost".equals(statusZdravlja)) {
            this.brojSerija = 4;
        } else if ("Gojaznost".equals(statusZdravlja)) {
            this.brojSerija = 4;
        } else if ("Jaka gojaznost".equals(statusZdravlja)) {
            this.brojSerija = 5;
        }
    }

    public void odrediVreme(Date datumRodjenja, char pol, String statusZdravlja, String nazivVezbe) {
        int vreme = 0;
        int godinaRodjenja = LocalDate.now().getYear() - (datumRodjenja.getYear() + 1900);

        if (nazivVezbe.equals("Plank") || nazivVezbe.equals("Mountain climbers")) {
            if ("Smanjena telesna težina".equals(statusZdravlja)) {
                vreme = 100;
                if (godinaRodjenja > 55) {
                    vreme = vreme - 40;
                }
            } else if ("Normalna težina".equals(statusZdravlja)) {
                vreme = 80;
                if (godinaRodjenja > 55) {
                    vreme = vreme - 20;
                }
            } else if ("Mala gojaznost".equals(statusZdravlja)) {
                vreme = 80;
                if (godinaRodjenja > 55) {
                    vreme = vreme - 30;
                }
            } else if ("Gojaznost".equals(statusZdravlja)) {
                vreme = 60;
                if (godinaRodjenja > 55) {
                    vreme = vreme - 15;
                }
            } else if ("Jaka gojaznost".equals(statusZdravlja)) {
                vreme = 60;
                if (godinaRodjenja > 55) {
                    vreme = vreme - 10;
                }
            }
        }

        this.vremeTrajanja = vreme;
    }

    public void odrediKilazuZaDizanje(Date datumRodjenja, char pol, String statusZdravlja, float telesnaTezina, String nazivVezbe) {
        int kilaza = 0;

//Bez +1900 mi ne bi registrovalo validan datum...
        int godinaRodjenja = LocalDate.now().getYear() - (datumRodjenja.getYear() + 1900);

        if (pol == 'M') {
            if (nazivVezbe.equals("Mrtva dizanja") || nazivVezbe.equals("Cucanj")
                    || nazivVezbe.equals("Rumunska mrtva dizanja")) {
                if ("Smanjena telesna težina".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.9);
                } else if ("Normalna težina".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 1.15);
                } else if ("Mala gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 1);
                } else if ("Gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.7);
                } else if ("Jaka gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.6);
                }

                if (godinaRodjenja >= 18 && godinaRodjenja <= 35) {
                    kilaza = kilaza;
                } else if (godinaRodjenja > 35 && godinaRodjenja < 45) {
                    kilaza = kilaza - (int) (kilaza * 0.08);
                } else if (godinaRodjenja >= 45 && godinaRodjenja <= 55) {
                    kilaza = kilaza - (int) (kilaza * 0.2);
                } else if (godinaRodjenja >= 56) {
                    kilaza = 20;
                }

            }

            if (nazivVezbe.equals("Bench press")) {
                if ("Smanjena telesna težina".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.9);
                } else if ("Normalna težina".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 1.1);
                } else if ("Mala gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 1.1);
                } else if ("Gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.7);
                } else if ("Jaka gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.5);
                }
                if (godinaRodjenja >= 18 && godinaRodjenja <= 35) {
                    kilaza = kilaza;
                } else if (godinaRodjenja > 35 && godinaRodjenja < 45) {
                    kilaza = kilaza - (int) (kilaza * 0.1);
                } else if (godinaRodjenja >= 45 && godinaRodjenja <= 55) {
                    kilaza = kilaza - (int) (kilaza * 0.2);
                } else if (godinaRodjenja >= 56) {
                    kilaza = 20;
                }

            } else if (nazivVezbe.equals("Zgibovi") || nazivVezbe.equals("Propadanja")) {
                if ("Smanjena telesna težina".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.15);
                } else if ("Normalna težina".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.25);
                } else if ("Mala gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.15);
                }
                if (godinaRodjenja >= 18 && godinaRodjenja <= 35) {
                    kilaza = kilaza;
                } else if (godinaRodjenja > 35 && godinaRodjenja < 45) {
                    kilaza = kilaza - (int) (kilaza * 0.12);
                } else if (godinaRodjenja >= 45) {
                    kilaza = 0;
                }

            } else if (nazivVezbe.equals("Skullcrushers") || nazivVezbe.equals("Bugarski cucanj")
                    || nazivVezbe.equals("Iskoraci")) {
                if ("Smanjena telesna težina".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.25);
                } else if ("Normalna težina".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.35);
                } else if ("Mala gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.35);
                } else if ("Gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.36);
                } else if ("Jaka gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.4);
                }
                if (godinaRodjenja >= 18 && godinaRodjenja <= 35) {
                    kilaza = kilaza;
                } else if (godinaRodjenja > 35 && godinaRodjenja < 45) {
                    kilaza = kilaza - (int) (kilaza * 0.1);
                } else if (godinaRodjenja >= 45 && godinaRodjenja <= 55) {
                    kilaza = kilaza - (int) (kilaza * 0.12);
                } else if (godinaRodjenja >= 56) {
                    kilaza = 10;
                }
            }
        } else {
            if (nazivVezbe.equals("Mrtva dizanja") || nazivVezbe.equals("Cucanj")
                    || nazivVezbe.equals("Rumunska mrtva dizanja")) {
                if ("Smanjena telesna težina".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.85);
                } else if ("Normalna težina".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 1);
                } else if ("Mala gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.8);
                } else if ("Gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.6);
                } else if ("Jaka gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.5);
                }
                if (godinaRodjenja >= 18 && godinaRodjenja <= 35) {
                    kilaza = kilaza;
                } else if (godinaRodjenja > 35 && godinaRodjenja < 45) {
                    kilaza = kilaza - (int) (kilaza * 0.1);
                } else if (godinaRodjenja >= 45 && godinaRodjenja <= 55) {
                    kilaza = kilaza - (int) (kilaza * 0.18);
                } else if (godinaRodjenja >= 56) {
                    kilaza = 20;
                }

            }

            if (nazivVezbe.equals("Bench press")) {
                if ("Smanjena telesna težina".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.7);
                } else if ("Normalna težina".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.8);
                } else if ("Mala gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.7);
                } else if ("Gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.6);
                } else if ("Jaka gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.5);
                }
                if (godinaRodjenja >= 18 && godinaRodjenja <= 35) {
                    kilaza = kilaza;
                } else if (godinaRodjenja > 35 && godinaRodjenja < 45) {
                    kilaza = kilaza - (int) (kilaza * 0.1);
                } else if (godinaRodjenja >= 45 && godinaRodjenja <= 55) {
                    kilaza = kilaza - (int) (kilaza * 0.2);
                } else if (godinaRodjenja >= 56) {
                    kilaza = 20;
                }

            } else if (nazivVezbe.equals("Zgibovi") || nazivVezbe.equals("Propadanja")) {
                if ("Smanjena telesna težina".equals(statusZdravlja)) {
                    kilaza = 0;
                } else if ("Normalna težina".equals(statusZdravlja)) {
                    kilaza = 0;
                } else if ("Mala gojaznost".equals(statusZdravlja)) {
                    kilaza = 0;
                }

            } else if (nazivVezbe.equals("Skullcrushers") || nazivVezbe.equals("Bugarski cucanj")
                    || nazivVezbe.equals("Iskoraci")) {
                if ("Smanjena telesna težina".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.12);
                } else if ("Normalna težina".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.15);
                } else if ("Mala gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.15);
                } else if ("Gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.12);
                } else if ("Jaka gojaznost".equals(statusZdravlja)) {
                    kilaza = (int) (telesnaTezina * 0.12);
                }
                if (godinaRodjenja >= 18 && godinaRodjenja <= 35) {
                    kilaza = kilaza;
                } else if (godinaRodjenja > 35 && godinaRodjenja < 45) {
                    kilaza = kilaza - (int) (kilaza * 0.05);
                } else if (godinaRodjenja >= 45 && godinaRodjenja <= 55) {
                    kilaza = kilaza - (int) (kilaza * 0.1);
                } else if (godinaRodjenja >= 56) {
                    kilaza = 6;
                }

            }
        }

        this.dodatnoOpterecenje = kilaza;
    }

    public static ArrayList<eVezba> vratiVezbe(Date datumRodjenja, char pol, String statusZdravlja, Float telesnaTezina) {

        ArrayList<eVezba> listaVezbi = new ArrayList<>();
        try {
            ResultSet rsVezbe = Database.getResultSet("select * from evezba");

            while (rsVezbe.next()) {

                eVezba vezba = new eVezba();
                vezba.setIdVezbe(rsVezbe.getInt("IDVEZBE"));
                vezba.setNaziv(rsVezbe.getNString("NAZIVVEZBE"));
                //odredi broj ponavljanja,serija,vreme trajanja,kilazu za dizanje
                vezba.odrediBrojPonavljanja(statusZdravlja, vezba.getNaziv());
                vezba.odrediBrojSerija(statusZdravlja);
                vezba.odrediVreme(datumRodjenja, pol, statusZdravlja, vezba.getNaziv());
                vezba.odrediKilazuZaDizanje(datumRodjenja, pol, statusZdravlja, telesnaTezina, vezba.getNaziv());

                //dodaj misicne grupe u vezbu
                vezba.setListaMisicnihGrupa(eMisicnaGrupa.dodajMisicneGrupeVezbi(vezba.getIdVezbe()));
                listaVezbi.add(vezba);
            }
            return listaVezbi;
        } catch (SQLException ex) {
            PomocnaKlasa.greska(ex);
            return null;
        }

    }

    public boolean upisiSveVezbeZaClana(int idClana, eVezba vezba) {
        try {
            String sql = "INSERT INTO odradjuje (IDCLANA,IDVEZBE,BROJPONAVLJANJA,BROJSERIJA,VREMETRAJANJA,DODATNOOPTERECENJE) VALUES (?,?,?,?,?,?)";

            Connection konekcija = Database.connectToDatabase();

            if (konekcija != null) {
                try {

                    PreparedStatement prepStat = konekcija.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                    prepStat.setInt(1, idClana);
                    prepStat.setInt(2, vezba.getIdVezbe());
                    prepStat.setInt(3, vezba.getBrojPonavljanja());
                    prepStat.setInt(4, vezba.getBrojSerija());
                    prepStat.setInt(5, vezba.getVremeTrajanja());
                    prepStat.setInt(6, vezba.getDodatnoOpterecenje());

                    int i = prepStat.executeUpdate();

                    if (i == 1) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (SQLException ex) {
                    PomocnaKlasa.greska(ex);
                    return false;
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(eClanovi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
