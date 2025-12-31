package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.seasons;

/**
 * A season. You know the ones.
 *
 * @author Hiroku
 * @since November 25th, 2022
 */
public enum CobblemonSeason {
    SPRING,
    AUTUMN,
    SUMMER,
    WINTER;

    final class Companion {
        val ALL_VALUES = EnumSet.allOf(CobblemonSeason.class);
    }
}
