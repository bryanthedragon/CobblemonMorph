package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail

import com.bedrockk.molang.runtime.struct.ArrayStruct
import com.bedrockk.molang.runtime.struct.VariableStruct
import com.bedrockk.molang.runtime.value.DoubleValue
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ModDependant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.CompositeSpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.RegisteredSpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.multiplier.WeightMultiplier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.MutableComponent

@SourceDebugExtension(["SMAP\nSpawnDetail.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawnDetail.kt\ncom/cobblemon/mod/common/api/spawning/detail/SpawnDetail\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,94:1\n1559#2:95\n1590#2,4:96\n2624#2,3:100\n1747#2,3:103\n*S KotlinDebug\n*F\n+ 1 SpawnDetail.kt\ncom/cobblemon/mod/common/api/spawning/detail/SpawnDetail\n*L\n70#1:95\n70#1:96,4\n78#1:100,3\n80#1:103,3\n*E\n"])
public abstract class SpawnDetail : ModDependant {
   public final var anticonditions: MutableList<SpawningCondition<*>> = (new ArrayList()) as java.util.List
   public final var bucket: SpawnBucket = new SpawnBucket("", 0.0F)
   public final var compositeCondition: CompositeSpawningCondition?
   public final var conditions: MutableList<SpawningCondition<*>> = (new ArrayList()) as java.util.List
   public final lateinit var context: RegisteredSpawningContext<*>
   public final var displayName: String?
   public final var height: Int = -1
   public final var id: String = ""
   public final var labels: MutableList<String> = (new ArrayList()) as java.util.List
   public open var neededInstalledMods: List<String> = CollectionsKt.emptyList()
   public open var neededUninstalledMods: List<String> = CollectionsKt.emptyList()
   public final var percentage: Float = -1.0F
   public final val struct: VariableStruct = new VariableStruct()
   public abstract val type: String
   public final var weight: Float = -1.0F
   public final var weightMultipliers: MutableList<WeightMultiplier> = (new ArrayList()) as java.util.List
   public final var width: Int = -1

   public open fun autoLabel() {
      this.struct.setDirectly("weight", new DoubleValue((double)this.weight));
      this.struct.setDirectly("percentage", new DoubleValue((double)this.percentage));
      this.struct.setDirectly("id", new StringValue(this.id));
      this.struct.setDirectly("bucket", new StringValue(this.bucket.getName()));
      this.struct.setDirectly("width", new DoubleValue((double)this.width));
      this.struct.setDirectly("height", new DoubleValue((double)this.height));
      this.struct.setDirectly("context", new StringValue(this.getContext().getName()));
      val `$this$mapIndexed$iv`: java.lang.Iterable = this.labels;
      val var13: VariableStruct = this.struct;
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$mapIndexed$iv`, 10));
      var `index$iv$iv`: Int = 0;

      for (Object item$iv$iv : $this$mapIndexed$iv) {
         val var9: Int = `index$iv$iv`++;
         if (var9 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         `destination$iv$iv`.add(TuplesKt.to(java.lang.String.valueOf(var9), new StringValue(`item$iv$iv` as java.lang.String)));
      }

      var13.setDirectly("labels", new ArrayStruct(MapsKt.toMap(`destination$iv$iv` as java.util.List)));
   }

   public open fun getName(): MutableComponent {
      var var10000: MutableComponent = if (this.displayName != null) MiscUtilsKt.asTranslated(this.displayName) else null;
      if (var10000 == null) {
         var10000 = TextKt.text(this.id);
      }

      return var10000;
   }

   public open fun isSatisfiedBy(ctx: SpawningContext): Boolean {
      if (!ctx.preFilter(this)) {
         return false;
      } else {
         if (!this.conditions.isEmpty()) {
            val `$this$any$iv`: java.lang.Iterable = this.conditions;
            var var10000: Boolean;
            if (this.conditions is java.util.Collection && this.conditions.isEmpty()) {
               var10000 = true;
            } else {
               val var4: java.util.Iterator = `$this$any$iv`.iterator();

               while (true) {
                  if (!var4.hasNext()) {
                     var10000 = true;
                     break;
                  }

                  if ((var4.next() as SpawningCondition).isSatisfiedBy(ctx)) {
                     var10000 = false;
                     break;
                  }
               }
            }

            if (var10000) {
               return false;
            }
         }

         if (!this.anticonditions.isEmpty()) {
            val var8: java.lang.Iterable = this.anticonditions;
            var var14: Boolean;
            if (this.anticonditions is java.util.Collection && this.anticonditions.isEmpty()) {
               var14 = false;
            } else {
               val var10: java.util.Iterator = var8.iterator();

               while (true) {
                  if (!var10.hasNext()) {
                     var14 = false;
                     break;
                  }

                  if ((var10.next() as SpawningCondition).isSatisfiedBy(ctx)) {
                     var14 = true;
                     break;
                  }
               }
            }

            if (var14) {
               return false;
            }
         }

         if (this.compositeCondition != null && !this.compositeCondition.satisfiedBy(ctx)) {
            return false;
         } else {
            return ctx.postFilter(this);
         }
      }
   }

   public open fun isValid(): Boolean {
      return this.isModDependencySatisfied();
   }

   public abstract fun doSpawn(ctx: SpawningContext): SpawnAction<*> {
   }

   override fun isModDependencySatisfied(): Boolean {
      return ModDependant.DefaultImpls.isModDependencySatisfied(this);
   }

   public companion object {
      public final val spawnDetailTypes: MutableMap<String, RegisteredSpawnDetail<*>>

      public fun <T : SpawnDetail> registerSpawnType(name: String, detailClass: Class<Any>) {
         this.getSpawnDetailTypes().put(name, new RegisteredSpawnDetail(detailClass));
      }
   }
}
