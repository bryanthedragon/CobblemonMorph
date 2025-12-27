package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.PokemonSideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.JsonPose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.EntityExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.InstanceCreator
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import java.lang.reflect.Type
import java.util.ArrayList;
import java.util.Map.Entry
import java.util.function.Supplier
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nJsonPokemonPoseableModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonPokemonPoseableModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pokemon/JsonPokemonPoseableModel\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,205:1\n1#2:206\n*E\n"])
public class JsonPokemonPoseableModel(rootPart: Bone) : PokemonPoseableModel, HeadedFrame {
   public final val cry: Supplier<StatefulAnimation<PokemonEntity, ModelFrame>>?
   public open val cryAnimation: CryProvider
   public final val faint: Supplier<StatefulAnimation<PokemonEntity, ModelFrame>>?

   public open val head: Bone
      public open get() {
         return this.head$delegate.getValue() as Bone;
      }


   public final val headJoint: String?
   public open var portraitScale: Float
   public open var portraitTranslation: Vec3
   public open var profileScale: Float
   public open var profileTranslation: Vec3
   public open val rootPart: ModelPart

   init {
      val it: Entry = CollectionsKt.first((rootPart as ModelPart).f_104213_.entrySet()) as Entry;
      val var10001: ModelPart = rootPart as ModelPart;
      val var10002: Any = it.getKey();
      this.rootPart = this.registerChildWithAllChildren(var10001, var10002 as java.lang.String);
      this.head$delegate = LazyKt.lazy((new Function0<Bone>(this, rootPart) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$rootPart = `$rootPart`;
         }

         @NotNull
         public final Bone invoke() {
            val var10000: java.lang.String = this.this$0.getHeadJoint();
            if (var10000 != null) {
               val var5: ModelPart = this.this$0.getPart(var10000);
               if (var5 != null) {
                  return var5 as Bone;
               }
            }

            return this.$rootPart;
         }
      }) as Function0);
      this.portraitScale = 1.0F;
      this.portraitTranslation = Vec3.f_82478_;
      this.profileScale = 1.0F;
      this.profileTranslation = Vec3.f_82478_;
      this.cryAnimation = JsonPokemonPoseableModel::cryAnimation$lambda$1;
   }

   public override fun registerPoses() {
   }

   public override fun getFaintAnimation(pokemonEntity: PokemonEntity, state: PoseableEntityState<PokemonEntity>): StatefulAnimation<PokemonEntity, ModelFrame>? {
      return if (this.faint != null) this.faint.get() else null;
   }

   override fun <T extends Entity> singleBoneLook(
      invertX: Boolean,
      invertY: Boolean,
      disableX: Boolean,
      disableY: Boolean,
      pitchMultiplier: java.lang.Float?,
      yawMultiplier: java.lang.Float?,
      maxPitch: java.lang.Float?,
      minPitch: java.lang.Float?,
      maxYaw: java.lang.Float?,
      minYaw: java.lang.Float?
   ): SingleBoneLookAnimation<T> {
      return HeadedFrame.DefaultImpls.singleBoneLook(
         this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw
      );
   }

   @JvmStatic
   fun `cryAnimation$lambda$1`(`this$0`: JsonPokemonPoseableModel, var1: PokemonEntity, var2: PoseableEntityState): StatefulAnimation {
      return if (`this$0`.cry != null) `this$0`.cry.get() else null;
   }

   public companion object {
      public final val ANIMATION_FACTORIES: MutableMap<String, AnimationReferenceFactory>
      public final val gson: Gson

      public fun registerFactory(id: String, factory: AnimationReferenceFactory) {
         this.getANIMATION_FACTORIES().put(id, factory);
      }
   }

   public object JsonModelExclusion : ExclusionStrategy {
      public open fun shouldSkipField(f: FieldAttributes): Boolean {
         return !CollectionsKt.listOf(new java.lang.String[]{"JsonPokemonPoseableModel", "PoseableEntityModel", "Pose"})
            .contains(f.getDeclaringClass().getSimpleName());
      }

      public open fun shouldSkipClass(clazz: Class<*>): Boolean {
         return false;
      }
   }

   public object JsonPokemonPoseableModelAdapter : InstanceCreator<JsonPokemonPoseableModel> {
      public final var model: JsonPokemonPoseableModel?
      public final var modelPart: Bone?

      public open fun createInstance(type: Type): JsonPokemonPoseableModel {
         val var10002: Bone = modelPart;
         val var2: JsonPokemonPoseableModel = new JsonPokemonPoseableModel(var10002);
         model = var2;
         val var10001: Bone = modelPart;
         var2.loadAllNamedChildren(var10001);
         return var2;
      }
   }

   @SourceDebugExtension(["SMAP\nJsonPokemonPoseableModel.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonPokemonPoseableModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pokemon/JsonPokemonPoseableModel$PoseAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,205:1\n2661#2,7:206\n1603#2,9:215\n1855#2:224\n1856#2:226\n1612#2:227\n37#3,2:213\n1#4:225\n*S KotlinDebug\n*F\n+ 1 JsonPokemonPoseableModel.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pokemon/JsonPokemonPoseableModel$PoseAdapter\n*L\n184#1:206,7\n198#1:215,9\n198#1:224\n198#1:226\n198#1:227\n194#1:213,2\n198#1:225\n*E\n"])
   public object PoseAdapter : JsonDeserializer<Pose<PokemonEntity, ModelFrame>> {
      public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): Pose<PokemonEntity, ModelFrame> {
         val var10000: JsonPokemonPoseableModel = JsonPokemonPoseableModel.JsonPokemonPoseableModelAdapter.INSTANCE.getModel();
         val model: JsonPokemonPoseableModel = var10000;
         val pose: JsonPose = new JsonPose(var10000, json as JsonObject);
         val conditionsList: java.util.List = new ArrayList();
         val var50: JsonElement = (json as JsonObject).get("isBattle");
         val mustBeInBattle: java.lang.Boolean = if (var50 != null) var50.getAsBoolean() else null;
         if (mustBeInBattle != null) {
            conditionsList.add(new Function1<PokemonEntity, java.lang.Boolean>(mustBeInBattle) {
               {
                  super(1);
                  this.$mustBeInBattle = `$mustBeInBattle`;
               }

               @NotNull
               public final java.lang.Boolean invoke(@NotNull PokemonEntity it) {
                  return this.$mustBeInBattle == it.isBattling();
               }
            });
         }

         val var51: JsonElement = (json as JsonObject).get("isTouchingWater");
         val mustBeTouchingWater: java.lang.Boolean = if (var51 != null) var51.getAsBoolean() else null;
         if (mustBeTouchingWater != null) {
            conditionsList.add(new Function1<PokemonEntity, java.lang.Boolean>(mustBeTouchingWater) {
               {
                  super(1);
                  this.$mustBeTouchingWater = `$mustBeTouchingWater`;
               }

               @NotNull
               public final java.lang.Boolean invoke(@NotNull PokemonEntity it) {
                  return this.$mustBeTouchingWater == it.m_20069_();
               }
            });
         }

         val var52: JsonElement = (json as JsonObject).get("isTouchingWaterOrRain");
         val mustBeTouchingWaterOrRain: java.lang.Boolean = if (var52 != null) var52.getAsBoolean() else null;
         if (mustBeTouchingWaterOrRain != null) {
            conditionsList.add(new Function1<PokemonEntity, java.lang.Boolean>(mustBeTouchingWaterOrRain) {
               {
                  super(1);
                  this.$mustBeTouchingWaterOrRain = `$mustBeTouchingWaterOrRain`;
               }

               @NotNull
               public final java.lang.Boolean invoke(@NotNull PokemonEntity it) {
                  return this.$mustBeTouchingWaterOrRain == it.m_20070_();
               }
            });
         }

         val var53: JsonElement = (json as JsonObject).get("isSubmergedInWater");
         val mustBeSubmergedInWater: java.lang.Boolean = if (var53 != null) var53.getAsBoolean() else null;
         if (mustBeSubmergedInWater != null) {
            conditionsList.add(new Function1<PokemonEntity, java.lang.Boolean>(mustBeSubmergedInWater) {
               {
                  super(1);
                  this.$mustBeSubmergedInWater = `$mustBeSubmergedInWater`;
               }

               @NotNull
               public final java.lang.Boolean invoke(@NotNull PokemonEntity it) {
                  return this.$mustBeSubmergedInWater == it.m_5842_();
               }
            });
         }

         val var54: JsonElement = (json as JsonObject).get("isStandingOnRedSand");
         val mustBeStandingOnRedSand: java.lang.Boolean = if (var54 != null) var54.getAsBoolean() else null;
         if (mustBeStandingOnRedSand != null) {
            conditionsList.add(new Function1<PokemonEntity, java.lang.Boolean>(mustBeStandingOnRedSand) {
               {
                  super(1);
                  this.$mustBeStandingOnRedSand = `$mustBeStandingOnRedSand`;
               }

               @NotNull
               public final java.lang.Boolean invoke(@NotNull PokemonEntity it) {
                  return this.$mustBeStandingOnRedSand == EntityExtensionsKt.isStandingOnRedSand(it as Entity);
               }
            });
         }

         val var55: JsonElement = (json as JsonObject).get("isStandingOnSand");
         val mustBeStandingOnSand: java.lang.Boolean = if (var55 != null) var55.getAsBoolean() else null;
         if (mustBeStandingOnSand != null) {
            conditionsList.add(new Function1<PokemonEntity, java.lang.Boolean>(mustBeStandingOnSand) {
               {
                  super(1);
                  this.$mustBeStandingOnSand = `$mustBeStandingOnSand`;
               }

               @NotNull
               public final java.lang.Boolean invoke(@NotNull PokemonEntity it) {
                  return this.$mustBeStandingOnSand == EntityExtensionsKt.isStandingOnSand(it as Entity);
               }
            });
         }

         val var56: JsonElement = (json as JsonObject).get("isStandingOnSandOrRedSand");
         val mustBeStandingOnSandOrRedSand: java.lang.Boolean = if (var56 != null) var56.getAsBoolean() else null;
         if (mustBeStandingOnSandOrRedSand != null) {
            conditionsList.add(new Function1<PokemonEntity, java.lang.Boolean>(mustBeStandingOnSandOrRedSand) {
               {
                  super(1);
                  this.$mustBeStandingOnSandOrRedSand = `$mustBeStandingOnSandOrRedSand`;
               }

               @NotNull
               public final java.lang.Boolean invoke(@NotNull PokemonEntity it) {
                  return this.$mustBeStandingOnSandOrRedSand == EntityExtensionsKt.isStandingOnSandOrRedSand(it as Entity);
               }
            });
         }

         val var57: JsonElement = (json as JsonObject).get("isDusk");
         val mustBeDusk: java.lang.Boolean = if (var57 != null) var57.getAsBoolean() else null;
         if (mustBeDusk != null) {
            conditionsList.add(new Function1<PokemonEntity, java.lang.Boolean>(mustBeDusk) {
               {
                  super(1);
                  this.$mustBeDusk = `$mustBeDusk`;
               }

               @NotNull
               public final java.lang.Boolean invoke(@NotNull PokemonEntity it) {
                  return this.$mustBeDusk == EntityExtensionsKt.isDusk(it as Entity);
               }
            });
         }

         conditionsList.add(new Function1<PokemonEntity, java.lang.Boolean>(pose) {
            {
               super(1);
               this.$pose = `$pose`;
            }

            @NotNull
            public final java.lang.Boolean invoke(@NotNull PokemonEntity it) {
               val var10000: PokemonSideDelegate = it.getDelegate();
               return MoLangExtensionsKt.resolveBoolean((var10000 as PokemonClientDelegate).getRuntime(), this.$pose.getCondition());
            }
         });
         val var58: Function1;
         if (conditionsList.isEmpty()) {
            var58 = null;
         } else {
            val var19: java.util.Iterator = conditionsList.iterator();
            if (!var19.hasNext()) {
               throw new UnsupportedOperationException("Empty collection can't be reduced.");
            }

            var `$this$mapNotNull$iv`: Any = var19.next();

            while (iterator$iv.hasNext()) {
               val function: Function1 = var19.next() as Function1;
               val `$i$f$mapNotNull`: Function1 = `$this$mapNotNull$iv` as Function1;
               `$this$mapNotNull$iv` = (new Function1<PokemonEntity, java.lang.Boolean>(`$i$f$mapNotNull`, function) {
                  {
                     super(1);
                     this.$acc = `$acc`;
                     this.$function = `$function`;
                  }

                  @NotNull
                  public final java.lang.Boolean invoke(@NotNull PokemonEntity it) {
                     return this.$acc.invoke(it) as java.lang.Boolean && this.$function.invoke(it) as java.lang.Boolean;
                  }
               }) as Function1;
            }

            var58 = `$this$mapNotNull$iv` as Function1;
         }

         val var37: Pose = new Pose(
            pose.getPoseName(),
            CollectionsKt.toSet(pose.getPoseTypes()),
            var58,
            null,
            pose.getTransformTicks(),
            pose.getAnimations(),
            pose.getIdleAnimations(),
            pose.getTransformedParts(),
            pose.getQuirks().toArray(new ModelQuirk[0]),
            8,
            null
         );
         val var59: java.util.Map = var37.getTransitions();
         val var42: java.lang.Iterable = pose.getTransitions();
         val var48: java.util.Collection = new ArrayList();

         for (Object element$iv$iv$iv : var42) {
            val it: JsonPose.JsonPoseTransition = `element$iv$iv$iv` as JsonPose.JsonPoseTransition;
            val var60: Pair = TuplesKt.to(
               it.getTo(),
               new Function2<Pose<PokemonEntity, ? extends ModelFrame>, Pose<PokemonEntity, ? extends ModelFrame>, StatefulAnimation<PokemonEntity, ModelFrame>>(
                  it, model
               ) {
                  {
                     super(2);
                     this.$it = `$it`;
                     this.$model = `$model`;
                  }

                  @NotNull
                  public final StatefulAnimation<PokemonEntity, ModelFrame> invoke(
                     @NotNull Pose<PokemonEntity, ? extends ModelFrame> var1, @NotNull Pose<PokemonEntity, ? extends ModelFrame> var2
                  ) {
                     val var10000: Any = this.$it.getAnimation().resolveObject(this.$model.getRuntime()).getObj();
                     return var10000 as StatefulAnimation<PokemonEntity, ModelFrame>;
                  }
               }
            );
            if (var60 != null) {
               var48.add(var60);
            }
         }

         var59.putAll(MapsKt.toMap(var48 as java.util.List));
         return var37;
      }
   }

   public object StatefulAnimationAdapter : JsonDeserializer<Supplier<StatefulAnimation<PokemonEntity, ModelFrame>>> {
      public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): Supplier<StatefulAnimation<PokemonEntity, ModelFrame>> {
         val animString: java.lang.String = (json as JsonPrimitive).getAsString();
         return JsonPokemonPoseableModel.StatefulAnimationAdapter::deserialize$lambda$0;
      }

      @JvmStatic
      fun `deserialize$lambda$0`(`$format`: java.lang.String, `$animString`: java.lang.String): StatefulAnimation {
         var var10000: Any = JsonPokemonPoseableModel.Companion.getANIMATION_FACTORIES().get(`$format`);
         var10000 = var10000 as AnimationReferenceFactory;
         val var10001: JsonPokemonPoseableModel = JsonPokemonPoseableModel.JsonPokemonPoseableModelAdapter.INSTANCE.getModel();
         val var3: PoseableEntityModel = var10001;
         return ((AnimationReferenceFactory)var10000).stateful(var3, `$animString`);
      }
   }
}
