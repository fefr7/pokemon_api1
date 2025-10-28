package org.felipe.pokemon_api1.modeldto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PokemonFavoriteRequest {

    @NotNull(message = "O campo 'favorite' não pode ser nulo.") // NOVO: Validação
    private Boolean favorite;

    @Size(max = 500, message = "A nota não pode exceder 500 caracteres.") // NOVO: Validação
    private String note;

}
