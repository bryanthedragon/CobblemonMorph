package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import kotlin.jvm.internal.SourceDebugExtension

public interface ModDependant {
   public var neededInstalledMods: List<String>
   public var neededUninstalledMods: List<String>

   public open fun isModDependencySatisfied(): Boolean {
   }

   // $VF: Class flags could not be determined
   @SourceDebugExtension(["SMAP\nModDependant.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ModDependant.kt\ncom/cobblemon/mod/common/api/ModDependant$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,32:1\n1747#2,3:33\n1747#2,3:36\n*S KotlinDebug\n*F\n+ 1 ModDependant.kt\ncom/cobblemon/mod/common/api/ModDependant$DefaultImpls\n*L\n24#1:33,3\n26#1:36,3\n*E\n"])
   internal class DefaultImpls {
      @JvmStatic
      fun isModDependencySatisfied(`$this`: ModDependant): Boolean {
         if (!`$this`.getNeededInstalledMods().isEmpty()) {
            val `$this$any$iv`: java.lang.Iterable = `$this`.getNeededInstalledMods();
            var var10000: Boolean;
            if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
               var10000 = false;
            } else {
               label82: {
                  for (Object element$iv : $this$any$iv) {
                     if (!Cobblemon.INSTANCE.getImplementation().isModInstalled(`element$iv` as java.lang.String)) {
                        var10000 = true;
                        break label82;
                     }
                  }

                  var10000 = false;
               }
            }

            if (var10000) {
               return false;
            }
         }

         if (!`$this`.getNeededUninstalledMods().isEmpty()) {
            val `$this$any$ivx`: java.lang.Iterable = `$this`.getNeededUninstalledMods();
            var var13: Boolean;
            if (`$this$any$ivx` is java.util.Collection && (`$this$any$ivx` as java.util.Collection).isEmpty()) {
               var13 = false;
            } else {
               val var9: java.util.Iterator = `$this$any$ivx`.iterator();

               while (true) {
                  if (!var9.hasNext()) {
                     var13 = false;
                     break;
                  }

                  if (Cobblemon.INSTANCE.getImplementation().isModInstalled(var9.next() as java.lang.String)) {
                     var13 = true;
                     break;
                  }
               }
            }

            if (var13) {
               return false;
            }
         }

         return true;
      }
   }
}
