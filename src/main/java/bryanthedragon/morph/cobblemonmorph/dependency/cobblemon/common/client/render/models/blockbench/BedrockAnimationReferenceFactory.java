/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.AnimationReferenceFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J;\u0010\n\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\t0\b\"\b\b\u0000\u0010\u0003*\u00020\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ;\u0010\r\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\t0\f\"\b\b\u0000\u0010\u0003*\u00020\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/BedrockAnimationReferenceFactory;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/AnimationReferenceFactory;", "Lnet/minecraft/world/entity/Entity;", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;", "model", "", "animString", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/frame/ModelFrame;", "stateful", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Ljava/lang/String;)Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "stateless", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityModel;Ljava/lang/String;)Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatelessAnimation;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBedrockAnimationReferenceFactory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BedrockAnimationReferenceFactory.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/BedrockAnimationReferenceFactory\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,41:1\n1549#2:42\n1620#2,3:43\n1549#2:46\n1620#2,3:47\n*S KotlinDebug\n*F\n+ 1 BedrockAnimationReferenceFactory.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/BedrockAnimationReferenceFactory\n*L\n30#1:42\n30#1:43,3\n35#1:46\n35#1:47,3\n*E\n"})
public final class BedrockAnimationReferenceFactory
implements AnimationReferenceFactory {
    @NotNull
    public static final BedrockAnimationReferenceFactory INSTANCE = new BedrockAnimationReferenceFactory();

    private BedrockAnimationReferenceFactory() {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public <T extends Entity> StatelessAnimation<T, ModelFrame> stateless(@NotNull PoseableEntityModel<T> model, @NotNull String animString) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(model, (String)"model");
        Intrinsics.checkNotNullParameter((Object)animString, (String)"animString");
        String[] stringArray = new String[]{","};
        Iterable $this$map$iv = StringsKt.split$default((CharSequence)StringsKt.replace$default((String)StringsKt.replace$default((String)animString, (String)"bedrock(", (String)"", (boolean)false, (int)4, null), (String)")", (String)"", (boolean)false, (int)4, null), (String[])stringArray, (boolean)false, (int)0, (int)6, null);
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void p0;
            String string = (String)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(((Object)StringsKt.trim((CharSequence)((CharSequence)p0))).toString());
        }
        List split = (List)destination$iv$iv;
        return PoseableEntityModel.bedrock$default(model, (String)split.get(0), (String)split.get(1), null, 4, null);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public <T extends Entity> StatefulAnimation<T, ModelFrame> stateful(@NotNull PoseableEntityModel<T> model, @NotNull String animString) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter(model, (String)"model");
        Intrinsics.checkNotNullParameter((Object)animString, (String)"animString");
        String[] stringArray = new String[]{","};
        Iterable $this$map$iv = StringsKt.split$default((CharSequence)StringsKt.replace$default((String)StringsKt.replace$default((String)animString, (String)"bedrock(", (String)"", (boolean)false, (int)4, null), (String)")", (String)"", (boolean)false, (int)4, null), (String[])stringArray, (boolean)false, (int)0, (int)6, null);
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void p0;
            String string = (String)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(((Object)StringsKt.trim((CharSequence)((CharSequence)p0))).toString());
        }
        List split = (List)destination$iv$iv;
        return PoseableEntityModel.bedrockStateful$default(model, (String)split.get(0), (String)split.get(1), null, 4, null);
    }
}

