/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.CachedExperienceGroup;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u001a\u0010\u0007\u001a\u00020\u00068\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/experience/Erratic;", "Lcom/cobblemon/mod/common/api/pokemon/experience/CachedExperienceGroup;", "", "level", "getExperience", "(I)I", "", "name", "Ljava/lang/String;", "getName", "()Ljava/lang/String;", "<init>", "()V", "common"})
public final class Erratic
extends CachedExperienceGroup {
    @NotNull
    public static final Erratic INSTANCE = new Erratic();
    @NotNull
    private static final String name = "erratic";

    private Erratic() {
    }

    @Override
    @NotNull
    public String getName() {
        return name;
    }

    @Override
    public int getExperience(int level) {
        return level == 1 ? 0 : (level < 50 ? SimpleMathExtensionsKt.pow(level, 3) * (100 - level) / 50 : (level < 68 ? SimpleMathExtensionsKt.pow(level, 3) * (150 - level) / 100 : (level < 98 ? SimpleMathExtensionsKt.pow(level, 3) * (1911 - 10 * level) / 3 / 500 : SimpleMathExtensionsKt.pow(level, 3) * (160 - level) / 100)));
    }
}

