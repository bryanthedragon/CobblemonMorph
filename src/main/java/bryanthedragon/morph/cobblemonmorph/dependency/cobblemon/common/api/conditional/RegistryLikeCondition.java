/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.core.Registry
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional;

import kotlin.Metadata;
import net.minecraft.core.Registry;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J%\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00028\u00002\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004H&\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "T", "", "t", "Lnet/minecraft/core/Registry;", "registry", "", "fits", "(Ljava/lang/Object;Lnet/minecraft/core/Registry;)Z", "common"})
public interface RegistryLikeCondition<T> {
    public boolean fits(T var1, @NotNull Registry<T> var2);
}

