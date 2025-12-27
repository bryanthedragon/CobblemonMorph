package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PasturePermissions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import java.util.ArrayList;
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

public class OpenPasturePacket(pcId: UUID,
      pastureId: UUID,
      limit: Int,
      tetheredPokemon: List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.OpenPasturePacket.PasturePokemonDataDTO>,
      permissions: PasturePermissions
   ) :
   NetworkPacket<OpenPasturePacket> {
   public open val id: ResourceLocation
   public final val limit: Int
   public final val pastureId: UUID
   public final val pcId: UUID
   public final val permissions: PasturePermissions
   public final val tetheredPokemon: List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.OpenPasturePacket.PasturePokemonDataDTO>

   init {
      this.pcId = pcId;
      this.pastureId = pastureId;
      this.limit = limit;
      this.tetheredPokemon = tetheredPokemon;
      this.permissions = permissions;
      this.id = ID;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.pcId);
      buffer.m_130077_(this.pastureId);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.limit);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.tetheredPokemon.size());

      for (OpenPasturePacket.PasturePokemonDataDTO tethered : this.tetheredPokemon) {
         tethered.encode(buffer);
      }

      this.permissions.encode(buffer);
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
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): OpenPasturePacket {
         val pcId: UUID = buffer.m_130259_();
         val pastureId: UUID = buffer.m_130259_();
         val limit: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);
         val dtos: java.util.List = new ArrayList();
         val permissions: Short = buffer.readUnsignedByte();

         for (int var7 = 0; var7 < permissions; var7++) {
            dtos.add(OpenPasturePacket.PasturePokemonDataDTO.Companion.decode(buffer));
         }

         val var10: PasturePermissions = PasturePermissions.Companion.decode(buffer);
         return new OpenPasturePacket(pcId, pastureId, limit, dtos, var10);
      }
   }

   public class PasturePokemonDataDTO(pokemonId: UUID,
      playerId: UUID,
      displayName: Component,
      species: ResourceLocation,
      aspects: Set<String>,
      heldItem: ItemStack,
      level: Int,
      entityKnown: Boolean
   ) {
      public final val aspects: Set<String>
      public final val displayName: Component
      public final val entityKnown: Boolean
      public final val heldItem: ItemStack
      public final val level: Int
      public final val playerId: UUID
      public final val pokemonId: UUID
      public final val species: ResourceLocation

      init {
         this.pokemonId = pokemonId;
         this.playerId = playerId;
         this.displayName = displayName;
         this.species = species;
         this.aspects = aspects;
         this.heldItem = heldItem;
         this.level = level;
         this.entityKnown = entityKnown;
      }

      public fun encode(buffer: FriendlyByteBuf) {
         buffer.m_130077_(this.pokemonId);
         buffer.m_130077_(this.playerId);
         buffer.m_130083_(this.displayName);
         buffer.m_130085_(this.species);
         buffer.m_236828_(this.aspects, OpenPasturePacket.PasturePokemonDataDTO::encode$lambda$0);
         buffer.m_130055_(this.heldItem);
         NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_SHORT, this.level);
         buffer.writeBoolean(this.entityKnown);
      }

      @JvmStatic
      fun `encode$lambda$0`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v: java.lang.String) {
         `$buffer`.m_130070_(v);
      }

      public companion object {
         public fun decode(buffer: FriendlyByteBuf): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.OpenPasturePacket.PasturePokemonDataDTO {
            val pokemonId: UUID = buffer.m_130259_();
            val playerId: UUID = buffer.m_130259_();
            val displayName: Component = buffer.m_130238_();
            val species: ResourceLocation = buffer.m_130281_();
            val var10000: java.util.List = buffer.m_236845_(OpenPasturePacket.PasturePokemonDataDTO.Companion::decode$lambda$0);
            val aspects: java.util.Set = CollectionsKt.toSet(var10000);
            val heldItem: ItemStack = buffer.m_130267_();
            val level: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_SHORT);
            val entityKnown: Boolean = buffer.readBoolean();
            return new OpenPasturePacket.PasturePokemonDataDTO(pokemonId, playerId, displayName, species, aspects, heldItem, level, entityKnown);
         }

         @JvmStatic
         fun `decode$lambda$0`(it: FriendlyByteBuf): java.lang.String {
            return it.m_130277_();
         }
      }
   }
}
