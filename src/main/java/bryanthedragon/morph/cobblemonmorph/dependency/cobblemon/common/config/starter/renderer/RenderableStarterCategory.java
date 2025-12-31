package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.renderer;

import java.util.List;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon;

public record RenderableStarterCategory(String name, String displayName, List<RenderablePokemon> pokemon) {
    displayNameText = displayName.asTranslated()
}
