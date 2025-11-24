/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.nbt.CompoundTag
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.conversions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.CobblemonAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001f\u0010\r\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\nH&\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001f\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\nH&\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u000f\u0010\u0012\u001a\u00020\u0003H&\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0017\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u000b\u001a\u00020\nH&\u00a2\u0006\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/api/storage/adapter/conversions/CobblemonConverter;", "S", "Lcom/cobblemon/mod/common/api/storage/adapter/CobblemonAdapter;", "Ljava/nio/file/Path;", "target", "", "exists", "(Ljava/nio/file/Path;)Z", "Ljava/util/UUID;", "user", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "Lcom/cobblemon/mod/common/api/storage/party/PlayerPartyStore;", "party", "(Ljava/util/UUID;Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/storage/party/PlayerPartyStore;", "Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "pc", "(Ljava/util/UUID;Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "root", "()Ljava/nio/file/Path;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "translate", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "common"})
public interface CobblemonConverter<S>
extends CobblemonAdapter<S> {
    @NotNull
    public Path root();

    public boolean exists(@NotNull Path var1);

    @NotNull
    public PlayerPartyStore party(@NotNull UUID var1, @NotNull CompoundTag var2);

    @NotNull
    public PCStore pc(@NotNull UUID var1, @NotNull CompoundTag var2);

    @NotNull
    public Pokemon translate(@NotNull CompoundTag var1);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static <S> boolean exists(@NotNull CobblemonConverter<S> $this, @NotNull Path target) {
            Intrinsics.checkNotNullParameter((Object)target, (String)"target");
            LinkOption[] linkOptionArray = new LinkOption[]{};
            return Files.exists(target, Arrays.copyOf(linkOptionArray, linkOptionArray.length));
        }
    }
}

