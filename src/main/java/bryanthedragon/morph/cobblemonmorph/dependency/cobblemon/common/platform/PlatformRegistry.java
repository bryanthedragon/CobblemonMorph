/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItemConvertible;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\b&\u0018\u0000*\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u00028\u00020\u0001*\u000e\b\u0001\u0010\u0004*\b\u0012\u0004\u0012\u00028\u00000\u0003*\u0004\b\u0002\u0010\u00052\u00020\u0006B\u0007\u00a2\u0006\u0004\b\"\u0010#J\u0015\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ)\u0010\u000e\u001a\u00028\u0003\"\b\b\u0003\u0010\n*\u00028\u00022\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00028\u0003H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ)\u0010\u0014\u001a\u00020\u00122\u0018\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00020\u00120\u0010H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015R6\u0010\u0018\u001a\u001e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00028\u00020\u0016j\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00028\u0002`\u00178\u0004X\u0084\u0004\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001e\u001a\u00028\u00008&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010!\u001a\u00028\u00018&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010 \u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/platform/PlatformRegistry;", "Lnet/minecraft/core/Registry;", "R", "Lnet/minecraft/resources/ResourceKey;", "K", "T", "", "", "all", "()Ljava/util/Collection;", "E", "", "name", "entry", "create", "(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlin/Function2;", "Lnet/minecraft/resources/ResourceLocation;", "", "consumer", "register", "(Lkotlin/jvm/functions/Function2;)V", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "queue", "Ljava/util/HashMap;", "getQueue", "()Ljava/util/HashMap;", "getRegistry", "()Lnet/minecraft/core/Registry;", "registry", "getRegistryKey", "()Lnet/minecraft/resources/ResourceKey;", "registryKey", "<init>", "()V", "common"})
public abstract class PlatformRegistry<R extends Registry<T>, K extends ResourceKey<R>, T> {
    @NotNull
    private final HashMap<ResourceLocation, T> queue = new HashMap();

    @NotNull
    public abstract R getRegistry();

    @NotNull
    public abstract K getRegistryKey();

    @NotNull
    protected final HashMap<ResourceLocation, T> getQueue() {
        return this.queue;
    }

    public <E extends T> E create(@NotNull String name, E entry) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        ResourceLocation identifier = MiscUtils.cobblemonResource(name);
        ((Map)this.queue).put(identifier, entry);
        if (entry instanceof BagItemConvertible) {
            BagItems.INSTANCE.getBagItems().add(Priority.NORMAL, (BagItemConvertible)entry);
        }
        return entry;
    }

    public void register(@NotNull Function2<? super ResourceLocation, ? super T, Unit> consumer) {
        Intrinsics.checkNotNullParameter(consumer, (String)"consumer");
        this.queue.forEach((arg_0, arg_1) -> PlatformRegistry.register$lambda$0(consumer, arg_0, arg_1));
    }

    @NotNull
    public Collection<T> all() {
        Collection<T> collection = this.queue.values();
        Intrinsics.checkNotNullExpressionValue(collection, (String)"this.queue.values");
        return CollectionsKt.toList((Iterable)collection);
    }

    private static final void register$lambda$0(Function2 $tmp0, Object p0, Object p1) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke(p0, p1);
    }
}

