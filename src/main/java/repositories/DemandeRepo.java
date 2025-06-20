package repositories;
import dtos.CriteresRechercheDemande;
import dtos.DemandeDetails ;
import models.Demande;

import java.util.List ;

public interface DemandeRepo {

    Demande findDemande (String idDemande) ;
    void saveDemande (Demande demande) ; // ajouter + modifier
    void suppDemande (String idDemande) ;

    DemandeDetails trouverDemandeDetails(String idDemande) ;

    List<Demande> findAllDemande(int page , int pageSize) ;
    List<Demande> findAllDemande () ;

    List<Demande> rechercheDemande(CriteresRechercheDemande critere) ;
    List<Demande> rechercheDemande (CriteresRechercheDemande critere , int page ,  int pageSize) ;
}
