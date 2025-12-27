package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.elemental.ElementalTypeTeraType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.gimmick.StellarTeraType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.ArrayList;
import java.util.HashMap
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nTeraTypes.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TeraTypes.kt\ncom/cobblemon/mod/common/api/types/tera/TeraTypes\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,128:1\n766#2:129\n857#2,2:130\n*S KotlinDebug\n*F\n+ 1 TeraTypes.kt\ncom/cobblemon/mod/common/api/types/tera/TeraTypes\n*L\n92#1:129\n92#1:130,2\n*E\n"])
public object TeraTypes {
   @JvmStatic
   public final val BUG: TeraType = INSTANCE.create(MiscUtilsKt.cobblemonResource("bug"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getBUG()))

   @JvmStatic
   public final val DARK: TeraType = INSTANCE.create(MiscUtilsKt.cobblemonResource("dark"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getDARK()))

   @JvmStatic
   public final val DRAGON: TeraType = INSTANCE.create(MiscUtilsKt.cobblemonResource("dragon"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getDRAGON()))

   @JvmStatic
   public final val ELECTRIC: TeraType =
      INSTANCE.create(MiscUtilsKt.cobblemonResource("electric"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getELECTRIC()))

   @JvmStatic
   public final val FAIRY: TeraType = INSTANCE.create(MiscUtilsKt.cobblemonResource("fairy"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getFAIRY()))

   @JvmStatic
   public final val FIGHTING: TeraType =
      INSTANCE.create(MiscUtilsKt.cobblemonResource("fighting"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getFIGHTING()))

   @JvmStatic
   public final val FIRE: TeraType = INSTANCE.create(MiscUtilsKt.cobblemonResource("fire"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getFIRE()))

   @JvmStatic
   public final val FLYING: TeraType = INSTANCE.create(MiscUtilsKt.cobblemonResource("flying"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getFLYING()))

   @JvmStatic
   public final val GHOST: TeraType = INSTANCE.create(MiscUtilsKt.cobblemonResource("ghost"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getGHOST()))

   @JvmStatic
   public final val GRASS: TeraType = INSTANCE.create(MiscUtilsKt.cobblemonResource("grass"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getGRASS()))

   @JvmStatic
   public final val GROUND: TeraType = INSTANCE.create(MiscUtilsKt.cobblemonResource("ground"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getGROUND()))

   @JvmStatic
   public final val ICE: TeraType = INSTANCE.create(MiscUtilsKt.cobblemonResource("ice"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getICE()))

   @JvmStatic
   public final val NORMAL: TeraType = INSTANCE.create(MiscUtilsKt.cobblemonResource("normal"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getNORMAL()))

   @JvmStatic
   public final val POISON: TeraType = INSTANCE.create(MiscUtilsKt.cobblemonResource("poison"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getPOISON()))

   @JvmStatic
   public final val PSYCHIC: TeraType =
      INSTANCE.create(MiscUtilsKt.cobblemonResource("psychic"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getPSYCHIC()))

   @JvmStatic
   public final val ROCK: TeraType = INSTANCE.create(MiscUtilsKt.cobblemonResource("rock"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getROCK()))

   @JvmStatic
   public final val STEEL: TeraType = INSTANCE.create(MiscUtilsKt.cobblemonResource("steel"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getSTEEL()))

   @JvmStatic
   public final val STELLAR: TeraType = INSTANCE.create(StellarTeraType.Companion.getID(), new StellarTeraType())

   @JvmStatic
   public final val WATER: TeraType = INSTANCE.create(MiscUtilsKt.cobblemonResource("water"), new ElementalTypeTeraType(ElementalTypes.INSTANCE.getWATER()))

   private final val types: HashMap<ResourceLocation, TeraType> = new HashMap()

   @JvmStatic
   public fun random(legalOnly: Boolean): TeraType {
      val var10000: java.util.Collection = types.values();
      if (legalOnly) {
         val `$this$filter$iv`: java.lang.Iterable = var10000;
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$filter$iv) {
            if ((`element$iv$iv` as TeraType).getLegalAsStatic()) {
               `destination$iv$iv`.add(`element$iv$iv`);
            }
         }

         return CollectionsKt.random(`destination$iv$iv` as java.util.List, Random.Default as Random) as TeraType;
      } else {
         return CollectionsKt.random(var10000, Random.Default as Random) as TeraType;
      }
   }

   @JvmStatic
   public fun get(id: ResourceLocation): TeraType? {
      return types.get(id);
   }

   @JvmStatic
   public fun get(id: String): TeraType? {
      return get(MiscUtilsKt.cobblemonResource(id));
   }

   @JvmStatic
   public fun forElementalType(type: ElementalType): TeraType {
      val var10000: TeraType = get(MiscUtilsKt.cobblemonResource(type.getName()));
      return var10000;
   }

   private fun create(id: ResourceLocation, type: TeraType): TeraType {
      types.put(id, type);
      return type;
   }
}
