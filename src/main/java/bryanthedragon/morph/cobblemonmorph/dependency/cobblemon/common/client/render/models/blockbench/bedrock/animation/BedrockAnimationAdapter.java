package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

import com.bedrockk.molang.Expression
import com.bedrockk.molang.MoLang
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.BedrockParticleEffectRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import java.lang.reflect.Type
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nBedrockAnimationAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockAnimationAdapter.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,210:1\n1855#2,2:211\n1855#2,2:213\n1855#2,2:215\n1855#2:217\n1549#2:218\n1620#2,2:219\n1622#2:222\n1856#2:223\n1855#2,2:224\n1549#2:226\n1620#2,3:227\n1#3:221\n*S KotlinDebug\n*F\n+ 1 BedrockAnimationAdapter.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationAdapter\n*L\n37#1:211,2\n40#1:213,2\n64#1:215,2\n83#1:217\n88#1:218\n88#1:219,2\n88#1:222\n83#1:223\n160#1:224,2\n47#1:226\n47#1:227,3\n*E\n"])
public object BedrockAnimationAdapter : JsonDeserializer<BedrockAnimation> {
   public open fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): BedrockAnimation {
      if (json is JsonObject) {
         var animationLength: Double;
         var var63: Boolean;
         label159: {
            val var10000: JsonElement = (json as JsonObject).get("animation_length");
            animationLength = if (var10000 != null) var10000.getAsDouble() else -1.0;
            if (animationLength > 0.0) {
               val var62: JsonElement = (json as JsonObject).get("loop");
               if (var62 != null && var62.getAsBoolean()) {
                  var63 = true;
                  break label159;
               }
            }

            var63 = false;
         }

         val boneTimelines: java.util.Map = new LinkedHashMap();
         val effects: java.util.List = new ArrayList();
         val var64: JsonElement = (json as JsonObject).get("bones");
         if (var64 != null) {
            val var65: JsonObject = var64.getAsJsonObject();
            if (var65 != null && var65.entrySet() != null) {
               val `$this$forEach$iv`: java.lang.Iterable;
               for (Object element$iv : $this$forEach$iv) {
                  val var13: Entry = `element$iv` as Entry;
                  val frame: java.lang.String = var13.getKey() as java.lang.String;
                  val effectJson: JsonElement = var13.getValue() as JsonElement;
                  val var67: BedrockAnimationAdapter = INSTANCE;
                  val var10001: JsonObject = effectJson.getAsJsonObject();
                  boneTimelines.put(frame, var67.deserializeBoneTimeline(var10001));
               }
            }
         }

         val var68: JsonElement = (json as JsonObject).get("particle_effects");
         if (var68 != null) {
            val var69: JsonObject = var68.getAsJsonObject();
            if (var69 != null && var69.entrySet() != null) {
               val var34: java.lang.Iterable;
               for (Object element$iv : var34) {
                  val var46: Entry = var43 as Entry;
                  val var52: java.lang.String = var46.getKey() as java.lang.String;
                  val var55: JsonElement = var46.getValue() as JsonElement;
                  if (var55 is JsonObject) {
                     effects.add(deserialize$lambda$2$resolveEffect(var52, var55 as JsonObject));
                  } else if (var55 is JsonArray) {
                     for (JsonElement effectJsonElement : (JsonArray)effectJson) {
                        effects.add(deserialize$lambda$2$resolveEffect(var52, effectJsonElement as JsonObject));
                     }
                  }
               }
            }
         }

         val var71: JsonElement = (json as JsonObject).get("sound_effects");
         if (var71 != null) {
            val var72: JsonObject = var71.getAsJsonObject();
            if (var72 != null && var72.entrySet() != null) {
               val var35: java.lang.Iterable;
               for (Object element$ivx : var35) {
                  val var47: Entry = `element$ivx` as Entry;
                  val var53: java.lang.String = var47.getKey() as java.lang.String;
                  val var56: JsonElement = var47.getValue() as JsonElement;
                  if (var56 is JsonObject) {
                     effects.add(deserialize$lambda$4$resolveEffect$3(var53, var56 as JsonObject));
                  } else if (var56 is JsonArray) {
                     for (JsonElement effectJsonElement : (JsonArray)effectJson) {
                        effects.add(deserialize$lambda$4$resolveEffect$3(var53, var60 as JsonObject));
                     }
                  }
               }
            }
         }

         val var74: JsonElement = (json as JsonObject).get("timeline");
         if (var74 != null) {
            val var75: JsonObject = var74.getAsJsonObject();
            if (var75 != null && var75.entrySet() != null) {
               val var36: java.lang.Iterable;
               for (Object element$ivxx : var36) {
                  val var48: Entry = `element$ivxx` as Entry;
                  val var54: java.lang.String = var48.getKey() as java.lang.String;
                  val var57: JsonElement = var48.getValue() as JsonElement;
                  var var77: java.util.List = effects;
                  val var79: Float = java.lang.Float.parseFloat(var54);
                  val var80: java.util.List;
                  if (var57 !is JsonArray) {
                     val var81: java.lang.String = var57.getAsString();
                     var80 = CollectionsKt.listOf(MoLangExtensionsKt.asExpression(var81));
                  } else {
                     val var10002: JsonArray = (var57 as JsonArray).getAsJsonArray();
                     val var59: java.lang.Iterable = var10002 as java.lang.Iterable;
                     val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var10002 as java.lang.Iterable, 10));

                     for (Object item$iv$iv : var59) {
                        val it: java.lang.String = (`item$iv$iv` as JsonElement).getAsString();
                        val var78: java.lang.String;
                        if (StringsKt.endsWith$default(it, ";", false, 2, null)) {
                           var78 = it.substring(0, it.length() - 1);
                        } else {
                           var78 = it;
                        }

                        `destination$iv$iv`.add(MoLangExtensionsKt.asExpression(var78));
                     }

                     val var28: java.util.List = `destination$iv$iv` as java.util.List;
                     var77 = effects;
                     var80 = var28;
                  }

                  var77.add(new BedrockInstructionKeyframe(var79, var80));
               }
            }
         }

         return new BedrockAnimation(var63, animationLength, effects, boneTimelines);
      } else {
         throw new IllegalStateException("animation json could not be parsed");
      }
   }

   private fun deserializeBoneTimeline(bone: JsonObject): BedrockBoneTimeline {
      var var10000: BedrockBoneValue;
      if (bone.has("position")) {
         if (bone.get("position").isJsonObject()) {
            val var10001: JsonObject = bone.get("position").getAsJsonObject();
            var10000 = this.deserializeKeyframe(var10001, Transformation.POSITION);
         } else {
            val var17: JsonArray = bone.get("position").getAsJsonArray();
            var10000 = this.deserializeMolangBoneValue(var17, Transformation.POSITION);
         }
      } else {
         var10000 = EmptyBoneValue.INSTANCE;
      }

      if (bone.has("rotation")) {
         if (bone.get("rotation").isJsonObject()) {
            val var18: JsonObject = bone.get("rotation").getAsJsonObject();
            var10000 = this.deserializeKeyframe(var18, Transformation.ROTATION);
         } else {
            val var19: JsonArray = bone.get("rotation").getAsJsonArray();
            var10000 = this.deserializeMolangBoneValue(var19, Transformation.ROTATION);
         }
      } else {
         var10000 = EmptyBoneValue.INSTANCE;
      }

      if (bone.has("scale")) {
         val json: JsonElement = bone.get("scale");
         if (json.isJsonObject()) {
            val var20: JsonObject = json.getAsJsonObject();
            var10000 = this.deserializeKeyframe(var20, Transformation.SCALE);
         } else if (json.isJsonArray()) {
            val var21: JsonArray = json.getAsJsonArray();
            var10000 = this.deserializeMolangBoneValue(var21, Transformation.SCALE);
         } else {
            val str: java.lang.String = json.getAsString();
            val var7: JsonArray = new JsonArray();
            val arr: JsonArray = var7;
            val var10: Byte = 3;

            for (int var11 = 0; var11 < var10; var11++) {
               arr.add((new JsonPrimitive(str)) as JsonElement);
            }

            var10000 = this.deserializeMolangBoneValue(var7, Transformation.SCALE);
         }
      } else {
         var10000 = EmptyBoneValue.INSTANCE;
      }

      return new BedrockBoneTimeline(var10000, var10000, var10000);
   }

   public fun cleanExpression(value: String): String {
      val var10000: java.lang.String;
      if (StringsKt.startsWith$default(value, "+", false, 2, null)) {
         var10000 = value.substring(1);
      } else {
         var10000 = value;
      }

      return StringsKt.replace$default(
         StringsKt.replace$default(
            StringsKt.replace$default(
               StringsKt.replace$default(
                  if (StringsKt.startsWith$default(var10000, "-(", false, 2, null))
                     StringsKt.replaceFirst$default(var10000, "-(", "-1*(", false, 4, null)
                     else
                     var10000,
                  "*+",
                  "*",
                  false,
                  4,
                  null
               ),
               "q.",
               "query.",
               false,
               4,
               null
            ),
            "camera_rotation(0)",
            "camera_rotation_x",
            false,
            4,
            null
         ),
         "camera_rotation(1)",
         "camera_rotation_y",
         false,
         4,
         null
      );
   }

   public fun deserializeMolangBoneValue(array: JsonArray, transformation: Transformation): MolangBoneValue {
      try {
         val var10003: java.lang.String = array.get(0).getAsString();
         val var10002: Expression = MoLang.createParser(this.cleanExpression(var10003)).parseExpression();
         val var10004: java.lang.String = array.get(1).getAsString();
         val var5: Expression = MoLang.createParser(this.cleanExpression(var10004)).parseExpression();
         val var10005: java.lang.String = array.get(2).getAsString();
         val var6: Expression = MoLang.createParser(this.cleanExpression(var10005)).parseExpression();
         return new MolangBoneValue(var10002, var5, var6, transformation);
      } catch (var4: Exception) {
         Cobblemon.INSTANCE
            .getLOGGER()
            .error(CollectionsKt.joinToString$default(array as java.lang.Iterable, null, null, null, 0, null, <unrepresentable>.INSTANCE, 31, null));
         throw var4;
      }
   }

   private fun deserializeKeyframe(frames: JsonObject, transformation: Transformation): BedrockKeyFrameBoneValue {
      val keyframes: BedrockKeyFrameBoneValue = new BedrockKeyFrameBoneValue();

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val var8: Entry = `element$iv` as Entry;
         val time: java.lang.String = var8.getKey() as java.lang.String;
         val keyframeJson: JsonElement = var8.getValue() as JsonElement;
         val timeDbl: Double = java.lang.Double.parseDouble(time);
         if (keyframeJson is JsonObject) {
            val var33: JsonElement = (keyframeJson as JsonObject).get("lerp_mode");
            val var14: java.lang.String = if (var33 != null) var33.getAsString() else null;
            val var34: java.lang.String;
            if (var14 == null) {
               var34 = "linear";
            } else {
               var34 = var14;
            }

            val interpolationType: InterpolationType = if (var34 == "catmullrom") InterpolationType.SMOOTH else InterpolationType.LINEAR;
            if ((keyframeJson as JsonObject).has("post")) {
               var pre: JsonElement;
               var var17: java.lang.Double;
               var var39: JsonArray;
               label51: {
                  pre = (keyframeJson as JsonObject).get("post");
                  var20 = keyframes;
                  var17 = timeDbl;
                  var35 = INSTANCE;
                  val var10001: JsonElement = (keyframeJson as JsonObject).get("pre");
                  if (var10001 != null) {
                     var39 = var10001.getAsJsonArray();
                     if (var39 != null) {
                        break label51;
                     }
                  }

                  var39 = pre.getAsJsonArray();
               }

               val var18: MolangBoneValue = var35.deserializeMolangBoneValue(var39, transformation);
               val var36: BedrockAnimationAdapter = INSTANCE;
               var39 = pre.getAsJsonArray();
               var20.put(
                  var17,
                  new JumpBedrockAnimationKeyFrame(timeDbl, transformation, interpolationType, var18, var36.deserializeMolangBoneValue(var39, transformation))
               );
            } else {
               if (!(keyframeJson as JsonObject).has("pre")) {
                  throw new IllegalStateException("transformation data ('post') could not be found");
               }

               var var27: java.lang.Double;
               var var30: MolangBoneValue;
               var var43: JsonArray;
               label45: {
                  val var25: JsonElement = (keyframeJson as JsonObject).get("pre");
                  var21 = keyframes;
                  var27 = timeDbl;
                  val var37: BedrockAnimationAdapter = INSTANCE;
                  var43 = var25.getAsJsonArray();
                  var30 = var37.deserializeMolangBoneValue(var43, transformation);
                  var38 = INSTANCE;
                  val var42: JsonElement = (keyframeJson as JsonObject).get("post");
                  if (var42 != null) {
                     var43 = var42.getAsJsonArray();
                     if (var43 != null) {
                        break label45;
                     }
                  }

                  var43 = var25.getAsJsonArray();
               }

               var21.put(
                  var27,
                  new JumpBedrockAnimationKeyFrame(timeDbl, transformation, interpolationType, var30, var38.deserializeMolangBoneValue(var43, transformation))
               );
            }
         } else {
            if (keyframeJson !is JsonArray) {
               throw new IllegalStateException("keyframe json could not be parsed");
            }

            keyframes.put(
               timeDbl,
               new SimpleBedrockAnimationKeyFrame(
                  timeDbl, transformation, InterpolationType.LINEAR, INSTANCE.deserializeMolangBoneValue(keyframeJson as JsonArray, transformation)
               )
            );
         }
      }

      return keyframes;
   }

   @JvmStatic
   fun `deserialize$lambda$2$resolveEffect`(frame: java.lang.String, jsonObject: JsonObject): BedrockParticleKeyframe {
      var var10000: java.lang.String = jsonObject.get("effect").getAsString();
      val effectId: ResourceLocation = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var10000, null, 1, null);
      val var18: BedrockParticleEffect = BedrockParticleEffectRepository.INSTANCE.getEffect(effectId);
      if (var18 == null) {
         throw new IllegalArgumentException(
            "Unrecognized particle effect $effectId referenced in animation. Maybe your particle effect isn't named correctly inside the effect file?"
         );
      } else {
         val var19: JsonElement = jsonObject.get("locator");
         var10000 = if (var19 != null) var19.getAsString() else null;
         if (var10000 == null) {
            var10000 = "root";
         }

         var seconds: Float;
         label33: {
            seconds = java.lang.Float.parseFloat(frame);
            val var21: JsonElement = jsonObject.get("pre_effect_script");
            if (var21 != null) {
               var10000 = var21.getAsString();
               if (var10000 != null) {
                  val var24: java.util.List = StringsKt.split$default(var10000, new java.lang.String[]{"\n"}, false, 0, 6, null);
                  if (var24 != null) {
                     val `$this$map$iv`: java.lang.Iterable = var24;
                     val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var24, 10));

                     for (Object item$iv$iv : $this$map$iv) {
                        `destination$iv$iv`.add(MoLang.createParser(`item$iv$iv` as java.lang.String).parseExpression());
                     }

                     var25 = `destination$iv$iv` as java.util.List;
                     break label33;
                  }
               }
            }

            var25 = CollectionsKt.emptyList();
         }

         return new BedrockParticleKeyframe(seconds, var18, var10000, var25);
      }
   }

   @JvmStatic
   fun `deserialize$lambda$4$resolveEffect$3`(frame: java.lang.String, jsonObject: JsonObject): BedrockSoundKeyframe {
      val var10000: java.lang.String = jsonObject.get("effect").getAsString();
      val effectId: ResourceLocation = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var10000, null, 1, null);
      return new BedrockSoundKeyframe(java.lang.Float.parseFloat(frame), effectId);
   }
}
