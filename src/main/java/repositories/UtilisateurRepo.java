package repositories;

import dtos.CritereRechercheUtilisateur;
import dtos.UtilisateurDetails;
import models.Utilisateur;

import java.util.List;

public interface UtilisateurRepo {

    Utilisateur findUtilisateur (String idUser) ;
    void saveUtilisateur (Utilisateur utilisateur) ;
    void suppUtilisateur (String idUser) ;

    UtilisateurDetails  findUtilisateurDetails(String idUser) ;

    List<Utilisateur> findAllUtilisateur (int page , int pageSize) ;
    List<Utilisateur> findAllUtilisateur () ;

    List<Utilisateur> rechercherUtilisateur (CritereRechercheUtilisateur critere) ;
    List<Utilisateur> rechercherUtilisateur (CritereRechercheUtilisateur critere , int page , int pageSize) ;

    boolean verifierMotDePasse(String idUser, String mdpClair) ;
}
