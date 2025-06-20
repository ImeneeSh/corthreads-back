package models;

public class Demande {

    private String idDemande ;
    private String idUser ;
    private String typeDemande ;
    private String urgence ;
    private String wilaya ;
    private String groupeSang ;
    private String typeHLA ;

    public Demande(String idDemande,String idUser,String typeDemande,String urgence,String wilaya,String groupeSang,String typeHLA){
        this.idDemande=idDemande;
        this.idUser=idUser;
        this.typeDemande=typeDemande;
        this.urgence=urgence;
        this.wilaya=wilaya;
        this.groupeSang=groupeSang;
        this.typeHLA=typeHLA;
    }

    public Demande(){}

    public String getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(String idDemande) {
        this.idDemande = idDemande;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTypeDemande() {
        return typeDemande;
    }

    public void setTypeDemande(String typeDemande) {
        this.typeDemande = typeDemande;
    }

    public String getUrgence() {
        return urgence;
    }

    public void setUrgence(String urgence) {
        this.urgence = urgence;
    }

    public String getWilaya() {
        return wilaya;
    }

    public void setWilaya(String wilaya) {
        this.wilaya = wilaya;
    }

    public String getGroupeSang() {
        return groupeSang;
    }

    public void setGroupeSang(String groupeSang) {
        this.groupeSang = groupeSang;
    }

    public String getTypeHLA() {
        return typeHLA;
    }

    public void setTypeHLA(String typeHLA) {
        this.typeHLA = typeHLA;
    }

}
