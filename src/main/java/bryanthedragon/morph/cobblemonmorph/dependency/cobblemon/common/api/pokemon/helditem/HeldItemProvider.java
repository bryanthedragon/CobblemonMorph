/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.helditem;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.PrioritizedList;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.helditem.HeldItemManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0013\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0015\u0010\b\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001f\u0010\u0011\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u0011\u0010\u0012J!\u0010\u0013\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\u0004\b\u0013\u0010\u0012R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00148\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0004\u0010\u0015\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/helditem/HeldItemProvider;", "", "", "Lcom/cobblemon/mod/common/api/pokemon/helditem/HeldItemManager;", "managers", "()Ljava/util/List;", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "pokemon", "provide", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Lcom/cobblemon/mod/common/api/pokemon/helditem/HeldItemManager;", "", "provideShowdownId", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Ljava/lang/String;", "manager", "Lcom/cobblemon/mod/common/api/Priority;", "priority", "", "register", "(Lcom/cobblemon/mod/common/api/pokemon/helditem/HeldItemManager;Lcom/cobblemon/mod/common/api/Priority;)V", "unregister", "Lcom/cobblemon/mod/common/api/PrioritizedList;", "Lcom/cobblemon/mod/common/api/PrioritizedList;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nHeldItemProvider.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HeldItemProvider.kt\ncom/cobblemon/mod/common/api/pokemon/helditem/HeldItemProvider\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,73:1\n288#2,2:74\n1#3:76\n*S KotlinDebug\n*F\n+ 1 HeldItemProvider.kt\ncom/cobblemon/mod/common/api/pokemon/helditem/HeldItemProvider\n*L\n32#1:74,2\n*E\n"})
public final class HeldItemProvider {
    @NotNull
    public static final HeldItemProvider INSTANCE = new HeldItemProvider();
    @NotNull
    private static final PrioritizedList<HeldItemManager> managers = new PrioritizedList();

    private HeldItemProvider() {
    }

    @NotNull
    public final HeldItemManager provide(@NotNull BattlePokemon pokemon) {
        HeldItemManager heldItemManager2;
        Object v0;
        block2: {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Iterable $this$firstOrNull$iv = managers;
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                HeldItemManager manager = (HeldItemManager)element$iv;
                boolean bl = false;
                if (!(manager.showdownId(pokemon) != null)) continue;
                v0 = element$iv;
                break block2;
            }
            v0 = null;
        }
        if ((heldItemManager2 = (HeldItemManager)v0) == null) {
            heldItemManager2 = HeldItemManager.Companion.getEMPTY();
        }
        return heldItemManager2;
    }

    @Nullable
    public final String provideShowdownId(@NotNull BattlePokemon pokemon) {
        String string;
        block1: {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            for (HeldItemManager manager : (Iterable)managers) {
                boolean bl = false;
                String string2 = manager.showdownId(pokemon);
                if (string2 == null) continue;
                string = string2;
                break block1;
            }
            string = null;
        }
        return string;
    }

    public final void register(@NotNull HeldItemManager manager, @NotNull Priority priority) {
        Intrinsics.checkNotNullParameter((Object)manager, (String)"manager");
        Intrinsics.checkNotNullParameter((Object)((Object)priority), (String)"priority");
        managers.add(priority, manager);
    }

    public static /* synthetic */ void register$default(HeldItemProvider heldItemProvider, HeldItemManager heldItemManager2, Priority priority, int n, Object object) {
        if ((n & 2) != 0) {
            priority = Priority.NORMAL;
        }
        heldItemProvider.register(heldItemManager2, priority);
    }

    public final void unregister(@NotNull HeldItemManager manager, @Nullable Priority priority) {
        Intrinsics.checkNotNullParameter((Object)manager, (String)"manager");
        if (priority != null) {
            managers.remove(priority, manager);
            return;
        }
        managers.remove(manager);
    }

    public static /* synthetic */ void unregister$default(HeldItemProvider heldItemProvider, HeldItemManager heldItemManager2, Priority priority, int n, Object object) {
        if ((n & 2) != 0) {
            priority = null;
        }
        heldItemProvider.unregister(heldItemManager2, priority);
    }

    @NotNull
    public final List<HeldItemManager> managers() {
        return CollectionsKt.toList((Iterable)managers);
    }
}

