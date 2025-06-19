package corthreads;

public interface DemandeRepo {
    Demande findDemande (String idDemande) ;
    void ajoutDemande (Demande demande) ;
    void majDemande (String idDemande , Demande demandeMaj) ;
    void suppDemande (String idDemande) ;
}
