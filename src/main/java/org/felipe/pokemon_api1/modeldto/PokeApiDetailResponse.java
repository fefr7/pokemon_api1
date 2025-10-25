package org.felipe.pokemon_api1.modeldto;

import lombok.Data;
import java.util.List;

@Data
public class PokeApiDetailResponse {

    private Integer id;
    private String name;
    private Integer height;
    private Integer weight;

    private List<AbilityWrapper> abilities;
    private List<TypeWrapper> types;

    @Data
    public static class AbilityWrapper {
        private Ability ability;
    }

    @Data
    public static class Ability {
        private String name;
    }

    @Data
    public static class TypeWrapper {
        private Type type;
    }

    @Data
    public static class Type {
        private String name;
    }
}