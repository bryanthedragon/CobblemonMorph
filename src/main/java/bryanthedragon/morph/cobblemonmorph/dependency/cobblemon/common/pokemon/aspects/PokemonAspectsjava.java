package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.aspects

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.AspectProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.SingleConditionalAspectProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import org.jetbrains.annotations.NotNull

public final val GENDER_ASPECT: AspectProvider = (new AspectProvider() {
   @NotNull
   public final java.util.Set<java.lang.String> getAspectsForGender(@NotNull Gender gender) {
      var var10000: java.lang.String;
      switch (gender) {
         case MALE:
            var10000 = "male";
            break;
         case FEMALE:
            var10000 = "female";
            break;
         case GENDERLESS:
            var10000 = "genderless";
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }

      return SetsKt.setOf(var10000);
   }

   @NotNull
   @Override
   public java.util.Set<java.lang.String> provide(@NotNull Pokemon pokemon) {
      return this.getAspectsForGender(pokemon.getGender());
   }

   @NotNull
   @Override
   public java.util.Set<java.lang.String> provide(@NotNull PokemonProperties properties) {
      val var10000: Gender = properties.getGender();
      if (var10000 != null) {
         val var4: java.util.Set = this.getAspectsForGender(var10000);
         if (var4 != null) {
            return var4;
         }
      }

      return SetsKt.emptySet();
   }

   @NotNull
   @Override
   public AspectProvider register() {
      return AspectProvider.DefaultImpls.register(this);
   }
}) as AspectProvider

public final val SHINY_ASPECT: SingleConditionalAspectProvider = (new SingleConditionalAspectProvider() {
   @NotNull
   private final java.lang.String aspect;

   {
      this.aspect = "shiny";
   }

   @NotNull
   @Override
   public java.lang.String getAspect() {
      return this.aspect;
   }

   @Override
   public boolean meetsCondition(@NotNull Pokemon pokemon) {
      return pokemon.getShiny();
   }

   @Override
   public boolean meetsCondition(@NotNull PokemonProperties pokemonProperties) {
      return pokemonProperties.getShiny() == true;
   }

   @NotNull
   @Override
   public java.util.Set<java.lang.String> provide(@NotNull PokemonProperties properties) {
      return SingleConditionalAspectProvider.DefaultImpls.provide(this, properties);
   }

   @NotNull
   @Override
   public java.util.Set<java.lang.String> provide(@NotNull Pokemon pokemon) {
      return SingleConditionalAspectProvider.DefaultImpls.provide(this, pokemon);
   }

   @NotNull
   @Override
   public AspectProvider register() {
      return SingleConditionalAspectProvider.DefaultImpls.register(this);
   }
}) as SingleConditionalAspectProvider
