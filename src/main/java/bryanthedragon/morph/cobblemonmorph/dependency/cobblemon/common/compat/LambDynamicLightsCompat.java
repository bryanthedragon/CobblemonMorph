/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  dev.lambdaurora.lambdynlights.api.DynamicLightHandler
 *  dev.lambdaurora.lambdynlights.api.DynamicLightHandlers
 *  net.minecraft.util.Tuple
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Player
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.compat;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.PokemonOnShoulderRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.lighthing.LightingData;
import dev.lambdaurora.lambdynlights.api.DynamicLightHandler;
import dev.lambdaurora.lambdynlights.api.DynamicLightHandlers;
import java.util.Optional;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LambDynamicLightsCompat {
    public static void hookCompat() {
        DynamicLightHandlers.registerDynamicLightHandler(CobblemonEntities.POKEMON, (DynamicLightHandler)DynamicLightHandler.makeHandler(pokemon -> LambDynamicLightsCompat.resolvedPokemonLightLevel(pokemon, false), pokemon -> true));
        DynamicLightHandlers.registerDynamicLightHandler(CobblemonEntities.POKEMON, (DynamicLightHandler)DynamicLightHandler.makeHandler(pokemon -> LambDynamicLightsCompat.resolvedPokemonLightLevel(pokemon, true), pokemon -> false));
        DynamicLightHandlers.registerDynamicLightHandler((EntityType)EntityType.f_20532_, (DynamicLightHandler)DynamicLightHandler.makeHandler(player -> LambDynamicLightsCompat.resolvedShoulderLightLevel(player, false), player -> true));
        DynamicLightHandlers.registerDynamicLightHandler((EntityType)EntityType.f_20532_, (DynamicLightHandler)DynamicLightHandler.makeHandler(player -> LambDynamicLightsCompat.resolvedShoulderLightLevel(player, true), player -> false));
    }

    private static int resolvedPokemonLightLevel(PokemonEntity pokemon, boolean underwater) {
        return LambDynamicLightsCompat.extractFormLightLevel(pokemon.getForm(), underwater).orElse(0);
    }

    private static int resolvedShoulderLightLevel(Player player, boolean underwater) {
        Tuple<PokemonOnShoulderRenderer.ShoulderData, PokemonOnShoulderRenderer.ShoulderData> shoulderDataPair = PokemonOnShoulderRenderer.shoulderDataOf(player);
        Optional<Integer> leftLightLevel = LambDynamicLightsCompat.extractShoulderLightLevel((PokemonOnShoulderRenderer.ShoulderData)shoulderDataPair.m_14418_(), underwater);
        Optional<Integer> rightLightLevel = LambDynamicLightsCompat.extractShoulderLightLevel((PokemonOnShoulderRenderer.ShoulderData)shoulderDataPair.m_14419_(), underwater);
        return Math.max(leftLightLevel.orElse(0), rightLightLevel.orElse(0));
    }

    private static Optional<Integer> extractFormLightLevel(@NotNull FormData form2, boolean underwater) {
        if (form2.getLightingData() == null || !LambDynamicLightsCompat.liquidGlowModeSupport(form2.getLightingData().getLiquidGlowMode(), underwater)) {
            return Optional.empty();
        }
        return Optional.of(form2.getLightingData().getLightLevel());
    }

    private static Optional<Integer> extractShoulderLightLevel(@Nullable PokemonOnShoulderRenderer.ShoulderData shoulderData, boolean underwater) {
        if (shoulderData == null) {
            return Optional.empty();
        }
        return LambDynamicLightsCompat.extractFormLightLevel(shoulderData.getForm(), underwater);
    }

    private static boolean liquidGlowModeSupport(@NotNull LightingData.LiquidGlowMode liquidGlowMode, boolean underwater) {
        return underwater ? liquidGlowMode.getGlowsUnderwater() : liquidGlowMode.getGlowsInLand();
    }
}

