package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import com.mojang.blaze3d.vertex.PoseStack
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.world.entity.Entity
import org.joml.Matrix4f

@SourceDebugExtension(["SMAP\nLocatorAccess.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LocatorAccess.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/LocatorAccess\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,115:1\n361#2,7:116\n361#2,7:123\n361#2,7:130\n361#2,7:137\n361#2,7:144\n1855#3,2:151\n*S KotlinDebug\n*F\n+ 1 LocatorAccess.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/LocatorAccess\n*L\n79#1:116,7\n87#1:123,7\n88#1:130,7\n96#1:137,7\n105#1:144,7\n109#1:151,2\n*E\n"])
public class LocatorAccess(joint: Bone, locators: Map<String, Bone> = MapsKt.emptyMap(), children: List<LocatorAccess> = CollectionsKt.emptyList()) {
   public final val children: List<LocatorAccess>
   public final val joint: Bone
   public final val locators: Map<String, Bone>

   init {
      this.joint = joint;
      this.locators = locators;
      this.children = children;
   }

   public fun update(matrixStack: PoseStack, entity: Entity, scale: Float, state: MutableMap<String, MatrixWrapper>, isRoot: Boolean = false) {
      matrixStack.m_85836_();
      this.joint.transform(matrixStack);
      if (isRoot) {
         matrixStack.m_85836_();
         matrixStack.m_85841_(-1.0F, -1.0F, 1.0F);
         var `element$iv`: Any = state.get("root");
         var var10000: Any;
         if (`element$iv` == null) {
            val var30: Any = new MatrixWrapper();
            state.put("root", var30);
            var10000 = (MatrixWrapper)var30;
         } else {
            var10000 = (MatrixWrapper)`element$iv`;
         }

         var10000 = var10000;
         var var10001: Matrix4f = matrixStack.m_85850_().m_252922_();
         var10000.updateMatrix(var10001);
         matrixStack.m_85849_();
         matrixStack.m_85836_();
         matrixStack.m_85837_(0.0, -entity.m_20191_().m_82376_() / 2.0 / (double)scale, (double)(-entity.m_20205_()) * 0.6 / (double)scale);
         matrixStack.m_85841_(-1.0F, -1.0F, 1.0F);
         `element$iv` = state.get("target");
         if (`element$iv` == null) {
            val var32: Any = new MatrixWrapper();
            state.put("target", var32);
            var10000 = (MatrixWrapper)var32;
         } else {
            var10000 = (MatrixWrapper)`element$iv`;
         }

         var10000 = var10000;
         var10001 = matrixStack.m_85850_().m_252922_();
         var10000.updateMatrix(var10001);
         `element$iv` = state.get("special_attack");
         if (`element$iv` == null) {
            val var34: Any = new MatrixWrapper();
            state.put("special_attack", var34);
            var10000 = (MatrixWrapper)var34;
         } else {
            var10000 = (MatrixWrapper)`element$iv`;
         }

         var10000 = var10000;
         var10001 = matrixStack.m_85850_().m_252922_();
         var10000.updateMatrix(var10001);
         matrixStack.m_85849_();
         matrixStack.m_85836_();
         matrixStack.m_85837_(0.0, -entity.m_20191_().m_82376_() / 2.0 / (double)scale, 0.0);
         matrixStack.m_85841_(-1.0F, -1.0F, 1.0F);
         `element$iv` = state.get("middle");
         if (`element$iv` == null) {
            val var36: Any = new MatrixWrapper();
            state.put("middle", var36);
            var10000 = (MatrixWrapper)var36;
         } else {
            var10000 = (MatrixWrapper)`element$iv`;
         }

         var10000 = var10000;
         var10001 = matrixStack.m_85850_().m_252922_();
         var10000.updateMatrix(var10001);
         matrixStack.m_85849_();
      }

      for (Entry var18 : this.locators.entrySet()) {
         val var23: java.lang.String = var18.getKey() as java.lang.String;
         val var28: Bone = var18.getValue() as Bone;
         matrixStack.m_85836_();
         var28.transform(matrixStack);
         matrixStack.m_85841_(-1.0F, -1.0F, 1.0F);
         val `value$ivx`: Any = state.get(var23);
         var var47: Any;
         if (`value$ivx` == null) {
            val var39: Any = new MatrixWrapper();
            state.put(var23, var39);
            var47 = (MatrixWrapper)var39;
         } else {
            var47 = (MatrixWrapper)`value$ivx`;
         }

         var47 = var47;
         val var52: Matrix4f = matrixStack.m_85850_().m_252922_();
         var47.updateMatrix(var52);
         matrixStack.m_85849_();
      }

      val var14: java.lang.Iterable;
      for (Object element$ivx : var14) {
         (`element$ivx` as LocatorAccess).update(matrixStack, entity, scale, state, false);
      }

      matrixStack.m_85849_();
   }

   @SourceDebugExtension(["SMAP\nLocatorAccess.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LocatorAccess.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/LocatorAccess$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,115:1\n3190#2,10:116\n1179#2,2:126\n1253#2,4:128\n1603#2,9:132\n1855#2:141\n1856#2:143\n1612#2:144\n1#3:142\n*S KotlinDebug\n*F\n+ 1 LocatorAccess.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/LocatorAccess$Companion\n*L\n36#1:116,10\n38#1:126,2\n38#1:128,4\n54#1:132,9\n54#1:141\n54#1:143\n54#1:144\n54#1:142\n*E\n"])
   public companion object {
      public const val PREFIX: String

      public fun resolve(part: Bone): LocatorAccess? {
         val locatorChildren: java.lang.Iterable = part.getChildren().entrySet();
         val locators: ArrayList = new ArrayList();
         val children: ArrayList = new ArrayList();

         for (Object element$iv : $this$partition$iv) {
            val var10000: Any = (`$i$f$mapNotNull` as Entry).getKey();
            if (StringsKt.startsWith$default(var10000 as java.lang.String, "locator_", false, 2, null)) {
               locators.add(`$i$f$mapNotNull`);
            } else {
               children.add(`$i$f$mapNotNull`);
            }
         }

         val var2: Pair = new Pair(locators, children);
         val var24: java.util.List = var2.component1() as java.util.List;
         val var25: java.util.List = var2.component2() as java.util.List;
         val var27: java.lang.Iterable = var24;
         val var33: java.util.Map = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(var24, 10)), 16));

         for (Object element$iv$iv : $this$associate$iv) {
            val var17: java.lang.String = (`$i$f$forEach` as Entry).getKey() as java.lang.String;
            val partx: Bone = (`$i$f$forEach` as Entry).getValue() as Bone;
            val var37: Pair = TuplesKt.to(StringsKt.substringAfter$default(var17, "locator_", null, 2, null), partx);
            var33.put(var37.getFirst(), var37.getSecond());
         }

         val var28: java.util.List = new ArrayList();
         if (var25.isEmpty()) {
            return if (var33.isEmpty()) null else new LocatorAccess(part, var33, null, 4, null);
         } else {
            val var30: java.lang.Iterable = var25;
            val `destination$iv$ivx`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv$iv : var30) {
               val partx: Bone = (var38 as Entry).getValue() as Bone;
               val var41: LocatorAccess.Companion = LocatorAccess.Companion;
               val var42: LocatorAccess = var41.resolve(partx);
               if (var42 != null) {
                  `destination$iv$ivx`.add(var42);
               }
            }

            var28.addAll(`destination$iv$ivx` as java.util.List);
            return if (var28.isEmpty() && var33.isEmpty()) null else new LocatorAccess(part, var33, var28);
         }
      }
   }
}
