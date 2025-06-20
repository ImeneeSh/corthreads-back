package repositories;

import models.Dons;

public interface DonsRepo {
    Dons findDons (String idDons) ;
    void ajoutDons (Dons dons) ;
    void majDons (String idDons , Dons donsMaj) ;
    void suppDons (String idDons) ;
}
