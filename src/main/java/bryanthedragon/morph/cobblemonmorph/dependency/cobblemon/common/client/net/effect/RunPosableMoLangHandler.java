package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.net.effect

import com.bedrockk.molang.Expression
import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ClientNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.Poseable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.RunPosableMoLangPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nRunPosableMoLangHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RunPosableMoLangHandler.kt\ncom/cobblemon/mod/common/client/net/effect/RunPosableMoLangHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,30:1\n1549#2:31\n1620#2,3:32\n*S KotlinDebug\n*F\n+ 1 RunPosableMoLangHandler.kt\ncom/cobblemon/mod/common/client/net/effect/RunPosableMoLangHandler\n*L\n25#1:31\n25#1:32,3\n*E\n"])
public object RunPosableMoLangHandler : ClientNetworkPacketHandler<RunPosableMoLangPacket> {
   public open fun handle(packet: RunPosableMoLangPacket, client: Minecraft) {
      if (client.f_91073_ != null) {
         val var10000: Entity = client.f_91073_.m_6815_(packet.getEntityId());
         if (var10000 != null) {
            if (var10000 is Poseable) {
               val expression: EntitySideDelegate = (var10000 as Poseable).getDelegate();
               val var19: PoseableEntityState = expression as? PoseableEntityState;
               if ((expression as? PoseableEntityState) == null) {
                  return;
               }

               val state: PoseableEntityState = var19;
               val var17: java.lang.Iterable = packet.getExpressions();
               val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var17, 10));

               for (Object item$iv$iv : $this$map$iv) {
                  `destination$iv$iv`.add(MoLangExtensionsKt.asExpression(`item$iv$iv` as java.lang.String));
               }

               for (Expression expressionx : (java.util.List)destination$iv$iv) {
                  val var20: MoLangRuntime = state.getRuntime();
                  MoLangExtensionsKt.resolve(var20, expressionx);
               }
            }
         }
      }
   }

   fun handleOnNettyThread(packet: RunPosableMoLangPacket) {
      ClientNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, packet);
   }
}
