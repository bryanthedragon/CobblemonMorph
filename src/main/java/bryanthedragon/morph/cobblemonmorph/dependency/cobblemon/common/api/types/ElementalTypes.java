package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types

import java.util.ArrayList;
import java.util.NoSuchElementException
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

@SourceDebugExtension(["SMAP\nElementalTypes.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ElementalTypes.kt\ncom/cobblemon/mod/common/api/types/ElementalTypes\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,175:1\n288#2,2:176\n223#2,2:178\n*S KotlinDebug\n*F\n+ 1 ElementalTypes.kt\ncom/cobblemon/mod/common/api/types/ElementalTypes\n*L\n164#1:176,2\n168#1:178,2\n*E\n"])
public object ElementalTypes {
   public final val BUG: ElementalType
   public final val DARK: ElementalType
   public final val DRAGON: ElementalType
   public final val ELECTRIC: ElementalType
   public final val FAIRY: ElementalType
   public final val FIGHTING: ElementalType
   public final val FIRE: ElementalType
   public final val FLYING: ElementalType
   public final val GHOST: ElementalType
   public final val GRASS: ElementalType
   public final val GROUND: ElementalType
   public final val ICE: ElementalType
   public final val NORMAL: ElementalType
   public final val POISON: ElementalType
   public final val PSYCHIC: ElementalType
   public final val ROCK: ElementalType
   public final val STEEL: ElementalType
   public final val WATER: ElementalType
   private final val allTypes: MutableList<ElementalType> = (new ArrayList()) as java.util.List

   public fun register(name: String, displayName: MutableComponent, hue: Int, textureXMultiplier: Int): ElementalType {
      val var5: ElementalType = new ElementalType(name, displayName, hue, textureXMultiplier, null, 16, null);
      allTypes.add(var5);
      return var5;
   }

   public fun register(elementalType: ElementalType): ElementalType {
      allTypes.add(elementalType);
      return elementalType;
   }

   public fun get(name: String): ElementalType? {
      val var4: java.util.Iterator = allTypes.iterator();

      var var10000: Any;
      while (true) {
         if (var4.hasNext()) {
            val `element$iv`: Any = var4.next();
            if (!StringsKt.equals((`element$iv` as ElementalType).getName(), name, true)) {
               continue;
            }

            var10000 = `element$iv`;
            break;
         }

         var10000 = null;
         break;
      }

      return var10000 as ElementalType;
   }

   public fun getOrException(name: String): ElementalType {
      val `$this$first$iv`: java.lang.Iterable;
      for (Object element$iv : $this$first$iv) {
         if (StringsKt.equals((`element$iv` as ElementalType).getName(), name, true)) {
            return `element$iv` as ElementalType;
         }
      }

      throw new NoSuchElementException("Collection contains no element matching the predicate.");
   }

   public fun count(): Int {
      return allTypes.size();
   }

   public fun all(): List<ElementalType> {
      return CollectionsKt.toList(allTypes);
   }

   @JvmStatic
   fun {
      var var10000: ElementalTypes = INSTANCE;
      var var10002: MutableComponent = Component.m_237115_("cobblemon.type.normal");
      NORMAL = var10000.register("normal", var10002, 14540239, 0);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.type.fire");
      FIRE = var10000.register("fire", var10002, 15031346, 1);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.type.water");
      WATER = var10000.register("water", var10002, 4889576, 2);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.type.grass");
      GRASS = var10000.register("grass", var10002, 5094460, 3);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.type.electric");
      ELECTRIC = var10000.register("electric", var10002, 15716648, 4);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.type.ice");
      ICE = var10000.register("ice", var10002, 7062511, 5);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.type.fighting");
      FIGHTING = var10000.register("fighting", var10002, 12864604, 6);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.type.poison");
      POISON = var10000.register("poison", var10002, 10636248, 7);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.type.ground");
      GROUND = var10000.register("ground", var10002, 14195024, 8);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.type.flying");
      FLYING = var10000.register("flying", var10002, 12370431, 9);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.type.psychic");
      PSYCHIC = var10000.register("psychic", var10002, 14183126, 10);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.type.bug");
      BUG = var10000.register("bug", var10002, 10668081, 11);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.type.rock");
      ROCK = var10000.register("rock", var10002, 11179622, 12);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.type.ghost");
      GHOST = var10000.register("ghost", var10002, 9794277, 13);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.type.dragon");
      DRAGON = var10000.register("dragon", var10002, 5463528, 14);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.type.dark");
      DARK = var10000.register("dark", var10002, 6057138, 15);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.type.steel");
      STEEL = var10000.register("steel", var10002, 12831968, 16);
      var10000 = INSTANCE;
      var10002 = Component.m_237115_("cobblemon.type.fairy");
      FAIRY = var10000.register("fairy", var10002, 15364734, 17);
   }
}
