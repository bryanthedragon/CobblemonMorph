package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.stat

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.StatProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.StatTypeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat.Type
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.EVs
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.adapters.CobblemonStatTypeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import java.util.LinkedHashMap
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.math.MathKt
import kotlin.random.Random
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nCobblemonStatProvider.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonStatProvider.kt\ncom/cobblemon/mod/common/pokemon/stat/CobblemonStatProvider\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,137:1\n1#2:138\n1855#3,2:139\n1855#3,2:141\n8811#4,2:143\n9071#4,4:145\n8811#4,2:149\n9071#4,4:151\n8676#4,2:155\n9358#4,4:157\n*S KotlinDebug\n*F\n+ 1 CobblemonStatProvider.kt\ncom/cobblemon/mod/common/pokemon/stat/CobblemonStatProvider\n*L\n64#1:139,2\n122#1:141,2\n38#1:143,2\n38#1:145,4\n39#1:149,2\n39#1:151,4\n40#1:155,2\n40#1:157,4\n*E\n"])
public object CobblemonStatProvider : StatProvider {
   private final val identifierToOrdinal: Map<ResourceLocation, Int>
   private final val ordinalToStat: Map<Int, Stats>
   private final val stats: Map<ResourceLocation, Stats>
   public open val typeAdapter: StatTypeAdapter = CobblemonStatTypeAdapter.INSTANCE as StatTypeAdapter

   public override fun all(): Collection<Stat> {
      return Stats.Companion.getALL();
   }

   public override fun ofType(type: Type): Collection<Stat> {
      var var10000: java.util.Collection;
      switch (CobblemonStatProvider.WhenMappings.$EnumSwitchMapping$0[type.ordinal()]) {
         case 1:
            var10000 = Stats.Companion.getBATTLE_ONLY();
            break;
         case 2:
            var10000 = Stats.Companion.getPERMANENT();
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }

      return var10000;
   }

   public override fun provide(species: Species) {
      this.allocate(species.getBaseStats());
   }

   public override fun provide(form: FormData) {
      val var10000: java.util.Map = form.get_baseStats$common();
      if (var10000 != null) {
         this.allocate(var10000);
      }
   }

   public override fun toShowdown(species: Species, form: FormData?): String {
      var var10000: java.util.Map;
      label11: {
         if (form != null) {
            var10000 = form.getBaseStats();
            if (var10000 != null) {
               break label11;
            }
         }

         var10000 = species.getBaseStats();
      }

      return "baseStats: { hp: ${var10000.get(Stats.HP)}, atk: ${var10000.get(Stats.ATTACK)}, def: ${var10000.get(Stats.DEFENCE)}, spa: ${var10000.get(
         Stats.SPECIAL_ATTACK
      )}, spd: ${var10000.get(Stats.SPECIAL_DEFENCE)}, spe: ${var10000.get(Stats.SPEED)} }";
   }

   public override fun createEmptyEVs(): EVs {
      val evs: EVs = new EVs();

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         evs.set(`element$iv` as Stat, evs.getDefaultValue());
      }

      return evs;
   }

   public override fun createEmptyIVs(minPerfectIVs: Int): IVs {
      val ivs: IVs = new IVs();

      for (Stat stat : this.ofType(Stat.Type.PERMANENT)) {
         ivs.set(stat, Random.Default.nextInt(32));
      }

      if (minPerfectIVs > 0) {
         for (Stat stat : CollectionsKt.take(CollectionsKt.shuffled(this.ofType(Stat.Type.PERMANENT)), minPerfectIVs)) {
            ivs.set(stat, 31);
         }
      }

      return ivs;
   }

   public override fun getStatForPokemon(pokemon: Pokemon, stat: Stat): Int {
      val stats: java.util.Map = pokemon.getForm().getBaseStats();
      val iv: Int = pokemon.getIvs().getOrDefault(stat);
      val var10000: Any = pokemon.getForm().getBaseStats().get(stat);
      val base: Int = (var10000 as java.lang.Number).intValue();
      val ev: Int = pokemon.getEvs().getOrDefault(stat);
      val level: Int = pokemon.getLevel();
      return if (stat === Stats.HP)
         (
            if (pokemon.getSpecies().getResourceIdentifier() == Pokemon.Companion.getSHEDINJA$common())
               1
               else
               (int)MathKt.truncate(
                  MathKt.truncate(2.0 * (double)base + (double)iv + MathKt.truncate((double)ev / 4.0) + (double)100) * (double)level / 100.0 + (double)10
               )
         )
         else
         pokemon.getEffectiveNature().modifyStat(stat, (2 * base + iv + ev / 4) * level / 100 + 5);
   }

   public override fun fromIdentifier(identifier: ResourceLocation): Stat? {
      return stats.get(identifier);
   }

   public override fun fromIdentifierOrThrow(identifier: ResourceLocation): Stat {
      val var10000: Stat = this.fromIdentifier(identifier);
      if (var10000 == null) {
         throw new IllegalArgumentException("No stat was found with the identifier $identifier");
      } else {
         return var10000;
      }
   }

   public override fun decode(buffer: FriendlyByteBuf): Stat {
      return this.ordinalLookup(NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE));
   }

   public override fun encode(buffer: FriendlyByteBuf, stat: Stat) {
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.identifierLookup(stat.getIdentifier()));
   }

   private fun allocate(map: MutableMap<Stat, Int>) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         map.putIfAbsent(`element$iv` as Stat, 1);
      }
   }

   private fun ordinalLookup(ordinal: Int): Stat {
      val var10000: Stats = ordinalToStat.get(ordinal);
      if (var10000 != null) {
         return var10000;
      } else {
         throw new IllegalArgumentException(
            "Cannot find the stat with the ordinal $ordinal, this should only happen if there is a custom Stat implementation but no StatProvider to go alongside it"
         );
      }
   }

   private fun identifierLookup(identifier: ResourceLocation): Int {
      val var10000: Int = identifierToOrdinal.get(identifier);
      if (var10000 != null) {
         return var10000;
      } else {
         throw new IllegalArgumentException(
            "Cannot find the stat to encode, this should only happen if there is a custom Stat implementation but no StatProvider to go alongside it on the server side"
         );
      }
   }

   @JvmStatic
   fun {
      var `$this$associate$iv`: Array<Any> = Stats.values();
      var `destination$iv$iv`: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(`$this$associate$iv`.length), 16));

      for (Object element$iv$iv : $this$associateBy$iv) {
         `destination$iv$iv`.put(((Stats)`element$iv$iv`).getIdentifier(), `element$iv$iv`);
      }

      stats = `destination$iv$iv`;
      `$this$associate$iv` = Stats.values();
      `destination$iv$iv` = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(`$this$associate$iv`.length), 16));

      for (Object element$iv$iv : $this$associateBy$iv) {
         `destination$iv$iv`.put(((Stats)var29).ordinal(), var29);
      }

      ordinalToStat = `destination$iv$iv`;
      `$this$associate$iv` = Stats.values();
      `destination$iv$iv` = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(`$this$associate$iv`.length), 16));

      for (Object element$iv$iv : $this$associateBy$iv) {
         val var32: Pair = TuplesKt.to(((Stats)var30).getIdentifier(), ((Stats)var30).ordinal());
         `destination$iv$iv`.put(var32.getFirst(), var32.getSecond());
      }

      identifierToOrdinal = `destination$iv$iv`;
   }
}
