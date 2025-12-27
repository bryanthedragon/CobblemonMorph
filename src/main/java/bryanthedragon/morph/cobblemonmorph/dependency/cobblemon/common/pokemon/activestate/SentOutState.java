package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.google.gson.JsonObject
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

public class SentOutState : ActivePokemonState() {
   private final var dimension: ResourceKey<Level> = Level.f_46428_

   public open val entity: PokemonEntity?
      public open get() {
         val var10000: Cobblemon = Cobblemon.INSTANCE;
         val var10001: ResourceKey = this.dimension;
         val var2: Level = var10000.getLevel(var10001);
         val var1: Entity = if (var2 != null) var2.m_6815_(this.entityId) else null;
         return var1 as? PokemonEntity;
      }


   private final var entityId: Int = -1

   public constructor(entity: PokemonEntity) : this() {
      this.entityId = entity.m_19879_();
      this.dimension = entity.m_9236_().m_46472_();
   }

   public override fun getIcon(pokemon: Pokemon): ResourceLocation {
      return MiscUtilsKt.cobblemonResource("textures/gui/party/party_icon_released.png");
   }

   public open fun writeToNBT(nbt: CompoundTag): Nothing? {
      return null;
   }

   public open fun writeToJSON(json: JsonObject): Nothing? {
      return null;
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      super.writeToBuffer(buffer);
      buffer.writeInt(this.entityId);
      buffer.m_130070_(this.dimension.m_135782_().toString());
   }

   public open fun readFromBuffer(buffer: FriendlyByteBuf): SentOutState {
      super.readFromBuffer(buffer);
      this.entityId = buffer.readInt();
      this.dimension = ResourceKey.m_135785_(ResourceKey.m_135788_(this.dimension.m_135782_()), new ResourceLocation(buffer.m_130277_()));
      return this;
   }

   public fun update(entity: PokemonEntity) {
      this.entityId = entity.m_19879_();
      this.dimension = entity.m_9236_().m_46472_();
   }

   public override fun recall() {
      val var10000: PokemonEntity = this.getEntity();
      if (var10000 != null) {
         var10000.m_146870_();
      }
   }
}
