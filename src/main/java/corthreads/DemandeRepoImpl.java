package corthreads;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DemandeRepoImpl implements DemandeRepo {

    @Override
    public Demande findDemande(String idDemande){
        Demande demande = null;

        try (Connection con = ConnexionBD.getConnexion();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM demande WHERE idDemande = ?")) {

            stmt.setString(1, idDemande);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    demande = new Demande();
                    demande.setIdDemande(rs.getString("idDemande"));
                    demande.setIdUser(rs.getString("idUser"));
                    demande.setTypeDemande(rs.getString("typeDemande"));
                    demande.setUrgence(rs.getString("urgence"));
                    demande.setWilaya(rs.getString("wilaya"));
                    demande.setGroupeSang(rs.getString("groupeSang"));
                    demande.setTypeHLA(rs.getString("typeHLA"));
                }

            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de la demande : " + e.getMessage());
        }
        return demande;
    }

    @Override
    public void ajoutDemande (Demande demande){

        try(Connection con2 = ConnexionBD.getConnexion();
            PreparedStatement stmt2 = con2.prepareStatement("INSERT INTO demande (idDemande,idUser,typeDemande,urgence,wilaya,groupeSang,typeHLA) VALUES (?,?,?,?,?,?,?)")){
            stmt2.setString(1 ,demande.getIdDemande());
            stmt2.setString(2,demande.getIdUser());
            stmt2.setString(3,demande.getTypeDemande());
            stmt2.setString(4,demande.getUrgence());
            stmt2.setString(5,demande.getWilaya());
            stmt2.setString(6,demande.getGroupeSang());
            stmt2.setString(7,demande.getTypeHLA());

            stmt2.executeUpdate();
        }catch(SQLException e){
            System.err.println("Erreur lors de l'insertion d'une nouvelle demande : " + e.getMessage());
        }
    }

    @Override
    public void majDemande (String idDemande , Demande demandeMaj){
        try(Connection con2 = ConnexionBD.getConnexion();
            PreparedStatement stmt2 = con2.prepareStatement("UPDATE demande SET idDemande = ? , idUser = ? , typeDemande = ? , urgence = ? , groupeSang = ? , typeHLA = ? WHERE idDemande = ?")) {
            stmt2.setString(1,demandeMaj.getIdDemande());
            stmt2.setString(2,demandeMaj.getIdUser());
            stmt2.setString(3,demandeMaj.getTypeDemande());
            stmt2.setString(4,demandeMaj.getUrgence());
            stmt2.setString(5,demandeMaj.getGroupeSang());
            stmt2.setString(6,demandeMaj.getTypeHLA());
            stmt2.setString(7,idDemande);

            int rowsAffected = stmt2.executeUpdate();

            if (rowsAffected == 0){
                throw new SQLException("Erreur lors de la modification ! demande non trouvée ");
            }
        }catch(SQLException e){
            System.err.println("Erreur lors de la modification de la demande : " + e.getMessage());
        }
    }

    @Override
    public void suppDemande (String idDemande){
        try(Connection con3 = ConnexionBD.getConnexion();
            PreparedStatement stmt3 = con3.prepareStatement("DELETE FROM demande WHERE idDemande = ?")){
            stmt3.setString(1,idDemande);
            stmt3.executeUpdate();
        }catch(SQLException e){
            System.err.println("Erreur lors de la suppression de la demande : " + e.getMessage());
        }
    }
}
