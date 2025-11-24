/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.CachedLevelThresholds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceGroup;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/experience/CachedExperienceGroup;", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;", "", "experience", "getLevel", "(I)I", "Lcom/cobblemon/mod/common/api/CachedLevelThresholds;", "thresholds", "Lcom/cobblemon/mod/common/api/CachedLevelThresholds;", "<init>", "()V", "common"})
public abstract class CachedExperienceGroup
implements ExperienceGroup {
    @NotNull
    private final CachedLevelThresholds thresholds = new CachedLevelThresholds(0, (Function1)new Function1<Integer, Integer>((Object)this){

        @NotNull
        public final Integer invoke(int p0) {
            return ((CachedExperienceGroup)this.receiver).getExperience(p0);
        }
    }, 1, null);

    @Override
    public int getLevel(int experience) {
        return this.thresholds.getLevel(experience);
    }

    @Override
    @NotNull
    public MutableComponent getTranslatedName() {
        return ExperienceGroup.DefaultImpls.getTranslatedName(this);
    }
}

