package repositories;

import config.ConnexionBD ;
import dtos.CritereRechercheDons ;
import dtos.DonsDetails;
import dtos.UtilisateurDetails ;
import models.Dons ;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class DonsRepoImpl implements DonsRepo {
    @Override
    public Dons findDons (String idDons){
        Dons dons = null;

        try(Connection con = ConnexionBD.getConnexion();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM dons WHERE idDons = ?")){

            stmt.setString(1,idDons);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    dons = new Dons();
                    dons.setIdDons(rs.getString("idDons"));
                    dons.setIdUser(rs.getString("idUser"));
                    dons.setType(rs.getString("type"));
                    dons.setDateDons(rs.getDate("dateDons"));
                    dons.setStatutDons(rs.getString("statutDons"));
                    dons.setDetailsDons(rs.getString("detailsDons"));
                }
            }
        }catch(SQLException e){
            System.err.println("Erreur lors de la recherche du dons :"+ e.getMessage());
        }
        return dons;
    }

    @Override
    public void saveDons (Dons dons){

        String sql = dons.getIdDons() == null ?
                "INSERT INTO dons (idDons,idUser,type,dateDons,statutDons,detailsDons) VALUES (?,?,?,?,?,?)" :
                "UPDATE dons SET type=? , dateDons=? , statutDons=? , detailsDons=? WHERE idDons = ?";

        try(Connection con= ConnexionBD.getConnexion();
        PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1,dons.getType());
            stmt.setDate(2, (Date) dons.getDateDons());
            stmt.setString(3,dons.getStatutDons());
            stmt.setString(4,dons.getDetailsDons());

            if(dons.getIdDons() != null){
                stmt.setString(5,dons.getIdDons());
            }

            stmt.executeUpdate();
        }catch(SQLException e){
            System.err.println("Erreur lors de la sauvefarde du dons :"+ e.getMessage());
            throw new RuntimeException("Echec lors de la sauvegarde" ,e) ;
        }
    }

    @Override
    public void suppDons (String idDons){
        try(Connection con = ConnexionBD.getConnexion();
        PreparedStatement stmt = con.prepareStatement("DELETE FROM dons WHERE idDons =  ?")){
            stmt.setString(1,idDons);
            stmt.executeUpdate();
        }catch(SQLException e){
            System.err.println("Erreur lors de la suppression du dons :"+ e.getMessage());
        }
    }

    @Override
    public DonsDetails findDonsDetails(String idDons){
        String sql = "SELECT * FROM dons WHERE idDons = ?";

        try(Connection con = ConnexionBD.getConnexion();
        PreparedStatement stmt= con.prepareStatement(sql)){

            stmt.setString(1,idDons);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return new DonsDetails(
                        rs.getString("idDons"),
                        rs.getString("idUser"),
                        rs.getString("type"),
                        rs.getDate("dateDons"),
                        rs.getString("statutDons"),
                        rs.getString("detailsDons")
                );
            }
            throw new RuntimeException("Dons non trouvé avec l'ID :" + idDons);
        }catch(SQLException e){
            System.err.println("Erreur lors de la recherche des détails:"+ e.getMessage());
            throw new RuntimeException("Echec lors de la récupération des détails" ,e);
        }
    }

    @Override
    public List<Dons> findAllDons (int page , int pageSize){
        List<Dons> dons = new ArrayList<>();
        String sql ="SELECT * FROM dons LIMIT ? OFFSET ?";

        try(Connection con = ConnexionBD.getConnexion();
        PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setInt(1,pageSize);
            stmt.setInt(2,page * pageSize);

            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    Dons don = new Dons();
                    don.setType(rs.getString("type"));
                    don.setDateDons(rs.getDate("dateDons"));
                    don.setStatutDons(rs.getString("statutDons"));
                    don.setDetailsDons(rs.getString("detailsDons"));
                    dons.add(don);

                }
            }
        }catch(SQLException e){
            System.err.println("Erreur lors de la récupération paginée :"+ e.getMessage());
            throw new RuntimeException("Echec lors de la récupération paginée ",e);
        }
        return dons;
    }

    @Override
    public List<Dons> findAllDons (){
        List<Dons> dons = new ArrayList<>();
        String sql ="SELECT * FROM dons";

        try(Connection con = ConnexionBD.getConnexion();
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){

            while(rs.next()){
                Dons don = new Dons();
                don.setIdDons(rs.getString("idDons"));
                don.setIdUser(rs.getString("idUser"));
                don.setType(rs.getString("type"));
                don.setDateDons(rs.getDate("dateDons"));
                don.setStatutDons(rs.getString("statutDons"));
                don.setDetailsDons(rs.getString("detailsDons"));
                dons.add(don);
            }
        }catch(SQLException e){
            System.err.println("Erreur lors de la récupération des dons :"+e.getMessage());
            throw new RuntimeException("Echec de la récupération des dons ",e);
        }
        return dons;
    }

    @Override
    public List<Dons> rechercherDons (CritereRechercheDons critere){
        List<Dons> dons = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM dons WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if(critere.type() != null){
            sql.append(" AND type=? ");
            params.add(critere.type());
        }

        if(critere.dateDons() != null){
            sql.append(" AND dateDons >= ? ");
            params.add(critere.dateDons());
        }

        if(critere.statutDons() != null){
            sql.append(" AND statutDons = ? ");
            params.add(critere.statutDons());
        }

        try(Connection con = ConnexionBD.getConnexion();
        PreparedStatement stmt = con.prepareStatement(sql.toString())){

            for(int i=0 ;i<params.size();i++){
                stmt.setObject(i+1,params.get(i));
            }

            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    Dons don = new Dons();
                    don.setIdDons(rs.getString("idDons"));
                    don.setIdUser(rs.getString("idUser"));
                    don.setType(rs.getString("type"));
                    don.setDateDons(rs.getDate("dateDons"));
                    don.setStatutDons(rs.getString("statutDons"));
                    don.setDetailsDons(rs.getString("detailsDons"));
                    dons.add(don);
                }
            }
        }catch(SQLException e){
            System.err.println("Erreur lors de la recherche :" + e.getMessage());
            throw new  RuntimeException("Echec de la recherche ",e);
        }
        return dons ;
    }

    @Override
    public List<Dons> rechercheDons (CritereRechercheDons critere, int page , int pageSize){
        List<Dons> dons = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM dons WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if(critere.type() != null){
            sql.append(" AND type=? ");
            params.add(critere.type());
        }
        if(critere.dateDons() != null){
            sql.append(" AND dateDons >= ? ");
            params.add(critere.dateDons());
        }
        if(critere.statutDons() != null){
            sql.append(" AND statutDons = ? ");
            params.add(critere.statutDons());
        }

        sql.append(" LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add(page * pageSize);

        try(Connection con = ConnexionBD.getConnexion();
        PreparedStatement stmt = con.prepareStatement(sql.toString())){

            for(int i=0 ; i<params.size();i++){
                stmt.setObject(i+1,params.get(i));
            }

            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    Dons don = new Dons();
                    don.setIdDons(rs.getString("idDons"));
                    don.setIdUser(rs.getString("idUser"));
                    don.setType(rs.getString("type"));
                    don.setDateDons(rs.getDate("dateDons"));
                    don.setStatutDons(rs.getString("statutDons"));
                    don.setDetailsDons(rs.getString("detailsDons"));
                    dons.add(don);
                }
            }
        }catch(SQLException e){
            System.err.println("Erreur lors de la recherche paginée :"+e.getMessage());
            throw new RuntimeException("Echec de la recherche paginée",e);
        }
        return dons ;
    }
}
