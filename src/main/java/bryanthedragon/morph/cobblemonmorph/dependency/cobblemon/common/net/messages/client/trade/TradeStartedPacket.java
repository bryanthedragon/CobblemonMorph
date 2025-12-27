package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

public class TradeStartedPacket(traderId: UUID,
      traderName: MutableComponent,
      traderParty: List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeStartedPacket.TradeablePokemon?>
   ) :
   NetworkPacket<TradeStartedPacket> {
   public open val id: ResourceLocation
   public final val traderId: UUID
   public final val traderName: MutableComponent
   public final val traderParty: List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeStartedPacket.TradeablePokemon?>

   init {
      this.traderId = traderId;
      this.traderName = traderName;
      this.traderParty = traderParty;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.traderId);
      buffer.m_130083_(this.traderName as Component);
      buffer.m_236828_(this.traderParty, TradeStartedPacket::encode$lambda$1);
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

   @JvmStatic
   fun `encode$lambda$1$lambda$0`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v2: TradeStartedPacket.TradeablePokemon) {
      v2.encode(`$buffer`);
   }

   @JvmStatic
   fun `encode$lambda$1`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v: TradeStartedPacket.TradeablePokemon) {
      `$buffer`.m_236821_(v, TradeStartedPacket::encode$lambda$1$lambda$0);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): TradeStartedPacket {
         val var10002: UUID = buffer.m_130259_();
         val var10003: MutableComponent = buffer.m_130238_().m_6881_();
         val var10004: java.util.List = buffer.m_236845_(TradeStartedPacket.Companion::decode$lambda$1);
         return new TradeStartedPacket(var10002, var10003, var10004);
      }

      @JvmStatic
      fun `decode$lambda$1$lambda$0`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): TradeStartedPacket.TradeablePokemon {
         return TradeStartedPacket.TradeablePokemon.Companion.decode(`$buffer`);
      }

      @JvmStatic
      fun `decode$lambda$1`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): TradeStartedPacket.TradeablePokemon {
         return `$buffer`.m_236868_(TradeStartedPacket.Companion::decode$lambda$1$lambda$0) as TradeStartedPacket.TradeablePokemon;
      }
   }

   public class TradeablePokemon(pokemonId: UUID,
      species: ResourceLocation,
      aspects: Set<String>,
      level: Int,
      gender: Gender,
      heldItem: ItemStack,
      tradeable: Boolean
   ) {
      public final val aspects: Set<String>
      public final val gender: Gender
      public final val heldItem: ItemStack
      public final val level: Int
      public final val pokemonId: UUID
      public final val species: ResourceLocation
      public final val tradeable: Boolean

      init {
         this.pokemonId = pokemonId;
         this.species = species;
         this.aspects = aspects;
         this.level = level;
         this.gender = gender;
         this.heldItem = heldItem;
         this.tradeable = tradeable;
      }

      public constructor(pokemon: Pokemon)  {
         val var10001: UUID = pokemon.getUuid();
         val var10002: ResourceLocation = pokemon.getSpecies().getResourceIdentifier();
         val var10003: java.util.Set = pokemon.getAspects();
         val var10004: Int = pokemon.getLevel();
         val var10005: Gender = pokemon.getGender();
         val var10006: ItemStack = pokemon.heldItem().m_41777_();
         this(var10001, var10002, var10003, var10004, var10005, var10006, pokemon.getTradeable());
      }

      public fun encode(buffer: FriendlyByteBuf) {
         buffer.m_130077_(this.pokemonId);
         buffer.m_130085_(this.species);
         buffer.m_236828_(this.aspects, TradeStartedPacket.TradeablePokemon::encode$lambda$0);
         NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_SHORT, this.level);
         NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.gender.ordinal());
         buffer.m_130055_(this.heldItem);
         buffer.writeBoolean(this.tradeable);
      }

      public fun asRenderablePokemon(): RenderablePokemon {
         val var10002: Species = PokemonSpecies.INSTANCE.getByIdentifier(this.species);
         return new RenderablePokemon(var10002, this.aspects);
      }

      @JvmStatic
      fun `encode$lambda$0`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v: java.lang.String) {
         `$buffer`.m_130070_(v);
      }

      public companion object {
         public fun decode(buffer: FriendlyByteBuf): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeStartedPacket.TradeablePokemon {
            val var10002: UUID = buffer.m_130259_();
            val var10003: ResourceLocation = buffer.m_130281_();
            val var10004: java.util.List = buffer.m_236845_(TradeStartedPacket.TradeablePokemon.Companion::decode$lambda$0);
            val var2: java.util.Set = CollectionsKt.toSet(var10004);
            val var10005: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_SHORT);
            val var10006: Gender = Gender.values()[NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE)];
            val var10007: ItemStack = buffer.m_130267_();
            return new TradeStartedPacket.TradeablePokemon(var10002, var10003, var2, var10005, var10006, var10007, buffer.readBoolean());
         }

         @JvmStatic
         fun `decode$lambda$0`(it: FriendlyByteBuf): java.lang.String {
            return it.m_130277_();
         }
      }
   }
}
