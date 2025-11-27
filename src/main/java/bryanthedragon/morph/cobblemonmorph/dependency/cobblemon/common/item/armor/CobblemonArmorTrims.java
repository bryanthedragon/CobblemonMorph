/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.armor;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import kotlin.Metadata;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/item/armor/CobblemonArmorTrims;", "", "Lnet/minecraft/resources/ResourceLocation;", "AUTOMATON", "Lnet/minecraft/resources/ResourceLocation;", "getAUTOMATON", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
public final class CobblemonArmorTrims {
    @NotNull
    public static final CobblemonArmorTrims INSTANCE = new CobblemonArmorTrims();
    @NotNull
    private static final ResourceLocation AUTOMATON = MiscUtils.cobblemonResource("automaton");

    private CobblemonArmorTrims() {
    }

    @NotNull
    public final ResourceLocation getAUTOMATON() {
        return AUTOMATON;
    }
}

