package repositories;

import models.Utilisateur;

public interface UtilisateurRepo {
    //ici , nous allons déclarer les méthodes qu'on utilisera lors de la manipulation des bdd (CRUD)

    Utilisateur findUtilisateur (String idUser) ;
    void inscription (Utilisateur utilisateur) ;
    void majUtilisateur (String idUser, Utilisateur utilisateurModifie) ;
    void suppUtilisateur (String idUser) ;
}
