/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Reflection
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.reflect.KClass
 *  net.minecraft.nbt.CompoundTag
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects.IllusionEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects.TransformEffect;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\bf\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fJ\u001f\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\f\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\f\u0010\rJ\u001f\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u000e\u0010\u0006\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/api/entity/pokemon/EntityEffect;", "", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "Ljava/util/concurrent/CompletableFuture;", "end", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Ljava/util/concurrent/CompletableFuture;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)V", "saveToNbt", "()Lnet/minecraft/nbt/CompoundTag;", "start", "Companion", "common"})
public interface EntityEffect {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon.EntityEffect$Companion.$$INSTANCE;

    @Nullable
    public CompletableFuture<PokemonEntity> start(@NotNull PokemonEntity var1);

    @Nullable
    public CompletableFuture<PokemonEntity> end(@NotNull PokemonEntity var1);

    @NotNull
    public CompoundTag saveToNbt();

    public void loadFromNBT(@NotNull CompoundTag var1);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\t\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\t\u0010\nJ;\u0010\u0011\u001a\u00020\u0010\"\b\b\u0000\u0010\u000b*\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\f2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e\u00a2\u0006\u0004\b\u0011\u0010\u0012R&\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u000e0\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015R(\u0010\u0016\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00040\f0\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0015\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/api/entity/pokemon/EntityEffect$Companion;", "", "", "id", "Lcom/cobblemon/mod/common/api/entity/pokemon/EntityEffect;", "createDefault", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/entity/pokemon/EntityEffect;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "loadFromNbt", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/entity/pokemon/EntityEffect;", "T", "Lkotlin/reflect/KClass;", "type", "Lkotlin/Function0;", "default", "", "register", "(Ljava/lang/String;Lkotlin/reflect/KClass;Lkotlin/jvm/functions/Function0;)V", "", "defaults", "Ljava/util/Map;", "effects", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nEntityEffect.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EntityEffect.kt\ncom/cobblemon/mod/common/api/entity/pokemon/EntityEffect$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,98:1\n1#2:99\n*E\n"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final Map<String, KClass<? extends EntityEffect>> effects;
        @NotNull
        private static final Map<String, Function0<EntityEffect>> defaults;

        private Companion() {
        }

        public final <T extends EntityEffect> void register(@NotNull String id, @NotNull KClass<T> type, @NotNull Function0<? extends T> function0) {
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            Intrinsics.checkNotNullParameter(type, (String)"type");
            Intrinsics.checkNotNullParameter(function0, (String)"default");
            effects.put(id, type);
            defaults.put(id, function0);
        }

        @Nullable
        public final EntityEffect createDefault(@NotNull String id) {
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            Function0<EntityEffect> function0 = defaults.get(id);
            return function0 != null ? (EntityEffect)function0.invoke() : null;
        }

        @Nullable
        public final EntityEffect loadFromNbt(@NotNull CompoundTag nbt) {
            Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
            if (nbt.m_128441_("EntityEffectID")) {
                EntityEffect entityEffect;
                String id = nbt.m_128461_("EntityEffectID");
                Intrinsics.checkNotNullExpressionValue((Object)id, (String)"id");
                EntityEffect entityEffect2 = this.createDefault(id);
                if (entityEffect2 != null) {
                    EntityEffect entityEffect3;
                    EntityEffect it = entityEffect3 = entityEffect2;
                    boolean bl = false;
                    it.loadFromNBT(nbt);
                    entityEffect = entityEffect3;
                } else {
                    entityEffect = null;
                }
                return entityEffect;
            }
            return null;
        }

        static {
            $$INSTANCE = new Companion();
            effects = new LinkedHashMap();
            defaults = new LinkedHashMap();
            $$INSTANCE.register(IllusionEffect.Companion.getID(), Reflection.getOrCreateKotlinClass(IllusionEffect.class), 1.INSTANCE);
            $$INSTANCE.register(TransformEffect.Companion.getID(), Reflection.getOrCreateKotlinClass(TransformEffect.class), 2.INSTANCE);
        }
    }
}

