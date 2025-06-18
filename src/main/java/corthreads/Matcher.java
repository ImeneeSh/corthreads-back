package corthreads;

public class Matcher {
    private String idDons ;
    private String idDemande ;

    public Matcher(String idDons, String idDemande) {
        this.idDons = idDons;
        this.idDemande = idDemande;
    }

    public String getIdDons() {
        return idDons;
    }

    public void setIdDons(String idDons) {
        this.idDons = idDons;
    }

    public String getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(String idDemande) {
        this.idDemande = idDemande;
    }
}
