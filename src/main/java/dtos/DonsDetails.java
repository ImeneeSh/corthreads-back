package dtos;

import java.util.Date;

public record DonsDetails(
        String idDons ,
        String idUser ,
        String type ,
        Date dateDons ,
        String statutDons ,
        String detailsDons
) {
}
