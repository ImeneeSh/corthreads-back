package corthreads;

import java.util.Date;

public class Dons {

    private String idDons ;
    private String idUser ;
    private String type ;
    private Date dateDons ;
    private String statutDons ;
    private String detailsDons ;

    public Dons(String idDons ,String typeHLA,String type,Date dateDons,String statutDons,String detailsDons) {
        this.idDons = idDons;
        this.idUser = idUser;
        this.type = type;
        this.dateDons = dateDons;
        this.statutDons = statutDons;
        this.detailsDons = detailsDons;
    }

    public String getIdDons() {
        return idDons;
    }

    public void setIdDons(String idDons) {
        this.idDons = idDons;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateDons() {
        return dateDons;
    }

    public void setDateDons(Date dateDons) {
        this.dateDons = dateDons;
    }

    public String getStatutDons() {
        return statutDons;
    }

    public void setStatutDons(String statutDons) {
        this.statutDons = statutDons;
    }

    public String getDetailsDons() {
        return detailsDons;
    }

    public void setDetailsDons(String detailsDons) {
        this.detailsDons = detailsDons;
    }
}
