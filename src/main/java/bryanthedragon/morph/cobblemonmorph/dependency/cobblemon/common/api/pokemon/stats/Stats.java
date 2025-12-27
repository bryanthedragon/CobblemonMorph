package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat.Type
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.EnumSet
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

public enum Stats(identifier: ResourceLocation, displayName: Component, type: Type, showdownId: String) : Stat {
   HP,
   ATTACK,
   DEFENCE,
   SPECIAL_ATTACK,
   SPECIAL_DEFENCE,
   SPEED,
   EVASION,
   ACCURACY
   public open val displayName: Component
   public open val identifier: ResourceLocation
   public open val showdownId: String
   public open val type: Type
   @JvmStatic
   public Stats.Companion Companion = new Stats.Companion(null);
   @JvmStatic
   private java.util.Set<Stat> ALL;
   @JvmStatic
   private java.util.Set<Stat> PERMANENT;
   @JvmStatic
   private java.util.Set<Stat> BATTLE_ONLY;

   init {
      this.identifier = identifier;
      this.displayName = displayName;
      this.type = type;
      this.showdownId = showdownId;
   }

   // $VF: Failed to inline enum fields
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @JvmStatic
   fun {
      var var10004: ResourceLocation = MiscUtilsKt.cobblemonResource("hp");
      var var10005: MutableComponent = LocalizationUtilsKt.lang("stat.hp.name");
      HP = new Stats(var10004, var10005 as Component, Stat.Type.PERMANENT, "hp");
      var10004 = MiscUtilsKt.cobblemonResource("attack");
      var10005 = LocalizationUtilsKt.lang("stat.attack.name");
      ATTACK = new Stats(var10004, var10005 as Component, Stat.Type.PERMANENT, "atk");
      var10004 = MiscUtilsKt.cobblemonResource("defence");
      var10005 = LocalizationUtilsKt.lang("stat.defence.name");
      DEFENCE = new Stats(var10004, var10005 as Component, Stat.Type.PERMANENT, "def");
      var10004 = MiscUtilsKt.cobblemonResource("special_attack");
      var10005 = LocalizationUtilsKt.lang("stat.special_attack.name");
      SPECIAL_ATTACK = new Stats(var10004, var10005 as Component, Stat.Type.PERMANENT, "spa");
      var10004 = MiscUtilsKt.cobblemonResource("special_defence");
      var10005 = LocalizationUtilsKt.lang("stat.special_defence.name");
      SPECIAL_DEFENCE = new Stats(var10004, var10005 as Component, Stat.Type.PERMANENT, "spd");
      var10004 = MiscUtilsKt.cobblemonResource("speed");
      var10005 = LocalizationUtilsKt.lang("stat.speed.name");
      SPEED = new Stats(var10004, var10005 as Component, Stat.Type.PERMANENT, "spe");
      var10004 = MiscUtilsKt.cobblemonResource("evasion");
      var10005 = LocalizationUtilsKt.lang("stat.evasion.name");
      EVASION = new Stats(var10004, var10005 as Component, Stat.Type.BATTLE_ONLY, "evasion");
      var10004 = MiscUtilsKt.cobblemonResource("accuracy");
      var10005 = LocalizationUtilsKt.lang("stat.accuracy.name");
      ACCURACY = new Stats(var10004, var10005 as Component, Stat.Type.BATTLE_ONLY, "accuracy");
      var var10000: EnumSet = EnumSet.allOf(Stats.class);
      ALL = var10000;
      var10000 = EnumSet.of(HP, new Stats[]{ATTACK, DEFENCE, SPECIAL_ATTACK, SPECIAL_DEFENCE, SPEED});
      PERMANENT = var10000;
      var10000 = EnumSet.of(EVASION, ACCURACY);
      BATTLE_ONLY = var10000;
   }

   public companion object {
      public final val ALL: Set<Stat>
      public final val BATTLE_ONLY: Set<Stat>
      public final val PERMANENT: Set<Stat>

      public fun getStat(statKey: String): Stats {
         switch (statKey.hashCode()) {
            case -1380056955:
               if (statKey.equals("evasion")) {
                  return Stats.EVASION;
               }
               break;
            case -1085397472:
               if (statKey.equals("Defense")) {
                  return Stats.DEFENCE;
               }
               break;
            case 96920:
               if (statKey.equals("atk")) {
                  return Stats.ATTACK;
               }
               break;
            case 99333:
               if (statKey.equals("def")) {
                  return Stats.DEFENCE;
               }
               break;
            case 114084:
               if (statKey.equals("spa")) {
                  return Stats.SPECIAL_ATTACK;
               }
               break;
            case 114087:
               if (statKey.equals("spd")) {
                  return Stats.SPECIAL_DEFENCE;
               }
               break;
            case 114088:
               if (statKey.equals("spe")) {
                  return Stats.SPEED;
               }
               break;
            case 1971575400:
               if (statKey.equals("Attack")) {
                  return Stats.ATTACK;
               }
            default:
         }

         return Stats.ACCURACY;
      }

      public fun getSeverity(stages: Int): String {
         var var10000: java.lang.String;
         switch (stages) {
            case 0:
               var10000 = "cap.single";
               break;
            case 1:
               var10000 = "slight";
               break;
            case 2:
               var10000 = "sharp";
               break;
            default:
               var10000 = "severe";
         }

         return var10000;
      }
   }
}
