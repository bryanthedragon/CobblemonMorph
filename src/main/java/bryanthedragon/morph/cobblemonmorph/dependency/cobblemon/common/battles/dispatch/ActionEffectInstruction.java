/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.StringValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0096\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\u000b\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\u000b\u0010\nJ\u0017\u0010\f\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\f\u0010\nJ\u001f\u0010\r\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\r\u0010\u000eR \u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u000f8&@&X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\"\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00160\u00158&@&X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001f\u001a\u00020\u001c8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/battles/dispatch/ActionEffectInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "", "addMolangQueries", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;)V", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "postActionEffect", "preActionEffect", "runActionEffect", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/bedrockk/molang/runtime/MoLangRuntime;)V", "Ljava/util/concurrent/CompletableFuture;", "getFuture", "()Ljava/util/concurrent/CompletableFuture;", "setFuture", "(Ljava/util/concurrent/CompletableFuture;)V", "future", "", "", "getHolds", "()Ljava/util/Set;", "setHolds", "(Ljava/util/Set;)V", "holds", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "id", "common"})
public interface ActionEffectInstruction
extends InterpreterInstruction {
    @NotNull
    public CompletableFuture<?> getFuture();

    public void setFuture(@NotNull CompletableFuture<?> var1);

    @NotNull
    public Set<String> getHolds();

    public void setHolds(@NotNull Set<String> var1);

    @NotNull
    public ResourceLocation getId();

    @Override
    public void invoke(@NotNull PokemonBattle var1);

    public void preActionEffect(@NotNull PokemonBattle var1);

    public void runActionEffect(@NotNull PokemonBattle var1, @NotNull MoLangRuntime var2);

    public void postActionEffect(@NotNull PokemonBattle var1);

    public void addMolangQueries(@NotNull MoLangRuntime var1);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static void invoke(@NotNull ActionEffectInstruction $this, @NotNull PokemonBattle battle2) {
            Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
            $this.preActionEffect(battle2);
            MoLangRuntime runtime2 = new MoLangRuntime();
            MoLangEnvironment moLangEnvironment = runtime2.getEnvironment();
            Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"runtime.environment");
            battle2.addQueryFunctions(MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null));
            MoLangEnvironment moLangEnvironment2 = runtime2.getEnvironment();
            Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment2, (String)"runtime.environment");
            MoLangFunctions.INSTANCE.addStandardFunctions(MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment2, null, 1, null));
            $this.addMolangQueries(runtime2);
            $this.runActionEffect(battle2, runtime2);
            $this.postActionEffect(battle2);
        }

        public static void addMolangQueries(@NotNull ActionEffectInstruction $this, @NotNull MoLangRuntime runtime2) {
            Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
            MoLangEnvironment moLangEnvironment = runtime2.getEnvironment();
            Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"runtime.environment");
            MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null).addFunction("instruction_id", arg_0 -> DefaultImpls.addMolangQueries$lambda$0($this, arg_0));
        }

        private static Object addMolangQueries$lambda$0(ActionEffectInstruction this$0, MoParams it) {
            Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
            return new StringValue(this$0.getId().toString());
        }
    }
}

