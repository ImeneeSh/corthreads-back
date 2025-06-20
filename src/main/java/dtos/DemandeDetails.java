package dtos;

public record DemandeDetails(
        String idDemande,
        String idUser,
        String typeDemande,
        String urgence,
        String wilaya,
        String groupeSang ,
        String typeHLA ,
        String nomCmpl
) {
}
