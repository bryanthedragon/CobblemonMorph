/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.BattleExperienceSource;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.CandyExperienceSource;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.CommandExperienceSource;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.SidemodExperienceSource;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0005\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0004J\u000f\u0010\u0007\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0007\u0010\u0004\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceSource;", "", "", "isBattle", "()Z", "isCommand", "isInteraction", "isSidemod", "common"})
public interface ExperienceSource {
    public boolean isBattle();

    public boolean isInteraction();

    public boolean isCommand();

    public boolean isSidemod();

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static boolean isBattle(@NotNull ExperienceSource $this) {
            return $this instanceof BattleExperienceSource;
        }

        public static boolean isInteraction(@NotNull ExperienceSource $this) {
            return $this instanceof CandyExperienceSource;
        }

        public static boolean isCommand(@NotNull ExperienceSource $this) {
            return $this instanceof CommandExperienceSource;
        }

        public static boolean isSidemod(@NotNull ExperienceSource $this) {
            return $this instanceof SidemodExperienceSource;
        }
    }
}

