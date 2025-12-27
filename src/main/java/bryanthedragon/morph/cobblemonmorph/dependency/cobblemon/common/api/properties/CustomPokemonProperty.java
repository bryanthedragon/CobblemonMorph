package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.PropertiesCompletionProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

public interface CustomPokemonProperty {
   public abstract fun asString(): String {
   }

   public abstract fun apply(pokemon: Pokemon) {
   }

   public open fun apply(pokemonEntity: PokemonEntity) {
   }

   public abstract fun matches(pokemon: Pokemon): Boolean {
   }

   public open fun matches(pokemonEntity: PokemonEntity): Boolean {
   }

   @SourceDebugExtension(["SMAP\nCustomPokemonProperty.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CustomPokemonProperty.kt\ncom/cobblemon/mod/common/api/properties/CustomPokemonProperty$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,85:1\n1855#2,2:86\n*S KotlinDebug\n*F\n+ 1 CustomPokemonProperty.kt\ncom/cobblemon/mod/common/api/properties/CustomPokemonProperty$Companion\n*L\n60#1:86,2\n*E\n"])
   public companion object {
      public final val properties: MutableList<CustomPokemonPropertyType<*>> = (new ArrayList()) as java.util.List

      public fun <T : CustomPokemonProperty> register(propertyType: CustomPokemonPropertyType<Any>) {
         properties.add(propertyType);
         this.triggerSyncAttempt();
      }

      public fun <T : CustomPokemonProperty> register(
         name: String,
         needsLabel: Boolean = true,
         fromString: (String?) -> Any?,
         examples: () -> Collection<String>
      ) {
         this.register(CollectionsKt.listOf(name), needsLabel, fromString, examples);
      }

      public fun <T : CustomPokemonProperty> register(
         aliases: Iterable<String>,
         needsLabel: Boolean = true,
         fromString: (String?) -> Any?,
         examples: () -> Collection<String>
      ) {
         properties.add(new CustomPokemonPropertyType<T>(aliases, needsLabel, fromString, examples) {
            @NotNull
            private final java.lang.Iterable<java.lang.String> keys;
            private final boolean needsKey;

            {
               this.$fromString = `$fromString`;
               this.$examples = `$examples`;
               this.keys = `$aliases`;
               this.needsKey = `$needsLabel`;
            }

            @NotNull
            @Override
            public java.lang.Iterable<java.lang.String> getKeys() {
               return this.keys;
            }

            @Override
            public boolean getNeedsKey() {
               return this.needsKey;
            }

            @Nullable
            @Override
            public T fromString(@Nullable java.lang.String value) {
               return (T)(this.$fromString.invoke(value) as CustomPokemonProperty);
            }

            @NotNull
            @Override
            public java.util.Collection<java.lang.String> examples() {
               return this.$examples.invoke() as MutableCollection<java.lang.String>;
            }
         });
         this.triggerSyncAttempt();
      }

      public fun unregister(property: CustomPokemonPropertyType<*>) {
         properties.remove(property);
      }

      private fun triggerSyncAttempt() {
         val var10000: MinecraftServer = DistributionUtilsKt.server();
         if (var10000 != null) {
            if (!var10000.m_129792_()) {
               PropertiesCompletionProvider.INSTANCE.reload();

               val `$this$forEach$iv`: java.lang.Iterable;
               for (Object element$iv : $this$forEach$iv) {
                  val player: ServerPlayer = `element$iv` as ServerPlayer;
                  val var9: PropertiesCompletionProvider = PropertiesCompletionProvider.INSTANCE;
                  var9.sync(player);
               }
            }
         }
      }
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun apply(`$this`: CustomPokemonProperty, pokemonEntity: PokemonEntity) {
         `$this`.apply(pokemonEntity.getPokemon());
      }

      @JvmStatic
      fun matches(`$this`: CustomPokemonProperty, pokemonEntity: PokemonEntity): Boolean {
         return `$this`.matches(pokemonEntity.getPokemon());
      }
   }
}
