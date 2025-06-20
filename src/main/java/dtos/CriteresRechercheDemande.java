package dtos;

// ici , nous avons fusionner les filtres en 1 seule méthode

public record CriteresRechercheDemande(
        String typeDemande,
        String urgence,
        String wilaya,
        String groupeSang,
        String typeHLA
) {
}
