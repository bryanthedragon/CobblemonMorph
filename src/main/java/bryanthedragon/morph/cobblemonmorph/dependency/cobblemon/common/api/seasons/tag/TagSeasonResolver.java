package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.seasons.tag;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.seasons.CobblemonSeason;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

/**
 * A [SeasonResolver] that works by delegating the work to biome tags.
 *
 * @author Hiroku
 * @since November 25th, 2022
 */
public final class TagSeasonResolver extends SeasonResolver {
    CobblemonSeason invoke(Level worldAccessor, BlockPos pos) ? {
        val biome = world.getBiome(pos);
        return if (biome.`is`(CobblemonBiomeTags.IS_WINTER)) {
            CobblemonSeason.WINTER;
        } 
        else if (biome.`is`(CobblemonBiomeTags.IS_SPRING)) {
            CobblemonSeason.SPRING;
        } 
        else if (biome.`is`(CobblemonBiomeTags.IS_AUTUMN)) {
            CobblemonSeason.AUTUMN;
        } 
        else if (biome.`is`(CobblemonBiomeTags.IS_SUMMER)) {
            CobblemonSeason.SUMMER;
        } 
        else {
            null
        }
    }
}