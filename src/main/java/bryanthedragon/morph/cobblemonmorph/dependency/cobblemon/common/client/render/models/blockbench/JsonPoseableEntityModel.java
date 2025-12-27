package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.InstanceCreator
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import java.lang.reflect.Type
import java.util.ArrayList;
import java.util.function.Supplier
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity
import org.jetbrains.annotations.NotNull

public abstract class JsonPoseableEntityModel<T extends Entity> : PoseableEntityModel<T> {
   public open val rootPart: Bone

   open fun JsonPoseableEntityModel(rootPart: Bone) {
      super(null, 1, null);
      this.rootPart = rootPart;
   }

   public override fun registerPoses() {
   }

   public object JsonModelExclusion : ExclusionStrategy {
      public open fun shouldSkipField(f: FieldAttributes): Boolean {
         return !CollectionsKt.listOf(new java.lang.String[]{"JsonPokemonPoseableModel", "JsonGenericPoseableModel", "PoseableEntityModel", "Pose"})
            .contains(f.getDeclaringClass().getSimpleName());
      }

      public open fun shouldSkipClass(clazz: Class<*>): Boolean {
         return false;
      }
   }

   public class JsonPoseableModelAdapter<T extends Entity>(constructor: (ModelPart) -> JsonPoseableEntityModel<Any>) : InstanceCreator<PoseableEntityModel<T>> {
      public final val constructor: (ModelPart) -> JsonPoseableEntityModel<Any>
      public final var model: JsonPoseableEntityModel<Any>?
      public final var modelPart: ModelPart?

      init {
         this.constructor = constructor;
      }

      public open fun createInstance(type: Type): JsonPoseableEntityModel<Any> {
         val var10000: Function1 = this.constructor;
         var var10001: ModelPart = this.modelPart;
         val var2: Any = var10000.invoke(var10001);
         val it: JsonPoseableEntityModel = var2 as JsonPoseableEntityModel;
         this.model = var2 as JsonPoseableEntityModel<T>;
         var10001 = this.modelPart;
         it.loadAllNamedChildren(var10001);
         return var2 as JsonPoseableEntityModel<T>;
      }
   }

   @SourceDebugExtension(["SMAP\nJsonPoseableEntityModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonPoseableEntityModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel$PoseAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,100:1\n2661#2,7:101\n1603#2,9:110\n1855#2:119\n1856#2:121\n1612#2:122\n37#3,2:108\n1#4:120\n*S KotlinDebug\n*F\n+ 1 JsonPoseableEntityModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel$PoseAdapter\n*L\n80#1:101,7\n93#1:110,9\n93#1:119\n93#1:121\n93#1:122\n89#1:108,2\n93#1:120\n*E\n"])
   public class PoseAdapter<T extends Entity>(modelFinder: () -> PoseableEntityModel<Any>) : JsonDeserializer<Pose<T, ModelFrame>> {
      public final val modelFinder: () -> PoseableEntityModel<Any>

      init {
         this.modelFinder = modelFinder;
      }

      public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): Pose<Any, ModelFrame> {
         val model: PoseableEntityModel = this.modelFinder.invoke() as PoseableEntityModel;
         val pose: JsonPose = new JsonPose(model, json as JsonObject);
         val conditionsList: java.util.List = new ArrayList();
         val var10000: JsonElement = (json as JsonObject).get("isTouchingWater");
         val mustBeTouchingWater: java.lang.Boolean = if (var10000 != null) var10000.getAsBoolean() else null;
         if (mustBeTouchingWater != null) {
            conditionsList.add(new Function1<T, java.lang.Boolean>(mustBeTouchingWater) {
               {
                  super(1);
                  this.$mustBeTouchingWater = `$mustBeTouchingWater`;
               }

               @NotNull
               public final java.lang.Boolean invoke(@NotNull T it) {
                  return this.$mustBeTouchingWater == it.m_20069_();
               }
            });
         }

         val var36: Function1;
         if (conditionsList.isEmpty()) {
            var36 = <unrepresentable>.INSTANCE;
         } else {
            val var12: java.util.Iterator = conditionsList.iterator();
            if (!var12.hasNext()) {
               throw new UnsupportedOperationException("Empty collection can't be reduced.");
            }

            var `$this$mapNotNull$iv`: Any = var12.next();

            while (iterator$iv.hasNext()) {
               val function: Function1 = var12.next() as Function1;
               val `$i$f$mapNotNull`: Function1 = `$this$mapNotNull$iv` as Function1;
               `$this$mapNotNull$iv` = (new Function1<T, java.lang.Boolean>(`$i$f$mapNotNull`, function) {
                  {
                     super(1);
                     this.$acc = `$acc`;
                     this.$function = `$function`;
                  }

                  @NotNull
                  public final java.lang.Boolean invoke(@NotNull T it) {
                     return this.$acc.invoke(it) as java.lang.Boolean && this.$function.invoke(it) as java.lang.Boolean;
                  }
               }) as Function1;
            }

            var36 = `$this$mapNotNull$iv` as Function1;
         }

         val var30: Pose = new Pose(
            pose.getPoseName(),
            CollectionsKt.toSet(pose.getPoseTypes()),
            var36,
            null,
            pose.getTransformTicks(),
            null,
            pose.getIdleAnimations(),
            pose.getTransformedParts(),
            pose.getQuirks().toArray(new ModelQuirk[0]),
            40,
            null
         );
         val var37: java.util.Map = var30.getTransitions();
         val var33: java.lang.Iterable = pose.getTransitions();
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv$iv : var33) {
            val it: JsonPose.JsonPoseTransition = `element$iv$iv$iv` as JsonPose.JsonPoseTransition;
            val var38: Pair = TuplesKt.to(
               it.getTo(), new Function2<Pose<T, ? extends ModelFrame>, Pose<T, ? extends ModelFrame>, StatefulAnimation<T, ModelFrame>>(it, model) {
                  {
                     super(2);
                     this.$it = `$it`;
                     this.$model = `$model`;
                  }

                  @NotNull
                  public final StatefulAnimation<T, ModelFrame> invoke(@NotNull Pose<T, ? extends ModelFrame> var1, @NotNull Pose<T, ? extends ModelFrame> var2) {
                     val var10000: Any = this.$it.getAnimation().resolveObject(this.$model.getRuntime()).getObj();
                     return var10000 as StatefulAnimation<T, ModelFrame>;
                  }
               }
            );
            if (var38 != null) {
               `destination$iv$iv`.add(var38);
            }
         }

         var37.putAll(MapsKt.toMap(`destination$iv$iv` as java.util.List));
         return var30;
      }
   }

   @SourceDebugExtension(["SMAP\nJsonPoseableEntityModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonPoseableEntityModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel$StatefulAnimationAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,100:1\n1549#2:101\n1620#2,3:102\n*S KotlinDebug\n*F\n+ 1 JsonPoseableEntityModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/JsonPoseableEntityModel$StatefulAnimationAdapter\n*L\n61#1:101\n61#1:102,3\n*E\n"])
   public class StatefulAnimationAdapter<T extends Entity>(modelFinder: () -> PoseableEntityModel<Any>) :
      JsonDeserializer<Supplier<StatefulAnimation<T, ModelFrame>>> {
      public final val modelFinder: () -> PoseableEntityModel<Any>

      init {
         this.modelFinder = modelFinder;
      }

      public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): Supplier<StatefulAnimation<Any, ModelFrame>> {
         val animString: java.lang.String = (json as JsonPrimitive).getAsString();
         val var16: java.lang.Iterable = StringsKt.split$default(
            StringsKt.replace$default(StringsKt.replace$default(animString, "bedrock(", "", false, 4, null), ")", "", false, 4, null),
            new java.lang.String[]{","},
            false,
            0,
            6,
            null
         );
         val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var16, 10));

         for (Object item$iv$iv : $this$map$iv) {
            `destination$iv$iv`.add(StringsKt.trim(`item$iv$iv` as java.lang.String).toString());
         }

         return JsonPoseableEntityModel.StatefulAnimationAdapter::deserialize$lambda$0;
      }

      @JvmStatic
      fun `deserialize$lambda$0`(`this$0`: JsonPoseableEntityModel.StatefulAnimationAdapter, `$file`: java.lang.String, `$animation`: java.lang.String): StatefulAnimation {
         return PoseableEntityModel.bedrockStateful$default(`this$0`.modelFinder.invoke() as PoseableEntityModel, `$file`, `$animation`, null, 4, null);
      }
   }
}
