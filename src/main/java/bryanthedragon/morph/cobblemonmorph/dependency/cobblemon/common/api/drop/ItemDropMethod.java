/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.ItemDropMethod;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization.StringIdentifiedObjectAdapter;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\u0001\u0018\u0000 \t2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\tB\u0011\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\nj\u0002\b\u000bj\u0002\b\f\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/api/drop/ItemDropMethod;", "", "", "methodName", "Ljava/lang/String;", "getMethodName", "()Ljava/lang/String;", "<init>", "(Ljava/lang/String;ILjava/lang/String;)V", "Companion", "ON_ENTITY", "ON_PLAYER", "TO_INVENTORY", "common"})
public final class ItemDropMethod
extends Enum<ItemDropMethod> {
    @NotNull
    public static final Companion Companion;
    @NotNull
    private final String methodName;
    @NotNull
    private static final StringIdentifiedObjectAdapter<ItemDropMethod> adapter;
    public static final /* enum */ ItemDropMethod ON_ENTITY;
    public static final /* enum */ ItemDropMethod ON_PLAYER;
    public static final /* enum */ ItemDropMethod TO_INVENTORY;
    private static final /* synthetic */ ItemDropMethod[] $VALUES;

    private ItemDropMethod(String methodName) {
        this.methodName = methodName;
    }

    @NotNull
    public final String getMethodName() {
        return this.methodName;
    }

    public static ItemDropMethod[] values() {
        return (ItemDropMethod[])$VALUES.clone();
    }

    public static ItemDropMethod valueOf(String value2) {
        return Enum.valueOf(ItemDropMethod.class, value2);
    }

    static {
        ON_ENTITY = new ItemDropMethod("on-entity");
        ON_PLAYER = new ItemDropMethod("on-player");
        TO_INVENTORY = new ItemDropMethod("to-inventory");
        $VALUES = itemDropMethodArray = new ItemDropMethod[]{ItemDropMethod.ON_ENTITY, ItemDropMethod.ON_PLAYER, ItemDropMethod.TO_INVENTORY};
        Companion = new Companion(null);
        adapter = new StringIdentifiedObjectAdapter(Companion.adapter.1.INSTANCE);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001f\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/drop/ItemDropMethod$Companion;", "", "Lcom/cobblemon/mod/common/api/serialization/StringIdentifiedObjectAdapter;", "Lcom/cobblemon/mod/common/api/drop/ItemDropMethod;", "adapter", "Lcom/cobblemon/mod/common/api/serialization/StringIdentifiedObjectAdapter;", "getAdapter", "()Lcom/cobblemon/mod/common/api/serialization/StringIdentifiedObjectAdapter;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final StringIdentifiedObjectAdapter<ItemDropMethod> getAdapter() {
            return adapter;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

