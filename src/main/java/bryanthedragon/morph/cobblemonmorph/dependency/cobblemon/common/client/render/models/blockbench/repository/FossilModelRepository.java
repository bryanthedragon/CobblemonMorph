package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.fossil.FossilModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.AnimationReferenceFactory
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.JsonPokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.ModelQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.ArrayList;
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nFossilModelRepository.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FossilModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/FossilModelRepository\n+ 2 GsonExtensions.kt\ncom/cobblemon/mod/common/util/GsonExtensionsKt\n*L\n1#1,96:1\n19#2:97\n*S KotlinDebug\n*F\n+ 1 FossilModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/FossilModelRepository\n*L\n38#1:97\n*E\n"])
public object FossilModelRepository : VaryingModelRepository<Entity, FossilModel> {
   public open val animationDirectories: List<String> = CollectionsKt.listOf("bedrock/${INSTANCE.getType()}/animations")
   public open val fallback: ResourceLocation = MiscUtilsKt.cobblemonResource("substitute")
   private final val gson: Gson = new GsonBuilder().create()
   public open val isForLivingEntityRenderer: Boolean
   public open val modelDirectories: List<String> = CollectionsKt.listOf("bedrock/${INSTANCE.getType()}/models")
   public open val poserDirectories: List<String> = CollectionsKt.listOf("bedrock/${INSTANCE.getType()}/posers")
   public open val title: String = "Fossil Pokémon"
   public open val type: String = "fossils"
   public open val variationDirectories: List<String> = CollectionsKt.listOf("bedrock/${INSTANCE.getType()}/variations")

   public override fun loadJsonPoser(json: String): (Bone) -> FossilModel {
      val var10000: Gson = gson;
      val jsonObject: JsonObject = var10000.fromJson(json, JsonObject.class) as JsonObject;
      val var7: JsonArray = jsonObject.getAsJsonArray("animations");
      val var9: JsonElement = jsonObject.get("maxScale");
      val var8: Float = if (var9 != null) var9.getAsFloat() else 1.0F;
      val var10: JsonElement = jsonObject.get("yTranslation");
      val yTranslation: Float = if (var10 != null) var10.getAsFloat() else 0.0F;
      val var11: JsonElement = jsonObject.get("yGrowthPoint");
      val yGrowthPoint: Float = if (var11 != null) var11.getAsFloat() else 0.0F;
      return (
         new Function1<Bone, FossilModel>(var8, yTranslation, yGrowthPoint, var7, jsonObject) {
            {
               super(1);
               this.$maxScale = `$maxScale`;
               this.$yTranslation = `$yTranslation`;
               this.$yGrowthPoint = `$yGrowthPoint`;
               this.$animations = `$animations`;
               this.$jsonObject = `$jsonObject`;
            }

            @NotNull
            public final FossilModel invoke(@NotNull Bone bone) {
               val model: FossilModel = new FossilModel(bone);
               model.setMaxScale(this.$maxScale);
               model.setYTranslation(this.$yTranslation);
               model.setYGrowthPoint(this.$yGrowthPoint);
               val var10001: JsonArray = this.$animations;
               val tankQuirks: java.lang.Iterable = var10001 as java.lang.Iterable;
               val `thisCollection$iv`: java.util.Collection = new ArrayList();

               for (Object element$iv$iv$iv : tankQuirks) {
                  val maxSeconds: java.lang.String = (json as JsonElement).getAsString();
                  val anim: java.lang.String = StringsKt.substringBefore$default(maxSeconds, "(", null, 2, null);
                  var var37: StatelessAnimation;
                  if (JsonPokemonPoseableModel.Companion.getANIMATION_FACTORIES().containsKey(anim)) {
                     var37 = JsonPokemonPoseableModel.Companion.getANIMATION_FACTORIES().get(anim);
                     var37 = (var37 as AnimationReferenceFactory).stateless(model, maxSeconds);
                  } else {
                     var37 = null;
                  }

                  if (var37 != null) {
                     `thisCollection$iv`.add(var37);
                  }
               }

               model.setTankAnimations((`thisCollection$iv` as java.util.List).toArray(new StatelessAnimation[0]));
               var var38: JsonElement = this.$jsonObject.get("quirks");
               var var39: JsonArray = if (var38 != null) var38.getAsJsonArray() else null;
               if (var39 == null) {
                  var39 = new JsonArray();
               }

               val var24: java.lang.Iterable = var39 as java.lang.Iterable;
               val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var39 as java.lang.Iterable, 10));

               for (Object item$iv$iv : $this$map$iv) {
                  val var30: JsonElement = var29 as JsonElement;
                  val var31: Function1 = (
                     new Function1<PoseableEntityState<Entity>, java.util.List<? extends StatefulAnimation<Entity, ModelFrame>>>(var30, model) {
                        {
                           super(1);
                           this.$json = `$json`;
                           this.$model = `$model`;
                        }

                        @NotNull
                        public final java.util.List<StatefulAnimation<Entity, ModelFrame>> invoke(@NotNull PoseableEntityState<Entity> var1) {
                           val var10000: JsonElement = (this.$json as JsonObject).get("animations");
                           var var21: JsonArray = if (var10000 != null) var10000.getAsJsonArray() else null;
                           if (var21 == null) {
                              var21 = new JsonArray();
                           }

                           val `$this$mapNotNull$iv`: java.lang.Iterable = var21 as java.lang.Iterable;
                           val var3: FossilModel = this.$model;
                           val `destination$iv$iv`: java.util.Collection = new ArrayList();

                           for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
                              val animString: java.lang.String = (`element$iv$iv$iv` as JsonElement).getAsString();
                              val animx: java.lang.String = StringsKt.substringBefore$default(animString, "(", null, 2, null);
                              val var23: StatefulAnimation;
                              if (JsonPokemonPoseableModel.Companion.getANIMATION_FACTORIES().containsKey(animx)) {
                                 val var22: AnimationReferenceFactory = JsonPokemonPoseableModel.Companion.getANIMATION_FACTORIES().get(animx);
                                 var23 = if (var22 != null) var22.stateful(var3, animString) else null;
                              } else {
                                 var23 = null;
                              }

                              if (var23 != null) {
                                 `destination$iv$iv`.add(var23);
                              }
                           }

                           return `destination$iv$iv` as MutableList<StatefulAnimation<Entity, ModelFrame>>;
                        }
                     }
                  ) as Function1;
                  var38 = (var30 as JsonObject).get("loopTimes");
                  val var32: Int = if (var38 != null) var38.getAsInt() else 1;
                  var38 = (var30 as JsonObject).get("minSeconds");
                  val var33: Float = if (var38 != null) var38.getAsFloat() else 8.0F;
                  var38 = (var30 as JsonObject).get("maxSeconds");
                  `destination$iv$ivx`.add(
                     model.quirkMultiple(
                        TuplesKt.to(var33, if (var38 != null) var38.getAsFloat() else 30.0F), new IntRange(1, var32), <unrepresentable>.INSTANCE, var31
                     )
                  );
               }

               model.setTankQuirks((`destination$iv$ivx` as java.util.List).toArray(new ModelQuirk[0]));
               return model;
            }
         }
      ) as (Bone?) -> FossilModel;
   }

   public override fun registerInBuiltPosers() {
   }
}
