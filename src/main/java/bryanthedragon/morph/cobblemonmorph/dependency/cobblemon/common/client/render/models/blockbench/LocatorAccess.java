/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  kotlin.text.StringsKt
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Matrix4f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\b\u0018\u0000 \"2\u00020\u0001:\u0001\"B5\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\u0014\b\u0002\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00160\u001b\u0012\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00000\u0011\u00a2\u0006\u0004\b \u0010!JC\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b2\b\b\u0002\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00000\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR#\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00160\u001b8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/LocatorAccess;", "", "Lcom/mojang/blaze3d/vertex/PoseStack;", "matrixStack", "Lnet/minecraft/world/entity/Entity;", "entity", "", "scale", "", "", "Lcom/cobblemon/mod/common/client/render/MatrixWrapper;", "state", "", "isRoot", "", "update", "(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/Entity;FLjava/util/Map;Z)V", "", "children", "Ljava/util/List;", "getChildren", "()Ljava/util/List;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "joint", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "getJoint", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "", "locators", "Ljava/util/Map;", "getLocators", "()Ljava/util/Map;", "<init>", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;Ljava/util/Map;Ljava/util/List;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nLocatorAccess.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LocatorAccess.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/LocatorAccess\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,115:1\n361#2,7:116\n361#2,7:123\n361#2,7:130\n361#2,7:137\n361#2,7:144\n1855#3,2:151\n*S KotlinDebug\n*F\n+ 1 LocatorAccess.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/LocatorAccess\n*L\n79#1:116,7\n87#1:123,7\n88#1:130,7\n96#1:137,7\n105#1:144,7\n109#1:151,2\n*E\n"})
public final class LocatorAccess {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Bone joint;
    @NotNull
    private final Map<String, Bone> locators;
    @NotNull
    private final List<LocatorAccess> children;
    @NotNull
    public static final String PREFIX = "locator_";

    public LocatorAccess(@NotNull Bone joint, @NotNull Map<String, ? extends Bone> locators, @NotNull List<LocatorAccess> children) {
        Intrinsics.checkNotNullParameter((Object)joint, (String)"joint");
        Intrinsics.checkNotNullParameter(locators, (String)"locators");
        Intrinsics.checkNotNullParameter(children, (String)"children");
        this.joint = joint;
        this.locators = locators;
        this.children = children;
    }

    public /* synthetic */ LocatorAccess(Bone bone, Map map, List list, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            map = MapsKt.emptyMap();
        }
        if ((n & 4) != 0) {
            list = CollectionsKt.emptyList();
        }
        this(bone, map, list);
    }

    @NotNull
    public final Bone getJoint() {
        return this.joint;
    }

    @NotNull
    public final Map<String, Bone> getLocators() {
        return this.locators;
    }

    @NotNull
    public final List<LocatorAccess> getChildren() {
        return this.children;
    }

    public final void update(@NotNull PoseStack matrixStack, @NotNull Entity entity2, float scale, @NotNull Map<String, MatrixWrapper> state, boolean isRoot) {
        Intrinsics.checkNotNullParameter((Object)matrixStack, (String)"matrixStack");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Intrinsics.checkNotNullParameter(state, (String)"state");
        matrixStack.m_85836_();
        this.joint.transform(matrixStack);
        if (isRoot) {
            Object object;
            Object object2;
            Object object3;
            Object object4;
            MatrixWrapper answer$iv;
            Map<String, MatrixWrapper> $this$getOrPut$iv;
            matrixStack.m_85836_();
            matrixStack.m_85841_(-1.0f, -1.0f, 1.0f);
            Map<String, MatrixWrapper> map = state;
            String string = "root";
            boolean $i$f$getOrPut = false;
            Object value$iv = $this$getOrPut$iv.get(string);
            if (value$iv == null) {
                boolean bl = false;
                answer$iv = new MatrixWrapper();
                $this$getOrPut$iv.put(string, answer$iv);
                object4 = answer$iv;
            } else {
                object4 = value$iv;
            }
            MatrixWrapper matrixWrapper = (MatrixWrapper)object4;
            Matrix4f matrix4f = matrixStack.m_85850_().m_252922_();
            Intrinsics.checkNotNullExpressionValue((Object)matrix4f, (String)"matrixStack.peek().positionMatrix");
            matrixWrapper.updateMatrix(matrix4f);
            matrixStack.m_85849_();
            matrixStack.m_85836_();
            matrixStack.m_85837_(0.0, -entity2.m_20191_().m_82376_() / 2.0 / (double)scale, (double)(-entity2.m_20205_()) * 0.6 / (double)scale);
            matrixStack.m_85841_(-1.0f, -1.0f, 1.0f);
            $this$getOrPut$iv = state;
            String string2 = "target";
            $i$f$getOrPut = false;
            value$iv = $this$getOrPut$iv.get(string2);
            if (value$iv == null) {
                boolean bl = false;
                answer$iv = new MatrixWrapper();
                $this$getOrPut$iv.put(string2, answer$iv);
                object3 = answer$iv;
            } else {
                object3 = value$iv;
            }
            MatrixWrapper matrixWrapper2 = (MatrixWrapper)object3;
            Matrix4f matrix4f2 = matrixStack.m_85850_().m_252922_();
            Intrinsics.checkNotNullExpressionValue((Object)matrix4f2, (String)"matrixStack.peek().positionMatrix");
            matrixWrapper2.updateMatrix(matrix4f2);
            $this$getOrPut$iv = state;
            String string3 = "special_attack";
            $i$f$getOrPut = false;
            value$iv = $this$getOrPut$iv.get(string3);
            if (value$iv == null) {
                boolean bl = false;
                answer$iv = new MatrixWrapper();
                $this$getOrPut$iv.put(string3, answer$iv);
                object2 = answer$iv;
            } else {
                object2 = value$iv;
            }
            MatrixWrapper matrixWrapper3 = (MatrixWrapper)object2;
            Matrix4f matrix4f3 = matrixStack.m_85850_().m_252922_();
            Intrinsics.checkNotNullExpressionValue((Object)matrix4f3, (String)"matrixStack.peek().positionMatrix");
            matrixWrapper3.updateMatrix(matrix4f3);
            matrixStack.m_85849_();
            matrixStack.m_85836_();
            matrixStack.m_85837_(0.0, -entity2.m_20191_().m_82376_() / 2.0 / (double)scale, 0.0);
            matrixStack.m_85841_(-1.0f, -1.0f, 1.0f);
            $this$getOrPut$iv = state;
            String string4 = "middle";
            $i$f$getOrPut = false;
            value$iv = $this$getOrPut$iv.get(string4);
            if (value$iv == null) {
                boolean bl = false;
                answer$iv = new MatrixWrapper();
                $this$getOrPut$iv.put(string4, answer$iv);
                object = answer$iv;
            } else {
                object = value$iv;
            }
            MatrixWrapper matrixWrapper4 = (MatrixWrapper)object;
            Matrix4f matrix4f4 = matrixStack.m_85850_().m_252922_();
            Intrinsics.checkNotNullExpressionValue((Object)matrix4f4, (String)"matrixStack.peek().positionMatrix");
            matrixWrapper4.updateMatrix(matrix4f4);
            matrixStack.m_85849_();
        }
        for (Map.Entry<String, Bone> entry : this.locators.entrySet()) {
            MatrixWrapper matrixWrapper;
            String name = entry.getKey();
            Bone locator = entry.getValue();
            matrixStack.m_85836_();
            locator.transform(matrixStack);
            matrixStack.m_85841_(-1.0f, -1.0f, 1.0f);
            Map<String, MatrixWrapper> $this$getOrPut$iv = state;
            boolean $i$f$getOrPut = false;
            MatrixWrapper value$iv = $this$getOrPut$iv.get(name);
            if (value$iv == null) {
                boolean bl = false;
                MatrixWrapper answer$iv = new MatrixWrapper();
                $this$getOrPut$iv.put(name, answer$iv);
                matrixWrapper = answer$iv;
            } else {
                matrixWrapper = value$iv;
            }
            Matrix4f matrix4f = matrixStack.m_85850_().m_252922_();
            Intrinsics.checkNotNullExpressionValue((Object)matrix4f, (String)"matrixStack.peek().positionMatrix");
            matrixWrapper.updateMatrix(matrix4f);
            matrixStack.m_85849_();
        }
        Iterable $this$forEach$iv = this.children;
        boolean bl = false;
        for (Object element$iv : $this$forEach$iv) {
            LocatorAccess it = (LocatorAccess)element$iv;
            boolean bl2 = false;
            it.update(matrixStack, entity2, scale, state, false);
        }
        matrixStack.m_85849_();
    }

    public static /* synthetic */ void update$default(LocatorAccess locatorAccess, PoseStack poseStack, Entity entity2, float f, Map map, boolean bl, int n, Object object) {
        if ((n & 0x10) != 0) {
            bl = false;
        }
        locatorAccess.update(poseStack, entity2, f, map, bl);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\b\u001a\u00020\u00078\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/LocatorAccess$Companion;", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "part", "Lcom/cobblemon/mod/common/client/render/models/blockbench/LocatorAccess;", "resolve", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;)Lcom/cobblemon/mod/common/client/render/models/blockbench/LocatorAccess;", "", "PREFIX", "Ljava/lang/String;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nLocatorAccess.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LocatorAccess.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/LocatorAccess$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,115:1\n3190#2,10:116\n1179#2,2:126\n1253#2,4:128\n1603#2,9:132\n1855#2:141\n1856#2:143\n1612#2:144\n1#3:142\n*S KotlinDebug\n*F\n+ 1 LocatorAccess.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/LocatorAccess$Companion\n*L\n36#1:116,10\n38#1:126,2\n38#1:128,4\n54#1:132,9\n54#1:141\n54#1:143\n54#1:144\n54#1:142\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        /*
         * WARNING - void declaration
         */
        @Nullable
        public final LocatorAccess resolve(@NotNull Bone part) {
            void $this$mapNotNullTo$iv$iv;
            void $this$mapNotNull$iv;
            Object object;
            void $this$associateTo$iv$iv;
            Object it;
            Intrinsics.checkNotNullParameter((Object)part, (String)"part");
            Iterable $this$partition$iv = part.getChildren().entrySet();
            boolean $i$f$partition = false;
            ArrayList first$iv = new ArrayList();
            ArrayList second$iv = new ArrayList();
            for (Object element$iv : $this$partition$iv) {
                it = (Map.Entry)element$iv;
                boolean bl = false;
                Object k = it.getKey();
                Intrinsics.checkNotNullExpressionValue(k, (String)"it.key");
                if (StringsKt.startsWith$default((String)((String)k), (String)LocatorAccess.PREFIX, (boolean)false, (int)2, null)) {
                    first$iv.add(element$iv);
                    continue;
                }
                second$iv.add(element$iv);
            }
            Pair pair = new Pair(first$iv, second$iv);
            List locatorChildren = (List)pair.component1();
            List nonLocatorChildren = (List)pair.component2();
            Iterable $this$associate$iv = locatorChildren;
            boolean $i$f$associate22 = false;
            int capacity$iv = RangesKt.coerceAtLeast((int)MapsKt.mapCapacity((int)CollectionsKt.collectionSizeOrDefault((Iterable)$this$associate$iv, (int)10)), (int)16);
            it = $this$associate$iv;
            Object destination$iv$iv = new LinkedHashMap(capacity$iv);
            boolean $i$f$associateTo = false;
            for (Object element$iv$iv : $this$associateTo$iv$iv) {
                object = destination$iv$iv;
                Map.Entry entry = (Map.Entry)element$iv$iv;
                boolean bl = false;
                String namePrefixed = (String)entry.getKey();
                Bone part2 = (Bone)entry.getValue();
                Intrinsics.checkNotNullExpressionValue((Object)namePrefixed, (String)"namePrefixed");
                entry = TuplesKt.to((Object)StringsKt.substringAfter$default((String)namePrefixed, (String)LocatorAccess.PREFIX, null, (int)2, null), (Object)part2);
                object.put(entry.getFirst(), entry.getSecond());
            }
            Map locators = destination$iv$iv;
            List children = new ArrayList();
            if (nonLocatorChildren.isEmpty()) {
                return locators.isEmpty() ? null : new LocatorAccess(part, locators, null, 4, null);
            }
            Iterable $i$f$associate22 = nonLocatorChildren;
            List list = children;
            boolean $i$f$mapNotNull = false;
            $this$associateTo$iv$iv = $this$mapNotNull$iv;
            destination$iv$iv = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            object = $this$forEach$iv$iv$iv.iterator();
            while (object.hasNext()) {
                LocatorAccess it$iv$iv;
                Object element$iv$iv$iv;
                Object element$iv$iv = element$iv$iv$iv = object.next();
                boolean bl = false;
                Map.Entry entry = (Map.Entry)element$iv$iv;
                boolean bl2 = false;
                Bone part3 = (Bone)entry.getValue();
                Intrinsics.checkNotNullExpressionValue((Object)part3, (String)"part");
                if (Companion.resolve(part3) == null) continue;
                boolean bl3 = false;
                destination$iv$iv.add(it$iv$iv);
            }
            list.addAll((List)destination$iv$iv);
            return children.isEmpty() && locators.isEmpty() ? null : new LocatorAccess(part, locators, children);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

