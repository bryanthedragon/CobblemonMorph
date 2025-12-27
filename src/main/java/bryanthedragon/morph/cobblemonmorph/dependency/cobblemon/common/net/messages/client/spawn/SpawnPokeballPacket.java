package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity

public class SpawnPokeballPacket(pokeBall: PokeBall, aspects: Set<String>, vanillaSpawnPacket: ClientboundAddEntityPacket) : SpawnExtraDataEntityPacket(
      vanillaSpawnPacket
   ) {
   public final val aspects: Set<String>
   public open val id: ResourceLocation
   public final val pokeBall: PokeBall

   init {
      this.pokeBall = pokeBall;
      this.aspects = aspects;
      this.id = ID;
   }

   public override fun encodeEntityData(buffer: FriendlyByteBuf) {
      buffer.m_130085_(this.pokeBall.getName());
      buffer.m_236828_(this.aspects, SpawnPokeballPacket::encodeEntityData$lambda$0);
   }

   public open fun applyData(entity: EmptyPokeBallEntity) {
      entity.setPokeBall(this.pokeBall);
      entity.setAspects(this.aspects);
   }

   public override fun checkType(entity: Entity): Boolean {
      return entity is EmptyPokeBallEntity;
   }

   @JvmStatic
   fun `encodeEntityData$lambda$0`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, aspect: java.lang.String) {
      `$buffer`.m_130070_(aspect);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): SpawnPokeballPacket {
         val var10000: PokeBalls = PokeBalls.INSTANCE;
         val var10001: ResourceLocation = buffer.m_130281_();
         val var5: PokeBall = var10000.getPokeBall(var10001);
         val var6: java.util.List = buffer.m_236845_(SpawnPokeballPacket.Companion::decode$lambda$0);
         return new SpawnPokeballPacket(var5, CollectionsKt.toSet(var6), SpawnExtraDataEntityPacket.Companion.decodeVanillaPacket(buffer));
      }

      @JvmStatic
      fun `decode$lambda$0`(it: FriendlyByteBuf): java.lang.String {
         return it.m_130277_();
      }
   }
}
