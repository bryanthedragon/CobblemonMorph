/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.item.Item
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item.CobblemonBuiltinItemRenderer;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001d\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\t\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\t\u0010\nR0\u0010\r\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00040\u000bj\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0004`\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/client/render/item/CobblemonBuiltinItemRendererRegistry;", "", "Lnet/minecraft/world/item/Item;", "item", "Lcom/cobblemon/mod/common/client/render/item/CobblemonBuiltinItemRenderer;", "renderer", "", "register", "(Lnet/minecraft/world/item/Item;Lcom/cobblemon/mod/common/client/render/item/CobblemonBuiltinItemRenderer;)V", "rendererOf", "(Lnet/minecraft/world/item/Item;)Lcom/cobblemon/mod/common/client/render/item/CobblemonBuiltinItemRenderer;", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "renderers", "Ljava/util/HashMap;", "<init>", "()V", "common"})
public final class CobblemonBuiltinItemRendererRegistry {
    @NotNull
    public static final CobblemonBuiltinItemRendererRegistry INSTANCE = new CobblemonBuiltinItemRendererRegistry();
    @NotNull
    private static final HashMap<Item, CobblemonBuiltinItemRenderer> renderers = new HashMap();

    private CobblemonBuiltinItemRendererRegistry() {
    }

    public final void register(@NotNull Item item, @NotNull CobblemonBuiltinItemRenderer renderer) {
        Intrinsics.checkNotNullParameter((Object)item, (String)"item");
        Intrinsics.checkNotNullParameter((Object)renderer, (String)"renderer");
        ((Map)renderers).put(item, renderer);
    }

    @Nullable
    public final CobblemonBuiltinItemRenderer rendererOf(@NotNull Item item) {
        Intrinsics.checkNotNullParameter((Object)item, (String)"item");
        return renderers.get(item);
    }
}

