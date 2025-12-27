package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.ShoulderEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CompoundTagExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt
import com.google.gson.JsonObject
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

@SourceDebugExtension(["SMAP\nPokemonState.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonState.kt\ncom/cobblemon/mod/common/pokemon/activestate/ShoulderedState\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,202:1\n1#2:203\n1855#3,2:204\n*S KotlinDebug\n*F\n+ 1 PokemonState.kt\ncom/cobblemon/mod/common/pokemon/activestate/ShoulderedState\n*L\n193#1:204,2\n*E\n"])
public class ShoulderedState : ActivePokemonState() {
   public open val entity: PokemonEntity?
   public final var isLeftShoulder: Boolean
   public final lateinit var playerUUID: UUID
   public final lateinit var pokemonUUID: UUID
   public final var stateId: UUID = UUID.randomUUID()

   public constructor(playerUUID: UUID, isLeftShoulder: Boolean, pokemonUUID: UUID) : this() {
      this.isLeftShoulder = isLeftShoulder;
      this.setPlayerUUID(playerUUID);
      this.setPokemonUUID(pokemonUUID);
   }

   public override fun getIcon(pokemon: Pokemon): ResourceLocation {
      return MiscUtilsKt.cobblemonResource("textures/gui/party/party_icon_shoulder_${if (this.isLeftShoulder) "left" else "right"}.png");
   }

   public override fun writeToNBT(nbt: CompoundTag): CompoundTag {
      super.writeToNBT(nbt);
      nbt.m_128379_("StateShoulder", this.isLeftShoulder);
      nbt.m_128362_("PlayerUUID", this.getPlayerUUID());
      nbt.m_128362_("StateId", this.stateId);
      nbt.m_128362_("PokemonUUID", this.getPokemonUUID());
      return nbt;
   }

   public override fun readFromNBT(nbt: CompoundTag): PokemonState {
      super.readFromNBT(nbt);
      this.isLeftShoulder = nbt.m_128471_("StateShoulder");
      var var10001: UUID = nbt.m_128342_("PlayerUUID");
      this.setPlayerUUID(var10001);
      this.stateId = nbt.m_128342_("StateId");
      var10001 = nbt.m_128342_("PokemonUUID");
      this.setPokemonUUID(var10001);
      return this;
   }

   public override fun writeToJSON(json: JsonObject): JsonObject? {
      super.writeToJSON(json);
      json.addProperty("StateShoulder", this.isLeftShoulder);
      json.addProperty("PlayerUUID", this.getPlayerUUID().toString());
      json.addProperty("StateId", this.stateId.toString());
      json.addProperty("PokemonUUID", this.getPokemonUUID().toString());
      return json;
   }

   public override fun readFromJSON(json: JsonObject): PokemonState {
      super.readFromJSON(json);
      this.isLeftShoulder = json.get("StateShoulder").getAsBoolean();
      var var10001: UUID = UUID.fromString(json.get("PlayerUUID").getAsString());
      this.setPlayerUUID(var10001);
      this.stateId = UUID.fromString(json.get("StateId").getAsString());
      var10001 = UUID.fromString(json.get("PokemonUUID").getAsString());
      this.setPokemonUUID(var10001);
      return this;
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      super.writeToBuffer(buffer);
      buffer.writeBoolean(this.isLeftShoulder);
      buffer.m_130077_(this.getPlayerUUID());
      buffer.m_130077_(this.stateId);
      buffer.m_130077_(this.getPokemonUUID());
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf): PokemonState {
      super.readFromBuffer(buffer);
      this.isLeftShoulder = buffer.readBoolean();
      var var10001: UUID = buffer.m_130259_();
      this.setPlayerUUID(var10001);
      this.stateId = buffer.m_130259_();
      var10001 = buffer.m_130259_();
      this.setPokemonUUID(var10001);
      return this;
   }

   public override fun recall() {
      val var10000: ServerPlayer = PlayerExtensionsKt.getPlayer(this.getPlayerUUID());
      if (var10000 != null) {
         val nbt: CompoundTag = if (this.isLeftShoulder) var10000.m_36331_() else var10000.m_36332_();
         if (this.isShoulderedPokemon(nbt)) {
            val var3: Level = var10000.m_9236_();
            val var10001: Vec3 = var10000.m_20182_();
            val var10002: SoundEvent = SoundEvents.f_144099_;
            WorldExtensionsKt.playSoundServer$default(var3, var10001, var10002, null, 0.0F, 0.0F, 28, null);
            if (this.isLeftShoulder) {
               var10000.m_36362_(new CompoundTag());
            } else {
               var10000.m_36364_(new CompoundTag());
            }

            this.removeShoulderEffects(var10000);
         }
      }
   }

   private fun removeShoulderEffects(player: ServerPlayer) {
      val `$this$forEach$iv`: java.util.Iterator = PlayerExtensionsKt.party(player).iterator();

      var var10000: Any;
      while (true) {
         if (`$this$forEach$iv`.hasNext()) {
            val `$i$f$forEach`: Any = `$this$forEach$iv`.next();
            if (!((`$i$f$forEach` as Pokemon).getUuid() == this.getPokemonUUID())) {
               continue;
            }

            var10000 = `$i$f$forEach`;
            break;
         }

         var10000 = null;
         break;
      }

      val partyPokemon: Pokemon = var10000 as Pokemon;
      if (var10000 as Pokemon != null) {
         var10000 = partyPokemon.getForm();
         if (var10000 != null && ((FormData)var10000).getShoulderEffects() != null) {
            for (Object element$iv : var10) {
               (var13 as ShoulderEffect).removeEffect(partyPokemon, player, this.isLeftShoulder);
            }
         }
      }
   }

   private fun isShoulderedPokemon(nbt: CompoundTag): Boolean {
      return CompoundTagExtensionsKt.isPokemonEntity(nbt) && nbt.m_128469_("Pokemon").m_128469_("State").m_128342_("StateId") == this.stateId;
   }

   public fun isStillShouldered(player: ServerPlayer): Boolean {
      val var10001: CompoundTag = if (this.isLeftShoulder) player.m_36331_() else player.m_36332_();
      return this.isShoulderedPokemon(var10001);
   }
}
