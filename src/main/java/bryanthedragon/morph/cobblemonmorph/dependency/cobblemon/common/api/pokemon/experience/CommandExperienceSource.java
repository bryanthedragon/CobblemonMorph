/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.commands.SharedSuggestionProvider
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceSource;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.commands.SharedSuggestionProvider;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/experience/CommandExperienceSource;", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceSource;", "Lnet/minecraft/commands/SharedSuggestionProvider;", "source", "Lnet/minecraft/commands/SharedSuggestionProvider;", "getSource", "()Lnet/minecraft/commands/SharedSuggestionProvider;", "<init>", "(Lnet/minecraft/commands/SharedSuggestionProvider;)V", "common"})
public class CommandExperienceSource
implements ExperienceSource {
    @NotNull
    private final SharedSuggestionProvider source;

    public CommandExperienceSource(@NotNull SharedSuggestionProvider source) {
        Intrinsics.checkNotNullParameter((Object)source, (String)"source");
        this.source = source;
    }

    @NotNull
    public final SharedSuggestionProvider getSource() {
        return this.source;
    }

    @Override
    public boolean isBattle() {
        return ExperienceSource.DefaultImpls.isBattle(this);
    }

    @Override
    public boolean isInteraction() {
        return ExperienceSource.DefaultImpls.isInteraction(this);
    }

    @Override
    public boolean isCommand() {
        return ExperienceSource.DefaultImpls.isCommand(this);
    }

    @Override
    public boolean isSidemod() {
        return ExperienceSource.DefaultImpls.isSidemod(this);
    }
}

