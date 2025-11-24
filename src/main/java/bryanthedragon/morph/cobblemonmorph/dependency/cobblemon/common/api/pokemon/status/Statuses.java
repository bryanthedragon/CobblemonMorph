/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.VolatileStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.nonpersistent.AttractStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.nonpersistent.ConfuseStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.BurnStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.FrozenStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.ParalysisStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.PoisonBadlyStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.PoisonStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.SleepStatus;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b=\u0010>J\u0013\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\b\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\b\u0010\fJ\u001f\u0010\u000f\u001a\u00028\u0000\"\b\b\u0000\u0010\r*\u00020\u00032\u0006\u0010\u000e\u001a\u00028\u0000\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u0017\u0010\u001c\u001a\u00020\u001b8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\u0017\u0010!\u001a\u00020 8\u0006\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$R\u0017\u0010&\u001a\u00020%8\u0006\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)R\u0017\u0010+\u001a\u00020*8\u0006\u00a2\u0006\f\n\u0004\b+\u0010,\u001a\u0004\b-\u0010.R\u0017\u00100\u001a\u00020/8\u0006\u00a2\u0006\f\n\u0004\b0\u00101\u001a\u0004\b2\u00103R\u0017\u00105\u001a\u0002048\u0006\u00a2\u0006\f\n\u0004\b5\u00106\u001a\u0004\b7\u00108R\u001a\u00109\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b9\u0010:R\u001a\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b;\u0010:R\u001a\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b<\u0010:\u00a8\u0006?"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/status/Statuses;", "", "", "Lcom/cobblemon/mod/common/api/pokemon/status/Status;", "getPersistentStatuses", "()Ljava/util/List;", "", "showdownName", "getStatus", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/status/Status;", "Lnet/minecraft/resources/ResourceLocation;", "name", "(Lnet/minecraft/resources/ResourceLocation;)Lcom/cobblemon/mod/common/api/pokemon/status/Status;", "T", "status", "registerStatus", "(Lcom/cobblemon/mod/common/api/pokemon/status/Status;)Lcom/cobblemon/mod/common/api/pokemon/status/Status;", "Lcom/cobblemon/mod/common/pokemon/status/statuses/nonpersistent/AttractStatus;", "ATTRACT", "Lcom/cobblemon/mod/common/pokemon/status/statuses/nonpersistent/AttractStatus;", "getATTRACT", "()Lcom/cobblemon/mod/common/pokemon/status/statuses/nonpersistent/AttractStatus;", "Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/BurnStatus;", "BURN", "Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/BurnStatus;", "getBURN", "()Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/BurnStatus;", "Lcom/cobblemon/mod/common/pokemon/status/statuses/nonpersistent/ConfuseStatus;", "CONFUSE", "Lcom/cobblemon/mod/common/pokemon/status/statuses/nonpersistent/ConfuseStatus;", "getCONFUSE", "()Lcom/cobblemon/mod/common/pokemon/status/statuses/nonpersistent/ConfuseStatus;", "Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/FrozenStatus;", "FROZEN", "Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/FrozenStatus;", "getFROZEN", "()Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/FrozenStatus;", "Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/ParalysisStatus;", "PARALYSIS", "Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/ParalysisStatus;", "getPARALYSIS", "()Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/ParalysisStatus;", "Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/PoisonStatus;", "POISON", "Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/PoisonStatus;", "getPOISON", "()Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/PoisonStatus;", "Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/PoisonBadlyStatus;", "POISON_BADLY", "Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/PoisonBadlyStatus;", "getPOISON_BADLY", "()Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/PoisonBadlyStatus;", "Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/SleepStatus;", "SLEEP", "Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/SleepStatus;", "getSLEEP", "()Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/SleepStatus;", "allStatuses", "Ljava/util/List;", "persistentStatuses", "volatileStatuses", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nStatuses.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Statuses.kt\ncom/cobblemon/mod/common/api/pokemon/status/Statuses\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,62:1\n1#2:63\n*E\n"})
public final class Statuses {
    @NotNull
    public static final Statuses INSTANCE = new Statuses();
    @NotNull
    private static final List<Status> persistentStatuses = new ArrayList();
    @NotNull
    private static final List<Status> volatileStatuses = new ArrayList();
    @NotNull
    private static final List<Status> allStatuses = new ArrayList();
    @NotNull
    private static final PoisonStatus POISON = (PoisonStatus)INSTANCE.registerStatus((Status)new PoisonStatus());
    @NotNull
    private static final PoisonBadlyStatus POISON_BADLY = (PoisonBadlyStatus)INSTANCE.registerStatus((Status)new PoisonBadlyStatus());
    @NotNull
    private static final ParalysisStatus PARALYSIS = (ParalysisStatus)INSTANCE.registerStatus((Status)new ParalysisStatus());
    @NotNull
    private static final SleepStatus SLEEP = (SleepStatus)INSTANCE.registerStatus((Status)new SleepStatus());
    @NotNull
    private static final FrozenStatus FROZEN = (FrozenStatus)INSTANCE.registerStatus((Status)new FrozenStatus());
    @NotNull
    private static final BurnStatus BURN = (BurnStatus)INSTANCE.registerStatus((Status)new BurnStatus());
    @NotNull
    private static final AttractStatus ATTRACT = (AttractStatus)INSTANCE.registerStatus((Status)new AttractStatus());
    @NotNull
    private static final ConfuseStatus CONFUSE = (ConfuseStatus)INSTANCE.registerStatus((Status)new ConfuseStatus());

    private Statuses() {
    }

    @NotNull
    public final PoisonStatus getPOISON() {
        return POISON;
    }

    @NotNull
    public final PoisonBadlyStatus getPOISON_BADLY() {
        return POISON_BADLY;
    }

    @NotNull
    public final ParalysisStatus getPARALYSIS() {
        return PARALYSIS;
    }

    @NotNull
    public final SleepStatus getSLEEP() {
        return SLEEP;
    }

    @NotNull
    public final FrozenStatus getFROZEN() {
        return FROZEN;
    }

    @NotNull
    public final BurnStatus getBURN() {
        return BURN;
    }

    @NotNull
    public final AttractStatus getATTRACT() {
        return ATTRACT;
    }

    @NotNull
    public final ConfuseStatus getCONFUSE() {
        return CONFUSE;
    }

    @NotNull
    public final <T extends Status> T registerStatus(@NotNull T status) {
        Intrinsics.checkNotNullParameter(status, (String)"status");
        if (status instanceof PersistentStatus) {
            persistentStatuses.add(status);
        } else if (status instanceof VolatileStatus) {
            volatileStatuses.add(status);
        }
        allStatuses.add(status);
        return status;
    }

    @Nullable
    public final Status getStatus(@NotNull ResourceLocation name) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Iterable iterable = allStatuses;
            for (Object t : iterable) {
                Status status = (Status)t;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)status.getName(), (Object)name)) continue;
                v0 = t;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    @Nullable
    public final Status getStatus(@NotNull String showdownName) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter((Object)showdownName, (String)"showdownName");
            Iterable iterable = allStatuses;
            for (Object t : iterable) {
                Status it = (Status)t;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getShowdownName(), (Object)showdownName)) continue;
                v0 = t;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    @NotNull
    public final List<Status> getPersistentStatuses() {
        return persistentStatuses;
    }
}

