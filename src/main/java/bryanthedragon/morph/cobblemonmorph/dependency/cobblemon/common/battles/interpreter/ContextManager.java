package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext.Type
import java.util.ArrayList;
import java.util.Arrays
import java.util.HashMap
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nContextManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContextManager.kt\ncom/cobblemon/mod/common/battles/interpreter/ContextManager\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,104:1\n13579#2:105\n13580#2:120\n13579#2,2:122\n13579#2:124\n13580#2:129\n13579#2:130\n13580#2:133\n361#3,7:106\n361#3,7:113\n1#4:121\n37#5,2:125\n37#5,2:127\n37#5,2:131\n*S KotlinDebug\n*F\n+ 1 ContextManager.kt\ncom/cobblemon/mod/common/battles/interpreter/ContextManager\n*L\n26#1:105\n26#1:120\n58#1:122,2\n66#1:124\n66#1:129\n78#1:130\n78#1:133\n28#1:106,7\n33#1:113,7\n71#1:125,2\n72#1:127,2\n81#1:131,2\n*E\n"])
public class ContextManager {
   public final val buckets: HashMap<Type, MutableCollection<BattleContext>> = new HashMap()

   public fun add(vararg contexts: BattleContext) {
      for (Object element$iv : contexts) {
         if (((BattleContext)`element$iv`).getType().getExclusive()) {
            val `key$iv`: java.util.Map = this.buckets;
            val `$i$f$getOrPut`: Any = ((BattleContext)`element$iv`).getType();
            val `answer$iv`: Any = `key$iv`.get(`$i$f$getOrPut`);
            val var10000: Any;
            if (`answer$iv` == null) {
               val var20: Any = new ArrayList();
               `key$iv`.put(`$i$f$getOrPut`, var20);
               var10000 = var20;
            } else {
               var10000 = `answer$iv`;
            }

            val `$this$getOrPut$iv`: java.util.Collection = var10000 as java.util.Collection;
            (var10000 as java.util.Collection).clear();
            `$this$getOrPut$iv`.add(`element$iv`);
         } else {
            val var21: java.util.Map = this.buckets;
            val var15: Any = ((BattleContext)`element$iv`).getType();
            val var17: Any = var21.get(var15);
            val var22: Any;
            if (var17 == null) {
               val var19: Any = new ArrayList();
               var21.put(var15, var19);
               var22 = var19;
            } else {
               var22 = var17;
            }

            (var22 as java.util.Collection).add(`element$iv`);
         }
      }
   }

   public fun addUnique(context: BattleContext) {
      var var10000: java.util.Collection = this.buckets.get(context.getType());
      val var9: BattleContext;
      if (var10000 != null) {
         val var4: java.util.Iterator = var10000.iterator();

         while (true) {
            if (!var4.hasNext()) {
               var10000 = null;
               break;
            }

            val var5: Any = var4.next();
            if ((var5 as BattleContext).getId() == context.getId()) {
               var10000 = (java.util.Collection)var5;
               break;
            }
         }

         var9 = var10000 as BattleContext;
      } else {
         var9 = null;
      }

      if (var9 == null) {
         this.add(context);
      }
   }

   public fun remove(contextID: String, bucketType: Type) {
      if (bucketType.getExclusive()) {
         val var10000: java.util.Collection = this.buckets.get(bucketType);
         if (var10000 != null) {
            var10000.clear();
         }
      } else {
         val var3: java.util.Collection = this.buckets.get(bucketType);
         if (var3 != null) {
            var3.removeIf(ContextManager::remove$lambda$4);
         }
      }
   }

   public fun clear(vararg bucketTypes: Type) {
      for (Object element$iv : bucketTypes) {
         val var10000: java.util.Collection = this.buckets.get(`element$iv`);
         if (var10000 != null) {
            var10000.clear();
         }
      }
   }

   public fun swap(with: ContextManager, vararg bucketTypes: Type) {
      for (Object element$iv : bucketTypes) {
         val newContexts: java.util.Collection = this.buckets.get(`element$iv`);
         var var10000: java.util.List;
         if (newContexts != null) {
            var10000 = CollectionsKt.toMutableList(newContexts);
         } else {
            var10000 = null;
         }

         val var12: java.util.Collection = with.buckets.get(`element$iv`);
         if (var12 != null) {
            var10000 = CollectionsKt.toMutableList(var12);
         } else {
            var10000 = null;
         }

         this.clear((BattleContext.Type)`element$iv`);
         with.clear((BattleContext.Type)`element$iv`);
         if (var10000 != null) {
            val var18: Array<BattleContext> = var10000.toArray(new BattleContext[0]);
            with.add(Arrays.copyOf(var18, var18.length));
         }

         if (var10000 != null) {
            val var25: Array<BattleContext> = var10000.toArray(new BattleContext[0]);
            this.add(Arrays.copyOf(var25, var25.length));
         }
      }
   }

   public fun copy(with: ContextManager, vararg bucketTypes: Type) {
      for (Object element$iv : bucketTypes) {
         val var10: java.util.Collection = with.buckets.get(`element$iv`);
         val var10000: java.util.List;
         if (var10 != null) {
            var10000 = CollectionsKt.toMutableList(var10);
         } else {
            var10000 = null;
         }

         this.clear((BattleContext.Type)`element$iv`);
         if (var10000 != null) {
            val var17: Array<BattleContext> = var10000.toArray(new BattleContext[0]);
            this.add(Arrays.copyOf(var17, var17.length));
         }
      }
   }

   public fun get(bucketType: Type): Collection<BattleContext>? {
      return this.buckets.get(bucketType);
   }

   @JvmStatic
   fun `remove$lambda$4`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }

   @SourceDebugExtension(["SMAP\nContextManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContextManager.kt\ncom/cobblemon/mod/common/battles/interpreter/ContextManager$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,104:1\n1855#2:105\n1856#2:107\n1#3:106\n*S KotlinDebug\n*F\n+ 1 ContextManager.kt\ncom/cobblemon/mod/common/battles/interpreter/ContextManager$Companion\n*L\n98#1:105\n98#1:107\n*E\n"])
   public companion object {
      public fun scoop(contextID: String, vararg contextBuckets: Collection<BattleContext>?): BattleContext? {
         val `$this$forEach$iv`: java.lang.Iterable;
         for (Object element$iv : $this$forEach$iv) {
            val it: java.lang.Iterable = `element$iv` as java.util.Collection;
            var var10: Any = null;

            for (Object var12 : it) {
               if ((var12 as BattleContext).getId() == contextID) {
                  var10 = var12;
               }
            }

            val var10000: BattleContext = var10 as BattleContext;
            if (var10 as BattleContext != null) {
               return var10000;
            }
         }

         return null;
      }
   }
}
