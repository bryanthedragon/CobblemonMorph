package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.BenchedMoves
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveSet
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.Natures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeatureProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.EVs
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.OriginalTrainerType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.PokemonState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import java.util.ArrayList;
import java.util.LinkedHashSet
import java.util.UUID
import java.util.Map.Entry
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

@SourceDebugExtension(["SMAP\nPokemonDTO.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonDTO.kt\ncom/cobblemon/mod/common/net/messages/PokemonDTO\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,299:1\n800#2,11:300\n766#2:311\n857#2,2:312\n1855#2,2:314\n1855#2,2:317\n1855#2,2:319\n800#2,11:321\n1#3:316\n*S KotlinDebug\n*F\n+ 1 PokemonDTO.kt\ncom/cobblemon/mod/common/net/messages/PokemonDTO\n*L\n119#1:300,11\n120#1:311\n120#1:312,2\n153#1:314,2\n230#1:317,2\n233#1:319,2\n271#1:321,11\n*E\n"])
public class PokemonDTO : Encodable, Decodable {
   public final var ability: String = ""
   public final var aspects: Set<String> = SetsKt.emptySet()
   public final var benchedMoves: BenchedMoves = new BenchedMoves()
   public final lateinit var caughtBall: ResourceLocation
   public final var currentHealth: Int
   public final var dmaxLevel: Int
   public final lateinit var evolutionBuffer: FriendlyByteBuf
   public final var evs: EVs = new EVs()
   public final var experience: Int = 1
   public final lateinit var featuresBuffer: FriendlyByteBuf
   public final var form: String = ""
   public final var friendship: Int
   public final var gender: Gender = Gender.MALE
   public final var gmaxFactor: Boolean
   public final var heldItem: ItemStack
   public final var ivs: IVs = new IVs()
   public final var level: Int = 1
   public final var mintNature: ResourceLocation?
   public final var moveSet: MoveSet = new MoveSet()
   public final lateinit var nature: ResourceLocation
   public final var nickname: MutableComponent?
   public final var originalTrainer: String?
   public final var originalTrainerName: String?
   public final var originalTrainerType: OriginalTrainerType
   public final var scaleModifier: Float
   public final var shiny: Boolean
   public final lateinit var species: ResourceLocation
   public final lateinit var state: PokemonState
   public final var status: ResourceLocation?
   public final var teraType: String
   public final var tetheringId: UUID?
   public final var toClient: Boolean
   public final var tradeable: Boolean
   public final var uuid: UUID = UUID.randomUUID()

   public constructor()  {
      val var10001: ItemStack = ItemStack.f_41583_;
      this.heldItem = var10001;
      this.teraType = "";
      this.tradeable = true;
      this.originalTrainerType = OriginalTrainerType.NONE;
   }

   public constructor(pokemon: Pokemon, toClient: Boolean) : super() {
      var var22: ResourceLocation;
      label48: {
         this.uuid = UUID.randomUUID();
         this.form = "";
         this.level = 1;
         this.experience = 1;
         this.gender = Gender.MALE;
         this.ivs = new IVs();
         this.evs = new EVs();
         this.moveSet = new MoveSet();
         this.ability = "";
         this.benchedMoves = new BenchedMoves();
         this.aspects = SetsKt.emptySet();
         val var10001: ItemStack = ItemStack.f_41583_;
         this.heldItem = var10001;
         this.teraType = "";
         this.tradeable = true;
         this.originalTrainerType = OriginalTrainerType.NONE;
         this.toClient = toClient;
         this.uuid = pokemon.getUuid();
         this.setSpecies(pokemon.getSpecies().getResourceIdentifier());
         this.nickname = pokemon.getNickname();
         this.form = pokemon.getForm().getName();
         this.level = pokemon.getLevel();
         this.experience = pokemon.getExperience();
         this.friendship = pokemon.getFriendship();
         this.currentHealth = pokemon.getCurrentHealth();
         this.gender = pokemon.getGender();
         this.ivs = pokemon.getIvs();
         this.evs = pokemon.getEvs();
         this.moveSet = pokemon.getMoveSet();
         this.scaleModifier = pokemon.getScaleModifier();
         this.ability = pokemon.getAbility().getName();
         this.shiny = pokemon.getShiny();
         val var20: PersistentStatusContainer = pokemon.getStatus();
         if (var20 != null) {
            val var21: PersistentStatus = var20.getStatus();
            if (var21 != null) {
               var22 = var21.getName();
               break label48;
            }
         }

         var22 = null;
      }

      this.status = var22;
      this.setState(pokemon.getState());
      this.setCaughtBall(pokemon.getCaughtBall().getName());
      this.benchedMoves = pokemon.getBenchedMoves();
      this.aspects = pokemon.getAspects();
      this.setEvolutionBuffer(new FriendlyByteBuf(Unpooled.buffer()));
      pokemon.getEvolutionProxy().saveToBuffer(this.getEvolutionBuffer(), toClient);
      this.setNature(pokemon.getNature().getName());
      val var23: Nature = pokemon.getMintedNature();
      this.mintNature = if (var23 != null) var23.getName() else null;
      this.heldItem = pokemon.heldItemNoCopy$common();
      this.tetheringId = pokemon.getTetheringId();
      val var24: java.lang.String = pokemon.getTeraType().getId().toString();
      this.teraType = var24;
      this.dmaxLevel = pokemon.getDmaxLevel();
      this.gmaxFactor = pokemon.getGmaxFactor();
      this.tradeable = pokemon.getTradeable();
      this.setFeaturesBuffer(new FriendlyByteBuf(Unpooled.buffer()));
      var `$this$filter$iv`: java.lang.Iterable = pokemon.getFeatures();
      var `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$filter$iv) {
         if (`element$iv$iv` is SynchronizedSpeciesFeature) {
            `destination$iv$iv`.add(`element$iv$iv`);
         }
      }

      `$this$filter$iv` = `destination$iv$iv` as java.util.List;
      `destination$iv$iv` = new ArrayList();

      for (Object element$iv$ivx : $this$filter$iv) {
         val var13: SpeciesFeatureProvider = SpeciesFeatures.INSTANCE.getFeature((`element$iv$ivx` as SynchronizedSpeciesFeature).getName());
         if ((var13 as? SynchronizedSpeciesFeatureProvider) != null && (var13 as? SynchronizedSpeciesFeatureProvider).getVisible()) {
            `destination$iv$iv`.add(`element$iv$ivx`);
         }
      }

      this.getFeaturesBuffer().m_236828_(`destination$iv$iv` as java.util.List, PokemonDTO::_init_$lambda$1);
      this.originalTrainerType = pokemon.getOriginalTrainerType();
      this.originalTrainer = pokemon.getOriginalTrainer();
      this.originalTrainerName = pokemon.getOriginalTrainerName();
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.writeBoolean(this.toClient);
      buffer.m_130077_(this.uuid);
      buffer.m_130085_(this.getSpecies());
      buffer.m_236821_(this.nickname, PokemonDTO::encode$lambda$2);
      buffer.m_130070_(this.form);
      buffer.writeInt(this.experience);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_SHORT, this.level);
      buffer.writeShort(this.friendship);
      buffer.writeShort(this.currentHealth);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.gender.ordinal());
      this.ivs.saveToBuffer(buffer);
      this.evs.saveToBuffer(buffer);
      this.moveSet.saveToBuffer(buffer);
      buffer.writeFloat(this.scaleModifier);
      buffer.m_130070_(this.ability);
      buffer.writeBoolean(this.shiny);
      this.getState().writeToBuffer(buffer);
      buffer.m_236821_(this.status, PokemonDTO::encode$lambda$3);
      buffer.m_130085_(this.getCaughtBall());
      this.benchedMoves.saveToBuffer(buffer);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.aspects.size());

      val byteCount: java.lang.Iterable;
      for (Object element$iv : byteCount) {
         buffer.m_130070_(`element$iv` as java.lang.String);
      }

      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_SHORT, this.getEvolutionBuffer().readableBytes());
      buffer.writeBytes(this.getEvolutionBuffer() as ByteBuf);
      this.getEvolutionBuffer().release();
      buffer.m_130085_(this.getNature());
      buffer.m_236821_(this.mintNature, PokemonDTO::encode$lambda$5);
      buffer.m_130055_(this.heldItem);
      buffer.m_236821_(this.tetheringId, PokemonDTO::encode$lambda$6);
      buffer.m_130070_(this.teraType);
      buffer.writeInt(this.dmaxLevel);
      buffer.writeBoolean(this.gmaxFactor);
      buffer.writeBoolean(this.tradeable);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_SHORT, this.getFeaturesBuffer().readableBytes());
      buffer.writeBytes(this.getFeaturesBuffer() as ByteBuf);
      this.getFeaturesBuffer().release();
      buffer.m_130070_(this.originalTrainerType.name());
      buffer.m_236821_(this.originalTrainer, PokemonDTO::encode$lambda$7);
      buffer.m_236821_(this.originalTrainerName, PokemonDTO::encode$lambda$8);
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      this.toClient = buffer.readBoolean();
      this.uuid = buffer.m_130259_();
      var var10001: ResourceLocation = buffer.m_130281_();
      this.setSpecies(var10001);
      this.nickname = buffer.m_236868_(PokemonDTO::decode$lambda$9) as MutableComponent;
      val var9: java.lang.String = buffer.m_130277_();
      this.form = var9;
      this.experience = buffer.readInt();
      this.level = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_SHORT);
      this.friendship = buffer.readUnsignedShort();
      this.currentHealth = buffer.readUnsignedShort();
      this.gender = Gender.values()[NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE)];
      this.ivs.loadFromBuffer(buffer);
      this.evs.loadFromBuffer(buffer);
      this.moveSet.loadFromBuffer(buffer);
      this.scaleModifier = buffer.readFloat();
      val var10: java.lang.String = buffer.m_130277_();
      this.ability = var10;
      this.shiny = buffer.readBoolean();
      this.setState(PokemonState.Companion.fromBuffer(buffer));
      this.status = buffer.m_236868_(PokemonDTO::decode$lambda$10) as ResourceLocation;
      var10001 = buffer.m_130281_();
      this.setCaughtBall(var10001);
      this.benchedMoves.loadFromBuffer(buffer);
      val aspects: java.util.Set = new LinkedHashSet();
      val bytesToRead: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);

      for (int featureBytesToRead = 0; featureBytesToRead < bytesToRead; featureBytesToRead++) {
         val var12: java.lang.String = buffer.m_130277_();
         aspects.add(var12);
      }

      this.aspects = aspects;
      this.setEvolutionBuffer(new FriendlyByteBuf(buffer.readBytes(NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_SHORT))));
      var10001 = buffer.m_130281_();
      this.setNature(var10001);
      this.mintNature = buffer.m_236868_(PokemonDTO::decode$lambda$12) as ResourceLocation;
      val var14: ItemStack = buffer.m_130267_();
      this.heldItem = var14;
      this.tetheringId = buffer.m_236868_(PokemonDTO::decode$lambda$13) as UUID;
      val var15: java.lang.String = buffer.m_130277_();
      this.teraType = var15;
      this.dmaxLevel = buffer.readInt();
      this.gmaxFactor = buffer.readBoolean();
      this.tradeable = buffer.readBoolean();
      this.setFeaturesBuffer(new FriendlyByteBuf(buffer.readBytes(NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_SHORT))));
      val var16: java.lang.String = buffer.m_130277_();
      this.originalTrainerType = OriginalTrainerType.valueOf(var16);
      this.originalTrainer = buffer.m_236868_(PokemonDTO::decode$lambda$14) as java.lang.String;
      this.originalTrainerName = buffer.m_236868_(PokemonDTO::decode$lambda$15) as java.lang.String;
   }

   public fun create(): Pokemon {
      val var1: Pokemon = new Pokemon();
      val it: Pokemon = var1;
      var1.setClient$common(this.toClient);
      var1.setUuid(this.uuid);
      val var10001: Species = PokemonSpecies.INSTANCE.getByIdentifier(this.getSpecies());
      var1.setSpecies(var10001);
      var1.setNickname(this.nickname);
      val var7: java.util.Iterator = var1.getSpecies().getForms().iterator();

      var var10000: Any;
      while (true) {
         if (var7.hasNext()) {
            val ot: Any = var7.next();
            if (!((ot as FormData).getName() == this.form)) {
               continue;
            }

            var10000 = (Pokemon)ot;
            break;
         }

         var10000 = null;
         break;
      }

      var var57: FormData = var10000 as FormData;
      if (var10000 as FormData == null) {
         var57 = var1.getSpecies().getStandardForm();
      }

      var1.setForm(var57);
      var1.setExperience$common(this.experience);
      var1.setLevel(this.level);
      Pokemon.setFriendship$default(var1, this.friendship, false, 2, null);
      var1.setGender(this.gender);

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         it.setIV((var26 as Entry).getKey() as Stat, ((var26 as Entry).getValue() as java.lang.Number).intValue());
      }

      for (Object element$iv : $this$forEach$iv) {
         it.setEV((var27 as Entry).getKey() as Stat, ((var27 as Entry).getValue() as java.lang.Number).intValue());
      }

      it.setCurrentHealth(this.currentHealth);
      it.getMoveSet().clear();

      for (Move move : this.moveSet) {
         it.getMoveSet().add(var21);
      }

      it.setScaleModifier(this.scaleModifier);
      it.setAbility$common(AbilityTemplate.create$default(Abilities.INSTANCE.getOrException(this.ability), false, 1, null));
      it.setShiny(this.shiny);
      it.setState(this.getState());
      var10000 = it;
      val var58: PersistentStatusContainer;
      if (this.status != null) {
         val var33: Status = Statuses.INSTANCE.getStatus(this.status);
         var58 = if (var33 is PersistentStatus) new PersistentStatusContainer(var33 as PersistentStatus, 0) else null;
         var10000 = it;
      } else {
         var58 = null;
      }

      var10000.setStatus(var58);
      val var59: PokeBall = PokeBalls.INSTANCE.getPokeBall(this.getCaughtBall());
      it.setCaughtBall(var59);
      it.getBenchedMoves().addAll(this.benchedMoves);
      it.setAspects(this.aspects);
      it.getEvolutionProxy().loadFromBuffer(this.getEvolutionBuffer());
      this.getEvolutionBuffer().release();
      val var60: Nature = Natures.INSTANCE.getNature(this.getNature());
      it.setNature(var60);
      var10000 = it;
      val var61: Nature;
      if (this.mintNature != null) {
         val var54: Nature = Natures.INSTANCE.getNature(this.mintNature);
         var61 = var54;
         var10000 = it;
      } else {
         var61 = null;
      }

      var10000.setMintedNature(var61);
      it.swapHeldItem(this.heldItem, false);
      it.setTetheringId(this.tetheringId);
      val var62: TeraType = TeraTypes.get(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.teraType, null, 1, null));
      it.setTeraType(var62);
      it.setDmaxLevel(this.dmaxLevel);
      it.setGmaxFactor(this.gmaxFactor);
      it.setTradeable(this.tradeable);
      val var47: Int = NetExtensionsKt.readSizedInt(this.getFeaturesBuffer() as ByteBuf, IntSize.U_BYTE);

      for (int var22 = 0; var22 < var47; var22++) {
         val var55: Species = PokemonSpecies.INSTANCE.getByIdentifier(this.getSpecies());
         val var39: java.lang.String = this.getFeaturesBuffer().m_130277_();
         val var42: java.lang.Iterable = SpeciesFeatures.INSTANCE.getFeaturesFor(var55);
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : var42) {
            if (`element$iv$iv` is SynchronizedSpeciesFeatureProvider) {
               `destination$iv$iv`.add(`element$iv$iv`);
            }
         }

         val var48: java.util.Iterator = (`destination$iv$iv` as java.util.List).iterator();

         while (true) {
            if (!var48.hasNext()) {
               var56 = null;
               break;
            }

            val itx: SynchronizedSpeciesFeatureProvider = var48.next() as SynchronizedSpeciesFeatureProvider;
            val var63: FriendlyByteBuf = this.getFeaturesBuffer();
            val var50: SynchronizedSpeciesFeature = itx.invoke(var63, var39);
            if (var50 != null) {
               var56 = var50;
               break;
            }
         }

         if (var56 == null) {
            throw new IllegalArgumentException("Couldn't find a feature provider to deserialize this feature. Something's wrong.");
         }

         val var43: SynchronizedSpeciesFeature = var56;
         it.getFeatures().removeIf(PokemonDTO::create$lambda$27$lambda$23$lambda$22);
         it.getFeatures().add(var43);
      }

      switch (PokemonDTO.WhenMappings.$EnumSwitchMapping$0[this.originalTrainerType.ordinal()]) {
         case 1:
            it.removeOriginalTrainer();
            break;
         case 2:
            if (this.originalTrainer != null) {
               val var44: UUID = UUID.fromString(this.originalTrainer);
               if (var44 != null) {
                  it.setOriginalTrainer(var44);
               }
            }
            break;
         case 3:
            if (this.originalTrainer != null) {
               it.setOriginalTrainer(this.originalTrainer);
            }
         default:
      }

      it.setOriginalTrainerName(this.originalTrainerName);
      return var1;
   }

   @JvmStatic
   fun `_init_$lambda$1`(`this$0`: PokemonDTO, var1: FriendlyByteBuf, value: SynchronizedSpeciesFeature) {
      `this$0`.getFeaturesBuffer().m_130070_(value.getName());
      value.encode(`this$0`.getFeaturesBuffer());
   }

   @JvmStatic
   fun `encode$lambda$2`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v: MutableComponent) {
      `$buffer`.m_130083_(v as Component);
   }

   @JvmStatic
   fun `encode$lambda$3`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v: ResourceLocation) {
      `$buffer`.m_130085_(v);
   }

   @JvmStatic
   fun `encode$lambda$5`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v: ResourceLocation) {
      `$buffer`.m_130085_(v);
   }

   @JvmStatic
   fun `encode$lambda$6`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v: UUID) {
      `$buffer`.m_130077_(v);
   }

   @JvmStatic
   fun `encode$lambda$7`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v: java.lang.String) {
      `$buffer`.m_130070_(v);
   }

   @JvmStatic
   fun `encode$lambda$8`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v: java.lang.String) {
      `$buffer`.m_130070_(v);
   }

   @JvmStatic
   fun `decode$lambda$9`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): MutableComponent {
      return `$buffer`.m_130238_().m_6881_();
   }

   @JvmStatic
   fun `decode$lambda$10`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): ResourceLocation {
      return `$buffer`.m_130281_();
   }

   @JvmStatic
   fun `decode$lambda$12`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): ResourceLocation {
      return `$buffer`.m_130281_();
   }

   @JvmStatic
   fun `decode$lambda$13`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): UUID {
      return `$buffer`.m_130259_();
   }

   @JvmStatic
   fun `decode$lambda$14`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
      return `$buffer`.m_130277_();
   }

   @JvmStatic
   fun `decode$lambda$15`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
      return `$buffer`.m_130277_();
   }

   @JvmStatic
   fun `create$lambda$27$lambda$23$lambda$22`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }
}
