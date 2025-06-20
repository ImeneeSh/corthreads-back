package models;

import java.util.Date;

public class Validation {

    private String idVal ;
    private String idUser ;
    private String statutVal ;
    private Date DateVal ;
    private String Commentaire ;

    public Validation(String idVal, String idUser, String statutVal, Date DateVal, String Commentaire) {
        this.idVal = idVal;
        this.idUser = idUser;
        this.statutVal = statutVal;
        this.DateVal = DateVal;
        this.Commentaire = Commentaire;
    }

    public String getIdVal() {
        return idVal;
    }

    public void setIdVal(String idVal) {
        this.idVal = idVal;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getStatutVal() {
        return statutVal;
    }

    public void setStatutVal(String statutVal) {
        this.statutVal = statutVal;
    }

    public Date getDateVal() {
        return DateVal;
    }

    public void setDateVal(Date DateVal) {
        this.DateVal = DateVal;
    }

    public String getCommentaire() {
        return Commentaire;
    }

    public void setCommentaire(String Commentaire) {
        this.Commentaire = Commentaire;
    }
}
