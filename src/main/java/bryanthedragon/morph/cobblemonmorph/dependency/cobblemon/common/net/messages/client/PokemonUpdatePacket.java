package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StoreCoordinates
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientStorageManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.UUID
import kotlin.jvm.functions.Function0
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import org.jetbrains.annotations.NotNull

public abstract class PokemonUpdatePacket<T extends NetworkPacket<T>> : NetworkPacket<T> {
   public final val pokemon: () -> Pokemon

   open fun PokemonUpdatePacket(pokemon: () -> Pokemon) {
      this.pokemon = pokemon;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      var pokemon: Pokemon;
      var var4: UUID;
      label13: {
         pokemon = this.pokemon.invoke() as Pokemon;
         val var10001: StoreCoordinates = pokemon.getStoreCoordinates().get();
         if (var10001 != null) {
            val var3: PokemonStore = var10001.getStore();
            if (var3 != null) {
               var4 = var3.getUuid();
               if (var4 != null) {
                  break label13;
               }
            }
         }

         var4 = UUID.randomUUID();
      }

      buffer.m_130077_(var4);
      buffer.m_130077_(pokemon.getUuid());
      this.encodeDetails(buffer);
   }

   public abstract fun encodeDetails(buffer: FriendlyByteBuf) {
   }

   public abstract fun applyToPokemon() {
   }

   override fun sendToPlayer(player: ServerPlayer) {
      NetworkPacket.DefaultImpls.sendToPlayer(this, player);
   }

   override fun sendToPlayers(players: MutableIterable<ServerPlayer>) {
      NetworkPacket.DefaultImpls.sendToPlayers(this, players);
   }

   override fun sendToAllPlayers() {
      NetworkPacket.DefaultImpls.sendToAllPlayers(this);
   }

   override fun sendToServer() {
      NetworkPacket.DefaultImpls.sendToServer(this);
   }

   override fun sendToPlayersAround(
      x: Double, y: Double, z: Double, distance: Double, worldKey: ResourceKey<Level>, exclusionCondition: (ServerPlayer?) -> java.lang.Boolean
   ) {
      NetworkPacket.DefaultImpls.sendToPlayersAround(this, x, y, z, distance, worldKey, exclusionCondition);
   }

   override fun toBuffer(): FriendlyByteBuf {
      return NetworkPacket.DefaultImpls.toBuffer(this);
   }

   public companion object {
      public fun decodePokemon(buffer: FriendlyByteBuf): () -> Pokemon {
         val storeId: UUID = buffer.m_130259_();
         val pokemonId: UUID = buffer.m_130259_();
         return (new Function0<Pokemon>(storeId, pokemonId) {
            {
               super(0);
               this.$storeId = `$storeId`;
               this.$pokemonId = `$pokemonId`;
            }

            @NotNull
            public final Pokemon invoke() {
               val var10000: ClientStorageManager = CobblemonClient.INSTANCE.getStorage();
               val var10001: UUID = this.$storeId;
               val var10002: UUID = this.$pokemonId;
               val var1: Pokemon = var10000.locatePokemon(var10001, var10002);
               return var1;
            }
         }) as () -> Pokemon;
      }
   }
}
