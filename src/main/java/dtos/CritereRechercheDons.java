package dtos;

import java.util.Date;

public record CritereRechercheDons(
        String type ,
        Date dateDons ,
        String statutDons
) {
}
