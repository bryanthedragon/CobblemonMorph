/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.jvm.internal.markers.KMappedMarker
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.Erratic;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceGroup;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.Fast;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.Fluctuating;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.MediumFast;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.MediumSlow;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.Slow;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010)\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0016\u0010\u000fJ\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007H\u0096\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u0015\u0010\u000b\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ\r\u0010\u000e\u001a\u00020\r\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0015\u0010\u0011\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroups;", "", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;", "", "name", "findByName", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;", "", "iterator", "()Ljava/util/Iterator;", "experienceGroup", "register", "(Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;)Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;", "", "registerDefaults", "()V", "", "unregister", "(Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;)Z", "", "groups", "Ljava/util/List;", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nExperienceGroups.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ExperienceGroups.kt\ncom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroups\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,138:1\n1#2:139\n*E\n"})
public final class ExperienceGroups
implements Iterable<ExperienceGroup>,
KMappedMarker {
    @NotNull
    public static final ExperienceGroups INSTANCE = new ExperienceGroups();
    @NotNull
    private static final List<ExperienceGroup> groups = new ArrayList();

    private ExperienceGroups() {
    }

    @Override
    @NotNull
    public Iterator<ExperienceGroup> iterator() {
        return groups.iterator();
    }

    @Nullable
    public final ExperienceGroup findByName(@NotNull String name) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Iterable iterable = this;
            for (Object t : iterable) {
                ExperienceGroup it = (ExperienceGroup)t;
                boolean bl = false;
                if (!StringsKt.equals((String)it.getName(), (String)name, (boolean)true)) continue;
                v0 = t;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    @NotNull
    public final ExperienceGroup register(@NotNull ExperienceGroup experienceGroup) {
        ExperienceGroup experienceGroup2;
        Intrinsics.checkNotNullParameter((Object)experienceGroup, (String)"experienceGroup");
        ExperienceGroup it = experienceGroup2 = experienceGroup;
        boolean bl = false;
        groups.add(it);
        return experienceGroup2;
    }

    public final boolean unregister(@NotNull ExperienceGroup experienceGroup) {
        Intrinsics.checkNotNullParameter((Object)experienceGroup, (String)"experienceGroup");
        return groups.remove(experienceGroup);
    }

    public final void registerDefaults() {
        this.register(Erratic.INSTANCE);
        this.register(Fast.INSTANCE);
        this.register(MediumFast.INSTANCE);
        this.register(MediumSlow.INSTANCE);
        this.register(Slow.INSTANCE);
        this.register(Fluctuating.INSTANCE);
    }
}

