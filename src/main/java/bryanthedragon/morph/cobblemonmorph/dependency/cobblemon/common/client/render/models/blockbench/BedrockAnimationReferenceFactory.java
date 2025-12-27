package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.AnimationReferenceFactory
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nBedrockAnimationReferenceFactory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockAnimationReferenceFactory.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/BedrockAnimationReferenceFactory\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,41:1\n1549#2:42\n1620#2,3:43\n1549#2:46\n1620#2,3:47\n*S KotlinDebug\n*F\n+ 1 BedrockAnimationReferenceFactory.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/BedrockAnimationReferenceFactory\n*L\n30#1:42\n30#1:43,3\n35#1:46\n35#1:47,3\n*E\n"])
public object BedrockAnimationReferenceFactory : AnimationReferenceFactory {
   public override fun <T : Entity> stateless(model: PoseableEntityModel<Any>, animString: String): StatelessAnimation<Any, ModelFrame> {
      val var14: java.lang.Iterable = StringsKt.split$default(
         StringsKt.replace$default(StringsKt.replace$default(animString, "bedrock(", "", false, 4, null), ")", "", false, 4, null),
         new java.lang.String[]{","},
         false,
         0,
         6,
         null
      );
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var14, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add(StringsKt.trim(`item$iv$iv` as java.lang.String).toString());
      }

      return PoseableEntityModel.bedrock$default(
         model,
         (`destination$iv$iv` as java.util.List).get(0) as java.lang.String,
         (`destination$iv$iv` as java.util.List).get(1) as java.lang.String,
         null,
         4,
         null
      );
   }

   public override fun <T : Entity> stateful(model: PoseableEntityModel<Any>, animString: String): StatefulAnimation<Any, ModelFrame> {
      val var14: java.lang.Iterable = StringsKt.split$default(
         StringsKt.replace$default(StringsKt.replace$default(animString, "bedrock(", "", false, 4, null), ")", "", false, 4, null),
         new java.lang.String[]{","},
         false,
         0,
         6,
         null
      );
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var14, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add(StringsKt.trim(`item$iv$iv` as java.lang.String).toString());
      }

      return PoseableEntityModel.bedrockStateful$default(
         model,
         (`destination$iv$iv` as java.util.List).get(0) as java.lang.String,
         (`destination$iv$iv` as java.util.List).get(1) as java.lang.String,
         null,
         4,
         null
      );
   }
}
