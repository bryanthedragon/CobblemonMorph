package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityPool
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ShowdownIdentifiable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropTable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.ShoulderEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.egg.EggGroup
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PreEvolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceGroup
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceGroups
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.Learnset
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.StatProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.FormPokemonBehaviour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.lighthing.LightingData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import java.util.ArrayList;
import java.util.LinkedHashSet
import java.util.Locale
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityDimensions

@SourceDebugExtension(["SMAP\nFormData.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FormData.kt\ncom/cobblemon/mod/common/pokemon/FormData\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,315:1\n1#2:316\n1855#3,2:317\n*S KotlinDebug\n*F\n+ 1 FormData.kt\ncom/cobblemon/mod/common/pokemon/FormData\n*L\n234#1:317,2\n*E\n"])
public class FormData(name: String = "Normal",
      _baseStats: MutableMap<Stat, Int>? = null,
      _maleRatio: Float? = null,
      _baseScale: Float? = null,
      _hitbox: EntityDimensions? = null,
      _catchRate: Int? = null,
      _experienceGroup: ExperienceGroup? = null,
      _baseExperienceYield: Int? = null,
      _baseFriendship: Int? = null,
      _evYield: MutableMap<Stat, Int>? = null,
      _primaryType: ElementalType? = null,
      _secondaryType: ElementalType? = null,
      _shoulderMountable: Boolean? = null,
      _shoulderEffects: MutableList<ShoulderEffect>? = null,
      _moves: Learnset? = null,
      _evolutions: MutableSet<Evolution>? = null,
      _abilities: AbilityPool? = null,
      _drops: DropTable? = null,
      _pokedex: MutableList<String>? = null,
      _preEvolution: PreEvolution? = null,
      standingEyeHeight: Float? = null,
      swimmingEyeHeight: Float? = null,
      flyingEyeHeight: Float? = null,
      _labels: Set<String>? = null,
      _dynamaxBlocked: Boolean? = null,
      _eggGroups: Set<EggGroup>? = null,
      _height: Float? = null,
      _weight: Float? = null,
      requiredMove: String? = null,
      requiredItem: String? = null,
      requiredItems: List<String>? = null,
      gigantamaxMove: MoveTemplate? = null,
      _battleTheme: ResourceLocation? = null,
      _lightingData: LightingData? = null
   ) :
   Decodable,
   Encodable,
   ShowdownIdentifiable {
   private final val _abilities: AbilityPool?
   private final var _baseExperienceYield: Int?
   private final var _baseFriendship: Int?
   private final var _baseScale: Float?
   internal final var _baseStats: MutableMap<Stat, Int>?
   private final var _battleTheme: ResourceLocation?
   private final var _catchRate: Int?
   private final val _drops: DropTable?
   private final var _dynamaxBlocked: Boolean?
   private final val _eggGroups: Set<EggGroup>?
   private final var _evYield: MutableMap<Stat, Int>?
   private final val _evolutions: MutableSet<Evolution>?
   private final var _experienceGroup: ExperienceGroup?
   private final var _height: Float?
   private final var _hitbox: EntityDimensions?
   private final val _labels: Set<String>?
   private final var _lightingData: LightingData?
   private final val _maleRatio: Float?
   private final var _moves: Learnset?
   private final var _pokedex: MutableList<String>?
   private final val _preEvolution: PreEvolution?
   private final var _primaryType: ElementalType?
   private final var _secondaryType: ElementalType?
   private final val _shoulderEffects: MutableList<ShoulderEffect>?
   private final val _shoulderMountable: Boolean?
   private final var _weight: Float?

   public final val abilities: AbilityPool
      public final get() {
         var var10000: AbilityPool = this._abilities;
         if (this._abilities == null) {
            var10000 = this.getSpecies().getAbilities();
         }

         return var10000;
      }


   public final var aspects: MutableList<String>

   public final val baseExperienceYield: Int
      public final get() {
         return if (this._baseExperienceYield != null) this._baseExperienceYield else this.getSpecies().getBaseExperienceYield();
      }


   public final val baseFriendship: Int
      public final get() {
         return if (this._baseFriendship != null) this._baseFriendship else this.getSpecies().getBaseFriendship();
      }


   public final val baseScale: Float
      public final get() {
         return if (this._baseScale != null) this._baseScale else this.getSpecies().getBaseScale();
      }


   public final val baseStats: Map<Stat, Int>
      public final get() {
         var var10000: java.util.Map = this._baseStats;
         if (this._baseStats == null) {
            var10000 = this.getSpecies().getBaseStats();
         }

         return var10000;
      }


   public final val battleTheme: ResourceLocation
      public final get() {
         var var10000: ResourceLocation = this._battleTheme;
         if (this._battleTheme == null) {
            var10000 = this.getSpecies().getBattleTheme();
         }

         return var10000;
      }


   public final val behaviour: FormPokemonBehaviour

   public final val catchRate: Int
      public final get() {
         return if (this._catchRate != null) this._catchRate else this.getSpecies().getCatchRate();
      }


   public final val drops: DropTable
      public final get() {
         var var10000: DropTable = this._drops;
         if (this._drops == null) {
            var10000 = this.getSpecies().getDrops();
         }

         return var10000;
      }


   public final val dynamaxBlocked: Boolean
      public final get() {
         return if (this._dynamaxBlocked != null) this._dynamaxBlocked else this.getSpecies().getDynamaxBlocked();
      }


   public final val eggGroups: Set<EggGroup>
      public final get() {
         var var10000: java.util.Set = this._eggGroups;
         if (this._eggGroups == null) {
            var10000 = this.getSpecies().getEggGroups();
         }

         return var10000;
      }


   public final val evYield: Map<Stat, Int>
      public final get() {
         var var10000: java.util.Map = this._evYield;
         if (this._evYield == null) {
            var10000 = this.getSpecies().getEvYield();
         }

         return var10000;
      }


   public final val evolutions: MutableSet<Evolution>
      public final get() {
         var var10000: java.util.Set = this._evolutions;
         if (this._evolutions == null) {
            var10000 = new LinkedHashSet();
         }

         return var10000;
      }


   public final val experienceGroup: ExperienceGroup
      public final get() {
         var var10000: ExperienceGroup = this._experienceGroup;
         if (this._experienceGroup == null) {
            var10000 = this.getSpecies().getExperienceGroup();
         }

         return var10000;
      }


   private final var flyingEyeHeight: Float?
   public final val gigantamaxMove: MoveTemplate?

   public final val height: Float
      public final get() {
         return if (this._height != null) this._height else this.getSpecies().getHeight();
      }


   public final val hitbox: EntityDimensions
      public final get() {
         var var10000: EntityDimensions = this._hitbox;
         if (this._hitbox == null) {
            var10000 = this.getSpecies().getHitbox();
         }

         return var10000;
      }


   public final val labels: Set<String>
      public final get() {
         var var10000: java.util.Set = this._labels;
         if (this._labels == null) {
            var10000 = this.getSpecies().getLabels();
         }

         return var10000;
      }


   public final val lightingData: LightingData?
      public final get() {
         return if (this.getSpecies().getStandardForm() == this) this.getSpecies().getLightingData() else this._lightingData;
      }


   public final val maleRatio: Float
      public final get() {
         return if (this._maleRatio != null) this._maleRatio else this.getSpecies().getMaleRatio();
      }


   public final val moves: Learnset
      public final get() {
         var var10000: Learnset = this._moves;
         if (this._moves == null) {
            var10000 = this.getSpecies().getMoves();
         }

         return var10000;
      }


   public final var name: String
      private set

   public final val pokedex: MutableList<String>
      public final get() {
         var var10000: java.util.List = this._pokedex;
         if (this._pokedex == null) {
            var10000 = this.getSpecies().getPokedex();
         }

         return var10000;
      }


   public final val preEvolution: PreEvolution?
      public final get() {
         var var10000: PreEvolution = this._preEvolution;
         if (this._preEvolution == null) {
            var10000 = this.getSpecies().getPreEvolution();
         }

         return var10000;
      }


   public final val primaryType: ElementalType
      public final get() {
         var var10000: ElementalType = this._primaryType;
         if (this._primaryType == null) {
            var10000 = this.getSpecies().getPrimaryType();
         }

         return var10000;
      }


   public final val requiredItem: String?
   public final val requiredItems: List<String>?
   public final val requiredMove: String?

   public final val secondaryType: ElementalType?
      public final get() {
         return if (this._secondaryType == null && this._primaryType == null) this.getSpecies().getSecondaryType() else this._secondaryType;
      }


   public final val shoulderEffects: MutableList<ShoulderEffect>
      public final get() {
         var var10000: java.util.List = this._shoulderEffects;
         if (this._shoulderEffects == null) {
            var10000 = this.getSpecies().getShoulderEffects();
         }

         return var10000;
      }


   public final val shoulderMountable: Boolean
      public final get() {
         return if (this._shoulderMountable != null) this._shoulderMountable else this.getSpecies().getShoulderMountable();
      }


   public final lateinit var species: Species
   private final var standingEyeHeight: Float?
   private final var swimmingEyeHeight: Float?

   public final val types: Iterable<ElementalType>
      public final get() {
         val var10000: ElementalType = this.getSecondaryType();
         if (var10000 != null) {
            val var4: java.util.List = CollectionsKt.listOf(new ElementalType[]{this.getPrimaryType(), var10000});
            if (var4 != null) {
               return var4;
            }
         }

         return CollectionsKt.listOf(this.getPrimaryType());
      }


   public final val weight: Float
      public final get() {
         return if (this._weight != null) this._weight else this.getSpecies().getWeight();
      }


   init {
      this._baseStats = _baseStats;
      this._maleRatio = _maleRatio;
      this._baseScale = _baseScale;
      this._hitbox = _hitbox;
      this._catchRate = _catchRate;
      this._experienceGroup = _experienceGroup;
      this._baseExperienceYield = _baseExperienceYield;
      this._baseFriendship = _baseFriendship;
      this._evYield = _evYield;
      this._primaryType = _primaryType;
      this._secondaryType = _secondaryType;
      this._shoulderMountable = _shoulderMountable;
      this._shoulderEffects = _shoulderEffects;
      this._moves = _moves;
      this._evolutions = _evolutions;
      this._abilities = _abilities;
      this._drops = _drops;
      this._pokedex = _pokedex;
      this._preEvolution = _preEvolution;
      this.standingEyeHeight = standingEyeHeight;
      this.swimmingEyeHeight = swimmingEyeHeight;
      this.flyingEyeHeight = flyingEyeHeight;
      this._labels = _labels;
      this._dynamaxBlocked = _dynamaxBlocked;
      this._eggGroups = _eggGroups;
      this._height = _height;
      this._weight = _weight;
      this.requiredMove = requiredMove;
      this.requiredItem = requiredItem;
      this.requiredItems = requiredItems;
      this.gigantamaxMove = gigantamaxMove;
      this._battleTheme = _battleTheme;
      this._lightingData = _lightingData;
      this.name = name;
      this.aspects = new ArrayList<>();
      this.behaviour = new FormPokemonBehaviour();
   }

   public fun eyeHeight(entity: PokemonEntity): Float {
      val var10000: java.lang.Float = this.resolveEyeHeight(entity);
      return if (var10000 != null) entity.m_20206_() * var10000 else this.getSpecies().eyeHeight(entity);
   }

   private fun resolveEyeHeight(entity: PokemonEntity): Float? {
      var var10000: java.lang.Float;
      if (PoseType.Companion.getSWIMMING_POSES().contains(entity.getCurrentPoseType())) {
         var10000 = this.swimmingEyeHeight;
         if (this.swimmingEyeHeight == null) {
            var10000 = this.standingEyeHeight;
         }
      } else if (PoseType.Companion.getFLYING_POSES().contains(entity.getCurrentPoseType())) {
         var10000 = this.flyingEyeHeight;
         if (this.flyingEyeHeight == null) {
            var10000 = this.standingEyeHeight;
         }
      } else {
         var10000 = this.standingEyeHeight;
      }

      return var10000;
   }

   public fun initialize(species: Species): FormData {
      this.setSpecies(species);
      this.behaviour.setParent(species.getBehaviour());
      Cobblemon.INSTANCE.getStatProvider().provide(this);
      var var10000: PreEvolution = this.getPreEvolution();
      if (var10000 != null) {
         var10000.getSpecies();
      }

      var10000 = this.getPreEvolution();
      if (var10000 != null) {
         var10000.getForm();
      }

      this.getEvolutions().size();
      if (this._lightingData != null) {
         this._lightingData = LightingData.copy$default(this._lightingData, RangesKt.coerceIn(this._lightingData.getLightLevel(), 0, 15), null, 2, null);
      }

      return this;
   }

   internal fun resolveEvolutionMoves() {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val evolution: Evolution = `element$iv` as Evolution;
         if (!(`element$iv` as Evolution).getLearnableMoves().isEmpty() && (`element$iv` as Evolution).getResult().getSpecies() != null) {
            CollectionsKt.addAll(evolution.getResult().create().getForm().getMoves().getEvolutionMoves(), evolution.getLearnableMoves());
         }
      }
   }

   public override operator fun equals(other: Any?): Boolean {
      return other is FormData && (other as FormData).showdownId() == this.showdownId();
   }

   public override fun hashCode(): Int {
      return this.showdownId().hashCode();
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.m_130070_(this.name);
      buffer.m_236828_(this.aspects, FormData::encode$lambda$3);
      buffer.m_236821_(this._baseStats, FormData::encode$lambda$6);
      buffer.m_236821_(this._primaryType, FormData::encode$lambda$7);
      buffer.m_236821_(this._secondaryType, FormData::encode$lambda$8);
      buffer.m_236821_(this._experienceGroup, FormData::encode$lambda$9);
      buffer.m_236821_(this._height, FormData::encode$lambda$10);
      buffer.m_236821_(this._weight, FormData::encode$lambda$11);
      buffer.m_236821_(this._baseScale, FormData::encode$lambda$12);
      buffer.m_236821_(this._hitbox, FormData::encode$lambda$13);
      buffer.m_236821_(this._moves, FormData::encode$lambda$14);
      buffer.m_236821_(this._pokedex, FormData::encode$lambda$16);
      buffer.m_236821_(this.getLightingData(), FormData::encode$lambda$17);
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      val var10001: java.lang.String = buffer.m_130277_();
      this.name = var10001;
      val var2: java.util.List = buffer.m_236845_(FormData::decode$lambda$18);
      this.aspects = CollectionsKt.toMutableList(var2);
      buffer.m_236868_(FormData::decode$lambda$21);
      this._primaryType = buffer.m_236868_(FormData::decode$lambda$22) as ElementalType;
      this._secondaryType = buffer.m_236868_(FormData::decode$lambda$23) as ElementalType;
      this._experienceGroup = buffer.m_236868_(FormData::decode$lambda$24) as ExperienceGroup;
      this._height = buffer.m_236868_(FormData::decode$lambda$25) as java.lang.Float;
      this._weight = buffer.m_236868_(FormData::decode$lambda$26) as java.lang.Float;
      this._baseScale = buffer.m_236868_(FormData::decode$lambda$27) as java.lang.Float;
      this._hitbox = buffer.m_236868_(FormData::decode$lambda$28) as EntityDimensions;
      this._moves = buffer.m_236868_(FormData::decode$lambda$30) as Learnset;
      this._pokedex = buffer.m_236868_(FormData::decode$lambda$32) as MutableList<java.lang.String>;
      this._lightingData = buffer.m_236868_(FormData::decode$lambda$33) as LightingData;
   }

   public override fun showdownId(): String {
      return "${this.getSpecies().showdownId()}${this.formOnlyShowdownId()}";
   }

   public fun formOnlyShowdownId(): String {
      val var10000: Regex = ShowdownIdentifiable.Companion.getREGEX$common();
      val var10001: java.lang.String = this.name.toLowerCase(Locale.ROOT);
      return var10000.replace(var10001, "");
   }

   @JvmStatic
   fun `encode$lambda$3`(pb: FriendlyByteBuf, aspect: java.lang.String) {
      pb.m_130070_(aspect);
   }

   @JvmStatic
   fun `encode$lambda$6$lambda$4`(keyBuffer: FriendlyByteBuf, stat: Stat) {
      val var10000: StatProvider = Cobblemon.INSTANCE.getStatProvider();
      var10000.encode(keyBuffer, stat);
   }

   @JvmStatic
   fun `encode$lambda$6$lambda$5`(valueBuffer: FriendlyByteBuf, value: Int) {
      val var10000: ByteBuf = valueBuffer as ByteBuf;
      val var10001: IntSize = IntSize.U_SHORT;
      NetExtensionsKt.writeSizedInt(var10000, var10001, value);
   }

   @JvmStatic
   fun `encode$lambda$6`(statsBuffer: FriendlyByteBuf, map: java.util.Map) {
      statsBuffer.m_236831_(map, FormData::encode$lambda$6$lambda$4, FormData::encode$lambda$6$lambda$5);
   }

   @JvmStatic
   fun `encode$lambda$7`(pb: FriendlyByteBuf, type: ElementalType) {
      pb.m_130070_(type.getName());
   }

   @JvmStatic
   fun `encode$lambda$8`(pb: FriendlyByteBuf, type: ElementalType) {
      pb.m_130070_(type.getName());
   }

   @JvmStatic
   fun `encode$lambda$9`(pb: FriendlyByteBuf, value: ExperienceGroup) {
      pb.m_130070_(value.getName());
   }

   @JvmStatic
   fun `encode$lambda$10`(pb: FriendlyByteBuf, height: java.lang.Float) {
      pb.writeFloat(height);
   }

   @JvmStatic
   fun `encode$lambda$11`(pb: FriendlyByteBuf, weight: java.lang.Float) {
      pb.writeFloat(weight);
   }

   @JvmStatic
   fun `encode$lambda$12`(buf: FriendlyByteBuf, fl: java.lang.Float) {
      buf.writeFloat(fl);
   }

   @JvmStatic
   fun `encode$lambda$13`(pb: FriendlyByteBuf, hitbox: EntityDimensions) {
      pb.writeFloat(hitbox.f_20377_);
      pb.writeFloat(hitbox.f_20378_);
      pb.writeBoolean(hitbox.f_20379_);
   }

   @JvmStatic
   fun `encode$lambda$14`(buf: FriendlyByteBuf, moves: Learnset) {
      moves.encode(buf);
   }

   @JvmStatic
   fun `encode$lambda$16$lambda$15`(pb2: FriendlyByteBuf, line: java.lang.String) {
      pb2.m_130070_(line);
   }

   @JvmStatic
   fun `encode$lambda$16`(pb1: FriendlyByteBuf, pokedex: java.util.List) {
      pb1.m_236828_(pokedex, FormData::encode$lambda$16$lambda$15);
   }

   @JvmStatic
   fun `encode$lambda$17`(pb: FriendlyByteBuf, data: LightingData) {
      pb.writeInt(data.getLightLevel());
      pb.m_130068_(data.getLiquidGlowMode());
   }

   @JvmStatic
   fun `decode$lambda$18`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
      return `$buffer`.m_130277_();
   }

   @JvmStatic
   fun `decode$lambda$21$lambda$19`(keyBuffer: FriendlyByteBuf): Stat {
      val var10000: StatProvider = Cobblemon.INSTANCE.getStatProvider();
      return var10000.decode(keyBuffer);
   }

   @JvmStatic
   fun `decode$lambda$21$lambda$20`(valueBuffer: FriendlyByteBuf): Int {
      return NetExtensionsKt.readSizedInt(valueBuffer as ByteBuf, IntSize.U_SHORT);
   }

   @JvmStatic
   fun `decode$lambda$21`(`this$0`: FormData, mapBuffer: FriendlyByteBuf): Unit {
      `this$0`._baseStats = mapBuffer.m_236847_(FormData::decode$lambda$21$lambda$19, FormData::decode$lambda$21$lambda$20);
      return Unit.INSTANCE;
   }

   @JvmStatic
   fun `decode$lambda$22`(pb: FriendlyByteBuf): ElementalType {
      val var10000: ElementalTypes = ElementalTypes.INSTANCE;
      val var10001: java.lang.String = pb.m_130277_();
      return var10000.get(var10001);
   }

   @JvmStatic
   fun `decode$lambda$23`(pb: FriendlyByteBuf): ElementalType {
      val var10000: ElementalTypes = ElementalTypes.INSTANCE;
      val var10001: java.lang.String = pb.m_130277_();
      return var10000.get(var10001);
   }

   @JvmStatic
   fun `decode$lambda$24`(pb: FriendlyByteBuf): ExperienceGroup {
      val var10000: ExperienceGroups = ExperienceGroups.INSTANCE;
      val var10001: java.lang.String = pb.m_130277_();
      return var10000.findByName(var10001);
   }

   @JvmStatic
   fun `decode$lambda$25`(pb: FriendlyByteBuf): java.lang.Float {
      return pb.readFloat();
   }

   @JvmStatic
   fun `decode$lambda$26`(pb: FriendlyByteBuf): java.lang.Float {
      return pb.readFloat();
   }

   @JvmStatic
   fun `decode$lambda$27`(pb: FriendlyByteBuf): java.lang.Float {
      return pb.readFloat();
   }

   @JvmStatic
   fun `decode$lambda$28`(pb: FriendlyByteBuf): EntityDimensions {
      return new EntityDimensions(pb.readFloat(), pb.readFloat(), pb.readBoolean());
   }

   @JvmStatic
   fun `decode$lambda$30`(pb: FriendlyByteBuf): Learnset {
      val var1: Learnset = new Learnset();
      var1.decode(pb);
      return var1;
   }

   @JvmStatic
   fun `decode$lambda$32$lambda$31`(it: FriendlyByteBuf): java.lang.String {
      return it.m_130277_();
   }

   @JvmStatic
   fun `decode$lambda$32`(pb: FriendlyByteBuf): java.util.List {
      return pb.m_236845_(FormData::decode$lambda$32$lambda$31);
   }

   @JvmStatic
   fun `decode$lambda$33`(pb: FriendlyByteBuf): LightingData {
      val var10002: Int = pb.readInt();
      val var10003: java.lang.Enum = pb.m_130066_(LightingData.LiquidGlowMode.class);
      return new LightingData(var10002, var10003 as LightingData.LiquidGlowMode);
   }

   fun FormData() {
      this(
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         null,
         -1,
         3,
         null
      );
   }
}
