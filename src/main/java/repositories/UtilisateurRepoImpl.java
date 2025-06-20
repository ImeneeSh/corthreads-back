package repositories;

import config.ConnexionBD;
import models.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurRepoImpl implements UtilisateurRepo {

    @Override
    public Utilisateur findUtilisateur(String idUser) {
        Utilisateur user = null;

        try (Connection con = ConnexionBD.getConnexion();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM Utilisateur WHERE idUser = ?")) {

            stmt.setString(1, idUser);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new Utilisateur();
                    user.setIdUser(rs.getString("idUser"));
                    user.setNomCmpl(rs.getString("nomCmpl"));
                    user.setWilaya(rs.getString("wilaya"));
                    user.setRole(rs.getString("role"));
                    user.setMdp(rs.getString("mdp"));
                    user.setGroupeSang(rs.getString("groupeSang"));
                    user.setTypeHLA(rs.getString("typeHLA"));
                }

            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'utilisateur : " + e.getMessage());
        }
        return user;
    }

    @Override
    public void inscription(Utilisateur utilisateur) {

        //bien évidemment ici on doit haché le mdp avant de l'enregistrer , et ceci grace à BCrypt
        String mdpHach = BCrypt.hashpw(utilisateur.getMdp(), BCrypt.gensalt());

        try(Connection con2 = ConnexionBD.getConnexion();
        PreparedStatement stmt2 = con2.prepareStatement("INSERT INTO utilisateur (idUser,nomCmpl,wilaya,role,mdp,groupeSang,typeHLA) VALUES (?,?,?,?,?,?,?)")){
            stmt2.setString(1 ,utilisateur.getIdUser());
            stmt2.setString(2,utilisateur.getNomCmpl());
            stmt2.setString(3,utilisateur.getWilaya());
            stmt2.setString(4,utilisateur.getRole());

            stmt2.setString(5,mdpHach);
            stmt2.setString(6,utilisateur.getGroupeSang());
            stmt2.setString(7,utilisateur.getTypeHLA());

            stmt2.executeUpdate();
        }catch(SQLException e){
           System.err.println("Erreur lors de l'insertion d'un nouvel utilisateur : " + e.getMessage());
        }
    }

    @Override
    public void majUtilisateur (String idUser, Utilisateur utilisateurModifie){

        String newMdp = (utilisateurModifie.getMdp() != null && !utilisateurModifie.getMdp().isEmpty()) ? BCrypt.hashpw(utilisateurModifie.getMdp(), BCrypt.gensalt()) : null;

        try(Connection con2 = ConnexionBD.getConnexion();
            PreparedStatement stmt2 = con2.prepareStatement("UPDATE utilisateur SET NomCmpl = ? , wilaya = ? , role = ? , mdp = COALESCE(?, mdp) , groupeSang = ? , typeHLA = ? WHERE idUser = ?")) {
            stmt2.setString(1,utilisateurModifie.getNomCmpl());
            stmt2.setString(2,utilisateurModifie.getWilaya());
            stmt2.setString(3,utilisateurModifie.getRole());
            stmt2.setString(4,newMdp);
            stmt2.setString(5,utilisateurModifie.getGroupeSang());
            stmt2.setString(6,utilisateurModifie.getTypeHLA());
            stmt2.setString(7,idUser);

            int rowsAffected = stmt2.executeUpdate();

            if (rowsAffected == 0){
                throw new SQLException("Erreur lors de la modification ! utilisateur non trouvé ");
            }
        }catch(SQLException e){
            System.err.println("Erreur lors de la modification de l'utilisateur : " + e.getMessage());
        }

    }

    @Override
    public void suppUtilisateur (String idUser){
        try(Connection con3 = ConnexionBD.getConnexion();
            PreparedStatement stmt3 = con3.prepareStatement("DELETE FROM utilisateur WHERE idUser = ?")){
            stmt3.setString(1,idUser);
            stmt3.executeUpdate();
        }catch(SQLException e){
            System.err.println("Erreur lors de la suppression d'un utilisateur : " + e.getMessage());
        }
    }
}