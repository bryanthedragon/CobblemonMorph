package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nSpawnPokemonPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnPokemonPacket.kt\ncom/cobblemon/mod/common/net/messages/client/spawn/SpawnPokemonPacket\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,138:1\n1#2:139\n*E\n"])
public class SpawnPokemonPacket(ownerId: UUID?,
   scaleModifier: Float,
   species: Species,
   form: FormData,
   aspects: Set<String>,
   battleId: UUID?,
   phasingTargetId: Int,
   beamMode: Byte,
   nickname: MutableComponent?,
   labelLevel: Int,
   poseType: PoseType,
   unbattlable: Boolean,
   hideLabel: Boolean,
   caughtBall: ResourceLocation,
   spawnYaw: Float,
   friendship: Int,
   vanillaSpawnPacket: ClientboundAddEntityPacket
) : SpawnExtraDataEntityPacket(vanillaSpawnPacket) {
   private final val aspects: Set<String>
   private final val battleId: UUID?
   private final val beamMode: Byte
   private final val caughtBall: ResourceLocation
   private final val form: FormData
   private final val friendship: Int
   private final val hideLabel: Boolean
   public open val id: ResourceLocation
   private final val labelLevel: Int
   private final val nickname: MutableComponent?
   private final val ownerId: UUID?
   private final val phasingTargetId: Int
   private final val poseType: PoseType
   private final val scaleModifier: Float
   private final val spawnYaw: Float
   private final val species: Species
   private final val unbattlable: Boolean

   init {
      this.ownerId = ownerId;
      this.scaleModifier = scaleModifier;
      this.species = species;
      this.form = form;
      this.aspects = aspects;
      this.battleId = battleId;
      this.phasingTargetId = phasingTargetId;
      this.beamMode = beamMode;
      this.nickname = nickname;
      this.labelLevel = labelLevel;
      this.poseType = poseType;
      this.unbattlable = unbattlable;
      this.hideLabel = hideLabel;
      this.caughtBall = caughtBall;
      this.spawnYaw = spawnYaw;
      this.friendship = friendship;
      this.id = ID;
   }

   public constructor(entity: PokemonEntity, vanillaSpawnPacket: ClientboundAddEntityPacket)  {
      val var10001: UUID = entity.m_21805_();
      val var10002: Float = entity.getPokemon().getScaleModifier();
      val var10003: Species = entity.getExposedSpecies();
      val var10004: FormData = entity.getPokemon().getForm();
      val var10005: java.util.Set = entity.getPokemon().getAspects();
      val var10006: UUID = entity.getBattleId();
      val var10007: Int = entity.getPhasingTargetId();
      val var10008: Byte = (byte)entity.getBeamMode();
      val var10009: MutableComponent = entity.getPokemon().getNickname();
      val var10010: Int = if (Cobblemon.INSTANCE.getConfig().getDisplayEntityLevelLabel())
         entity.m_20088_().m_135370_(PokemonEntity.Companion.getLABEL_LEVEL()) as Int
         else
         -1;
      val var3: Int = var10010.intValue();
      var var10011: Any = entity.m_20088_().m_135370_(PokemonEntity.Companion.getPOSE_TYPE());
      var10011 = var10011 as PoseType;
      val var10012: Any = entity.m_20088_().m_135370_(PokemonEntity.Companion.getUNBATTLEABLE());
      val var5: Boolean = var10012 as java.lang.Boolean;
      val var10013: Any = entity.m_20088_().m_135370_(PokemonEntity.Companion.getHIDE_LABEL());
      val var6: Boolean = var10013 as java.lang.Boolean;
      val var10014: ResourceLocation = entity.getPokemon().getCaughtBall().getName();
      val var10015: Any = entity.m_20088_().m_135370_(PokemonEntity.Companion.getSPAWN_DIRECTION());
      val var7: Float = (var10015 as java.lang.Number).floatValue();
      val var10016: Any = entity.m_20088_().m_135370_(PokemonEntity.Companion.getFRIENDSHIP());
      this(
         var10001,
         var10002,
         var10003,
         var10004,
         var10005,
         var10006,
         var10007,
         var10008,
         var10009,
         var3,
         (PoseType)var10011,
         var5,
         var6,
         var10014,
         var7,
         (var10016 as java.lang.Number).intValue(),
         vanillaSpawnPacket
      );
   }

   public override fun encodeEntityData(buffer: FriendlyByteBuf) {
      buffer.m_236821_(this.ownerId, SpawnPokemonPacket::encodeEntityData$lambda$0);
      buffer.writeFloat(this.scaleModifier);
      buffer.m_130085_(this.species.getResourceIdentifier());
      buffer.m_130070_(this.form.formOnlyShowdownId());
      buffer.m_236828_(this.aspects, SpawnPokemonPacket::encodeEntityData$lambda$1);
      buffer.m_236821_(this.battleId, SpawnPokemonPacket::encodeEntityData$lambda$2);
      buffer.writeInt(this.phasingTargetId);
      buffer.writeByte(this.beamMode);
      buffer.m_236821_(this.nickname, SpawnPokemonPacket::encodeEntityData$lambda$3);
      buffer.writeInt(this.labelLevel);
      buffer.m_130068_(this.poseType);
      buffer.writeBoolean(this.unbattlable);
      buffer.writeBoolean(this.hideLabel);
      buffer.m_130085_(this.caughtBall);
      buffer.writeFloat(this.spawnYaw);
      buffer.writeInt(this.friendship);
   }

   public open fun applyData(entity: PokemonEntity) {
      entity.m_21816_(this.ownerId);
      val var2: Pokemon = entity.getPokemon();
      var2.setScaleModifier(this.scaleModifier);
      var2.setSpecies(this.species);
      var2.setForm(this.form);
      var2.setAspects(this.aspects);
      var2.setNickname(this.nickname);
      val var10000: PokeBall = PokeBalls.INSTANCE.getPokeBall(this.caughtBall);
      if (var10000 != null) {
         var2.setCaughtBall(var10000);
      }

      entity.setPhasingTargetId(this.phasingTargetId);
      entity.setBeamMode(this.beamMode);
      entity.setBattleId(this.battleId);
      entity.m_20088_().m_135381_(PokemonEntity.Companion.getLABEL_LEVEL(), this.labelLevel);
      entity.m_20088_().m_135381_(PokemonEntity.Companion.getSPECIES(), entity.getPokemon().getSpecies().getResourceIdentifier().toString());
      entity.m_20088_().m_135381_(PokemonEntity.Companion.getASPECTS(), this.aspects);
      entity.m_20088_().m_135381_(PokemonEntity.Companion.getPOSE_TYPE(), this.poseType);
      entity.m_20088_().m_135381_(PokemonEntity.Companion.getUNBATTLEABLE(), this.unbattlable);
      entity.m_20088_().m_135381_(PokemonEntity.Companion.getHIDE_LABEL(), this.hideLabel);
      entity.m_20088_().m_135381_(PokemonEntity.Companion.getSPAWN_DIRECTION(), this.spawnYaw);
      entity.m_20088_().m_135381_(PokemonEntity.Companion.getFRIENDSHIP(), this.friendship);
   }

   public override fun checkType(entity: Entity): Boolean {
      return entity is PokemonEntity;
   }

   @JvmStatic
   fun `encodeEntityData$lambda$0`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v: UUID) {
      `$buffer`.m_130077_(v);
   }

   @JvmStatic
   fun `encodeEntityData$lambda$1`(pb: FriendlyByteBuf, value: java.lang.String) {
      pb.m_130070_(value);
   }

   @JvmStatic
   fun `encodeEntityData$lambda$2`(pb: FriendlyByteBuf, value: UUID) {
      pb.m_130077_(value);
   }

   @JvmStatic
   fun `encodeEntityData$lambda$3`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v: MutableComponent) {
      `$buffer`.m_130083_(v as Component);
   }

   @SourceDebugExtension(["SMAP\nSpawnPokemonPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnPokemonPacket.kt\ncom/cobblemon/mod/common/net/messages/client/spawn/SpawnPokemonPacket$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,138:1\n288#2,2:139\n*S KotlinDebug\n*F\n+ 1 SpawnPokemonPacket.kt\ncom/cobblemon/mod/common/net/messages/client/spawn/SpawnPokemonPacket$Companion\n*L\n119#1:139,2\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): SpawnPokemonPacket {
         val ownerId: UUID = buffer.m_236868_(SpawnPokemonPacket.Companion::decode$lambda$0) as UUID;
         val scaleModifier: Float = buffer.readFloat();
         val var10000: PokemonSpecies = PokemonSpecies.INSTANCE;
         val var10001: ResourceLocation = buffer.m_130281_();
         val var26: Species = var10000.getByIdentifier(var10001);
         val showdownId: java.lang.String = buffer.m_130277_();
         val beamModeEmitter: java.util.Iterator = var26.getForms().iterator();

         while (true) {
            if (beamModeEmitter.hasNext()) {
               val nickname: Any = beamModeEmitter.next();
               if (!((nickname as FormData).formOnlyShowdownId() == showdownId)) {
                  continue;
               }

               var27 = nickname;
               break;
            }

            var27 = null;
            break;
         }

         var var28: FormData = var27 as FormData;
         if (var27 as FormData == null) {
            var28 = var26.getStandardForm();
         }

         val var29: java.util.List = buffer.m_236845_(FriendlyByteBuf::m_130277_);
         val aspects: java.util.Set = CollectionsKt.toSet(var29);
         val var20: UUID = buffer.m_236868_(SpawnPokemonPacket.Companion::decode$lambda$2) as UUID;
         val var21: Int = buffer.readInt();
         val var22: Byte = buffer.readByte();
         val var23: MutableComponent = buffer.m_236868_(SpawnPokemonPacket.Companion::decode$lambda$3) as MutableComponent;
         val var24: Int = buffer.readInt();
         val var25: PoseType = buffer.m_130066_(PoseType.class) as PoseType;
         val unbattlable: Boolean = buffer.readBoolean();
         val hideLabel: Boolean = buffer.readBoolean();
         val caughtBall: ResourceLocation = buffer.m_130281_();
         val spawnAngle: Float = buffer.readFloat();
         val friendship: Int = buffer.readInt();
         val vanillaPacket: ClientboundAddEntityPacket = SpawnExtraDataEntityPacket.Companion.decodeVanillaPacket(buffer);
         return new SpawnPokemonPacket(
            ownerId,
            scaleModifier,
            var26,
            var28,
            aspects,
            var20,
            var21,
            var22,
            var23,
            var24,
            var25,
            unbattlable,
            hideLabel,
            caughtBall,
            spawnAngle,
            friendship,
            vanillaPacket
         );
      }

      @JvmStatic
      fun `decode$lambda$0`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): UUID {
         return `$buffer`.m_130259_();
      }

      @JvmStatic
      fun `decode$lambda$2`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): UUID {
         return `$buffer`.m_130259_();
      }

      @JvmStatic
      fun `decode$lambda$3`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): MutableComponent {
         return `$buffer`.m_130238_().m_6881_();
      }
   }
}
