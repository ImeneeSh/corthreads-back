package repositories;

import config.ConnexionBD;
import dtos.CritereRechercheUtilisateur;
import dtos.UtilisateurDetails;
import models.Utilisateur;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public void suppUtilisateur (String idUser){
        try(Connection con3 = ConnexionBD.getConnexion();
            PreparedStatement stmt3 = con3.prepareStatement("DELETE FROM utilisateur WHERE idUser = ?")){
            stmt3.setString(1,idUser);
            stmt3.executeUpdate();
        }catch(SQLException e){
            System.err.println("Erreur lors de la suppression d'un utilisateur : " + e.getMessage());
        }
    }

    @Override
    public void saveUtilisateur (Utilisateur utilisateur) {

        String mdpHache = BCrypt.hashpw(utilisateur.getMdp(), BCrypt.gensalt());

        String sql = utilisateur.getIdUser() == null ?
                "INSERT INTO utilisateur (idUser,nomCmpl,wilaya,role,mdp,groupeSang,typeHLA) VALUES (?,?,?,?,?,?,?)" :
                "UPDATE utilisateur SET nomCmpl = ? , wilaya= ? , role= ? , mdp= ? , groupeSang= ? , typeHLA= ? WHERE idUser = ?";

        try(Connection con = ConnexionBD.getConnexion() ;
        PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1,utilisateur.getNomCmpl());
            stmt.setString(2,utilisateur.getWilaya());
            stmt.setString(3,utilisateur.getRole());
            stmt.setString(4,mdpHache);
            stmt.setString(5,utilisateur.getGroupeSang());
            stmt.setString(6,utilisateur.getTypeHLA());

            if(utilisateur.getIdUser() != null){
                stmt.setString(7,utilisateur.getIdUser());
            }

            stmt.executeUpdate();
        }catch(SQLException e){
            System.err.println("Erreur lors de la sauvegarde de l'utilisateur :" + e.getMessage());
            throw new RuntimeException("Echec lors de la sauvegarde" ,e);
        }
    }

    @Override
    public UtilisateurDetails findUtilisateurDetails(String idUser){
        String sql = "SELECT * FROM utilisateur WHERE idUser = ?";

        try(Connection con = ConnexionBD.getConnexion();
        PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1,idUser);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return new UtilisateurDetails(
                        rs.getString("idUser"),
                        rs.getString("nomCmpl"),
                        rs.getString("wilaya"),
                        rs.getString("role"),
                        rs.getString("groupeSang"),
                        rs.getString("typeHLA")
                );
            }
            throw new RuntimeException("Utilisateur non trouvé avec i'ID :"+ idUser);
        }catch(SQLException e){
            System.err.println("Erreur lors de la recherche des détails :"+ e.getMessage());
            throw new RuntimeException("Echec lors de la récupération des détails" ,e);
        }
    }

    @Override
    public List<Utilisateur> findAllUtilisateur (int page , int pageSize){
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql ="SELECT * FROM utilisateur LIMIT ? OFFSET ?";

        try (Connection con  = ConnexionBD.getConnexion();
        PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1,pageSize);
            stmt.setInt(2,page * pageSize);

            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()) {
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setNomCmpl(rs.getString("nomCmpl"));
                    utilisateur.setWilaya(rs.getString("wilaya"));
                    utilisateur.setRole(rs.getString("role"));
                    utilisateur.setGroupeSang(rs.getString("groupeSang"));
                    utilisateur.setTypeHLA(rs.getString("typeHLA"));
                    utilisateurs.add(utilisateur);
                }
            }
        }catch(SQLException e){
            System.err.println("Erreur lors de la récupération paginée :" +e.getMessage());
            throw new RuntimeException("Echec de la récupération paginée ",e);
        }
        return utilisateurs;
    }

    @Override
    public List<Utilisateur> findAllUtilisateur (){
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql ="SELECT * FROM utilisateur";

        try(Connection con = ConnexionBD.getConnexion();
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){

            while(rs.next()){
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setIdUser(rs.getString("idUser"));
                utilisateur.setNomCmpl(rs.getString("nomCmpl"));
                utilisateur.setWilaya(rs.getString("wilaya"));
                utilisateur.setRole(rs.getString("role"));
                utilisateur.setMdp(rs.getString("mdp"));
                utilisateur.setGroupeSang(rs.getString("groupeSang"));
                utilisateur.setTypeHLA(rs.getString("typeHLA"));
                utilisateurs.add(utilisateur);
            }
        }catch(SQLException e){
            System.err.println("Erreur lors de la récupération des utilisateurs :" +e.getMessage());
            throw new RuntimeException("Echec de la récupération" ,e);
        }
        return utilisateurs;
    }

    @Override
    public List<Utilisateur> rechercherUtilisateur (CritereRechercheUtilisateur critere){
        List<Utilisateur> utilisateurs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM utilisateur WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if(critere.wilaya() != null){
            sql.append(" AND wilaya=? ");
            params.add(critere.wilaya());
        }
        if(critere.role() != null){
            sql.append(" AND role=? ");
            params.add(critere.role());
        }
        if(critere.groupeSang() != null){
            sql.append(" AND groupeSang=? ");
            params.add(critere.groupeSang());
        }
        if(critere.typeHLA() != null){
            sql.append(" AND typeHLA=? ");
            params.add(critere.typeHLA());
        }

        try(Connection con = ConnexionBD.getConnexion();
        PreparedStatement stmt = con.prepareStatement(sql.toString())){

            for(int i=0 ; i<params.size() ; i++){
                stmt.setObject(i+1,params.get(i));
            }

            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setIdUser(rs.getString("idUser"));
                    utilisateur.setNomCmpl(rs.getString("nomCmpl"));
                    utilisateur.setWilaya(rs.getString("wilaya"));
                    utilisateur.setRole(rs.getString("role"));
                    utilisateur.setMdp(rs.getString("mdp"));
                    utilisateur.setGroupeSang(rs.getString("groupeSang"));
                    utilisateur.setTypeHLA(rs.getString("typeHLA"));
                    utilisateurs.add(utilisateur);
                }
            }
        } catch(SQLException e){
            System.err.println("Erreur lors de la recherche :" +e.getMessage());
            throw new RuntimeException("Echec de la recherche", e);
        }
        return utilisateurs;
    }

    @Override
    public List<Utilisateur> rechercherUtilisateur (CritereRechercheUtilisateur critere , int page , int pageSize){
        List<Utilisateur> utilisateurs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM utilisateur WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if(critere.wilaya() != null){
            sql.append(" AND wilaya=? ");
            params.add(critere.wilaya());
        }
        if(critere.role() != null){
            sql.append(" AND role=? ");
            params.add(critere.role());
        }
        if(critere.groupeSang() != null){
            sql.append(" AND groupeSang=? ");
            params.add(critere.groupeSang());
        }
        if(critere.typeHLA() != null){
            sql.append(" AND typeHLA=? ");
            params.add(critere.typeHLA());
        }

        sql.append(" LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add(page * pageSize);

        try(Connection con = ConnexionBD.getConnexion();
        PreparedStatement stmt = con.prepareStatement(sql.toString())){

            for(int i=0 ; i<params.size() ; i++){
                stmt.setObject(i+1,params.get(i));
            }

            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setIdUser(rs.getString("idUser"));
                    utilisateur.setNomCmpl(rs.getString("nomCmpl"));
                    utilisateur.setWilaya(rs.getString("wilaya"));
                    utilisateur.setRole(rs.getString("role"));
                    utilisateur.setMdp(rs.getString("mdp"));
                    utilisateur.setGroupeSang(rs.getString("groupeSang"));
                    utilisateur.setTypeHLA(rs.getString("typeHLA"));
                    utilisateurs.add(utilisateur);
                }
            }
        }catch(SQLException e){
            System.err.println("Erreur lors de la recherche paginée :" + e.getMessage());
            throw new RuntimeException("Echec de la recherche paginée" ,e);
        }
        return utilisateurs;
    }

    @Override
    public boolean verifierMotDePasse(String idUser, String mdpClair) {
        String sql = "SELECT mdp FROM utilisateur WHERE idUser = ?";

        try (Connection con = ConnexionBD.getConnexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, idUser);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String mdpHache = rs.getString("mdp");
                return BCrypt.checkpw(mdpClair, mdpHache);
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de vérification du mot de passe", e);
        }
    }
}