package repositories;

import dtos.CritereRechercheDons;
import dtos.DonsDetails;
import models.Dons;

import java.util.List;

public interface DonsRepo {

    Dons findDons (String idDons) ;
    void saveDons (Dons dons);
    void suppDons (String idDons) ;

    DonsDetails findDonsDetails(String idDons) ;

    List<Dons> findAllDons (int page , int pageSize) ;
    List<Dons> findAllDons ();

    List<Dons> rechercherDons (CritereRechercheDons critere) ;
    List<Dons> rechercheDons (CritereRechercheDons critere, int page , int pageSize) ;
}
