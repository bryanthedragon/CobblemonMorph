package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.conversions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.Natures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.SidemodExperienceSource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.EVs
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import java.nio.file.Path
import java.util.Locale
import java.util.UUID
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtIo
import net.minecraft.nbt.Tag
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nReforgedConversion.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReforgedConversion.kt\ncom/cobblemon/mod/common/api/storage/adapter/conversions/ReforgedConversion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,192:1\n1#2:193\n2624#3,3:194\n1855#3,2:197\n1855#3,2:199\n*S KotlinDebug\n*F\n+ 1 ReforgedConversion.kt\ncom/cobblemon/mod/common/api/storage/adapter/conversions/ReforgedConversion\n*L\n103#1:194,3\n126#1:197,2\n129#1:199,2\n*E\n"])
public class ReforgedConversion(base: Path) : CobblemonConverter<CompoundTag> {
   public final val base: Path

   init {
      this.base = base;
   }

   public override fun root(): Path {
      val var10000: Path = this.base.resolve("data").resolve("pokemon");
      return var10000;
   }

   public override fun <E : StorePosition, T : PokemonStore<Any>> load(storeClass: Class<Any>, uuid: UUID): Any? {
      var var10000: java.lang.String = storeClass.getSimpleName();
      var10000 = var10000.toLowerCase(Locale.ROOT);
      val extension: java.lang.String = if (var10000 == "playerpartystore") "pk" else "comp";
      val target: Path = this.root().resolve("$uuid.$extension");
      if (!this.exists(target)) {
         return null;
      } else {
         val nbt: CompoundTag = NbtIo.m_128953_(target.toFile());
         if (nbt != null) {
            val var7: PokemonStore = if (extension == "pk") this.party(uuid, nbt) else this.pc(uuid, nbt);
            return (T)var7;
         } else {
            return null;
         }
      }
   }

   public override fun party(user: UUID, nbt: CompoundTag): PlayerPartyStore {
      val result: PlayerPartyStore = new PlayerPartyStore(user);

      for (int x = 0; x < 6; x++) {
         val key: java.lang.String = "party$x";
         if (nbt.m_128441_(key)) {
            val var10002: CompoundTag = nbt.m_128469_(key);
            result.add(this.translate(var10002));
         }
      }

      return result;
   }

   public override fun pc(user: UUID, nbt: CompoundTag): PCStore {
      val result: PCStore = new PCStore(user);

      for (int box = 0; nbt.m_128441_("BoxNumber" + box); box++) {
         val storage: CompoundTag = nbt.m_128469_("BoxNumber$box");

         for (int x = 0; x < 30; x++) {
            if (storage.m_128441_("pc$x")) {
               val var10001: CompoundTag = storage.m_128469_("pc$x");
               val pokemon: Pokemon = this.translate(var10001);
               if (!result.add(pokemon)) {
                  result.getBackupStore().add(pokemon);
               }
            }
         }
      }

      result.tryRestoreBackedUpPokemon();
      return result;
   }

   public override fun translate(nbt: CompoundTag): Pokemon {
      val result: Pokemon = new Pokemon();
      result.setUuid(nbt.m_128342_("UUID"));
      val var10001: Species = PokemonSpecies.getByPokedexNumber$default(PokemonSpecies.INSTANCE, nbt.m_128451_("ndex"), null, 2, null);
      if (var10001 == null) {
         throw new IllegalStateException("Failed to read a species with pokedex identifier ${nbt.m_128451_("ndex")}");
      } else {
         result.setSpecies(var10001);
         var var10000: PokemonProperties.Companion = PokemonProperties.Companion;
         val move: java.util.Iterator = result.getSpecies().getForms().iterator();

         while (true) {
            if (move.hasNext()) {
               val compound: Any = move.next();
               if (!((compound as FormData).getName() == nbt.m_128461_("Variant"))) {
                  continue;
               }

               var10000 = (PokemonProperties.Companion)compound;
               break;
            }

            var10000 = null;
            break;
         }

         var var49: FormData = var10000 as FormData;
         if (var10000 as FormData == null) {
            var49 = result.getSpecies().getStandardForm();
         }

         PokemonProperties.Companion.parse$default(var10000, var49.getName(), null, null, 6, null).apply(result);
         result.setGender(Gender.values()[nbt.m_128451_("Gender")]);
         val var50: java.lang.Boolean = this.find(nbt, "IsShiny", <unrepresentable>.INSTANCE);
         val var51: Boolean;
         if (var50 != null) {
            var51 = var50;
         } else {
            val var52: java.lang.String = this.find(nbt, "palette", <unrepresentable>.INSTANCE);
            val var53: java.lang.Boolean = if (var52 != null) var52.equals("shiny") else null;
            var51 = var53 != null && var53;
         }

         result.setShiny(var51);
         result.setLevel(nbt.m_128451_("Level"));
         result.addExperience(new SidemodExperienceSource("Reforged"), nbt.m_128451_("EXP"));
         Pokemon.setFriendship$default(result, nbt.m_128451_("Friendship"), false, 2, null);
         val var44: Abilities = Abilities.INSTANCE;
         val var54: java.lang.String = nbt.m_128461_("Ability");
         val var45: AbilityTemplate = var44.get(var54);
         if (var45 != null) {
            val ball: AbilityTemplate = var45;
            val var26: java.lang.Iterable = result.getForm().getAbilities();
            var var46: Boolean;
            if (var26 is java.util.Collection && (var26 as java.util.Collection).isEmpty()) {
               var46 = true;
            } else {
               val template: java.util.Iterator = var26.iterator();

               while (true) {
                  if (!template.hasNext()) {
                     var46 = true;
                     break;
                  }

                  if ((template.next() as PotentialAbility).getTemplate() == ball) {
                     var46 = false;
                     break;
                  }
               }
            }

            result.updateAbility(ball.create(var46));
         }

         val var55: Natures = Natures.INSTANCE;
         var var10004: java.lang.String = ReforgedConversion.ReforgedNatures.values()[nbt.m_128451_("Nature")].name().toLowerCase(Locale.ROOT);
         var var56: Nature = var55.getNature(new ResourceLocation(var10004));
         if (var56 == null) {
            var56 = Natures.INSTANCE.getRandomNature();
         }

         result.setNature(var56);
         val var57: Natures = Natures.INSTANCE;
         var10004 = ReforgedConversion.ReforgedNatures.values()[nbt.m_128451_("MintNature")].name().toLowerCase(Locale.ROOT);
         result.setMintedNature(var57.getNature(new ResourceLocation(var10004)));
         result.setCurrentHealth(nbt.m_128451_("Health"));
         val ivs: IVs = new IVs();
         ivs.set(Stats.HP, nbt.m_128451_("IVHP"));
         ivs.set(Stats.ATTACK, nbt.m_128451_("IVAttack"));
         ivs.set(Stats.DEFENCE, nbt.m_128451_("IVDefense"));
         ivs.set(Stats.SPECIAL_ATTACK, nbt.m_128451_("IVSpAtt"));
         ivs.set(Stats.SPECIAL_DEFENCE, nbt.m_128451_("IVSpDef"));
         ivs.set(Stats.SPEED, nbt.m_128451_("IVSpeed"));
         val var17: EVs = new EVs();
         var17.set(Stats.HP, nbt.m_128451_("EVHP"));
         var17.set(Stats.ATTACK, nbt.m_128451_("EVAttack"));
         var17.set(Stats.DEFENCE, nbt.m_128451_("EVDefense"));
         var17.set(Stats.SPECIAL_ATTACK, nbt.m_128451_("EVSpecialAttack"));
         var17.set(Stats.SPECIAL_DEFENCE, nbt.m_128451_("EVSpecialDefense"));
         var17.set(Stats.SPEED, nbt.m_128451_("EVSpeed"));

         val var18: java.lang.Iterable;
         for (Object element$iv : var18) {
            result.setIV((var30 as Entry).getKey() as Stat, ((var30 as Entry).getValue() as java.lang.Number).intValue());
         }

         for (Object element$iv : var18) {
            result.setEV((var31 as Entry).getKey() as Stat, ((var31 as Entry).getValue() as java.lang.Number).intValue());
         }

         for (Tag movex : nbt.m_128437_("Moveset", 10)) {
            val var29: CompoundTag = movex as CompoundTag;
            val var47: java.lang.String = (movex as CompoundTag).m_128461_("MoveID");
            val var32: java.lang.String = new Regex("[-\\s]", RegexOption.IGNORE_CASE).replace(var47, "");
            val var36: Int = var29.m_128451_("MovePP");
            val var40: Int = var29.m_128451_("MovePPLevel");
            val var48: Moves = Moves.INSTANCE;
            val var58: java.lang.String = var32.toLowerCase(Locale.ROOT);
            result.getMoveSet().add(var48.getByNameOrDummy(var58).create(var36, var40));
         }

         val var21: java.lang.String = this.find(nbt, "CaughtBall", <unrepresentable>.INSTANCE);
         var var59: PokeBall;
         if (var21 != null) {
            var59 = PokeBalls.INSTANCE.getPokeBall(new ResourceLocation(var21));
            if (var59 == null) {
               var59 = PokeBalls.INSTANCE.getPOKE_BALL();
            }
         } else {
            var59 = PokeBalls.INSTANCE.getPOKE_BALL();
         }

         result.setCaughtBall(var59);
         return result;
      }
   }

   public fun <T> find(nbt: CompoundTag, key: String, translator: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.conversions.ReforgedConversion.Translator<Any?>): Any? {
      return (T)(if (nbt.m_128441_(key)) translator.from(nbt, key) else null);
   }

   override fun exists(target: Path): Boolean {
      return CobblemonConverter.DefaultImpls.exists(this, target);
   }

   public enum ReforgedNatures {
      HARDY,
      SERIOUS,
      DOCILE,
      BASHFUL,
      QUIRKY,
      LONELY,
      BRAVE,
      ADAMANT,
      NAUGHTY,
      BOLD,
      RELAXED,
      IMPISH,
      LAX,
      TIMID,
      HASTY,
      JOLLY,
      NAIVE,
      MODEST,
      MILD,
      QUIET,
      RASH,
      CALM,
      GENTLE,
      SASSY,
      CAREFUL   }

   public fun interface Translator<R> {
      public abstract fun from(nbt: CompoundTag, key: String): Any? {
      }
   }
}
