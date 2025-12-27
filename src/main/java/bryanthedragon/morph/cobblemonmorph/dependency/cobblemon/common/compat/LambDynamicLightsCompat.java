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
      DynamicLightHandlers.registerDynamicLightHandler(
         CobblemonEntities.POKEMON, DynamicLightHandler.makeHandler(pokemon -> resolvedPokemonLightLevel(pokemon, false), pokemon -> true)
      );
      DynamicLightHandlers.registerDynamicLightHandler(
         CobblemonEntities.POKEMON, DynamicLightHandler.makeHandler(pokemon -> resolvedPokemonLightLevel(pokemon, true), pokemon -> false)
      );
      DynamicLightHandlers.registerDynamicLightHandler(
         EntityType.f_20532_, DynamicLightHandler.makeHandler(player -> resolvedShoulderLightLevel(player, false), player -> true)
      );
      DynamicLightHandlers.registerDynamicLightHandler(
         EntityType.f_20532_, DynamicLightHandler.makeHandler(player -> resolvedShoulderLightLevel(player, true), player -> false)
      );
   }

   private static int resolvedPokemonLightLevel(PokemonEntity pokemon, boolean underwater) {
      return extractFormLightLevel(pokemon.getForm(), underwater).orElse(0);
   }

   private static int resolvedShoulderLightLevel(Player player, boolean underwater) {
      Tuple<PokemonOnShoulderRenderer.ShoulderData, PokemonOnShoulderRenderer.ShoulderData> shoulderDataPair = PokemonOnShoulderRenderer.shoulderDataOf(player);
      Optional<Integer> leftLightLevel = extractShoulderLightLevel((PokemonOnShoulderRenderer.ShoulderData)shoulderDataPair.m_14418_(), underwater);
      Optional<Integer> rightLightLevel = extractShoulderLightLevel((PokemonOnShoulderRenderer.ShoulderData)shoulderDataPair.m_14419_(), underwater);
      return Math.max(leftLightLevel.orElse(0), rightLightLevel.orElse(0));
   }

   private static Optional<Integer> extractFormLightLevel(@NotNull FormData form, boolean underwater) {
      return form.getLightingData() != null && liquidGlowModeSupport(form.getLightingData().getLiquidGlowMode(), underwater)
         ? Optional.of(form.getLightingData().getLightLevel())
         : Optional.empty();
   }

   private static Optional<Integer> extractShoulderLightLevel(@Nullable PokemonOnShoulderRenderer.ShoulderData shoulderData, boolean underwater) {
      return shoulderData == null ? Optional.empty() : extractFormLightLevel(shoulderData.getForm(), underwater);
   }

   private static boolean liquidGlowModeSupport(@NotNull LightingData.LiquidGlowMode liquidGlowMode, boolean underwater) {
      return underwater ? liquidGlowMode.getGlowsUnderwater() : liquidGlowMode.getGlowsInLand();
   }
}
