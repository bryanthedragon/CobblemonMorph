package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityPool
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ClientDataSynchronizer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ShowdownIdentifiable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropTable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.ai.PokemonBehaviour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.lighthing.LightingData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import java.util.ArrayList;
import java.util.HashMap
import java.util.HashSet
import java.util.LinkedHashSet
import java.util.Locale
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityDimensions
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nSpecies.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Species.kt\ncom/cobblemon/mod/common/pokemon/Species\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,287:1\n1#2:288\n1855#3,2:289\n2624#3,3:291\n1855#3,2:294\n1855#3,2:296\n533#3,4:298\n1726#3,3:302\n538#3:305\n*S KotlinDebug\n*F\n+ 1 Species.kt\ncom/cobblemon/mod/common/pokemon/Species\n*L\n139#1:289,2\n140#1:291,3\n152#1:294,2\n158#1:296,2\n163#1:298,4\n163#1:302,3\n163#1:305\n*E\n"])
public class Species : ClientDataSynchronizer<Species>, ShowdownIdentifiable {
   public final var abilities: AbilityPool = new AbilityPool()
      private set

   public final var baseExperienceYield: Int = 10
   public final var baseFriendship: Int
   public final var baseScale: Float = 1.0F

   public final var baseStats: HashMap<Stat, Int> = new HashMap()
      private set

   public final var battleTheme: ResourceLocation

   public final var behaviour: PokemonBehaviour = new PokemonBehaviour()
      private set

   public final var catchRate: Int = 45
      private set

   public final var drops: DropTable = new DropTable()
      private set

   public final var dynamaxBlocked: Boolean

   public final var eggCycles: Int = 120
      private set

   public final var eggGroups: HashSet<EggGroup> = new HashSet()
      private set

   public final var evYield: HashMap<Stat, Int> = new HashMap()
      private set

   public final var evolutions: MutableSet<Evolution> = (new HashSet()) as java.util.Set
      private set

   public final var experienceGroup: ExperienceGroup = CollectionsKt.first(ExperienceGroups.INSTANCE) as ExperienceGroup

   public final var features: MutableSet<String> = (new LinkedHashSet()) as java.util.Set
      private set

   private final var flyingEyeHeight: Float?

   public final var forms: MutableList<FormData> = (new ArrayList()) as java.util.List
      private set

   public final var height: Float = 1.0F
      private set

   public final var hitbox: EntityDimensions = new EntityDimensions(1.0F, 1.0F, false)
   public final var implemented: Boolean

   public final var labels: HashSet<String> = new HashSet()
      private set

   public final var lightingData: LightingData?
      private set

   public final var maleRatio: Float = 0.5F
      private set

   public final var moves: Learnset = new Learnset()
      private set

   public final var name: String = "Bulbasaur"
   public final var nationalPokedexNumber: Int = 1

   public final var pokedex: MutableList<String> = (new ArrayList()) as java.util.List
      private set

   public final var preEvolution: PreEvolution?
      private set

   public final var primaryType: ElementalType = ElementalTypes.INSTANCE.getGRASS()
      public final set(<set-?>) {
         this.primaryType = `<set-?>`;
      }


   public final lateinit var resourceIdentifier: ResourceLocation

   public final var secondaryType: ElementalType?
      public final set(<set-?>) {
         this.secondaryType = `<set-?>`;
      }


   public final var shoulderEffects: MutableList<ShoulderEffect> = (new ArrayList()) as java.util.List
      private set

   public final var shoulderMountable: Boolean
      private set

   public final val standardForm: FormData by LazyKt.lazy(
      (
         new Function0<FormData>(this) {
            {
               super(0);
               this.this$0 = `$receiver`;
            }

            @NotNull
            public final FormData invoke() {
               return new FormData(
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
                     this.this$0.getEvolutions(),
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
                     -32769,
                     3,
                     null
                  )
                  .initialize(this.this$0);
            }
         }
      ) as Function0
   )
      public final get() {
         return this.standardForm$delegate.getValue() as FormData;
      }


   private final var standingEyeHeight: Float?
   private final var swimmingEyeHeight: Float?

   public final val translatedName: MutableComponent
      public final get() {
         val var10000: MutableComponent = Component.m_237115_("${this.getResourceIdentifier().m_135827_()}.species.${this.unformattedShowdownId()}.name");
         return var10000;
      }


   public final val types: Iterable<ElementalType>
      public final get() {
         if (this.secondaryType != null) {
            val var10000: java.util.List = CollectionsKt.listOf(new ElementalType[]{this.primaryType, this.secondaryType});
            if (var10000 != null) {
               return var10000;
            }
         }

         return CollectionsKt.listOf(this.primaryType);
      }


   public final var weight: Float = 1.0F
      private set

   public fun initialize() {
      Cobblemon.INSTANCE.getStatProvider().provide(this);

      val `$this$none$iv`: java.lang.Iterable;
      for (Object element$iv : $this$none$iv) {
         (var4 as FormData).initialize(this);
      }

      if (!this.forms.isEmpty()) {
         `$this$none$iv` = this.forms;
         var var10000: Boolean;
         if (this.forms is java.util.Collection && this.forms.isEmpty()) {
            var10000 = true;
         } else {
            val var9: java.util.Iterator = `$this$none$iv`.iterator();

            while (true) {
               if (!var9.hasNext()) {
                  var10000 = true;
                  break;
               }

               if (var9.next() as FormData == this.getStandardForm()) {
                  var10000 = false;
                  break;
               }
            }
         }

         if (var10000) {
            this.forms.add(0, this.getStandardForm());
         }
      }

      if (this.lightingData != null) {
         this.lightingData = LightingData.copy$default(this.lightingData, RangesKt.coerceIn(this.lightingData.getLightLevel(), 0, 15), null, 2, null);
      }

      if (this.preEvolution != null) {
         this.preEvolution.getSpecies();
      }

      if (this.preEvolution != null) {
         this.preEvolution.getForm();
      }

      this.evolutions.size();
   }

   internal fun resolveEvolutionMoves() {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val p0: Evolution = `element$iv` as Evolution;
         if (!(`element$iv` as Evolution).getLearnableMoves().isEmpty() && (`element$iv` as Evolution).getResult().getSpecies() != null) {
            CollectionsKt.addAll(p0.getResult().create().getForm().getMoves().getEvolutionMoves(), p0.getLearnableMoves());
         }
      }

      for (Object element$ivx : $this$forEach$iv) {
         (`element$ivx` as FormData).resolveEvolutionMoves$common();
      }
   }

   public fun create(level: Int = 10): Pokemon {
      return PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "species=\"${this.name}\" level=$level", null, null, 6, null).create();
   }

   public fun getForm(aspects: Set<String>): FormData {
      val `iterator$iv`: java.util.ListIterator = this.forms.listIterator(this.forms.size());

      var var14: Any;
      while (true) {
         if (!`iterator$iv`.hasPrevious()) {
            var14 = null;
            break;
         }

         val `element$iv`: Any = `iterator$iv`.previous();
         val `$this$all$iv`: java.lang.Iterable = (`element$iv` as FormData).getAspects();
         var var10000: Boolean;
         if (`$this$all$iv` is java.util.Collection && (`$this$all$iv` as java.util.Collection).isEmpty()) {
            var10000 = true;
         } else {
            val var10: java.util.Iterator = `$this$all$iv`.iterator();

            while (true) {
               if (!var10.hasNext()) {
                  var10000 = true;
                  break;
               }

               if (!aspects.contains(var10.next() as java.lang.String)) {
                  var10000 = false;
                  break;
               }
            }
         }

         if (var10000) {
            var14 = (FormData)`element$iv`;
            break;
         }
      }

      var14 = var14;
      if (var14 == null) {
         var14 = this.getStandardForm();
      }

      return var14;
   }

   public fun eyeHeight(entity: PokemonEntity): Float {
      val var10000: java.lang.Float = this.resolveEyeHeight(entity);
      return entity.m_20206_() * (var10000 ?: 0.85F);
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

   public fun canGmax(): Boolean {
      val var2: java.util.Iterator = this.forms.iterator();

      var var10000: Any;
      while (true) {
         if (var2.hasNext()) {
            val var3: Any = var2.next();
            if (!((var3 as FormData).formOnlyShowdownId() == "gmax")) {
               continue;
            }

            var10000 = var3;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 != null;
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      buffer.writeBoolean(this.implemented);
      buffer.m_130070_(this.name);
      buffer.writeInt(this.nationalPokedexNumber);
      buffer.m_236831_(this.baseStats, Species::encode$lambda$8, Species::encode$lambda$9);
      buffer.m_130070_(this.primaryType.getName());
      buffer.m_236821_(this.secondaryType, Species::encode$lambda$10);
      buffer.m_130070_(this.experienceGroup.getName());
      buffer.writeFloat(this.height);
      buffer.writeFloat(this.weight);
      buffer.writeFloat(this.baseScale);
      buffer.writeFloat(this.hitbox.f_20377_);
      buffer.writeFloat(this.hitbox.f_20378_);
      buffer.writeBoolean(this.hitbox.f_20379_);
      this.moves.encode(buffer);
      buffer.m_236828_(this.pokedex, Species::encode$lambda$11);
      buffer.m_236828_(this.forms, Species::encode$lambda$12);
      buffer.m_130085_(this.battleTheme);
      buffer.m_236828_(this.features, Species::encode$lambda$13);
      buffer.m_236821_(this.lightingData, Species::encode$lambda$14);
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      this.implemented = buffer.readBoolean();
      val var10001: java.lang.String = buffer.m_130277_();
      this.name = var10001;
      this.nationalPokedexNumber = buffer.readInt();
      this.baseStats.putAll(buffer.m_236847_(Species::decode$lambda$15, Species::decode$lambda$16));
      val var7: ElementalTypes = ElementalTypes.INSTANCE;
      var var10002: java.lang.String = buffer.m_130277_();
      this.primaryType = var7.getOrException(var10002);
      this.secondaryType = buffer.m_236868_(Species::decode$lambda$17) as ElementalType;
      val var8: ExperienceGroups = ExperienceGroups.INSTANCE;
      var10002 = buffer.m_130277_();
      val var9: ExperienceGroup = var8.findByName(var10002);
      this.experienceGroup = var9;
      this.height = buffer.readFloat();
      this.weight = buffer.readFloat();
      this.baseScale = buffer.readFloat();
      this.hitbox = new EntityDimensions(buffer.readFloat(), buffer.readFloat(), buffer.readBoolean());
      this.moves.decode(buffer);
      this.pokedex.clear();
      var var2: java.util.Collection = this.pokedex;
      var var10000: java.util.List = buffer.m_236845_(Species::decode$lambda$18);
      CollectionsKt.addAll(var2, var10000);
      this.forms.clear();
      var2 = this.forms;
      var10000 = buffer.m_236845_(Species::decode$lambda$20);
      CollectionsKt.addAll(var2, CollectionsKt.filterNotNull(var10000));
      val var10: ResourceLocation = buffer.m_130281_();
      this.battleTheme = var10;
      this.features.clear();
      var2 = this.features;
      var10000 = buffer.m_236845_(Species::decode$lambda$21);
      CollectionsKt.addAll(var2, var10000);
      this.lightingData = buffer.m_236868_(Species::decode$lambda$22) as LightingData;
      this.initialize();
   }

   public open fun shouldSynchronize(other: Species): Boolean {
      if (!(other.getResourceIdentifier().toString() == other.getResourceIdentifier().toString())) {
         return false;
      } else {
         return !(other.showdownId() == this.showdownId())
            || other.nationalPokedexNumber != this.nationalPokedexNumber
            || !(other.baseStats == this.baseStats)
            || !(other.hitbox == this.hitbox)
            || !(other.primaryType == this.primaryType)
            || !(other.secondaryType == this.secondaryType)
            || !(other.standingEyeHeight == this.standingEyeHeight)
            || !(other.swimmingEyeHeight == this.swimmingEyeHeight)
            || !(other.flyingEyeHeight == this.flyingEyeHeight)
            || other.dynamaxBlocked != this.dynamaxBlocked
            || !(other.pokedex == this.pokedex)
            || !(other.forms == this.forms)
            || this.moves.shouldSynchronize(other.moves)
            || !(other.battleTheme == this.battleTheme)
            || !(other.features == this.features);
      }
   }

   public override fun showdownId(): String {
      val id: java.lang.String = this.unformattedShowdownId();
      return if (this.getResourceIdentifier().m_135827_() == "cobblemon") id else "${this.getResourceIdentifier().m_135827_()}$id";
   }

   private fun unformattedShowdownId(): String {
      val var10000: Regex = ShowdownIdentifiable.Companion.getREGEX$common();
      val var10001: java.lang.String = this.name.toLowerCase(Locale.ROOT);
      return var10000.replace(var10001, "");
   }

   public override fun toString(): String {
      return this.showdownId();
   }

   @JvmStatic
   fun `encode$lambda$8`(keyBuffer: FriendlyByteBuf, stat: Stat) {
      val var10000: StatProvider = Cobblemon.INSTANCE.getStatProvider();
      var10000.encode(keyBuffer, stat);
   }

   @JvmStatic
   fun `encode$lambda$9`(valueBuffer: FriendlyByteBuf, value: Int) {
      val var10000: ByteBuf = valueBuffer as ByteBuf;
      val var10001: IntSize = IntSize.U_SHORT;
      NetExtensionsKt.writeSizedInt(var10000, var10001, value);
   }

   @JvmStatic
   fun `encode$lambda$10`(pb: FriendlyByteBuf, type: ElementalType) {
      pb.m_130070_(type.getName());
   }

   @JvmStatic
   fun `encode$lambda$11`(pb: FriendlyByteBuf, line: java.lang.String) {
      pb.m_130070_(line);
   }

   @JvmStatic
   fun `encode$lambda$12`(pb: FriendlyByteBuf, form: FormData) {
      form.encode(pb);
   }

   @JvmStatic
   fun `encode$lambda$13`(pb: FriendlyByteBuf, feature: java.lang.String) {
      pb.m_130070_(feature);
   }

   @JvmStatic
   fun `encode$lambda$14`(pb: FriendlyByteBuf, data: LightingData) {
      pb.writeInt(data.getLightLevel());
      pb.m_130068_(data.getLiquidGlowMode());
   }

   @JvmStatic
   fun `decode$lambda$15`(keyBuffer: FriendlyByteBuf): Stat {
      val var10000: StatProvider = Cobblemon.INSTANCE.getStatProvider();
      return var10000.decode(keyBuffer);
   }

   @JvmStatic
   fun `decode$lambda$16`(valueBuffer: FriendlyByteBuf): Int {
      return NetExtensionsKt.readSizedInt(valueBuffer as ByteBuf, IntSize.U_SHORT);
   }

   @JvmStatic
   fun `decode$lambda$17`(pb: FriendlyByteBuf): ElementalType {
      val var10000: ElementalTypes = ElementalTypes.INSTANCE;
      val var10001: java.lang.String = pb.m_130277_();
      return var10000.getOrException(var10001);
   }

   @JvmStatic
   fun `decode$lambda$18`(pb: FriendlyByteBuf): java.lang.String {
      return pb.m_130277_();
   }

   @JvmStatic
   fun `decode$lambda$20`(pb: FriendlyByteBuf): FormData {
      val var1: FormData = new FormData(
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
      var1.decode(pb);
      return var1;
   }

   @JvmStatic
   fun `decode$lambda$21`(pb: FriendlyByteBuf): java.lang.String {
      return pb.m_130277_();
   }

   @JvmStatic
   fun `decode$lambda$22`(pb: FriendlyByteBuf): LightingData {
      val var10002: Int = pb.readInt();
      val var10003: java.lang.Enum = pb.m_130066_(LightingData.LiquidGlowMode.class);
      return new LightingData(var10002, var10003 as LightingData.LiquidGlowMode);
   }

   public companion object {
      private const val VANILLA_DEFAULT_EYE_HEIGHT: Float
   }
}
