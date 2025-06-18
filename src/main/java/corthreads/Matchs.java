package corthreads;

public class Matchs {
    private String idMatch ;
    private String idVal ;
    private String idDons ;
    private String idDemande ;
    private String statusMatch ;

    public Matchs(String idMatch, String idVal, String idDons, String idDemande, String statusMatch) {
        this.idMatch = idMatch;
        this.idVal = idVal;
        this.idDons = idDons;
        this.idDemande = idDemande;
        this.statusMatch = statusMatch;
    }

    public String getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(String idMatch) {
        this.idMatch = idMatch;
    }

    public String getIdVal() {
        return idVal;
    }

    public void setIdVal(String idVal) {
        this.idVal = idVal;
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

    public String getStatusMatch() {
        return statusMatch;
    }

    public void setStatusMatch(String statusMatch) {
        this.statusMatch = statusMatch;
    }
}
