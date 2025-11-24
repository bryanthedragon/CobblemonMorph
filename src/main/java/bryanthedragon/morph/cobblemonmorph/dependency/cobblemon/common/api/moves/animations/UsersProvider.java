/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.ArraysKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.EntityProvider;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u001d\b\u0016\u0012\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\b\"\u00020\u0003\u00a2\u0006\u0004\b\n\u0010\u000bB\u0015\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\n\u0010\fR \u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/api/moves/animations/UsersProvider;", "Lcom/cobblemon/mod/common/api/moves/animations/EntityProvider;", "", "Lnet/minecraft/world/entity/Entity;", "entities", "Ljava/util/List;", "getEntities", "()Ljava/util/List;", "", "users", "<init>", "([Lnet/minecraft/world/entity/Entity;)V", "(Ljava/util/List;)V", "common"})
public final class UsersProvider
implements EntityProvider {
    @NotNull
    private final List<Entity> entities;

    public UsersProvider(@NotNull List<? extends Entity> users) {
        Intrinsics.checkNotNullParameter(users, (String)"users");
        this.entities = users;
    }

    @Override
    @NotNull
    public List<Entity> getEntities() {
        return this.entities;
    }

    public UsersProvider(Entity ... users) {
        Intrinsics.checkNotNullParameter((Object)users, (String)"users");
        this(ArraysKt.toList((Object[])users));
    }
}

