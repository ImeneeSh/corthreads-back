package corthreads;

import java.util.Date;

public class Temoignage {

    private String idTemoignage ;
    private String idUser ;
    private String contenu ;
    private Date datePub ;

    public Temoignage(String idTemoignage, String idUser, String contenu, Date datePub) {
        this.idTemoignage = idTemoignage;
        this.idUser = idUser;
        this.contenu = contenu;
        this.datePub = datePub;
    }

    public String getIdTemoignage() {
        return idTemoignage;
    }

    public void setIdTemoignage(String idTemoignage) {
        this.idTemoignage = idTemoignage;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDatePub() {
        return datePub;
    }

    public void setDatePub(Date datePub) {
        this.datePub = datePub;
    }
}
