/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.starter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.StarterCategory;
import java.util.List;
import kotlin.Metadata;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\t\u0010\nJ\u001d\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0011\u0010\u0010\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/starter/StarterHandler;", "", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "categoryName", "", "index", "", "chooseStarter", "(Lnet/minecraft/server/level/ServerPlayer;Ljava/lang/String;I)V", "", "Lcom/cobblemon/mod/common/config/starter/StarterCategory;", "getStarterList", "(Lnet/minecraft/server/level/ServerPlayer;)Ljava/util/List;", "handleJoin", "(Lnet/minecraft/server/level/ServerPlayer;)V", "requestStarterChoice", "common"})
public interface StarterHandler {
    @NotNull
    public List<StarterCategory> getStarterList(@NotNull ServerPlayer var1);

    public void handleJoin(@NotNull ServerPlayer var1);

    public void requestStarterChoice(@NotNull ServerPlayer var1);

    public void chooseStarter(@NotNull ServerPlayer var1, @NotNull String var2, int var3);
}

