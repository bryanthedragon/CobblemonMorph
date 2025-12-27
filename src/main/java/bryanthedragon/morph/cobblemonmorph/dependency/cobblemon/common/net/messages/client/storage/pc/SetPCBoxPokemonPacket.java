package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCBox
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.PokemonDTO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.UUID
import java.util.Map.Entry
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nSetPCBoxPokemonPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SetPCBoxPokemonPacket.kt\ncom/cobblemon/mod/common/net/messages/client/storage/pc/SetPCBoxPokemonPacket\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,58:1\n125#2:59\n152#2,3:60\n*S KotlinDebug\n*F\n+ 1 SetPCBoxPokemonPacket.kt\ncom/cobblemon/mod/common/net/messages/client/storage/pc/SetPCBoxPokemonPacket\n*L\n37#1:59\n37#1:60,3\n*E\n"])
public class SetPCBoxPokemonPacket internal constructor(storeID: UUID, boxNumber: Int, pokemon: Map<Int, PokemonDTO>) : NetworkPacket<SetPCBoxPokemonPacket> {
   public final val boxNumber: Int
   public open val id: ResourceLocation
   public final val pokemon: Map<Int, PokemonDTO>
   public final val storeID: UUID

   init {
      this.storeID = storeID;
      this.boxNumber = boxNumber;
      this.pokemon = pokemon;
      this.id = ID;
   }

   public constructor(box: PCBox)  {
      val var10001: UUID = box.getPc().getUuid();
      val `$this$map$iv`: java.util.Map = box.getNonEmptySlots();
      val var13: Int = box.getBoxNumber();
      val `destination$iv$iv`: java.util.Collection = new ArrayList(`$this$map$iv`.size());

      for (Entry item$iv$iv : $this$map$iv.entrySet()) {
         `destination$iv$iv`.add(TuplesKt.to(`item$iv$iv`.getKey(), new PokemonDTO(`item$iv$iv`.getValue() as Pokemon, true)));
      }

      this(var10001, var13, MapsKt.toMap(`destination$iv$iv` as java.util.List));
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130077_(this.storeID);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.boxNumber);
      NetExtensionsKt.writeMapK$default(buffer as ByteBuf, null, this.pokemon, (new Function1<Entry<? extends Integer, ? extends PokemonDTO>, Unit>(buffer) {
         {
            super(1);
            this.$buffer = `$buffer`;
         }

         public final void invoke(@NotNull Entry<Integer, PokemonDTO> var1) {
            val slot: Int = (var1.getKey() as java.lang.Number).intValue();
            val pokemon: PokemonDTO = var1.getValue() as PokemonDTO;
            NetExtensionsKt.writeSizedInt(this.$buffer as ByteBuf, IntSize.U_BYTE, slot);
            pokemon.encode(this.$buffer);
         }
      }) as Function1, 1, null);
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

      public fun decode(buffer: FriendlyByteBuf): SetPCBoxPokemonPacket {
         val storeID: UUID = buffer.m_130259_();
         val boxNumber: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);
         val pokemonMap: java.util.Map = new LinkedHashMap();
         NetExtensionsKt.readMapK$default(buffer as ByteBuf, null, pokemonMap, (new Function0<Pair<? extends Integer, ? extends PokemonDTO>>(buffer) {
            {
               super(0);
               this.$buffer = `$buffer`;
            }

            @NotNull
            public final Pair<Integer, PokemonDTO> invoke() {
               val var10000: Int = NetExtensionsKt.readSizedInt(this.$buffer as ByteBuf, IntSize.U_BYTE);
               val var1: PokemonDTO = new PokemonDTO();
               var1.decode(this.$buffer);
               return TuplesKt.to(var10000, var1);
            }
         }) as Function0, 1, null);
         return new SetPCBoxPokemonPacket(storeID, boxNumber, pokemonMap);
      }
   }
}
