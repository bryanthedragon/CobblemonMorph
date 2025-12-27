package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import java.util.Locale

public const val QUOTE: Char = '"'

public fun String.splitMap(delimiter: String, assigner: String): MutableList<Pair<String, String?>> {
   val result: java.util.List = new ArrayList();
   val split: java.util.List = StringsKt.split$default(`$this$splitMap`, new java.lang.String[]{delimiter}, false, 0, 6, null);
   var var11: java.lang.String = null;

   for (java.lang.String argument : split) {
      if (var11 != null && StringsKt.endsWith$default(argument, '"', false, 2, null)) {
         val var25: java.lang.String = argument.substring(0, argument.length() - 1);
         var11 = "$var11$delimiter$var25";
         val var13: java.util.List = StringsKt.split$default(var11, new java.lang.String[]{assigner}, false, 0, 6, null);
         val var21: java.lang.String = (var13.get(0) as java.lang.String).toLowerCase(Locale.ROOT);
         val var16: java.lang.String = if (StringsKt.contains$default(var11, assigner, false, 2, null)) var13.get(1) as java.lang.String else null;
         var11 = null;
         result.add(TuplesKt.to(var21, var16));
      } else if (var11 == null) {
         if (StringsKt.contains$default(argument, assigner, false, 2, null)) {
            val equalsIndex: Int = StringsKt.indexOf$default(argument, assigner, 0, false, 6, null);
            var var17: java.lang.String = argument.substring(0, equalsIndex);
            var17 = var17.toLowerCase(Locale.ROOT);
            var17 = argument.substring(equalsIndex + 1);
            if (StringsKt.startsWith$default(var17, '"', false, 2, null)) {
               if (StringsKt.endsWith$default(var17, '"', false, 2, null)) {
                  val var10002: java.lang.String = var17.substring(1, var17.length() - 1);
                  result.add(TuplesKt.to(var17, var10002));
               } else {
                  val var24: java.lang.String = var17.substring(1);
                  var11 = "$var17$assigner$var24";
               }
            } else {
               result.add(TuplesKt.to(var17, var17));
            }
         } else if (StringsKt.startsWith$default(argument, '"', false, 2, null) && StringsKt.endsWith$default(argument, '"', false, 2, null)) {
            var var22: java.lang.String = argument.toLowerCase(Locale.ROOT);
            var22 = var22.substring(1, argument.length() - 1);
            result.add(TuplesKt.to(var22, null));
         } else if (!StringsKt.contains$default(argument, '"', false, 2, null)) {
            val var10001: java.lang.String = argument.toLowerCase(Locale.ROOT);
            result.add(TuplesKt.to(var10001, null));
         }
      } else {
         var11 = "$var11$delimiter$argument";
      }
   }

   return result;
}

public fun String.isLaterVersion(otherVersion: String): Boolean {
   if (`$this$isLaterVersion` === otherVersion) {
      return false;
   } else {
      val splits1: java.util.List = StringsKt.split$default(`$this$isLaterVersion`, new java.lang.String[]{"."}, false, 0, 6, null);
      val var10: java.util.List = StringsKt.split$default(otherVersion, new java.lang.String[]{"."}, false, 0, 6, null);
      val var11: java.lang.String = if (splits1.size() > var10.size()) `$this$isLaterVersion` else otherVersion;
      var i: Int = 0;

      for (int var6 = StringsKt.split$default(smaller, new java.lang.String[]{"."}, false, 0, 6, null).size(); i < var6; i++) {
         try {
            val var12: Int = Integer.parseInt(splits1.get(i) as java.lang.String);
            val v2: Int = Integer.parseInt(var10.get(i) as java.lang.String);
            if (var12 > v2) {
               return true;
            }

            if (v2 > var12) {
               return false;
            }
         } catch (var9: NumberFormatException) {
            Cobblemon.INSTANCE
               .getLOGGER()
               .error("Tried comparing versions $`$this$isLaterVersion` and $otherVersion but at least one of them isn't formatted like a version.");
            return false;
         }
      }

      return !(var11 == `$this$isLaterVersion`);
   }
}

public fun String.toProperties(): PokemonProperties {
   return PokemonProperties.Companion.parse$default(PokemonProperties.Companion, `$this$toProperties`, null, null, 6, null);
}

public fun String.toPokemon(): Pokemon {
   return toProperties(`$this$toPokemon`).create();
}
