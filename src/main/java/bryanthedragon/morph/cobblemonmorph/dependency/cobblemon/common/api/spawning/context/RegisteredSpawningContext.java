/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\b\u0086\b\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003B%\u0012\u0006\u0010\u000b\u001a\u00020\u0004\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\u0006\u0010\r\u001a\u00020\u0004\u00a2\u0006\u0004\b \u0010!J\u0010\u0010\u0005\u001a\u00020\u0004H\u00c6\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H\u00c6\u0003\u00a2\u0006\u0004\b\b\u0010\tJ\u0010\u0010\n\u001a\u00020\u0004H\u00c6\u0003\u00a2\u0006\u0004\b\n\u0010\u0006J:\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u000b\u001a\u00020\u00042\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00072\b\b\u0002\u0010\r\u001a\u00020\u0004H\u00c6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001a\u0010\u0012\u001a\u00020\u00112\b\u0010\u0010\u001a\u0004\u0018\u00010\u0003H\u00d6\u0003\u00a2\u0006\u0004\b\u0012\u0010\u0013J\r\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0018\u001a\u00020\u0017H\u00d6\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0010\u0010\u001a\u001a\u00020\u0004H\u00d6\u0001\u00a2\u0006\u0004\b\u001a\u0010\u0006R\u001d\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00078\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\u001b\u001a\u0004\b\u001c\u0010\tR\u0017\u0010\r\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u001d\u001a\u0004\b\u001e\u0010\u0006R\u0017\u0010\u000b\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u001d\u001a\u0004\b\u001f\u0010\u0006\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/api/spawning/context/RegisteredSpawningContext;", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "T", "", "", "component1", "()Ljava/lang/String;", "Ljava/lang/Class;", "component2", "()Ljava/lang/Class;", "component3", "name", "clazz", "defaultCondition", "copy", "(Ljava/lang/String;Ljava/lang/Class;Ljava/lang/String;)Lcom/cobblemon/mod/common/api/spawning/context/RegisteredSpawningContext;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "getWeight", "()F", "", "hashCode", "()I", "toString", "Ljava/lang/Class;", "getClazz", "Ljava/lang/String;", "getDefaultCondition", "getName", "<init>", "(Ljava/lang/String;Ljava/lang/Class;Ljava/lang/String;)V", "common"})
public final class RegisteredSpawningContext<T extends SpawningContext> {
    @NotNull
    private final String name;
    @NotNull
    private final Class<T> clazz;
    @NotNull
    private final String defaultCondition;

    public RegisteredSpawningContext(@NotNull String name, @NotNull Class<T> clazz, @NotNull String defaultCondition) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter(clazz, (String)"clazz");
        Intrinsics.checkNotNullParameter((Object)defaultCondition, (String)"defaultCondition");
        this.name = name;
        this.clazz = clazz;
        this.defaultCondition = defaultCondition;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final Class<T> getClazz() {
        return this.clazz;
    }

    @NotNull
    public final String getDefaultCondition() {
        return this.defaultCondition;
    }

    public final float getWeight() {
        Float f = Cobblemon.INSTANCE.getBestSpawner().getConfig().getContextWeights().get(this.name);
        return f != null ? f.floatValue() : 1.0f;
    }

    @NotNull
    public final String component1() {
        return this.name;
    }

    @NotNull
    public final Class<T> component2() {
        return this.clazz;
    }

    @NotNull
    public final String component3() {
        return this.defaultCondition;
    }

    @NotNull
    public final RegisteredSpawningContext<T> copy(@NotNull String name, @NotNull Class<T> clazz, @NotNull String defaultCondition) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter(clazz, (String)"clazz");
        Intrinsics.checkNotNullParameter((Object)defaultCondition, (String)"defaultCondition");
        return new RegisteredSpawningContext<T>(name, clazz, defaultCondition);
    }

    public static /* synthetic */ RegisteredSpawningContext copy$default(RegisteredSpawningContext registeredSpawningContext, String string, Class clazz, String string2, int n, Object object) {
        if ((n & 1) != 0) {
            string = registeredSpawningContext.name;
        }
        if ((n & 2) != 0) {
            clazz = registeredSpawningContext.clazz;
        }
        if ((n & 4) != 0) {
            string2 = registeredSpawningContext.defaultCondition;
        }
        return registeredSpawningContext.copy(string, clazz, string2);
    }

    @NotNull
    public String toString() {
        return "RegisteredSpawningContext(name=" + this.name + ", clazz=" + this.clazz + ", defaultCondition=" + this.defaultCondition + ")";
    }

    public int hashCode() {
        int result = this.name.hashCode();
        result = result * 31 + this.clazz.hashCode();
        result = result * 31 + this.defaultCondition.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RegisteredSpawningContext)) {
            return false;
        }
        RegisteredSpawningContext registeredSpawningContext = (RegisteredSpawningContext)other;
        if (!Intrinsics.areEqual((Object)this.name, (Object)registeredSpawningContext.name)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.clazz, registeredSpawningContext.clazz)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.defaultCondition, (Object)registeredSpawningContext.defaultCondition);
    }
}

