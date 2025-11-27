/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.commands.CommandSourceStack
 *  net.minecraft.commands.Commands
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropEntry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b#\u0010$J3\u0010\u000b\u001a\u00020\n2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR\u001a\u0010\u000e\u001a\u00020\r8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0013\u001a\u00020\u00128\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0018\u001a\u00020\u00178\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u00128\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u001c\u0010\u0014\u001a\u0004\b\u001d\u0010\u0016R\u001a\u0010\u001f\u001a\u00020\u001e8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"\u00a8\u0006%"}, d2={"Lcom/cobblemon/mod/common/api/drop/CommandDropEntry;", "Lcom/cobblemon/mod/common/api/drop/DropEntry;", "Lnet/minecraft/world/entity/LivingEntity;", "entity", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/world/phys/Vec3;", "pos", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "drop", "(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/server/level/ServerPlayer;)V", "", "command", "Ljava/lang/String;", "getCommand", "()Ljava/lang/String;", "", "maxSelectableTimes", "I", "getMaxSelectableTimes", "()I", "", "percentage", "F", "getPercentage", "()F", "quantity", "getQuantity", "", "requiresPlayer", "Z", "getRequiresPlayer", "()Z", "<init>", "()V", "common"})
public final class CommandDropEntry
implements DropEntry {
    private final boolean requiresPlayer;
    @NotNull
    private final String command;
    private final float percentage;
    private final int quantity;
    private final int maxSelectableTimes;

    public CommandDropEntry() {
        this.requiresPlayer = true;
        this.command = "";
        this.percentage = 100.0f;
        this.quantity = 1;
        this.maxSelectableTimes = 1;
    }

    public final boolean getRequiresPlayer() {
        return this.requiresPlayer;
    }

    @NotNull
    public final String getCommand() {
        return this.command;
    }

    @Override
    public float getPercentage() {
        return this.percentage;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public int getMaxSelectableTimes() {
        return this.maxSelectableTimes;
    }

    @Override
    public void drop(@Nullable LivingEntity entity2, @NotNull ServerLevel world, @NotNull Vec3 pos, @Nullable ServerPlayer player) {
        String string;
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        if (this.requiresPlayer && player == null) {
            return;
        }
        Commands commands = world.m_7654_().m_129892_();
        CommandSourceStack commandSourceStack = world.m_7654_().m_129893_();
        ServerPlayer serverPlayer = player;
        if ((serverPlayer != null && (serverPlayer = serverPlayer.m_7755_()) != null ? serverPlayer.getString() : (string = null)) == null) {
            string = "";
        }
        commands.m_230957_(commandSourceStack, MiscUtils.substitute(MiscUtils.substitute(MiscUtils.substitute(MiscUtils.substitute(MiscUtils.substitute(this.command, "player", string), "world", world.m_46472_().m_135782_()), "x", pos.f_82479_), "y", pos.f_82480_), "z", pos.f_82481_));
    }
}

