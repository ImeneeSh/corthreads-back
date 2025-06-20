package repositories;

import config.ConnexionBD;
import dtos.CriteresRechercheDemande;
import dtos.DemandeDetails;
import models.Demande;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public void suppDemande (String idDemande){
        try(Connection con3 = ConnexionBD.getConnexion();
            PreparedStatement stmt3 = con3.prepareStatement("DELETE FROM demande WHERE idDemande = ?")){
            stmt3.setString(1,idDemande);
            stmt3.executeUpdate();
        }catch(SQLException e){
            System.err.println("Erreur lors de la suppression de la demande : " + e.getMessage());
        }
    }

    @Override
    public void saveDemande(Demande demande) {
        String sql = demande.getIdDemande() == null ?
                "INSERT INTO demande (idUser, typeDemande, urgence, wilaya, groupeSang, typeHLA) VALUES (?, ?, ?, ?, ?, ?)" :
                "UPDATE demande SET idUser = ?, typeDemande = ?, urgence = ?, wilaya = ?, groupeSang = ?, typeHLA = ? WHERE idDemande = ?";

        try (Connection con = ConnexionBD.getConnexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, demande.getIdUser());
            stmt.setString(2, demande.getTypeDemande());
            stmt.setString(3, demande.getUrgence());
            stmt.setString(4, demande.getWilaya());
            stmt.setString(5, demande.getGroupeSang());
            stmt.setString(6, demande.getTypeHLA());

            if (demande.getIdDemande() != null) {
                stmt.setString(7, demande.getIdDemande());
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la sauvegarde de la demande : " + e.getMessage());
            throw new RuntimeException("Échec de la sauvegarde", e);
        }
    }

    @Override
    public DemandeDetails trouverDemandeDetails(String idDemande) {
        String sql = "SELECT utilisateur.nomCmpl FROM utilisateur JOIN demande ON utilisateur.idUser = demande.idUser WHERE demande.idDemande= ?";

        try (Connection con = ConnexionBD.getConnexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, idDemande);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new DemandeDetails(
                        rs.getString("idDemande"),
                        rs.getString("idUser"),
                        rs.getString("typeDemande"),
                        rs.getString("urgence"),
                        rs.getString("wilaya"),
                        rs.getString("groupeSang"),
                        rs.getString("typeHLA"),
                        rs.getString("nomCmpl")
                );
            }
            throw new RuntimeException("Demande non trouvée avec l'ID: " + idDemande);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche des détails : " + e.getMessage());
            throw new RuntimeException("Échec de la récupération des détails", e);
        }
    }

    @Override
    public List<Demande> findAllDemande(int page, int pageSize) {
        List<Demande> demandes = new ArrayList<>();
        String sql = "SELECT * FROM demande LIMIT ? OFFSET ?";

        try (Connection con = ConnexionBD.getConnexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, pageSize);
            stmt.setInt(2, page * pageSize);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Demande demande = new Demande();
                    demande.setIdUser(rs.getString("idUser"));
                    demande.setTypeDemande(rs.getString("typeDemande"));
                    demande.setUrgence(rs.getString("urgence"));
                    demande.setWilaya(rs.getString("wilaya"));
                    demande.setGroupeSang(rs.getString("groupeSang"));
                    demande.setTypeHLA(rs.getString("typeHLA"));
                    demandes.add(demande);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération paginée : " + e.getMessage());
            throw new RuntimeException("Échec de la récupération paginée", e);
        }
        return demandes;
    }

    @Override
    public List<Demande> findAllDemande() {
        List<Demande> demandes = new ArrayList<>();
        String sql = "SELECT * FROM demande";

        try (Connection con = ConnexionBD.getConnexion();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Demande demande = new Demande();
                demande.setIdDemande(rs.getString("idDemande"));
                demande.setIdUser(rs.getString("idUser"));
                demande.setTypeDemande(rs.getString("typeDemande"));
                demande.setUrgence(rs.getString("urgence"));
                demande.setWilaya(rs.getString("wilaya"));
                demande.setGroupeSang(rs.getString("groupeSang"));
                demande.setTypeHLA(rs.getString("typeHLA"));
                demandes.add(demande);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des demandes : " + e.getMessage());
            throw new RuntimeException("Échec de la récupération", e);
        }
        return demandes;
    }

    @Override
    public List<Demande> rechercheDemande(CriteresRechercheDemande critere) {

        List<Demande> demandes = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM demande WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (critere.typeDemande() != null) { //construction dynamique de la requete
            sql.append(" AND typeDemande = ?");
            params.add(critere.typeDemande());
        }
        if (critere.urgence() != null) {
            sql.append(" AND urgence = ?");
            params.add(critere.urgence());
        }
        if (critere.wilaya() != null) {
            sql.append(" AND wilaya = ?");
            params.add(critere.wilaya());
        }
        if (critere.groupeSang() != null) {
            sql.append(" AND groupeSang = ?");
            params.add(critere.groupeSang());
        }
        if (critere.typeHLA() != null) {
            sql.append(" AND typeHLA = ?");
            params.add(critere.typeHLA());
        }

        try (Connection con = ConnexionBD.getConnexion();
             PreparedStatement stmt = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) { // pour régler le positionnement des paramètres
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Demande demande = new Demande();
                    demande.setIdDemande(rs.getString("idDemande"));
                    demande.setIdUser(rs.getString("idUser"));
                    demande.setTypeDemande(rs.getString("typeDemande"));
                    demande.setUrgence(rs.getString("urgence"));
                    demande.setWilaya(rs.getString("wilaya"));
                    demande.setGroupeSang(rs.getString("groupeSang"));
                    demande.setTypeHLA(rs.getString("typeHLA"));
                    demandes.add(demande);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche : " + e.getMessage());
            throw new RuntimeException("Échec de la recherche", e);
        }
        return demandes;
    }

    @Override
    public List<Demande> rechercheDemande(CriteresRechercheDemande critere, int page, int pageSize) {
        List<Demande> demandes = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM demande WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (critere.typeDemande() != null) {
            sql.append(" AND typeDemande = ?");
            params.add(critere.typeDemande());
        }
        if (critere.urgence() != null) {
            sql.append(" AND urgence = ?");
            params.add(critere.urgence());
        }
        if (critere.wilaya() != null) {
            sql.append(" AND wilaya = ?");
            params.add(critere.wilaya());
        }
        if (critere.groupeSang() != null) {
            sql.append(" AND groupeSang = ?");
            params.add(critere.groupeSang());
        }
        if (critere.typeHLA() != null) {
            sql.append(" AND typeHLA = ?");
            params.add(critere.typeHLA());
        }

        sql.append(" LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add(page * pageSize);

        try (Connection con = ConnexionBD.getConnexion();
             PreparedStatement stmt = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Demande demande = new Demande();
                    demande.setIdDemande(rs.getString("idDemande"));
                    demande.setIdUser(rs.getString("idUser"));
                    demande.setTypeDemande(rs.getString("typeDemande"));
                    demande.setUrgence(rs.getString("urgence"));
                    demande.setWilaya(rs.getString("wilaya"));
                    demande.setGroupeSang(rs.getString("groupeSang"));
                    demande.setTypeHLA(rs.getString("typeHLA"));
                    demandes.add(demande);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche paginée : " + e.getMessage());
            throw new RuntimeException("Échec de la recherche paginée", e);
        }
        return demandes;
    }
}
