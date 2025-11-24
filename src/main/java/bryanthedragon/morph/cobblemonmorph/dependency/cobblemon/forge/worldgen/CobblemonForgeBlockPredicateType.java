/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.registries.Registries
 *  net.minecraftforge.registries.RegisterEvent
 *  net.minecraftforge.registries.RegisterEvent$RegisterHelper
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.forge.worldgen;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.predicate.CobblemonBlockPredicates;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.RegisterEvent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/forge/worldgen/CobblemonForgeBlockPredicateType;", "", "Lnet/minecraftforge/registries/RegisterEvent;", "event", "", "register", "(Lnet/minecraftforge/registries/RegisterEvent;)V", "<init>", "()V", "forge"})
public final class CobblemonForgeBlockPredicateType {
    @NotNull
    public static final CobblemonForgeBlockPredicateType INSTANCE = new CobblemonForgeBlockPredicateType();

    private CobblemonForgeBlockPredicateType() {
    }

    public final void register(@NotNull RegisterEvent event) {
        Intrinsics.checkNotNullParameter((Object)event, (String)"event");
        event.register(Registries.f_256774_, CobblemonForgeBlockPredicateType::register$lambda$0);
    }

    private static final void register$lambda$0(RegisterEvent.RegisterHelper it) {
        CobblemonBlockPredicates.INSTANCE.touch();
    }
}

