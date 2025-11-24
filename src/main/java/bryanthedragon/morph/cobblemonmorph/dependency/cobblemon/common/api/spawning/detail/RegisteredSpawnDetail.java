/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003B\u0015\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\n\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001d\u0010\b\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/spawning/detail/RegisteredSpawnDetail;", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "T", "", "Lcom/google/gson/JsonElement;", "element", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserializeDetail", "(Lcom/google/gson/JsonElement;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "Ljava/lang/Class;", "detailClass", "Ljava/lang/Class;", "getDetailClass", "()Ljava/lang/Class;", "<init>", "(Ljava/lang/Class;)V", "common"})
public final class RegisteredSpawnDetail<T extends SpawnDetail> {
    @NotNull
    private final Class<T> detailClass;

    public RegisteredSpawnDetail(@NotNull Class<T> detailClass) {
        Intrinsics.checkNotNullParameter(detailClass, (String)"detailClass");
        this.detailClass = detailClass;
    }

    @NotNull
    public final Class<T> getDetailClass() {
        return this.detailClass;
    }

    @NotNull
    public final T deserializeDetail(@NotNull JsonElement element, @NotNull JsonDeserializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)element, (String)"element");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        Object object = ctx.deserialize(element, (Type)this.detailClass);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"ctx.deserialize(element, detailClass)");
        return (T)((SpawnDetail)object);
    }
}

