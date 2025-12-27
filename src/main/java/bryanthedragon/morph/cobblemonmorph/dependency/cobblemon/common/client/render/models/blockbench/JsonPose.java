package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench

import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.struct.QueryStruct
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.ClientMoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.SingleBoneLookAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.HeadedFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.quirk.SimpleQuirk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.GsonExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import java.util.ArrayList;
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.Locale
import java.util.Map.Entry
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

@SourceDebugExtension(["SMAP\nJsonPose.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonPose.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/JsonPose\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,131:1\n1549#2:132\n1620#2,3:133\n1549#2:136\n1620#2,2:137\n1622#2:140\n1549#2:141\n1620#2,3:142\n1603#2,9:147\n1855#2:156\n1549#2:157\n1620#2,3:158\n1856#2:162\n1612#2:163\n1549#2:166\n1620#2,3:167\n1549#2:170\n1620#2,3:171\n1#3:139\n1#3:161\n37#4,2:145\n37#4,2:164\n*S KotlinDebug\n*F\n+ 1 JsonPose.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/JsonPose\n*L\n40#1:132\n40#1:133,3\n43#1:136\n43#1:137,2\n43#1:140\n48#1:141\n48#1:142,3\n58#1:147,9\n58#1:156\n70#1:157\n70#1:158,3\n58#1:162\n58#1:163\n83#1:166\n83#1:167,3\n124#1:170\n124#1:171,3\n58#1:161\n56#1:145,2\n81#1:164,2\n*E\n"])
public class JsonPose<T extends Entity>(model: PoseableEntityModel<Any>, json: JsonObject) {
   public final val animations: MutableMap<String, ExpressionLike>
   public final val condition: ExpressionLike
   public final val idleAnimations: Array<StatelessAnimation<Any, out ModelFrame>>
   public final val poseName: String
   public final val poseTypes: List<PoseType>
   public final val quirks: List<SimpleQuirk<Any>>
   public final val runtime: MoLangRuntime
   public final val transformTicks: Int
   public final val transformedParts: Array<ModelPartTransformation>
   public final val transitions: List<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.JsonPose.JsonPoseTransition>

   init {
      var var115: JsonPose;
      var var144: ExpressionLike;
      label275: {
         super();
         val `$this$map$iv`: MoLangRuntime = ClientMoLangFunctions.INSTANCE.setupClient(MoLangFunctions.INSTANCE.setup(new MoLangRuntime()));
         val var10000: MoLangFunctions = MoLangFunctions.INSTANCE;
         val var10001: MoLangFunctions = MoLangFunctions.INSTANCE;
         val var10002: MoLangEnvironment = `$this$map$iv`.getEnvironment();
         val var140: QueryStruct = MoLangFunctions.getQueryStruct$default(var10001, var10002, null, 1, null);
         val var171: HashMap = model.getFunctions().functions;
         var10000.addFunctions(var140, var171);
         this.runtime = `$this$map$iv`;
         var115 = this;
         val var141: JsonElement = GsonExtensionsKt.singularToPluralList$default(json, "condition", null, 2, null).get("conditions");
         if (var141 != null) {
            val var142: JsonArray = GsonExtensionsKt.normalizeToArray(var141);
            if (var142 != null) {
               val it: java.lang.Iterable = var142 as java.lang.Iterable;
               val `$this$mapTo$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var142 as java.lang.Iterable, 10));

               for (Object item$iv$iv : it) {
                  `$this$mapTo$iv$iv`.add((animations as JsonElement).getAsString());
               }

               val var143: java.util.List = `$this$mapTo$iv$iv` as java.util.List;
               var115 = this;
               var144 = MoLangExtensionsKt.asExpressionLike(var143);
               if (var144 != null) {
                  break label275;
               }
            }
         }

         var144 = MoLangExtensionsKt.asExpressionLike("true");
      }

      var115.condition = var144;
      val var145: JsonElement = json.get("poseName");
      var var146: java.lang.String = if (var145 != null) var145.getAsString() else null;
      if (var146 == null) {
         var146 = "pose";
      }

      label262: {
         this.poseName = var146;
         var115 = this;
         val var147: JsonElement = json.get("poseTypes");
         if (var147 != null) {
            val var148: JsonArray = var147.getAsJsonArray();
            if (var148 != null) {
               val var40: java.lang.Iterable = var148 as java.lang.Iterable;
               val var55: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var148 as java.lang.Iterable, 10));

               for (Object item$iv$iv : var40) {
                  val var77: JsonElement = var72 as JsonElement;
                  val var15: Array<PoseType> = PoseType.values();
                  var from: Int = 0;
                  val to: Int = var15.length;

                  while (true) {
                     if (from >= to) {
                        var118 = null;
                        break;
                     }

                     val animation: PoseType = var15[from];
                     val var117: java.lang.String = var15[from].name().toLowerCase(Locale.ROOT);
                     val var150: java.lang.String = var77.getAsString();
                     val var151: java.lang.String = var150.toLowerCase(Locale.ROOT);
                     if (var117 == var151) {
                        var118 = animation;
                        break;
                     }

                     from++;
                  }

                  if (var118 == null) {
                     throw new IllegalArgumentException("Unknown pose type ${var77.getAsString()}");
                  }

                  var55.add(var118);
               }

               var149 = var55 as java.util.List;
               var115 = this;
               break label262;
            }
         }

         var149 = CollectionsKt.emptyList();
      }

      label242: {
         val var152: java.util.Collection = var149;
         val var172: JsonElement = json.get("allPoseTypes");
         var115.poseTypes = CollectionsKt.plus(
            var152, if (var172 != null && var172.getAsBoolean()) ArraysKt.toList(PoseType.values()) else CollectionsKt.emptyList()
         );
         val var153: JsonElement = json.get("transformTicks");
         this.transformTicks = if (var153 != null) var153.getAsInt() else 10;
         var115 = this;
         val var154: JsonElement = json.get("transformedParts");
         if (var154 != null) {
            val var155: JsonArray = var154.getAsJsonArray();
            if (var155 != null) {
               val var41: java.lang.Iterable = var155 as java.lang.Iterable;
               val var56: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var155 as java.lang.Iterable, 10));

               for (Object item$iv$iv : var41) {
                  var var78: JsonElement;
                  var var93: ModelPartTransformation;
                  label230: {
                     var78 = var73 as JsonElement;
                     val var89: java.lang.String = (var78 as JsonObject).get("part").getAsString();
                     var93 = ModelPartExtensionsKt.createTransformation(model.getPart(var89));
                     val var120: JsonElement = (var78 as JsonObject).get("rotation");
                     if (var120 != null) {
                        val var98: JsonArray = var120.getAsJsonArray();
                        if (var98 != null) {
                           var121 = new Vec3(var98.get(0).getAsDouble(), var98.get(1).getAsDouble(), var98.get(2).getAsDouble());
                           break label230;
                        }
                     }

                     var121 = Vec3.f_82478_;
                  }

                  label224: {
                     val var122: JsonElement = (var78 as JsonObject).get("position");
                     if (var122 != null) {
                        val `$i$f$mapTo`: JsonArray = var122.getAsJsonArray();
                        if (`$i$f$mapTo` != null) {
                           var123 = new Vec3(`$i$f$mapTo`.get(0).getAsDouble(), `$i$f$mapTo`.get(1).getAsDouble(), `$i$f$mapTo`.get(2).getAsDouble());
                           break label224;
                        }
                     }

                     var123 = Vec3.f_82478_;
                  }

                  val var124: JsonElement = (var78 as JsonObject).get("isVisible");
                  var56.add(
                     var93.withPosition(var123.f_82479_, var123.f_82480_, var123.f_82481_)
                        .withRotationDegrees(var121.f_82479_, var121.f_82480_, var121.f_82481_)
                        .withVisibility(var124 == null || var124.getAsBoolean())
                  );
               }

               val var156: java.util.List = var56 as java.util.List;
               var115 = this;
               var157 = var156.toArray(new ModelPartTransformation[0]);
               if (var157 != null) {
                  break label242;
               }
            }
         }

         var157 = new ModelPartTransformation[0];
      }

      label218: {
         var115.transformedParts = var157;
         val var158: JsonElement = json.get("animations");
         if (var158 != null) {
            var159 = var158.getAsJsonArray();
            if (var159 != null) {
               break label218;
            }
         }

         var159 = new JsonArray();
      }

      val var160: JsonArray = var159.getAsJsonArray();
      var var33: java.lang.Iterable = var160 as java.lang.Iterable;
      var var42: java.util.Collection = new ArrayList();

      for (Object element$iv$iv$iv : var33) {
         val var94: java.lang.String = (var68 as JsonElement).getAsString();
         var var126: StatelessAnimation;
         if (var94 == "look") {
            var126 = if (model is HeadedFrame)
               HeadedFrame.DefaultImpls.singleBoneLook$default(model as HeadedFrame, false, false, false, false, null, null, null, null, null, null, 1023, null)
               else
               HeadedFrame.DefaultImpls.singleBoneLook$default(
                  new HeadedFrame(model) {
                     @NotNull
                     private final Bone rootPart;
                     @NotNull
                     private final Bone head;

                     {
                        this.rootPart = `$model`.getRootPart();
                        this.head = `$model`.getPartFallback("head_ai", "head");
                     }

                     @NotNull
                     @Override
                     public Bone getRootPart() {
                        return this.rootPart;
                     }

                     @NotNull
                     @Override
                     public Bone getHead() {
                        return this.head;
                     }

                     @NotNull
                     @Override
                     public <T extends Entity> SingleBoneLookAnimation<T> singleBoneLook(
                        boolean invertX,
                        boolean invertY,
                        boolean disableX,
                        boolean disableY,
                        @Nullable java.lang.Float pitchMultiplier,
                        @Nullable java.lang.Float yawMultiplier,
                        @Nullable java.lang.Float maxPitch,
                        @Nullable java.lang.Float minPitch,
                        @Nullable java.lang.Float maxYaw,
                        @Nullable java.lang.Float minYaw
                     ) {
                        return HeadedFrame.DefaultImpls.singleBoneLook(
                           this, invertX, invertY, disableX, disableY, pitchMultiplier, yawMultiplier, maxPitch, minPitch, maxYaw, minYaw
                        );
                     }
                  },
                  false,
                  false,
                  false,
                  false,
                  null,
                  null,
                  null,
                  null,
                  null,
                  null,
                  1023,
                  null
               );
         } else if (!StringsKt.startsWith$default(var94, "bedrock", false, 2, null)) {
            try {
               val var127: Any = MoLangExtensionsKt.resolveObject(this.runtime, MoLangExtensionsKt.asExpressionLike(var94)).getObj();
               var126 = var127 as StatelessAnimation;
            } catch (var32: Exception) {
               var126 = null;
            }
         } else {
            val var101: java.lang.Iterable = StringsKt.split$default(
               StringsKt.replace$default(StringsKt.replace$default(var94, "bedrock(", "", false, 4, null), ")", "", false, 4, null),
               new java.lang.String[]{","},
               false,
               0,
               6,
               null
            );
            val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var101, 10));

            for (Object item$iv$iv : $this$map$iv) {
               `destination$iv$ivx`.add(StringsKt.trim(var112 as java.lang.String).toString());
            }

            var126 = PoseableEntityModel.bedrock$default(
               model,
               (`destination$iv$ivx` as java.util.List).get(0) as java.lang.String,
               (`destination$iv$ivx` as java.util.List).get(1) as java.lang.String,
               null,
               4,
               null
            );
         }

         if (var126 != null) {
            var42.add(var126);
         }
      }

      this.idleAnimations = (var42 as java.util.List).toArray(new StatelessAnimation[0]);
      val var161: JsonElement = json.get("quirks");
      var var162: JsonArray = if (var161 != null) var161.getAsJsonArray() else null;
      if (var162 == null) {
         var162 = new JsonArray();
      }

      var33 = var162 as java.lang.Iterable;
      var42 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var162 as java.lang.Iterable, 10));

      for (Object item$iv$iv : var33) {
         val var63: JsonElement = var58 as JsonElement;
         val var130: SimpleQuirk;
         if (var58 as JsonElement is JsonPrimitive) {
            val var128: java.lang.String = (var63 as JsonPrimitive).getAsString();
            val var129: Any = MoLangExtensionsKt.asExpressionLike(var128).resolveObject(this.runtime).getObj();
            var130 = var129 as SimpleQuirk;
         } else {
            var var74: Function1;
            var var80: Int;
            var var86: Float;
            var var91: Float;
            label191: {
               GsonExtensionsKt.singularToPluralList$default(var63 as JsonObject, "animation", null, 2, null);
               var74 = (
                  new Function1<PoseableEntityState<T>, java.util.List<? extends StatefulAnimation<T, ? extends ModelFrame>>>(var63, this, model) {
                     {
                        super(1);
                        this.$json = `$json`;
                        this.this$0 = `$receiver`;
                        this.$model = `$model`;
                     }

                     @NotNull
                     public final java.util.List<StatefulAnimation<T, ? extends ModelFrame>> invoke(@NotNull PoseableEntityState<T> var1) {
                        var var31: JsonArray;
                        label43: {
                           val var10000: JsonElement = (this.$json as JsonObject).get("animations");
                           if (var10000 != null) {
                              var31 = GsonExtensionsKt.normalizeToArray(var10000);
                              if (var31 != null) {
                                 var31 = var31.getAsJsonArray();
                                 break label43;
                              }
                           }

                           var31 = null;
                        }

                        if (var31 == null) {
                           var31 = new JsonArray();
                        }

                        val `$this$map$iv`: java.lang.Iterable = var31 as java.lang.Iterable;
                        val var3: JsonPose = this.this$0;
                        val var4: PoseableEntityModel = this.$model;
                        val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

                        for (Object item$iv$iv : $this$map$iv) {
                           val animJson: JsonElement = `item$iv$iv` as JsonElement;

                           var expr: StatefulAnimation;
                           try {
                              val var34: java.lang.String = animJson.getAsString();
                              var31 = (JsonArray)MoLangExtensionsKt.resolveObject(var3.getRuntime(), MoLangExtensionsKt.asExpressionLike(var34)).getObj();
                              expr = var31 as StatefulAnimation;
                           } catch (var27: Exception) {
                              val var32: java.lang.String = (`item$iv$iv` as JsonElement).getAsString();
                              val var29: java.lang.Iterable = StringsKt.split$default(
                                 StringsKt.replace$default(StringsKt.replace$default(var32, "bedrock(", "", false, 4, null), ")", "", false, 4, null),
                                 new java.lang.String[]{","},
                                 false,
                                 0,
                                 6,
                                 null
                              );
                              val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var29, 10));

                              for (Object item$iv$ivx : $this$map$iv) {
                                 `destination$iv$ivx`.add(StringsKt.trim(`item$iv$ivx` as java.lang.String).toString());
                              }

                              expr = PoseableEntityModel.bedrockStateful$default(
                                 var4,
                                 (`destination$iv$ivx` as java.util.List).get(0) as java.lang.String,
                                 (`destination$iv$ivx` as java.util.List).get(1) as java.lang.String,
                                 null,
                                 4,
                                 null
                              );
                           }

                           `destination$iv$iv`.add(expr);
                        }

                        return `destination$iv$iv` as MutableList<StatefulAnimation<T, ? extends ModelFrame>>;
                     }
                  }
               ) as Function1;
               val var131: JsonElement = (var63 as JsonObject).get("loopTimes");
               var80 = if (var131 != null) var131.getAsInt() else 1;
               val var132: JsonElement = (var63 as JsonObject).get("minSecondsBetweenOccurrences");
               var86 = if (var132 != null) var132.getAsFloat() else 8.0F;
               val var133: JsonElement = (var63 as JsonObject).get("maxSecondsBetweenOccurrences");
               var91 = if (var133 != null) var133.getAsFloat() else 30.0F;
               val var134: JsonElement = (var63 as JsonObject).get("condition");
               if (var134 != null) {
                  val var95: java.lang.String = var134.getAsString();
                  if (var95 != null) {
                     var135 = MoLangExtensionsKt.asExpressionLike(var95);
                     if (var135 != null) {
                        break label191;
                     }
                  }
               }

               var135 = MoLangExtensionsKt.asExpressionLike("true");
            }

            var130 = model.quirkMultiple(TuplesKt.to(var86, var91), new IntRange(1, var80), (new Function1<PoseableEntityState<T>, java.lang.Boolean>(var135) {
               {
                  super(1);
                  this.$condition = `$condition`;
               }

               @NotNull
               public final java.lang.Boolean invoke(@NotNull PoseableEntityState<T> it) {
                  return MoLangExtensionsKt.resolveBoolean(it.getRuntime(), this.$condition);
               }
            }) as (PoseableEntityState<T>?) -> java.lang.Boolean, var74);
         }

         var42.add(var130);
      }

      label183: {
         this.quirks = var42 as MutableList<SimpleQuirk<T>>;
         var115 = this;
         val var163: JsonElement = json.get("namedAnimations");
         if (var163 != null) {
            val var31: Boolean = var163 is JsonObject;
            var115 = this;
            val var164: JsonElement = if (var31) var163 else null;
            if ((if (var31) var163 else null) != null) {
               val var165: JsonObject = var164.getAsJsonObject();
               if (var165 != null) {
                  val var64: java.util.Map = new LinkedHashMap();

                  for (Entry var75 : var165.entrySet()) {
                     val var81: java.lang.String = var75.getKey() as java.lang.String;
                     val var87: JsonElement = var75.getValue() as JsonElement;
                     val var137: java.lang.String = var87.getAsString();
                     var64.put(var81, MoLangExtensionsKt.asExpressionLike(var137));
                  }

                  var166 = var64;
                  var115 = this;
                  break label183;
               }
            }
         }

         var166 = new LinkedHashMap();
      }

      label170: {
         var115.animations = var166;
         var115 = this;
         val var167: JsonElement = json.get("transitions");
         if (var167 != null) {
            val var114: Boolean = var167 is JsonArray;
            var115 = this;
            val var168: JsonElement = if (var114) var167 else null;
            if ((if (var114) var167 else null) != null) {
               val var169: JsonArray = var168.getAsJsonArray();
               if (var169 != null) {
                  val var51: java.lang.Iterable = var169 as java.lang.Iterable;
                  val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var169 as java.lang.Iterable, 10));

                  for (Object item$iv$iv : var51) {
                     val var88: JsonElement = var82 as JsonElement;
                     val var97: java.lang.String = (var88 as JsonObject).get("from").getAsString();
                     val var104: java.lang.String = (var88 as JsonObject).get("to").getAsString();
                     val var139: java.lang.String = (var88 as JsonObject).get("animation").getAsString();
                     val var107: ExpressionLike = MoLangExtensionsKt.asExpressionLike(var139);
                     `destination$iv$ivx`.add(new JsonPose.JsonPoseTransition(var97, var104, var107));
                  }

                  var170 = `destination$iv$ivx` as java.util.List;
                  var115 = this;
                  break label170;
               }
            }
         }

         var170 = CollectionsKt.emptyList();
      }

      var115.transitions = var170;
   }

   public class JsonPoseTransition(from: String, to: String, animation: ExpressionLike) {
      public final val animation: ExpressionLike
      public final val from: String
      public final val to: String

      init {
         this.from = from;
         this.to = to;
         this.animation = animation;
      }
   }
}
