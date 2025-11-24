/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.LevelCurve;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000 \n2\u00020\u0001:\u0001\nR\u0014\u0010\u0005\u001a\u00020\u00028&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004R\u0014\u0010\t\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;", "Lcom/cobblemon/mod/common/api/LevelCurve;", "", "getName", "()Ljava/lang/String;", "name", "Lnet/minecraft/network/chat/MutableComponent;", "getTranslatedName", "()Lnet/minecraft/network/chat/MutableComponent;", "translatedName", "Companion", "common"})
public interface ExperienceGroup
extends LevelCurve {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceGroup$Companion.$$INSTANCE;

    @NotNull
    public String getName();

    @NotNull
    public MutableComponent getTranslatedName();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup$Companion;", "", "", "name", "Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;", "dummy", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/experience/ExperienceGroup;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
        }

        @NotNull
        public final ExperienceGroup dummy(@NotNull String name) {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            return new ExperienceGroup(name){
                @NotNull
                private final String name;
                {
                    this.name = $name;
                }

                @NotNull
                public String getName() {
                    return this.name;
                }

                public int getExperience(int level) {
                    return 0;
                }

                public int getLevel(int experience) {
                    return 1;
                }

                @NotNull
                public MutableComponent getTranslatedName() {
                    return DefaultImpls.getTranslatedName(this);
                }
            };
        }

        static {
            $$INSTANCE = new Companion();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        @NotNull
        public static MutableComponent getTranslatedName(@NotNull ExperienceGroup $this) {
            String string = $this.getName().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("experience_group." + string, new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"experience_group.${name.lowercase()}\")");
            return mutableComponent;
        }
    }
}

