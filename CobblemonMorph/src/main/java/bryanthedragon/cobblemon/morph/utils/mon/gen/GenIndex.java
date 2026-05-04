package bryanthedragon.cobblemon.morph.utils.mon.gen;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.pokemon.Species;

import bryanthedragon.cobblemon.morph.utils.mon.MonUtils;

public class GenIndex extends MonUtils {
    protected final Collection<Species> allSpecies = PokemonSpecies.INSTANCE.getSpecies();
    protected final Map<Integer, List<Species>> speciesByGen;

    public GenIndex(Map<Integer, List<Species>> speciesByGen) {
        this.speciesByGen = speciesByGen;
    }
} 
