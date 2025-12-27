package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle

import com.bedrockk.molang.Expression
import com.bedrockk.molang.ast.NumberExpression
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.AnimatedParticleUVMode
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEmitter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BezierChainMoLangCurve
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BezierMoLangCurve
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BoxParticleEmitterShape
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.CatmullRomMoLangCurve
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.CustomMotionDirection
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.CustomViewDirection
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.DirectionX
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.DirectionY
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.DirectionZ
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.DiscParticleEmitterShape
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.DynamicParticleMotion
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.DynamicParticleRotation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EmitterXYPlane
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EmitterXZPlane
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EmitterYZPlane
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EntityBoundingBoxParticleEmitterShape
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EventParticleEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EventSoundEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.EventTriggerTimeline
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ExpressionEmitterLifetime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ExpressionParticleTinting
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.FromMotionViewDirection
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.GradientParticleTinting
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.InstantParticleEmitterRate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.InwardsMotionDirection
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LinearMoLangCurve
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LookAtDirection
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LookAtXYZ
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LookAtY
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LoopingEmitterLifetime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LoopingTravelDistanceEventTrigger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.MoLangCurve
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.OnceEmitterLifetime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.OutwardsMotionDirection
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParametricParticleMotion
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParametricParticleRotation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleCollision
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterLifetime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterRate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterShape
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMaterial
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotion
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotionDirection
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleRotation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleSpace
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleTinting
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleUVMode
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleViewDirection
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.PointParticleEmitterShape
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.RotateXYZCameraMode
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.RotateYCameraMode
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.SimpleEventTrigger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.SphereParticleEmitterShape
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.StaticParticleMotion
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.StaticParticleUVMode
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.SteadyParticleEmitterRate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.GsonExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.Locale
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.Ref.ObjectRef
import net.minecraft.resources.ResourceLocation
import org.joml.Vector4f

@SourceDebugExtension(["SMAP\nSnowstormParticleReader.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SnowstormParticleReader.kt\ncom/cobblemon/mod/common/particle/SnowstormParticleReader\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,449:1\n1#2:450\n1#2:477\n1603#3,9:451\n1855#3:460\n1549#3:461\n1620#3,3:462\n1549#3:465\n1620#3,3:466\n1549#3:469\n1620#3,3:470\n1549#3:473\n1620#3,3:474\n1856#3:478\n1612#3:479\n1855#3,2:480\n766#3:482\n857#3,2:483\n1549#3:485\n1620#3,3:486\n766#3:489\n857#3,2:490\n1549#3:492\n1620#3,3:493\n766#3:496\n857#3,2:497\n1549#3:499\n1620#3,3:500\n766#3:503\n857#3,2:504\n1549#3:506\n1620#3,3:507\n1549#3:510\n1620#3,3:511\n1549#3:514\n1620#3,3:515\n1549#3:518\n1620#3,3:519\n1549#3:522\n1620#3,3:523\n1549#3:526\n1620#3,3:527\n1549#3:530\n1620#3,3:531\n1549#3:534\n1620#3,3:535\n1549#3:538\n1620#3,3:539\n1549#3:542\n1620#3,3:543\n1549#3:546\n1620#3,3:547\n1549#3:550\n1620#3,3:551\n1549#3:554\n1620#3,3:555\n1549#3:558\n1620#3,3:559\n1549#3:562\n1620#3,3:563\n1549#3:566\n1620#3,2:567\n1549#3:569\n1620#3,3:570\n1622#3:573\n1549#3:574\n1620#3,3:575\n1549#3:578\n1620#3,3:579\n1549#3:582\n1620#3,2:583\n1549#3:585\n1620#3,3:586\n1622#3:589\n1549#3:590\n1620#3,2:591\n1549#3:593\n1620#3,3:594\n1622#3:597\n1549#3:598\n1620#3,2:599\n1549#3:601\n1620#3,3:602\n1622#3:605\n1549#3:606\n1620#3,3:607\n*S KotlinDebug\n*F\n+ 1 SnowstormParticleReader.kt\ncom/cobblemon/mod/common/particle/SnowstormParticleReader\n*L\n71#1:477\n71#1:451,9\n71#1:460\n76#1:461\n76#1:462,3\n85#1:465\n85#1:466,3\n98#1:469\n98#1:470,3\n110#1:473\n110#1:474,3\n71#1:478\n71#1:479\n118#1:480,2\n134#1:482\n134#1:483,2\n134#1:485\n134#1:486,3\n135#1:489\n135#1:490,2\n135#1:492\n135#1:493,3\n136#1:496\n136#1:497,2\n136#1:499\n136#1:500,3\n137#1:503\n137#1:504,2\n137#1:506\n137#1:507,3\n185#1:510\n185#1:511,3\n189#1:514\n189#1:515,3\n198#1:518\n198#1:519,3\n201#1:522\n201#1:523,3\n220#1:526\n220#1:527,3\n222#1:530\n222#1:531,3\n236#1:534\n236#1:535,3\n246#1:538\n246#1:539,3\n248#1:542\n248#1:543,3\n260#1:546\n260#1:547,3\n328#1:550\n328#1:551,3\n333#1:554\n333#1:555,3\n368#1:558\n368#1:559,3\n369#1:562\n369#1:563,3\n370#1:566\n370#1:567,2\n371#1:569\n371#1:570,3\n370#1:573\n380#1:574\n380#1:575,3\n381#1:578\n381#1:579,3\n382#1:582\n382#1:583,2\n383#1:585\n383#1:586,3\n382#1:589\n385#1:590\n385#1:591,2\n388#1:593\n388#1:594,3\n385#1:597\n391#1:598\n391#1:599,2\n392#1:601\n392#1:602,3\n391#1:605\n172#1:606\n172#1:607,3\n*E\n"])
public object SnowstormParticleReader {
   public fun loadEffect(json: JsonObject): BedrockParticleEffect {
      val effectJson: JsonObject = json.get("particle_effect").getAsJsonObject();
      val descJson: JsonObject = effectJson.get("description").getAsJsonObject();
      val basicRenderParametersJson: JsonObject = descJson.get("basic_render_parameters").getAsJsonObject();
      val emitterInitializationJson: JsonElement = effectJson.get("curves");
      val componentsJson: JsonObject = if (emitterInitializationJson != null) emitterInitializationJson.getAsJsonObject() else null;
      var var10000: JsonObject = componentsJson;
      if (componentsJson == null) {
         var10000 = new JsonObject();
      }

      val particleInitializationJson: JsonElement = effectJson.get("components");
      val var112: JsonObject = if (particleInitializationJson != null) particleInitializationJson.getAsJsonObject() else null;
      var10000 = var112;
      if (var112 == null) {
         var10000 = new JsonObject();
      }

      val steadyRateJson: JsonElement = var10000.get("minecraft:emitter_initialization");
      val var114: JsonObject = if (steadyRateJson != null) steadyRateJson.getAsJsonObject() else null;
      var10000 = var114;
      if (var114 == null) {
         var10000 = new JsonObject();
      }

      var instantRateJson: JsonElement = var10000.get("minecraft:particle_initialization");
      val var116: JsonObject = if (instantRateJson != null) instantRateJson.getAsJsonObject() else null;
      var10000 = var116;
      if (var116 == null) {
         var10000 = new JsonObject();
      }

      instantRateJson = var10000.get("minecraft:emitter_rate_steady");
      val var117: JsonObject = if (instantRateJson != null) instantRateJson.getAsJsonObject() else null;
      val emitterLifetimeOnceJson: JsonElement = var10000.get("minecraft:emitter_rate_instant");
      val var119: JsonObject = if (emitterLifetimeOnceJson != null) emitterLifetimeOnceJson.getAsJsonObject() else null;
      val emitterLifetimeLoopingJson: JsonElement = var10000.get("minecraft:emitter_lifetime_once");
      val var120: JsonObject = if (emitterLifetimeLoopingJson != null) emitterLifetimeLoopingJson.getAsJsonObject() else null;
      val emitterLifetimeExpressionJson: JsonElement = var10000.get("minecraft:emitter_lifetime_looping");
      val var121: JsonObject = if (emitterLifetimeExpressionJson != null) emitterLifetimeExpressionJson.getAsJsonObject() else null;
      val emitterShapePointJson: JsonElement = var10000.get("minecraft:emitter_lifetime_expression");
      val var122: JsonObject = if (emitterShapePointJson != null) emitterShapePointJson.getAsJsonObject() else null;
      val emitterShapeSphereJson: JsonElement = var10000.get("minecraft:emitter_shape_point");
      val var123: JsonObject = if (emitterShapeSphereJson != null) emitterShapeSphereJson.getAsJsonObject() else null;
      val emitterShapeDiscJson: JsonElement = var10000.get("minecraft:emitter_shape_sphere");
      val var124: JsonObject = if (emitterShapeDiscJson != null) emitterShapeDiscJson.getAsJsonObject() else null;
      val emitterShapeBoxJson: JsonElement = var10000.get("minecraft:emitter_shape_disc");
      val var125: JsonObject = if (emitterShapeBoxJson != null) emitterShapeBoxJson.getAsJsonObject() else null;
      val emitterShapeEntityBoundingBoxJson: JsonElement = var10000.get("minecraft:emitter_shape_box");
      val var126: JsonObject = if (emitterShapeEntityBoundingBoxJson != null) emitterShapeEntityBoundingBoxJson.getAsJsonObject() else null;
      val emitterLifetimeEventsJson: JsonElement = var10000.get("minecraft:emitter_shape_entity_aabb");
      val var127: JsonObject = if (emitterLifetimeEventsJson != null) emitterLifetimeEventsJson.getAsJsonObject() else null;
      val dynamicMotionJson: JsonElement = var10000.get("minecraft:emitter_lifetime_events");
      val var128: JsonObject = if (dynamicMotionJson != null) dynamicMotionJson.getAsJsonObject() else null;
      val parametricMotionJson: JsonElement = var10000.get("minecraft:particle_motion_dynamic");
      val var129: JsonObject = if (parametricMotionJson != null) parametricMotionJson.getAsJsonObject() else null;
      val particleAppearanceJson: JsonElement = var10000.get("minecraft:particle_motion_parametric");
      val var130: JsonObject = if (particleAppearanceJson != null) particleAppearanceJson.getAsJsonObject() else null;
      val var131: JsonObject = var10000.get("minecraft:particle_appearance_billboard").getAsJsonObject();
      val particleLifetimeJson: JsonElement = var131.get("size");
      val sizeJson: JsonArray = if (particleLifetimeJson != null) particleLifetimeJson.getAsJsonArray() else null;
      val cameraModeJson: JsonElement = var10000.get("minecraft:particle_lifetime_expression");
      val var132: JsonObject = if (cameraModeJson != null) cameraModeJson.getAsJsonObject() else null;
      val particleDirectionJson: JsonElement = var131.get("facing_camera_mode");
      var var764: JsonElement = particleDirectionJson;
      if (particleDirectionJson == null) {
         var764 = (new JsonPrimitive("rotate_xyz")) as JsonElement;
      }

      val uvModeJson: JsonElement = var131.get("direction");
      var var765: JsonElement = uvModeJson;
      if (uvModeJson == null) {
         var765 = null;
      }

      var particleInitialSpinJson: JsonObject;
      var collisionJson: JsonObject;
      var var137: JsonElement;
      var var138: JsonObject;
      var var139: JsonObject;
      var var140: ResourceLocation;
      label1399: {
         var135 = var131.get("uv").getAsJsonObject();
         val tintingJson: JsonElement = var10000.get("minecraft:particle_initial_spin");
         particleInitialSpinJson = if (tintingJson != null) tintingJson.getAsJsonObject() else null;
         var137 = var10000.get("minecraft:particle_appearance_tinting");
         val var136: JsonObject = if (var137 != null) var137.getAsJsonObject() else null;
         var137 = if (var136 != null) var136.get("color") else null;
         val spaceJson: JsonElement = var10000.get("minecraft:particle_motion_collision");
         collisionJson = if (spaceJson != null) spaceJson.getAsJsonObject() else null;
         val particleLifetimeEventsJson: JsonElement = var10000.get("minecraft:emitter_local_space");
         var138 = if (particleLifetimeEventsJson != null) particleLifetimeEventsJson.getAsJsonObject() else null;
         val id: JsonElement = var10000.get("minecraft:particle_lifetime_events");
         var139 = if (id != null) id.getAsJsonObject() else null;
         var140 = new ResourceLocation(descJson.get("identifier").getAsString());
         if (var132 != null) {
            val material: JsonElement = var132.get("max_lifetime");
            if (material != null) {
               val texture: java.lang.String = material.getAsString();
               if (texture != null) {
                  var766 = MoLangExtensionsKt.asExpression(texture);
                  break label1399;
               }
            }
         }

         var766 = null;
      }

      var var767: Expression = var766;
      if (var766 == null) {
         var767 = MoLangExtensionsKt.asExpression(0.0);
      }

      label1392: {
         if (var132 != null) {
            val var144: JsonElement = var132.get("expiration_expression");
            if (var144 != null) {
               val sizeX: java.lang.String = var144.getAsString();
               if (sizeX != null) {
                  var768 = MoLangExtensionsKt.asExpression(sizeX);
                  break label1392;
               }
            }
         }

         var768 = null;
      }

      var var769: Expression = var768;
      if (var768 == null) {
         var769 = MoLangExtensionsKt.asExpression(0.0);
      }

      var var143: ParticleMaterial;
      var var147: ResourceLocation;
      label1383: {
         val var145: java.lang.String = basicRenderParametersJson.get("material").getAsString();
         val var770: java.lang.String = StringsKt.substringAfter$default(var145, "_", null, 2, null).toUpperCase(Locale.ROOT);
         var143 = ParticleMaterial.valueOf(var770);
         val sizeY: java.lang.String = basicRenderParametersJson.get("texture").getAsString();
         val var148: java.lang.String = if (StringsKt.endsWith$default(sizeY, ".png", false, 2, null))
            StringsKt.replace$default(sizeY, ".png", "", false, 4, null)
            else
            sizeY;
         var147 = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(
            StringsKt.replace$default(StringsKt.replace$default(var148, "particles/", "", false, 4, null), "textures/", "", false, 4, null), null, 1, null
         );
         if (sizeJson != null) {
            val startRotation: JsonElement = sizeJson.get(0);
            if (startRotation != null) {
               val var154: java.lang.String = startRotation.getAsString();
               if (var154 != null) {
                  var771 = MoLangExtensionsKt.asExpression(var154);
                  break label1383;
               }
            }
         }

         var771 = null;
      }

      var var772: Expression = var771;
      if (var771 == null) {
         var772 = MoLangExtensionsKt.asExpression(1.0);
      }

      label1376: {
         if (sizeJson != null) {
            val var155: JsonElement = sizeJson.get(1);
            if (var155 != null) {
               val curves: java.lang.String = var155.getAsString();
               if (curves != null) {
                  var773 = MoLangExtensionsKt.asExpression(curves);
                  break label1376;
               }
            }
         }

         var773 = null;
      }

      var var774: Expression = var773;
      if (var773 == null) {
         var774 = MoLangExtensionsKt.asExpression(1.0);
      }

      label1369: {
         if (particleInitialSpinJson != null) {
            val var158: JsonElement = particleInitialSpinJson.get("rotation");
            if (var158 != null) {
               val events: java.lang.String = var158.getAsString();
               if (events != null) {
                  var775 = MoLangExtensionsKt.asExpression(events);
                  break label1369;
               }
            }
         }

         var775 = null;
      }

      var var776: Expression = var775;
      if (var775 == null) {
         var776 = MoLangExtensionsKt.asExpression(0.0);
      }

      label1362: {
         if (particleInitialSpinJson != null) {
            val var161: JsonElement = particleInitialSpinJson.get("rotation_rate");
            if (var161 != null) {
               val eventJson: java.lang.String = var161.getAsString();
               if (eventJson != null) {
                  var777 = MoLangExtensionsKt.asExpression(eventJson);
                  break label1362;
               }
            }
         }

         var777 = null;
      }

      var var778: Expression = var777;
      if (var777 == null) {
         var778 = MoLangExtensionsKt.asExpression(0.0);
      }

      val var162: java.util.Set = var10000.entrySet();
      val var163: java.lang.Iterable = var162;
      val emitterUpdateExpressions: java.util.Collection = new ArrayList();
      val speed: java.util.Iterator = var163.iterator();

      label1355:
      while (true) {
         if (!speed.hasNext()) {
            var var160: java.util.List;
            var160 = emitterUpdateExpressions as java.util.List;
            var164 = new LinkedHashMap();
            val emitterStartExpressions: JsonElement = effectJson.get("events");
            val var166: JsonObject = if (emitterStartExpressions != null) emitterStartExpressions.getAsJsonObject() else null;
            label1311:
            if (var166 != null) {
               val var167: java.util.Set = var166.entrySet();
               if (var167 != null) {
                  val particleRenderExpressions: java.util.Iterator = var167.iterator();

                  while (true) {
                     if (!particleRenderExpressions.hasNext()) {
                        break label1311;
                     }

                     var lifetime: java.lang.String;
                     var var253: JsonObject;
                     label1301: {
                        val var203: Entry = particleRenderExpressions.next() as Entry;
                        lifetime = var203.getKey() as java.lang.String;
                        var253 = (var203.getValue() as JsonElement).getAsJsonObject();
                        val var789: JsonElement = var253.get("particle_effect");
                        if (var789 != null) {
                           val var276: JsonObject = var789.getAsJsonObject();
                           if (var276 != null) {
                              var var341: ResourceLocation;
                              var var360: java.lang.String;
                              label1296: {
                                 val var791: java.lang.String = var276.get("effect").getAsString();
                                 var341 = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var791, null, 1, null);
                                 var360 = var276.get("type").getAsString();
                                 val var792: JsonElement = var276.get("pre_effect_expression");
                                 if (var792 != null) {
                                    val var383: java.lang.String = var792.getAsString();
                                    if (var383 != null) {
                                       var793 = MoLangExtensionsKt.asExpressionLike(var383);
                                       break label1296;
                                    }
                                 }

                                 var793 = null;
                              }

                              val var794: java.lang.String = var360.toUpperCase(Locale.ROOT);
                              var790 = new EventParticleEffect(var341, EventParticleEffect.EventParticleType.valueOf(var794), var793);
                              break label1301;
                           }
                        }

                        var790 = null;
                     }

                     label1290: {
                        val var795: JsonElement = var253.get("sound_effect");
                        if (var795 != null) {
                           val var459: JsonObject = var795.getAsJsonObject();
                           if (var459 != null) {
                              val var797: java.lang.String = var459.get("event_name").getAsString();
                              var796 = new EventSoundEffect(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var797, null, 1, null));
                              break label1290;
                           }
                        }

                        var796 = null;
                     }

                     label1284: {
                        val var798: JsonElement = var253.get("expression");
                        if (var798 != null) {
                           val var301: java.lang.String = var798.getAsString();
                           if (var301 != null) {
                              var799 = MoLangExtensionsKt.asExpressionLike(var301);
                              break label1284;
                           }
                        }

                        var799 = null;
                     }

                     var164.put(lifetime, new ParticleEvent(var790, var796, var799));
                  }
               }
            }

            val var176: JsonElement = var10000.get("creation_expression");
            val var170: java.lang.String = if (var176 != null) var176.getAsString() else null;
            var var800: java.lang.String = var170;
            if (var170 == null) {
               var800 = "";
            }

            val var172: java.lang.Iterable = StringsKt.split$default(var800, new java.lang.String[]{";"}, false, 0, 6, null);
            var `destination$iv$ivx`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv : $this$filter$iv) {
               if ((var221 as java.lang.String).length() > 0) {
                  `destination$iv$ivx`.add(var221);
               }
            }

            val var173: java.lang.Iterable = `destination$iv$ivx` as java.util.List;
            `destination$iv$ivx` = new ArrayList(CollectionsKt.collectionSizeOrDefault(`destination$iv$ivx` as java.util.List, 10));

            for (Object item$iv$iv : var173) {
               `destination$iv$ivx`.add(MoLangExtensionsKt.asExpression(var222 as java.lang.String));
            }

            val var168: java.util.List = `destination$iv$ivx` as java.util.List;
            val var184: JsonElement = var10000.get("per_update_expression");
            val var179: java.lang.String = if (var184 != null) var184.getAsString() else null;
            var var802: java.lang.String = var179;
            if (var179 == null) {
               var802 = "";
            }

            val `$this$filter$ivx`: java.lang.Iterable = StringsKt.split$default(var802, new java.lang.String[]{";"}, false, 0, 6, null);
            val `destination$iv$ivxx`: java.util.Collection = new ArrayList();

            for (Object element$iv$ivx : $this$filter$ivx) {
               if ((`element$iv$ivx` as java.lang.String).length() > 0) {
                  `destination$iv$ivxx`.add(`element$iv$ivx`);
               }
            }

            val var182: java.lang.Iterable = `destination$iv$ivxx` as java.util.List;
            val `destination$iv$ivxxx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`destination$iv$ivxx` as java.util.List, 10));

            for (Object item$iv$iv : var182) {
               `destination$iv$ivxxx`.add(MoLangExtensionsKt.asExpression(var238 as java.lang.String));
            }

            val var174: java.util.List = `destination$iv$ivxxx` as java.util.List;
            val var195: JsonElement = var10000.get("per_update_expression");
            val var187: java.lang.String = if (var195 != null) var195.getAsString() else null;
            var var804: java.lang.String = var187;
            if (var187 == null) {
               var804 = "";
            }

            val `$this$filter$ivxx`: java.lang.Iterable = StringsKt.split$default(var804, new java.lang.String[]{";"}, false, 0, 6, null);
            var `destination$iv$ivxxxx`: java.util.Collection = new ArrayList();

            for (Object element$iv$ivxx : $this$filter$ivxx) {
               if ((`element$iv$ivxx` as java.lang.String).length() > 0) {
                  `destination$iv$ivxxxx`.add(`element$iv$ivxx`);
               }
            }

            val var190: java.lang.Iterable = `destination$iv$ivxxxx` as java.util.List;
            `destination$iv$ivxxxx` = new ArrayList(CollectionsKt.collectionSizeOrDefault(`destination$iv$ivxxxx` as java.util.List, 10));

            for (Object item$iv$iv : var190) {
               `destination$iv$ivxxxx`.add(MoLangExtensionsKt.asExpression(var259 as java.lang.String));
            }

            val var183: java.util.List = `destination$iv$ivxxxx` as java.util.List;
            val var208: JsonElement = var10000.get("per_render_expressions");
            val var198: java.lang.String = if (var208 != null) var208.getAsString() else null;
            var var806: java.lang.String = var198;
            if (var198 == null) {
               var806 = "";
            }

            val `$this$filter$ivxxx`: java.lang.Iterable = StringsKt.split$default(var806, new java.lang.String[]{";"}, false, 0, 6, null);
            var `destination$iv$ivxxxxx`: java.util.Collection = new ArrayList();

            for (Object element$iv$ivxxx : $this$filter$ivxxx) {
               if ((`element$iv$ivxxx` as java.lang.String).length() > 0) {
                  `destination$iv$ivxxxxx`.add(`element$iv$ivxxx`);
               }
            }

            val var201: java.lang.Iterable = `destination$iv$ivxxxxx` as java.util.List;
            `destination$iv$ivxxxxx` = new ArrayList(CollectionsKt.collectionSizeOrDefault(`destination$iv$ivxxxxx` as java.util.List, 10));

            for (Object item$iv$iv : var201) {
               `destination$iv$ivxxxxx`.add(MoLangExtensionsKt.asExpression(var283 as java.lang.String));
            }

            val var191: java.util.List = `destination$iv$ivxxxxx` as java.util.List;
            val var202: ObjectRef = new ObjectRef();
            val var229: JsonElement = var10000.get("minecraft:particle_initial_speed");
            val var219: java.lang.String = if (var229 != null) var229.getAsString() else null;
            var var808: java.lang.String = var219;
            if (var219 == null) {
               var808 = "0.0";
            }

            val var211: Expression = MoLangExtensionsKt.asExpression(var808);
            val var810: ParticleEmitterRate;
            if (var119 != null) {
               val var809: InstantParticleEmitterRate = new InstantParticleEmitterRate;
               val var262: JsonElement = var119.get("num_particles");
               val var243: java.lang.String = if (var262 != null) var262.getAsString() else null;
               var var10002: java.lang.String = var243;
               if (var243 == null) {
                  var10002 = "1.0";
               }

               val var230: Expression = MoLangExtensionsKt.asExpression(var10002);
               var809./* $VF: Unable to resugar constructor */<init>(var230);
               var810 = var809;
            } else {
               if (var117 == null) {
                  throw new IllegalStateException("Missing or unspecified emitter rate");
               }

               val var811: SteadyParticleEmitterRate = new SteadyParticleEmitterRate;
               val var263: JsonElement = var117.get("spawn_rate");
               var var244: java.lang.String = if (var263 != null) var263.getAsString() else null;
               var var894: java.lang.String = var244;
               if (var244 == null) {
                  var894 = "1.0";
               }

               val var231: Expression = MoLangExtensionsKt.asExpression(var894);
               var244 = var117.get("max_particles").getAsString();
               var var921: java.lang.String = var244;
               if (var244 == null) {
                  var921 = "1.0";
               }

               val var232: Expression = MoLangExtensionsKt.asExpression(var921);
               var811./* $VF: Unable to resugar constructor */<init>(var231, var232);
               var810 = var811;
            }

            val var813: ParticleEmitterLifetime;
            if (var120 != null) {
               val var812: OnceEmitterLifetime = new OnceEmitterLifetime;
               val var284: JsonElement = var120.get("active_time");
               val var264: java.lang.String = if (var284 != null) var284.getAsString() else null;
               var var896: java.lang.String = var264;
               if (var264 == null) {
                  var896 = "";
               }

               val var246: Expression = MoLangExtensionsKt.asExpression(var896);
               var812./* $VF: Unable to resugar constructor */<init>(var246);
               var813 = var812;
            } else if (var121 != null) {
               val var814: LoopingEmitterLifetime = new LoopingEmitterLifetime;
               var var285: JsonElement = var121.get("active_time");
               var var265: java.lang.String = if (var285 != null) var285.getAsString() else null;
               var var897: java.lang.String = var265;
               if (var265 == null) {
                  var897 = "";
               }

               val var247: Expression = MoLangExtensionsKt.asExpression(var897);
               var285 = var121.get("sleep_time");
               var265 = if (var285 != null) var285.getAsString() else null;
               var var922: java.lang.String = var265;
               if (var265 == null) {
                  var922 = "0.0";
               }

               val var248: Expression = MoLangExtensionsKt.asExpression(var922);
               var814./* $VF: Unable to resugar constructor */<init>(var247, var248);
               var813 = var814;
            } else {
               if (var122 == null) {
                  throw new NotImplementedError("An operation is not implemented: Missing or unspecified emitter lifetime");
               }

               val var815: ExpressionEmitterLifetime = new ExpressionEmitterLifetime;
               var var287: JsonElement = var122.get("activation_expression");
               var var267: java.lang.String = if (var287 != null) var287.getAsString() else null;
               var var899: java.lang.String = var267;
               if (var267 == null) {
                  var899 = "";
               }

               val var249: Expression = MoLangExtensionsKt.asExpression(var899);
               var287 = var122.get("expiration_expression");
               var267 = if (var287 != null) var287.getAsString() else null;
               var var923: java.lang.String = var267;
               if (var267 == null) {
                  var923 = "";
               }

               val var250: Expression = MoLangExtensionsKt.asExpression(var923);
               var815./* $VF: Unable to resugar constructor */<init>(var249, var250);
               var813 = var815;
            }

            val var818: ParticleEmitterShape;
            if (var123 != null) {
               label1224: {
                  val var289: JsonElement = var123.get("offset");
                  if (var289 != null) {
                     val var306: JsonArray = var289.getAsJsonArray();
                     if (var306 != null) {
                        val var344: java.lang.Iterable = var306 as java.lang.Iterable;
                        val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(
                           CollectionsKt.collectionSizeOrDefault(var306 as java.lang.Iterable, 10)
                        );

                        for (Object item$iv$iv : $this$map$iv) {
                           val var817: java.lang.String = (var460 as JsonElement).getAsString();
                           `destination$iv$ivxxxxxx`.add(MoLangExtensionsKt.asExpression(var817));
                        }

                        var816 = `destination$iv$ivxxxxxx` as java.util.List;
                        break label1224;
                     }
                  }

                  var816 = CollectionsKt.listOf(
                     new NumberExpression[]{MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0)}
                  );
               }

               loadEffect$resolveDirection(var202, var123);
               var818 = new PointParticleEmitterShape(new Triple(var816.get(0), var816.get(1), var816.get(2)));
            } else if (var124 != null) {
               label1161: {
                  val var290: JsonElement = var124.get("offset");
                  if (var290 != null) {
                     val var307: JsonArray = var290.getAsJsonArray();
                     if (var307 != null) {
                        val var346: java.lang.Iterable = var307 as java.lang.Iterable;
                        val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(
                           CollectionsKt.collectionSizeOrDefault(var307 as java.lang.Iterable, 10)
                        );

                        for (Object item$iv$iv : $this$map$iv) {
                           val var820: java.lang.String = (var461 as JsonElement).getAsString();
                           `destination$iv$ivxxxxxx`.add(MoLangExtensionsKt.asExpression(var820));
                        }

                        var819 = `destination$iv$ivxxxxxx` as java.util.List;
                        break label1161;
                     }
                  }

                  var819 = CollectionsKt.listOf(
                     new NumberExpression[]{MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0)}
                  );
               }

               var var901: Triple;
               var var924: Expression;
               label1150: {
                  loadEffect$resolveDirection(var202, var124);
                  var821 = new SphereParticleEmitterShape;
                  var901 = new Triple(var819.get(0), var819.get(1), var819.get(2));
                  val var308: JsonElement = var124.get("radius");
                  if (var308 != null) {
                     val var327: java.lang.String = var308.getAsString();
                     if (var327 != null) {
                        var924 = MoLangExtensionsKt.asExpression(var327);
                        break label1150;
                     }
                  }

                  var924 = null;
               }

               var924 = var924;
               if (var924 == null) {
                  var924 = MoLangExtensionsKt.asExpression(0.0);
               }

               val var292: JsonElement = var124.get("surface_only");
               var821./* $VF: Unable to resugar constructor */<init>(var901, var924, var292 != null && var292.getAsBoolean());
               var818 = var821;
            } else if (var125 != null) {
               label1188: {
                  loadEffect$resolveDirection(var202, var125);
                  val var293: JsonElement = var125.get("offset");
                  if (var293 != null) {
                     val var310: JsonArray = var293.getAsJsonArray();
                     if (var310 != null) {
                        val var348: java.lang.Iterable = var310 as java.lang.Iterable;
                        val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(
                           CollectionsKt.collectionSizeOrDefault(var310 as java.lang.Iterable, 10)
                        );

                        for (Object item$iv$iv : $this$map$iv) {
                           val var823: java.lang.String = (var462 as JsonElement).getAsString();
                           `destination$iv$ivxxxxxx`.add(MoLangExtensionsKt.asExpression(var823));
                        }

                        var822 = `destination$iv$ivxxxxxx` as java.util.List;
                        break label1188;
                     }
                  }

                  var822 = CollectionsKt.listOf(
                     new NumberExpression[]{MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0)}
                  );
               }

               val var311: JsonElement = var125.get("plane_normal");
               var var824: JsonElement = var311;
               if (var311 == null) {
                  var824 = (new JsonPrimitive("y")) as JsonElement;
               }

               val var826: Triple;
               if (!var824.isJsonArray()) {
                  val var330: java.lang.String = var824.getAsString();
                  var826 = if (var330 == "x")
                     new Triple(MoLangExtensionsKt.asExpression(1.0), MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0))
                     else
                     (
                        if (var330 == "y")
                           new Triple(MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(1.0), MoLangExtensionsKt.asExpression(0.0))
                           else
                           new Triple(MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(1.0))
                     );
               } else {
                  val var349: JsonArray = var824.getAsJsonArray();
                  val var350: java.lang.Iterable = var349 as java.lang.Iterable;
                  val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var349 as java.lang.Iterable, 10));

                  for (Object item$iv$iv : $this$map$iv) {
                     val var825: java.lang.String = (var463 as JsonElement).getAsString();
                     `destination$iv$ivxxxxxx`.add(MoLangExtensionsKt.asExpression(var825));
                  }

                  var826 = new Triple(
                     (`destination$iv$ivxxxxxx` as java.util.List).get(0),
                     (`destination$iv$ivxxxxxx` as java.util.List).get(1),
                     (`destination$iv$ivxxxxxx` as java.util.List).get(2)
                  );
               }

               var var331: Triple;
               label1166: {
                  loadEffect$resolveDirection(var202, var125);
                  var331 = new Triple(var822.get(0), var822.get(1), var822.get(2));
                  val var384: JsonElement = var125.get("radius");
                  if (var384 != null) {
                     val var404: java.lang.String = var384.getAsString();
                     if (var404 != null) {
                        var827 = MoLangExtensionsKt.asExpression(var404);
                        break label1166;
                     }
                  }

                  var827 = null;
               }

               var var828: Expression = var827;
               if (var827 == null) {
                  var828 = MoLangExtensionsKt.asExpression(0.0);
               }

               val var385: JsonElement = var125.get("surface_only");
               var818 = new DiscParticleEmitterShape(var331, var828, var826, var385 != null && var385.getAsBoolean());
            } else if (var126 != null) {
               label1210: {
                  loadEffect$resolveDirection(var202, var126);
                  val var295: JsonElement = var126.get("offset");
                  if (var295 != null) {
                     val var313: JsonArray = var295.getAsJsonArray();
                     if (var313 != null) {
                        val var353: java.lang.Iterable = var313 as java.lang.Iterable;
                        val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(
                           CollectionsKt.collectionSizeOrDefault(var313 as java.lang.Iterable, 10)
                        );

                        for (Object item$iv$iv : $this$map$iv) {
                           val var831: java.lang.String = (var464 as JsonElement).getAsString();
                           `destination$iv$ivxxxxxx`.add(MoLangExtensionsKt.asExpression(var831));
                        }

                        var830 = `destination$iv$ivxxxxxx` as java.util.List;
                        break label1210;
                     }
                  }

                  var830 = CollectionsKt.listOf(
                     new NumberExpression[]{MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0)}
                  );
               }

               label1199: {
                  val var314: JsonElement = var126.get("half_dimensions");
                  if (var314 != null) {
                     val var333: JsonArray = var314.getAsJsonArray();
                     if (var333 != null) {
                        val var370: java.lang.Iterable = var333 as java.lang.Iterable;
                        val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(
                           CollectionsKt.collectionSizeOrDefault(var333 as java.lang.Iterable, 10)
                        );

                        for (Object item$iv$iv : $this$map$iv) {
                           val var833: java.lang.String = (var494 as JsonElement).getAsString();
                           `destination$iv$ivxxxxxx`.add(MoLangExtensionsKt.asExpression(var833));
                        }

                        var832 = `destination$iv$ivxxxxxx` as java.util.List;
                        break label1199;
                     }
                  }

                  var832 = CollectionsKt.listOf(
                     new NumberExpression[]{MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0)}
                  );
               }

               val var902: Triple = new Triple(var830.get(0), var830.get(1), var830.get(2));
               val var926: Triple = new Triple(var832.get(0), var832.get(1), var832.get(2));
               val var315: JsonElement = var126.get("surface_only");
               var818 = new BoxParticleEmitterShape(var902, var926, var315 != null && var315.getAsBoolean());
            } else {
               if (var127 == null) {
                  throw new NotImplementedError("An operation is not implemented: Missing or unimplemented emitter shape");
               }

               loadEffect$resolveDirection(var202, var127);
               val var273: JsonElement = var127.get("surface_only");
               var818 = new EntityBoundingBoxParticleEmitterShape(var273 != null && var273.getAsBoolean());
            }

            val var840: ParticleMotion;
            if (var129 != null) {
               label1120: {
                  val var316: JsonElement = var129.get("linear_acceleration");
                  if (var316 != null) {
                     val var335: JsonArray = var316.getAsJsonArray();
                     if (var335 != null) {
                        val var372: java.lang.Iterable = var335 as java.lang.Iterable;
                        val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(
                           CollectionsKt.collectionSizeOrDefault(var335 as java.lang.Iterable, 10)
                        );

                        for (Object item$iv$iv : $this$map$iv) {
                           val var837: java.lang.String = (var495 as JsonElement).getAsString();
                           `destination$iv$ivxxxxxx`.add(MoLangExtensionsKt.asExpression(var837));
                        }

                        var836 = `destination$iv$ivxxxxxx` as java.util.List;
                        break label1120;
                     }
                  }

                  var836 = CollectionsKt.listOf(
                     new NumberExpression[]{MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0)}
                  );
               }

               label1109: {
                  val var356: JsonElement = var129.get("linear_drag_coefficient");
                  if (var356 != null) {
                     val var373: java.lang.String = var356.getAsString();
                     if (var373 != null) {
                        var838 = MoLangExtensionsKt.asExpression(var373);
                        break label1109;
                     }
                  }

                  var838 = null;
               }

               var var839: Expression = var838;
               if (var838 == null) {
                  var839 = MoLangExtensionsKt.asExpression(0.0);
               }

               var var904: Any = var202.element;
               var904 = var904 as ParticleMotionDirection;
               var840 = new DynamicParticleMotion((ParticleMotionDirection)var904, var211, new Triple(var836.get(0), var836.get(1), var836.get(2)), var839);
            } else if (var130 != null) {
               label1142: {
                  val var318: JsonElement = var130.get("relative_position");
                  if (var318 != null) {
                     val var337: JsonArray = var318.getAsJsonArray();
                     if (var337 != null) {
                        val var375: java.lang.Iterable = var337 as java.lang.Iterable;
                        val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(
                           CollectionsKt.collectionSizeOrDefault(var337 as java.lang.Iterable, 10)
                        );

                        for (Object item$iv$iv : $this$map$iv) {
                           val var842: java.lang.String = (var496 as JsonElement).getAsString();
                           `destination$iv$ivxxxxxx`.add(MoLangExtensionsKt.asExpression(var842));
                        }

                        var841 = `destination$iv$ivxxxxxx` as java.util.List;
                        break label1142;
                     }
                  }

                  var841 = CollectionsKt.listOf(
                     new NumberExpression[]{MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0)}
                  );
               }

               label1131: {
                  val var338: JsonElement = var130.get("direction");
                  if (var338 != null) {
                     val var358: JsonArray = var338.getAsJsonArray();
                     if (var358 != null) {
                        val var390: java.lang.Iterable = var358 as java.lang.Iterable;
                        val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(
                           CollectionsKt.collectionSizeOrDefault(var358 as java.lang.Iterable, 10)
                        );

                        for (Object item$iv$iv : $this$map$iv) {
                           val var844: java.lang.String = (var517 as JsonElement).getAsString();
                           `destination$iv$ivxxxxxx`.add(MoLangExtensionsKt.asExpression(var844));
                        }

                        var843 = `destination$iv$ivxxxxxx` as java.util.List;
                        break label1131;
                     }
                  }

                  var843 = CollectionsKt.listOf(
                     new NumberExpression[]{MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0), MoLangExtensionsKt.asExpression(0.0)}
                  );
               }

               var840 = new ParametricParticleMotion(
                  new Triple(var841.get(0), var841.get(1), var841.get(2)), new Triple(var843.get(0), var843.get(1), var843.get(2))
               );
            } else {
               var840 = new StaticParticleMotion();
            }

            label1104: {
               if (var765 != null) {
                  val var320: JsonObject = var765.getAsJsonObject();
                  if (var320 != null) {
                     val var848: ParticleViewDirection;
                     if (!(var320.get("mode").getAsString() == "custom")) {
                        val var906: JsonElement = var320.get("min_speed_threshold");
                        var848 = new FromMotionViewDirection(if (var906 != null) var906.getAsDouble() else 0.01);
                     } else {
                        val var846: JsonArray = var320.get("custom_direction").getAsJsonArray();
                        val var408: java.lang.Iterable = var846 as java.lang.Iterable;
                        val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(
                           CollectionsKt.collectionSizeOrDefault(var846 as java.lang.Iterable, 10)
                        );

                        for (Object item$iv$iv : $this$map$iv) {
                           val var847: java.lang.String = (var554 as JsonElement).getAsString();
                           `destination$iv$ivxxxxxx`.add(MoLangExtensionsKt.asExpression(var847));
                        }

                        var848 = new CustomViewDirection(
                           new Triple(
                              (`destination$iv$ivxxxxxx` as java.util.List).get(0),
                              (`destination$iv$ivxxxxxx` as java.util.List).get(1),
                              (`destination$iv$ivxxxxxx` as java.util.List).get(2)
                           )
                        );
                     }

                     var845 = var848;
                     break label1104;
                  }
               }

               var845 = new FromMotionViewDirection(0.0, 1, null);
            }

            label1088: {
               val var321: java.lang.String = if (var764.isJsonPrimitive()) var764.getAsString() else "rotate_xyz";
               if (var321 != null) {
                  switch (cameraModeType.hashCode()) {
                     case -2041694580:
                        if (var321.equals("lookat_y")) {
                           var849 = new LookAtY();
                           break label1088;
                        }
                        break;
                     case -1888716142:
                        if (var321.equals("lookat_direction")) {
                           var849 = new LookAtDirection();
                           break label1088;
                        }
                        break;
                     case -1631834184:
                        if (var321.equals("direction_x")) {
                           var849 = new DirectionX();
                           break label1088;
                        }
                        break;
                     case -1631834183:
                        if (var321.equals("direction_y")) {
                           var849 = new DirectionY();
                           break label1088;
                        }
                        break;
                     case -1631834182:
                        if (var321.equals("direction_z")) {
                           var849 = new DirectionZ();
                           break label1088;
                        }
                        break;
                     case -78399307:
                        if (var321.equals("rotate_xyz")) {
                           var849 = new RotateXYZCameraMode();
                           break label1088;
                        }
                        break;
                     case -40305003:
                        if (var321.equals("rotate_y")) {
                           var849 = new RotateYCameraMode();
                           break label1088;
                        }
                        break;
                     case 731565804:
                        if (var321.equals("lookat_xyz")) {
                           var849 = new LookAtXYZ();
                           break label1088;
                        }
                        break;
                     case 1939370469:
                        if (var321.equals("emitter_transform_xy")) {
                           var849 = new EmitterXYPlane();
                           break label1088;
                        }
                        break;
                     case 1939370470:
                        if (var321.equals("emitter_transform_xz")) {
                           var849 = new EmitterXZPlane();
                           break label1088;
                        }
                     default:
                  }
               }

               var849 = new EmitterYZPlane();
            }

            val var858: ParticleUVMode;
            if (var135.has("flipbook")) {
               var var377: JsonObject;
               var var446: Expression;
               var var471: Expression;
               var var500: Expression;
               var var520: Expression;
               var var535: Expression;
               var var544: Expression;
               label1069: {
                  var377 = var135.get("flipbook").getAsJsonObject();
                  val var392: JsonArray = var377.get("base_UV").getAsJsonArray();
                  val var409: JsonArray = var377.get("size_UV").getAsJsonArray();
                  val var425: JsonArray = var377.get("step_UV").getAsJsonArray();
                  val var470: java.lang.String = var392.get(0).getAsString();
                  var446 = MoLangExtensionsKt.asExpression(var470);
                  val var499: java.lang.String = var392.get(1).getAsString();
                  var471 = MoLangExtensionsKt.asExpression(var499);
                  val var519: java.lang.String = var409.get(0).getAsString();
                  var500 = MoLangExtensionsKt.asExpression(var519);
                  val var534: java.lang.String = var409.get(1).getAsString();
                  var520 = MoLangExtensionsKt.asExpression(var534);
                  val var543: java.lang.String = var425.get(0).getAsString();
                  var535 = MoLangExtensionsKt.asExpression(var543);
                  val var555: java.lang.String = var425.get(1).getAsString();
                  var544 = MoLangExtensionsKt.asExpression(var555);
                  val var570: JsonElement = var135.get("texture_width");
                  var850 = if (var570 != null) var570.getAsInt() else 128;
                  val var589: JsonElement = var135.get("texture_height");
                  var851 = if (var589 != null) var589.getAsInt() else 128;
                  val var622: JsonElement = var377.get("max_frame");
                  if (var622 != null) {
                     val var635: java.lang.String = var622.getAsString();
                     if (var635 != null) {
                        var852 = MoLangExtensionsKt.asExpression(var635);
                        break label1069;
                     }
                  }

                  var852 = null;
               }

               var var853: Expression = var852;
               if (var852 == null) {
                  var853 = new NumberExpression(0.0);
               }

               label1061: {
                  val var623: JsonElement = var377.get("loop");
                  var854 = var623 != null && var623.getAsBoolean();
                  val var648: JsonElement = var377.get("frames_per_second");
                  if (var648 != null) {
                     val var659: java.lang.String = var648.getAsString();
                     if (var659 != null) {
                        var855 = MoLangExtensionsKt.asExpression(var659);
                        break label1061;
                     }
                  }

                  var855 = null;
               }

               var var856: Expression = var855;
               if (var855 == null) {
                  var856 = new NumberExpression(0.0);
               }

               val var649: JsonElement = var377.get("stretch_to_lifetime");
               val var857: Boolean = var649 != null && var649.getAsBoolean();
               var858 = new AnimatedParticleUVMode(var446, var471, var850, var851, var500, var520, var535, var544, var853, var856, var857, var854);
            } else {
               val var393: JsonArray = var135.get("uv").getAsJsonArray();
               val var859: JsonArray;
               if (var393 == null) {
                  val var410: JsonArray = new JsonArray();
                  var410.add((new JsonPrimitive("0")) as JsonElement);
                  var410.add((new JsonPrimitive("0")) as JsonElement);
                  var859 = var410;
               } else {
                  var859 = var393;
               }

               val var426: JsonElement = var135.get("uv_size");
               val var411: JsonArray = if (var426 != null) var426.getAsJsonArray() else null;
               val var860: JsonArray;
               if (var411 == null) {
                  val var427: JsonArray = new JsonArray();
                  var427.add((new JsonPrimitive("128")) as JsonElement);
                  var427.add((new JsonPrimitive("128")) as JsonElement);
                  var860 = var427;
               } else {
                  var860 = var411;
               }

               val var428: java.lang.String = var859.get(0).getAsString();
               val var412: Expression = MoLangExtensionsKt.asExpression(var428);
               val var448: java.lang.String = var859.get(1).getAsString();
               val var429: Expression = MoLangExtensionsKt.asExpression(var448);
               val var473: java.lang.String = var860.get(0).getAsString();
               val var449: Expression = MoLangExtensionsKt.asExpression(var473);
               val var501: java.lang.String = var860.get(1).getAsString();
               val var474: Expression = MoLangExtensionsKt.asExpression(var501);
               val var521: JsonElement = var135.get("texture_width");
               val var861: Int = if (var521 != null) var521.getAsInt() else 128;
               val var537: JsonElement = var135.get("texture_height");
               val var862: Int = if (var537 != null) var537.getAsInt() else 128;
               var858 = new StaticParticleUVMode(var412, var429, var861, var862, var449, var474);
            }

            var10000 = var129;
            if (var129 == null) {
               var10000 = var130;
            }

            label1055: {
               if (var10000 != null) {
                  val var413: JsonElement = var10000.get("rotation");
                  if (var413 != null) {
                     val var430: java.lang.String = var413.getAsString();
                     if (var430 != null) {
                        var864 = MoLangExtensionsKt.asExpression(var430);
                        break label1055;
                     }
                  }
               }

               var864 = null;
            }

            val var865: ParticleRotation;
            if (var864 != null) {
               var865 = new ParametricParticleRotation(var864);
            } else {
               var var933: Expression;
               label1047: {
                  var866 = new DynamicParticleRotation;
                  if (var129 != null) {
                     val var450: JsonElement = var129.get("rotation_acceleration");
                     if (var450 != null) {
                        val var475: java.lang.String = var450.getAsString();
                        if (var475 != null) {
                           var933 = MoLangExtensionsKt.asExpression(var475);
                           break label1047;
                        }
                     }
                  }

                  var933 = null;
               }

               var933 = var933;
               if (var933 == null) {
                  var933 = MoLangExtensionsKt.asExpression(0.0);
               }

               var var10005: Expression;
               label1040: {
                  if (var129 != null) {
                     val var451: JsonElement = var129.get("rotation_drag_coefficient");
                     if (var451 != null) {
                        val var476: java.lang.String = var451.getAsString();
                        if (var476 != null) {
                           var10005 = MoLangExtensionsKt.asExpression(var476);
                           break label1040;
                        }
                     }
                  }

                  var10005 = null;
               }

               var10005 = var10005;
               if (var10005 == null) {
                  var10005 = MoLangExtensionsKt.asExpression(0.0);
               }

               var866./* $VF: Unable to resugar constructor */<init>(var776, var778, var933, var10005);
               var865 = var866;
            }

            val var869: ParticleTinting;
            if (var137 is JsonObject) {
               val var477: java.lang.String = (var137 as JsonObject).get("interpolant").getAsString();
               val var452: Expression = MoLangExtensionsKt.asExpression(var477);
               val var453: java.util.Set = (var137 as JsonObject).get("gradient").getAsJsonObject().entrySet();
               val var454: java.lang.Iterable = var453;
               val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var453, 10));

               for (Object item$iv$iv : var454) {
                  val var572: Entry = var557 as Entry;
                  val var610: java.lang.String = var572.getKey() as java.lang.String;
                  val var625: JsonElement = var572.getValue() as JsonElement;
                  val var868: java.lang.Double = java.lang.Double.parseDouble(var610);
                  val var10001: SnowstormParticleReader = INSTANCE;
                  val var907: java.lang.String = var625.getAsString();
                  `destination$iv$ivxxxxxx`.add(TuplesKt.to(var868, var10001.parseHex(var907)));
               }

               var869 = new GradientParticleTinting(var452, MapsKt.toMap(`destination$iv$ivxxxxxx` as java.util.List));
            } else if (var137 is JsonArray) {
               var var479: java.lang.Iterable = var137 as java.lang.Iterable;
               val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var137 as java.lang.Iterable, 10));

               for (Object item$iv$iv : $this$map$iv) {
                  val var870: java.lang.String = (var573 as JsonElement).getAsString();
                  `destination$iv$ivxxxxxx`.add(MoLangExtensionsKt.asExpression(var870));
               }

               val var455: java.util.List = `destination$iv$ivxxxxxx` as java.util.List;
               var479 = (java.lang.Iterable)(`destination$iv$ivxxxxxx` as java.util.List).get(0);
               val var908: Expression = var479 as Expression;
               var479 = (java.lang.Iterable)var455.get(1);
               val var927: Expression = var479 as Expression;
               var479 = (java.lang.Iterable)var455.get(2);
               val var935: Expression = var479 as Expression;
               var479 = (java.lang.Iterable)var455.get(3);
               var869 = new ExpressionParticleTinting(var908, var927, var935, var479 as Expression);
            } else {
               var869 = new ExpressionParticleTinting(null, null, null, null, 15, null);
            }

            val var456: Boolean = var10000.has("minecraft:particle_appearance_lighting");
            val var875: ParticleCollision;
            if (collisionJson != null) {
               var var560: Boolean;
               label1017: {
                  var560 = true;
                  val var871: JsonElement = collisionJson.get("collision_radius");
                  if (var871 != null) {
                     val var574: java.lang.String = var871.getAsString();
                     if (var574 != null) {
                        var872 = MoLangExtensionsKt.asExpression(var574);
                        break label1017;
                     }
                  }

                  var872 = null;
               }

               val var873: Expression;
               if (var872 == null) {
                  val var575: SnowstormParticleReader = INSTANCE;
                  var560 = false;
                  var873 = new NumberExpression(0.1);
               } else {
                  var873 = var872;
               }

               var var910: Expression;
               label1011: {
                  var874 = new ParticleCollision;
                  val var909: JsonElement = collisionJson.get("enabled");
                  if (var909 != null) {
                     val var576: java.lang.String = var909.getAsString();
                     if (var576 != null) {
                        var910 = MoLangExtensionsKt.asExpression(var576);
                        break label1011;
                     }
                  }

                  var910 = null;
               }

               if (var910 == null) {
                  var910 = new NumberExpression(if (var560) 1.0 else 0.0);
               } else {
                  var910 = var910;
               }

               var var937: Expression;
               label1005: {
                  val var936: JsonElement = collisionJson.get("collision_drag");
                  if (var936 != null) {
                     val var577: java.lang.String = var936.getAsString();
                     if (var577 != null) {
                        var937 = MoLangExtensionsKt.asExpression(var577);
                        break label1005;
                     }
                  }

                  var937 = null;
               }

               if (var937 == null) {
                  var937 = new NumberExpression(10.0);
               } else {
                  var937 = var937;
               }

               var var942: Expression;
               label999: {
                  val var941: JsonElement = collisionJson.get("coefficient_of_restitution");
                  if (var941 != null) {
                     val var578: java.lang.String = var941.getAsString();
                     if (var578 != null) {
                        var942 = MoLangExtensionsKt.asExpression(var578);
                        break label999;
                     }
                  }

                  var942 = null;
               }

               if (var942 == null) {
                  var942 = new NumberExpression(0.0);
               } else {
                  var942 = var942;
               }

               val var10006: JsonElement = collisionJson.get("expire_on_contact");
               var874./* $VF: Unable to resugar constructor */<init>(var910, var873, var937, var942, var10006 != null && var10006.getAsBoolean());
               var875 = var874;
            } else {
               var875 = new ParticleCollision(null, null, null, null, false, 31, null);
            }

            val var878: ParticleSpace;
            if (var138 != null) {
               val var876: JsonElement = var138.get("rotation");
               val var579: Boolean = var876 != null && var876.getAsBoolean();
               val var877: ParticleSpace = new ParticleSpace;
               val var912: Boolean;
               if (var579) {
                  var912 = true;
               } else {
                  val var913: JsonElement = var138.get("position");
                  var912 = var913 != null && var913.getAsBoolean();
               }

               val var939: JsonElement = var138.get("velocity");
               var877./* $VF: Unable to resugar constructor */<init>(var912, var579, var939 != null && var939.getAsBoolean());
               var878 = var877;
            } else {
               var878 = new ParticleSpace(false, false, false, 7, null);
            }

            val var885: BedrockParticle.EventSet;
            if (var139 != null) {
               label991: {
                  val var597: JsonElement = var139.get("creation_event");
                  if (var597 != null) {
                     val var879: JsonArray = GsonExtensionsKt.normalizeToArray(var597);
                     if (var879 != null) {
                        val var613: java.lang.Iterable = var879 as java.lang.Iterable;
                        val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(
                           CollectionsKt.collectionSizeOrDefault(var879 as java.lang.Iterable, 10)
                        );

                        for (Object item$iv$iv : $this$map$iv) {
                           val var914: java.lang.String = (var679 as JsonElement).getAsString();
                           `destination$iv$ivxxxxxx`.add(new SimpleEventTrigger(var914));
                        }

                        var880 = CollectionsKt.toMutableList(`destination$iv$ivxxxxxx` as java.util.List);
                        if (var880 != null) {
                           break label991;
                        }
                     }
                  }

                  var880 = new ArrayList();
               }

               label979: {
                  val obj: JsonElement = var139.get("expiration_event");
                  if (obj != null) {
                     val var881: JsonArray = GsonExtensionsKt.normalizeToArray(obj);
                     if (var881 != null) {
                        val var628: java.lang.Iterable = var881 as java.lang.Iterable;
                        val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(
                           CollectionsKt.collectionSizeOrDefault(var881 as java.lang.Iterable, 10)
                        );

                        for (Object item$iv$iv : $this$map$iv) {
                           val var915: java.lang.String = (var687 as JsonElement).getAsString();
                           `destination$iv$ivxxxxxx`.add(new SimpleEventTrigger(var915));
                        }

                        var882 = CollectionsKt.toMutableList(`destination$iv$ivxxxxxx` as java.util.List);
                        if (var882 != null) {
                           break label979;
                        }
                     }
                  }

                  var882 = new ArrayList();
               }

               label967: {
                  val key: JsonElement = var139.get("timeline");
                  if (key != null) {
                     val var614: JsonObject = key.getAsJsonObject();
                     if (var614 != null) {
                        val var629: java.util.Set = var614.entrySet();
                        if (var629 != null) {
                           val var651: java.lang.Iterable = var629;
                           val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var629, 10));

                           for (Object item$iv$iv : $this$map$iv) {
                              val value: Entry = var710 as Entry;
                              val `$this$mapTo$iv$iv`: java.lang.String = value.getKey() as java.lang.String;
                              val `$i$f$map`: JsonElement = value.getValue() as JsonElement;
                              val var883: java.lang.Double = java.lang.Double.parseDouble(`$this$mapTo$iv$iv`);
                              val `$this$mapTo$iv$ivx`: java.lang.Iterable = GsonExtensionsKt.normalizeToArray(`$i$f$map`) as java.lang.Iterable;
                              val `item$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$mapTo$iv$ivx`, 10));

                              for (Object item$iv$ivxx : $this$mapTo$iv$ivx) {
                                 `item$iv$ivx`.add((`item$iv$ivxx` as JsonElement).getAsString());
                              }

                              `destination$iv$ivxxxxxx`.add(TuplesKt.to(var883, CollectionsKt.toMutableList(`item$iv$ivx` as java.util.List)));
                           }

                           val var652: java.util.Map = MapsKt.toMap(`destination$iv$ivxxxxxx` as java.util.List);
                           if (var652 != null) {
                              val var664: java.util.Map = MapsKt.toMutableMap(var652);
                              var884 = var664;
                              if (var664 != null) {
                                 break label967;
                              }
                           }
                        }
                     }
                  }

                  var884 = new LinkedHashMap();
               }

               var885 = new BedrockParticle.EventSet(var880, var882, new EventTriggerTimeline(var884));
            } else {
               var885 = new BedrockParticle.EventSet(new ArrayList<>(), new ArrayList<>(), new EventTriggerTimeline(new LinkedHashMap<>()));
            }

            label947: {
               if (var128 != null) {
                  val var549: JsonElement = var128.get("creation_event");
                  if (var549 != null) {
                     val var562: JsonArray = GsonExtensionsKt.normalizeToArray(var549);
                     if (var562 != null) {
                        val var599: java.lang.Iterable = var562 as java.lang.Iterable;
                        val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(
                           CollectionsKt.collectionSizeOrDefault(var562 as java.lang.Iterable, 10)
                        );

                        for (Object item$iv$iv : $this$map$iv) {
                           val var916: java.lang.String = (var672 as JsonElement).getAsString();
                           `destination$iv$ivxxxxxx`.add(new SimpleEventTrigger(var916));
                        }

                        val var600: java.util.List = CollectionsKt.toMutableList(`destination$iv$ivxxxxxx` as java.util.List);
                        var886 = var600;
                        if (var600 != null) {
                           break label947;
                        }
                     }
                  }
               }

               var886 = new ArrayList();
            }

            label934: {
               if (var128 != null) {
                  val var563: JsonElement = var128.get("expiration_event");
                  if (var563 != null) {
                     val var582: JsonArray = GsonExtensionsKt.normalizeToArray(var563);
                     if (var582 != null) {
                        val var616: java.lang.Iterable = var582 as java.lang.Iterable;
                        val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(
                           CollectionsKt.collectionSizeOrDefault(var582 as java.lang.Iterable, 10)
                        );

                        for (Object item$iv$iv : $this$map$iv) {
                           val var917: java.lang.String = (var683 as JsonElement).getAsString();
                           `destination$iv$ivxxxxxx`.add(new SimpleEventTrigger(var917));
                        }

                        val var617: java.util.List = CollectionsKt.toMutableList(`destination$iv$ivxxxxxx` as java.util.List);
                        var887 = var617;
                        if (var617 != null) {
                           break label934;
                        }
                     }
                  }
               }

               var887 = new ArrayList();
            }

            label921: {
               if (var128 != null) {
                  val var583: JsonElement = var128.get("travel_distance_events");
                  if (var583 != null) {
                     val var602: JsonObject = var583.getAsJsonObject();
                     if (var602 != null) {
                        val var618: java.util.Set = var602.entrySet();
                        if (var618 != null) {
                           val var642: java.lang.Iterable = var618;
                           val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var618, 10));

                           for (Object item$iv$iv : $this$map$iv) {
                              val var700: Entry = var695 as Entry;
                              val var707: java.lang.String = var700.getKey() as java.lang.String;
                              val var711: JsonElement = var700.getValue() as JsonElement;
                              val var888: java.lang.Double = java.lang.Double.parseDouble(var707);
                              val var714: java.lang.Iterable = GsonExtensionsKt.normalizeToArray(var711) as java.lang.Iterable;
                              val `destination$iv$ivxxxxxxx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var714, 10));

                              for (Object item$iv$ivx : var714) {
                                 `destination$iv$ivxxxxxxx`.add((`item$iv$ivx` as JsonElement).getAsString());
                              }

                              `destination$iv$ivxxxxxx`.add(TuplesKt.to(var888, CollectionsKt.toMutableList(`destination$iv$ivxxxxxxx` as java.util.List)));
                           }

                           val var643: java.util.Map = MapsKt.toMap(`destination$iv$ivxxxxxx` as java.util.List);
                           if (var643 != null) {
                              val var656: java.util.Map = MapsKt.toMutableMap(var643);
                              var889 = var656;
                              if (var656 != null) {
                                 break label921;
                              }
                           }
                        }
                     }
                  }
               }

               var889 = new LinkedHashMap();
            }

            label900: {
               if (var128 != null) {
                  val var603: JsonElement = var128.get("looping_travel_distance_events");
                  if (var603 != null) {
                     val var619: JsonArray = var603.getAsJsonArray();
                     if (var619 != null) {
                        val var644: java.lang.Iterable = var619 as java.lang.Iterable;
                        val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(
                           CollectionsKt.collectionSizeOrDefault(var619 as java.lang.Iterable, 10)
                        );

                        for (Object item$iv$iv : $this$map$iv) {
                           val var708: JsonObject = (var696 as JsonElement).getAsJsonObject();
                           val var712: Double = var708.get("distance").getAsDouble();
                           val var890: JsonElement = var708.get("events");
                           val `$this$map$ivx`: java.lang.Iterable = GsonExtensionsKt.normalizeToArray(var890) as java.lang.Iterable;
                           val `destination$iv$ivxxxxxxx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$ivx`, 10));

                           for (Object item$iv$ivx : $this$map$ivx) {
                              `destination$iv$ivxxxxxxx`.add((`item$iv$ivx` as JsonElement).getAsString());
                           }

                           `destination$iv$ivxxxxxx`.add(
                              new LoopingTravelDistanceEventTrigger(var712, CollectionsKt.toMutableList(`destination$iv$ivxxxxxxx` as java.util.List))
                           );
                        }

                        val var645: java.util.List = CollectionsKt.toMutableList(`destination$iv$ivxxxxxx` as java.util.List);
                        var891 = var645;
                        if (var645 != null) {
                           break label900;
                        }
                     }
                  }
               }

               var891 = new ArrayList();
            }

            label881: {
               if (var128 != null) {
                  val var620: JsonElement = var128.get("timeline");
                  if (var620 != null) {
                     val var633: JsonObject = var620.getAsJsonObject();
                     if (var633 != null) {
                        val var646: java.util.Set = var633.entrySet();
                        if (var646 != null) {
                           val var667: java.lang.Iterable = var646;
                           val `destination$iv$ivxxxxxx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var646, 10));

                           for (Object item$iv$iv : $this$map$iv) {
                              val var709: Entry = var705 as Entry;
                              val var715: java.lang.String = var709.getKey() as java.lang.String;
                              val var718: JsonElement = var709.getValue() as JsonElement;
                              val var892: java.lang.Double = java.lang.Double.parseDouble(var715);
                              val var721: java.lang.Iterable = GsonExtensionsKt.normalizeToArray(var718) as java.lang.Iterable;
                              val `destination$iv$ivxxxxxxx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var721, 10));

                              for (Object item$iv$ivx : var721) {
                                 `destination$iv$ivxxxxxxx`.add((`item$iv$ivx` as JsonElement).getAsString());
                              }

                              `destination$iv$ivxxxxxx`.add(TuplesKt.to(var892, CollectionsKt.toMutableList(`destination$iv$ivxxxxxxx` as java.util.List)));
                           }

                           val var668: java.util.Map = MapsKt.toMap(`destination$iv$ivxxxxxx` as java.util.List);
                           if (var668 != null) {
                              val var677: java.util.Map = MapsKt.toMutableMap(var668);
                              var893 = var677;
                              if (var677 != null) {
                                 break label881;
                              }
                           }
                        }
                     }
                  }
               }

               var893 = new LinkedHashMap();
            }

            return new BedrockParticleEffect(
               var140,
               new BedrockParticleEmitter(
                  CollectionsKt.toMutableList(var168),
                  CollectionsKt.toMutableList(var174),
                  var810,
                  var818,
                  var813,
                  new EventTriggerTimeline(var893),
                  var886,
                  var887,
                  new EventTriggerTimeline(var889),
                  var891
               ),
               new BedrockParticle(
                  var147,
                  var143,
                  var858,
                  var772,
                  var774,
                  var767,
                  var769,
                  CollectionsKt.toMutableList(var183),
                  CollectionsKt.toMutableList(var191),
                  var840,
                  var865,
                  var845,
                  var849,
                  var869,
                  var875,
                  var456,
                  var885.getCreationEvents(),
                  var885.getExpirationEvents(),
                  var885.getTimeline()
               ),
               CollectionsKt.toMutableList(var160),
               var878,
               var164
            );
         }

         val motion: Entry = speed.next() as Entry;
         val namex: java.lang.String = motion.getKey() as java.lang.String;
         val cameraMode: JsonElement = motion.getValue() as JsonElement;
         val uvMode: java.lang.String = StringsKt.substringAfter$default(namex, ".", null, 2, null);
         val motionJson: java.lang.String = (cameraMode as JsonObject).get("type").getAsString();
         if (motionJson == null) {
            break;
         }

         var var780: MoLangCurve;
         switch (motionJson.hashCode()) {
            case -1392296225:
               if (!motionJson.equals("bezier")) {
                  break label1355;
               }

               val var787: JsonArray = (cameraMode as JsonObject).getAsJsonArray("nodes");
               val var382: java.lang.Iterable = var787 as java.lang.Iterable;
               val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var787 as java.lang.Iterable, 10));

               for (Object item$iv$iv : $this$map$iv) {
                  `destination$iv$ivx`.add((var508 as JsonElement).getAsDouble());
               }

               val var567: java.util.List = `destination$iv$ivx` as java.util.List;
               val var919: java.lang.String = (cameraMode as JsonObject).get("input").getAsString();
               val var920: Expression = MoLangExtensionsKt.asExpression(var919);
               val var929: java.lang.String = (cameraMode as JsonObject).get("horizontal_range").getAsString();
               val var930: Expression = MoLangExtensionsKt.asExpression(var929);
               var780 = new BezierMoLangCurve(
                  uvMode,
                  var920,
                  var930,
                  (var567.get(0) as java.lang.Number).doubleValue(),
                  (var567.get(1) as java.lang.Number).doubleValue(),
                  (var567.get(2) as java.lang.Number).doubleValue(),
                  (var567.get(3) as java.lang.Number).doubleValue()
               );
               break;
            case -1102672091:
               if (!motionJson.equals("linear")) {
                  break label1355;
               }

               val var784: java.lang.String = (cameraMode as JsonObject).get("input").getAsString();
               val var566: Expression = MoLangExtensionsKt.asExpression(var784);
               val var785: java.lang.String = (cameraMode as JsonObject).get("horizontal_range").getAsString();
               val var381: Expression = MoLangExtensionsKt.asExpression(var785);
               val var786: JsonArray = (cameraMode as JsonObject).get("nodes").getAsJsonArray();
               val var415: java.lang.Iterable = var786 as java.lang.Iterable;
               val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var786 as java.lang.Iterable, 10));

               for (Object item$iv$iv : $this$map$iv) {
                  `destination$iv$ivx`.add((var552 as JsonElement).getAsDouble());
               }

               val var397: java.util.List = `destination$iv$ivx` as java.util.List;
               var780 = new LinearMoLangCurve(uvMode, var566, var381, var397);
               break;
            case 493524321:
               if (!motionJson.equals("bezier_chain")) {
                  break label1355;
               }

               val var781: java.lang.String = (cameraMode as JsonObject).get("input").getAsString();
               val var565: Expression = MoLangExtensionsKt.asExpression(var781);
               val var782: java.util.Set = (cameraMode as JsonObject).get("nodes").getAsJsonObject().entrySet();
               val var396: java.lang.Iterable = var782;
               val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var782, 10));

               for (Object item$iv$iv : $this$map$iv) {
                  val var551: Entry = var526 as Entry;
                  val `$this$map$iv`: java.lang.String = var551.getKey() as java.lang.String;
                  val `$i$f$map`: JsonElement = var551.getValue() as JsonElement;
                  val var783: java.lang.Double = java.lang.Double.parseDouble(`$this$map$iv`);
                  `destination$iv$ivx`.add(
                     TuplesKt.to(
                        var783,
                        new BezierChainMoLangCurve.BezierChainNode(
                           (`$i$f$map` as JsonObject).get("value").getAsDouble(), (`$i$f$map` as JsonObject).get("slope").getAsDouble()
                        )
                     )
                  );
               }

               val var380: java.util.Map = MapsKt.toMap(`destination$iv$ivx` as java.util.List);
               var780 = new BezierChainMoLangCurve(uvMode, var565, var380);
               break;
            case 2043326479:
               if (motionJson.equals("catmull_rom")) {
                  val var779: JsonArray = (cameraMode as JsonObject).getAsJsonArray("nodes");
                  val parametricParticleRotation: java.lang.Iterable = var779 as java.lang.Iterable;
                  val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var779 as java.lang.Iterable, 10));

                  for (Object item$iv$iv : $this$map$iv) {
                     `destination$iv$ivx`.add((particleEventSet as JsonElement).getAsDouble());
                  }

                  val emitterLoopingTravelDistanceEvents: java.util.List = `destination$iv$ivx` as java.util.List;
                  val var10003: java.lang.String = (cameraMode as JsonObject).get("input").getAsString();
                  val var918: Expression = MoLangExtensionsKt.asExpression(var10003);
                  val var10004: java.lang.String = (cameraMode as JsonObject).get("horizontal_range").getAsString();
                  val var928: Expression = MoLangExtensionsKt.asExpression(var10004);
                  var780 = new CatmullRomMoLangCurve(uvMode, var918, var928, emitterLoopingTravelDistanceEvents);
                  break;
               }
            default:
               break label1355;
         }

         emitterUpdateExpressions.add(var780);
      }

      throw new NotImplementedError("An operation is not implemented: Unrecognized curve type was used");
   }

   private fun parseHex(hex: String): Vector4f {
      val cleaned: java.lang.String = StringsKt.replace$default(hex, "#", "", false, 4, null);
      val var10000: java.lang.String = cleaned.substring(0, 2);
      val var7: java.lang.String = cleaned.substring(2, 4);
      val var8: java.lang.String = cleaned.substring(4, 6);
      val var9: java.lang.String = cleaned.substring(6, 8);
      return new Vector4f(
         Integer.parseInt(var7, CharsKt.checkRadix(16)) / 255.0F,
         Integer.parseInt(var8, CharsKt.checkRadix(16)) / 255.0F,
         Integer.parseInt(var9, CharsKt.checkRadix(16)) / 255.0F,
         Integer.parseInt(var10000, CharsKt.checkRadix(16)) / 255.0F
      );
   }

   @JvmStatic
   fun `loadEffect$resolveDirection`(direction: ObjectRef<ParticleMotionDirection>, json: JsonObject) {
      val name: JsonElement = json.get("direction");
      if (name == null) {
         val var17: SnowstormParticleReader = INSTANCE;
         direction.element = new OutwardsMotionDirection();
      } else {
         var var10000: ObjectRef = direction;
         val var20: ParticleMotionDirection;
         if (name.isJsonArray()) {
            val var10001: JsonArray = name.getAsJsonArray();
            val `$this$map$iv`: java.lang.Iterable = var10001 as java.lang.Iterable;
            val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var10001 as java.lang.Iterable, 10));

            for (Object item$iv$iv : $this$map$iv) {
               val var18: java.lang.String = (`item$iv$iv` as JsonElement).getAsString();
               `destination$iv$iv`.add(MoLangExtensionsKt.asExpression(var18));
            }

            val var19: java.util.List = `destination$iv$iv` as java.util.List;
            var10000 = direction;
            var20 = new CustomMotionDirection(new Triple(var19.get(0), var19.get(1), var19.get(2)));
         } else {
            var20 = if (name.getAsString() == "outwards") new OutwardsMotionDirection() else new InwardsMotionDirection();
         }

         var10000.element = var20;
      }
   }
}
