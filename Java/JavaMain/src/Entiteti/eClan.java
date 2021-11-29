package Entiteti;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class eClan {

    private int id;
    private String jmbg;
    private String ime;
    private String prezime;
    private Date datumRodjenja;
    private float visina;
    private float telesnaTezina;
    private char pol;
    private String planFizickeAktivnosti;
    private float BMI;
    private String statusZdravlja;
    private String brojTelefona;
    private Date datumUclanjenja;
    private ArrayList<ePlanTreninga> listaPlanaTreninga = new ArrayList<>();

    public eClan() {

    }

    public eClan(String jmbg,String ime, String prezime, Date datumRodjenja, float visina, float telesnaTezina, char pol, String planFizickeAktivnosti, String brojTelefona) {
        this.jmbg=jmbg;
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.visina = visina;
        this.telesnaTezina = telesnaTezina;
        this.pol = pol;
        this.planFizickeAktivnosti = planFizickeAktivnosti;
        this.brojTelefona = brojTelefona;
        
        izracunajBMI(visina, telesnaTezina);
        odrediStatusZdravlja(BMI, pol, datumRodjenja);
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public float getVisina() {
        return visina;
    }

    public void setVisina(float visina) {
        this.visina = visina;
    }

    public float getTelesnaTezina() {
        return telesnaTezina;
    }

    public void setTelesnaTezina(float telesnaTezina) {
        this.telesnaTezina = telesnaTezina;
    }

    public char getPol() {
        return pol;
    }

    public void setPol(char pol) {
        this.pol = pol;
    }

    public String getPlanFizickeAktivnosti() {
        return planFizickeAktivnosti;
    }

    public void setPlanFizickeAktivnosti(String planFizickeAktivnosti) {
        this.planFizickeAktivnosti = planFizickeAktivnosti;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBMI() {
        return BMI;
    }


    public String getStatusZdravlja() {
        return statusZdravlja;
    }

    public ArrayList<ePlanTreninga> getListaPlanaTreninga() {
        return listaPlanaTreninga;
    }

    public void setListaPlanaTreninga(ArrayList<ePlanTreninga> listaPlanaTreninga) {
        this.listaPlanaTreninga = listaPlanaTreninga;
    }

    public Date getDatumUclanjenja() {
        return datumUclanjenja;
    }

    public void setDatumUclanjenja(Date datumUclanjenja) {
        this.datumUclanjenja = datumUclanjenja;
    }

    
    public void izracunajBMI(float visina, float telesnaTezina) {
        float BMI;

        //da nisam pomnozio sa 10000 bilo bi 0.22 umesto 22.0....
        BMI = ((telesnaTezina / (visina * visina)) * 10000);
        BMI = roundFloat(BMI, 2);
        this.BMI=BMI;
    }

    //Big decimal zaokruzivanje na kolko decimala kazem
    private float roundFloat(float f, int zaKoliko) {

        BigDecimal bigDecimal = new BigDecimal(Float.toString(f));
        bigDecimal = bigDecimal.setScale(zaKoliko, RoundingMode.HALF_UP);
        return bigDecimal.floatValue();
    }

    public void odrediStatusZdravlja(float BMI, char pol, Date datum) {
        String status;
        StringBuilder statusZdravlja = new StringBuilder();
        //danasnji datum
        Date danasnjiDatum = new Date();

        int brojGodina = danasnjiDatum.getYear() - datum.getYear();

        if (pol == 'Z') {

            if (brojGodina >= 18 && brojGodina <= 24) {

                if (BMI < 19) {
                    status = "Smanjena telesna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 19 && BMI <= 24) {
                    status = "Normalna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 24 && BMI <= 29) {
                    status = "Mala gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI >= 29 && BMI <= 39) {
                    status = "Gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI > 39) {
                    status = "Jaka gojaznost";
                    statusZdravlja.append(status);
                }
            } else if (brojGodina >= 25 && brojGodina <= 34) {

                if (BMI < 20) {
                    status = "Smanjena telesna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 19 + 1 && BMI <= 24 + 1) {
                    status = "Normalna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 24 + 1 && BMI <= 29 + 1) {
                    status = "Mala gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI >= 29 + 1 && BMI <= 39 + 1) {
                    status = "Gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI > 39 + 1) {
                    status = "Jaka gojaznost";
                    statusZdravlja.append(status);
                }
            } else if (brojGodina >= 35 && brojGodina <= 44) {

                if (BMI < 21) {
                    status = "Smanjena telesna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 19 + 2 && BMI <= 24 + 2) {
                    status = "Normalna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 24 + 2 && BMI <= 29 + 2) {
                    status = "Mala gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI >= 29 + 2 && BMI <= 39 + 2) {
                    status = "Gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI > 39 + 2) {
                    status = "Jaka gojaznost";
                    statusZdravlja.append(status);
                }
            } else if (brojGodina >= 45 && brojGodina < 54) {

                if (BMI < 22) {
                    status = "Smanjena telesna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 19 + 3 && BMI <= 24 + 3) {
                    status = "Normalna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 24 + 3 && BMI <= 29 + 3) {
                    status = "Mala gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI >= 29 + 3 && BMI <= 39 + 3) {
                    status = "Gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI > 39 + 3) {
                    status = "Jaka gojaznost";
                    statusZdravlja.append(status);
                }
            } else if (brojGodina >= 55 && brojGodina < 64) {

                if (BMI < 23) {
                    status = "Smanjena telesna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 19 + 4 && BMI <= 24 + 4) {
                    status = "Normalna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 24 + 4 && BMI <= 29 + 4) {
                    status = "Mala gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI >= 29 + 4 && BMI <= 39 + 4) {
                    status = "Gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI > 39 + 4) {
                    status = "Jaka gojaznost";
                    statusZdravlja.append(status);
                }
            } else if (brojGodina >= 65) {

                if (BMI < 24) {
                    status = "Smanjena telesna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 19 + 5 && BMI <= 24 + 5) {
                    status = "Normalna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 24 + 5 && BMI <= 29 + 5) {
                    status = "Mala gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI >= 29 + 5 && BMI <= 39 + 5) {
                    status = "Gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI > 39 + 5) {
                    status = "Jaka gojaznost";
                    statusZdravlja.append(status);
                }
            }
            this.statusZdravlja=statusZdravlja.toString();
           

        } //BMI muski...
        else {

            if (brojGodina >= 18 && brojGodina <= 24) {

                if (BMI < 20) {
                    status = "Smanjena telesna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 20 && BMI <= 24 + 1) {
                    status = "Normalna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 24 + 1 && BMI <= 29 + 1) {
                    status = "Mala gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI >= 29 + 1 && BMI <= 39 + 1) {
                    status = "Gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI > 39 + 1) {
                    status = "Jaka gojaznost";
                    statusZdravlja.append(status);
                }
            } else if (brojGodina >= 25 && brojGodina <= 34) {

                if (BMI < 20 + 1) {
                    status = "Smanjena telesna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 19 + 2 && BMI <= 24 + 2) {
                    status = "Normalna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 24 + 2 && BMI <= 29 + 2) {
                    status = "Mala gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI >= 29 + 2 && BMI <= 39 + 2) {
                    status = "Gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI > 39 + 2) {
                    status = "Jaka gojaznost";
                    statusZdravlja.append(status);
                }
            } else if (brojGodina >= 35 && brojGodina <= 44) {

                if (BMI < 21 + 1) {
                    status = "Smanjena telesna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 19 + 3 && BMI <= 24 + 3) {
                    status = "Normalna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 24 + 3 && BMI <= 29 + 3) {
                    status = "Mala gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI >= 29 + 3 && BMI <= 39 + 3) {
                    status = "Gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI > 39 + 3) {
                    status = "Jaka gojaznost";
                    statusZdravlja.append(status);
                }
            } else if (brojGodina >= 45 && brojGodina < 54) {

                if (BMI < 22 + 1) {
                    status = "Smanjena telesna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 19 + 4 && BMI <= 24 + 4) {
                    status = "Normalna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 24 + 4 && BMI <= 29 + 4) {
                    status = "Mala gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI >= 29 + 4 && BMI <= 39 + 4) {
                    status = "Gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI > 39 + 4) {
                    status = "Jaka gojaznost";
                    statusZdravlja.append(status);
                }
            } else if (brojGodina >= 55 && brojGodina < 64) {

                if (BMI < 24) {
                    status = "Smanjena telesna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 19 + 5 && BMI <= 24 + 5) {
                    status = "Normalna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 24 + 5 && BMI <= 29 + 5) {
                    status = "Mala gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI >= 29 + 5 && BMI <= 39 + 5) {
                    status = "Gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI > 39 + 5) {
                    status = "Jaka gojaznost";
                    statusZdravlja.append(status);
                }
            } else if (brojGodina >= 65) {

                if (BMI < 24 + 1) {
                    status = "Smanjena telesna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 19 + 6 && BMI <= 24 + 6) {
                    status = "Normalna težina";
                    statusZdravlja.append(status);
                } else if (BMI >= 24 + 6 && BMI <= 29 + 6) {
                    status = "Mala gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI >= 29 + 6 && BMI <= 39 + 6) {
                    status = "Gojaznost";
                    statusZdravlja.append(status);
                } else if (BMI > 39 + 6) {
                    status = "Jaka gojaznost";
                    statusZdravlja.append(status);
                }
            }
            this.statusZdravlja=statusZdravlja.toString();
        }
    }

    public void dodajClanuPlanTreninga(ePlanTreninga plan) {
        this.listaPlanaTreninga.add(plan);
    }

    public void ispisiPodatke() {

        System.out.println("JMBG:"+this.jmbg);
        System.out.println("Ime:" + ime);
        System.out.println("Prezime:" + this.prezime);
        System.out.println(this.datumRodjenja);
        System.out.println(this.pol);
        System.out.println(this.telesnaTezina);
        System.out.println(this.visina);
        System.out.println(this.planFizickeAktivnosti);
        System.out.println(this.BMI);
        System.out.println(this.statusZdravlja);
        System.out.println(this.brojTelefona);

        for (ePlanTreninga planTreninga : listaPlanaTreninga) {
            planTreninga.ispisiPodatke();
        }
    }

}
