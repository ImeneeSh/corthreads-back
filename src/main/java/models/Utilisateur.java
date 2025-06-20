package models;

public class Utilisateur {

    private String idUser ;
    private String nomCmpl ;
    private String wilaya ;
    private String role ;
    private String mdp ;
    private String groupeSang ;
    private String typeHLA ;

    public Utilisateur(String idUser,String nomCmpl , String wilaya,String role,String mdp,String groupeSang,String typeHLA) {
        this.idUser = idUser;
        this.nomCmpl = nomCmpl;
        this.wilaya = wilaya;
        this.role = role;
        this.mdp = mdp;
        this.groupeSang = groupeSang;
        this.typeHLA = typeHLA;
    }

    public Utilisateur() {}

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNomCmpl() {
        return nomCmpl;
    }

    public void setNomCmpl(String nomCmpl) {
        this.nomCmpl = nomCmpl;
    }

    public String getWilaya() {
        return wilaya;
    }

    public void setWilaya(String wilaya) {
        this.wilaya = wilaya;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
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
