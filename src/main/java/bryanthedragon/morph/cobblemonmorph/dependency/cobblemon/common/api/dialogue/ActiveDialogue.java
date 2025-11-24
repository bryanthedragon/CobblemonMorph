/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.MoStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.QueryStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.StringValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.Dialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialoguePage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.ActiveInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueTimeout;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ServerRealTimeTaskTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.DialogueOpenedPacket;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u00104\u001a\u000203\u0012\u0006\u0010-\u001a\u00020,\u00a2\u0006\u0004\bD\u0010EJ\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\r\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0004J\r\u0010\u0006\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0004J\r\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\b\u0010\tJ\u0015\u0010\f\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\f\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\f\u0010\u0010J\u0015\u0010\f\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\f\u0010\u0013J\r\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b\u0015\u0010\u0016R\"\u0010\u0018\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\"\u0010\u001e\u001a\u00020\u000e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010\u0010R\u0011\u0010%\u001a\u00020\u00118F\u00a2\u0006\u0006\u001a\u0004\b#\u0010$R\u001f\u0010(\u001a\n '*\u0004\u0018\u00010&0&8\u0006\u00a2\u0006\f\n\u0004\b(\u0010)\u001a\u0004\b*\u0010+R\"\u0010-\u001a\u00020,8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b-\u0010.\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\"\u00104\u001a\u0002038\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b4\u00105\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u001d\u0010;\u001a\b\u0012\u0004\u0012\u0002030:8\u0006\u00a2\u0006\f\n\u0004\b;\u0010<\u001a\u0004\b=\u0010>R\u0017\u0010@\u001a\u00020?8\u0006\u00a2\u0006\f\n\u0004\b@\u0010A\u001a\u0004\bB\u0010C\u00a8\u0006F"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "", "", "close", "()V", "escape", "incrementPage", "", "isActive", "()Z", "Lcom/bedrockk/molang/runtime/value/MoValue;", "value", "setPage", "(Lcom/bedrockk/molang/runtime/value/MoValue;)V", "Lcom/cobblemon/mod/common/api/dialogue/DialoguePage;", "page", "(Lcom/cobblemon/mod/common/api/dialogue/DialoguePage;)V", "", "index", "(I)V", "Lcom/bedrockk/molang/runtime/struct/MoStruct;", "toMoLangStruct", "()Lcom/bedrockk/molang/runtime/struct/MoStruct;", "Lcom/cobblemon/mod/common/api/dialogue/input/ActiveInput;", "activeInput", "Lcom/cobblemon/mod/common/api/dialogue/input/ActiveInput;", "getActiveInput", "()Lcom/cobblemon/mod/common/api/dialogue/input/ActiveInput;", "setActiveInput", "(Lcom/cobblemon/mod/common/api/dialogue/input/ActiveInput;)V", "currentPage", "Lcom/cobblemon/mod/common/api/dialogue/DialoguePage;", "getCurrentPage", "()Lcom/cobblemon/mod/common/api/dialogue/DialoguePage;", "setCurrentPage", "getCurrentPageIndex", "()I", "currentPageIndex", "Ljava/util/UUID;", "kotlin.jvm.PlatformType", "dialogueId", "Ljava/util/UUID;", "getDialogueId", "()Ljava/util/UUID;", "Lcom/cobblemon/mod/common/api/dialogue/Dialogue;", "dialogueReference", "Lcom/cobblemon/mod/common/api/dialogue/Dialogue;", "getDialogueReference", "()Lcom/cobblemon/mod/common/api/dialogue/Dialogue;", "setDialogueReference", "(Lcom/cobblemon/mod/common/api/dialogue/Dialogue;)V", "Lnet/minecraft/server/level/ServerPlayer;", "playerEntity", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayerEntity", "()Lnet/minecraft/server/level/ServerPlayer;", "setPlayerEntity", "(Lnet/minecraft/server/level/ServerPlayer;)V", "Lcom/cobblemon/mod/common/api/molang/ObjectValue;", "playerStruct", "Lcom/cobblemon/mod/common/api/molang/ObjectValue;", "getPlayerStruct", "()Lcom/cobblemon/mod/common/api/molang/ObjectValue;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "getRuntime", "()Lcom/bedrockk/molang/runtime/MoLangRuntime;", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/api/dialogue/Dialogue;)V", "common"})
@SourceDebugExtension(value={"SMAP\nActiveDialogue.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ActiveDialogue.kt\ncom/cobblemon/mod/common/api/dialogue/ActiveDialogue\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,125:1\n1#2:126\n*E\n"})
public final class ActiveDialogue {
    @NotNull
    private ServerPlayer playerEntity;
    @NotNull
    private Dialogue dialogueReference;
    private final UUID dialogueId;
    @NotNull
    private final MoLangRuntime runtime;
    @NotNull
    private DialoguePage currentPage;
    @NotNull
    private final ObjectValue<ServerPlayer> playerStruct;
    @NotNull
    private ActiveInput activeInput;

    public ActiveDialogue(@NotNull ServerPlayer playerEntity, @NotNull Dialogue dialogueReference) {
        Intrinsics.checkNotNullParameter((Object)playerEntity, (String)"playerEntity");
        Intrinsics.checkNotNullParameter((Object)dialogueReference, (String)"dialogueReference");
        this.playerEntity = playerEntity;
        this.dialogueReference = dialogueReference;
        this.dialogueId = UUID.randomUUID();
        this.runtime = MoLangFunctions.INSTANCE.setup(new MoLangRuntime());
        this.currentPage = this.dialogueReference.getPages().get(0);
        this.playerStruct = MoLangFunctions.INSTANCE.asMoLangValue(this.playerEntity);
        this.activeInput = new ActiveInput(this, this.currentPage.getInput());
        MoLangEnvironment moLangEnvironment = this.runtime.getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"runtime.environment");
        Pair[] pairArray = new Pair[]{TuplesKt.to((Object)"dialogue", arg_0 -> ActiveDialogue._init_$lambda$0(this, arg_0)), TuplesKt.to((Object)"player", arg_0 -> ActiveDialogue._init_$lambda$1(this, arg_0))};
        MoLangFunctions.INSTANCE.addFunctions(MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null), MapsKt.mapOf((Pair[])pairArray));
    }

    @NotNull
    public final ServerPlayer getPlayerEntity() {
        return this.playerEntity;
    }

    public final void setPlayerEntity(@NotNull ServerPlayer serverPlayer) {
        Intrinsics.checkNotNullParameter((Object)serverPlayer, (String)"<set-?>");
        this.playerEntity = serverPlayer;
    }

    @NotNull
    public final Dialogue getDialogueReference() {
        return this.dialogueReference;
    }

    public final void setDialogueReference(@NotNull Dialogue dialogue2) {
        Intrinsics.checkNotNullParameter((Object)dialogue2, (String)"<set-?>");
        this.dialogueReference = dialogue2;
    }

    public final UUID getDialogueId() {
        return this.dialogueId;
    }

    @NotNull
    public final MoLangRuntime getRuntime() {
        return this.runtime;
    }

    @NotNull
    public final DialoguePage getCurrentPage() {
        return this.currentPage;
    }

    public final void setCurrentPage(@NotNull DialoguePage dialoguePage) {
        Intrinsics.checkNotNullParameter((Object)dialoguePage, (String)"<set-?>");
        this.currentPage = dialoguePage;
    }

    @NotNull
    public final ObjectValue<ServerPlayer> getPlayerStruct() {
        return this.playerStruct;
    }

    @NotNull
    public final ActiveInput getActiveInput() {
        return this.activeInput;
    }

    public final void setActiveInput(@NotNull ActiveInput activeInput) {
        Intrinsics.checkNotNullParameter((Object)activeInput, (String)"<set-?>");
        this.activeInput = activeInput;
    }

    public final int getCurrentPageIndex() {
        return this.dialogueReference.getPages().indexOf(this.currentPage);
    }

    public final void setPage(@NotNull MoValue value2) {
        DialoguePage dialoguePage;
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        if (value2 instanceof StringValue) {
            Object v0;
            block5: {
                Iterable iterable = this.dialogueReference.getPages();
                for (Object t : iterable) {
                    DialoguePage it = (DialoguePage)t;
                    boolean bl = false;
                    if (!Intrinsics.areEqual((Object)it.getId(), (Object)((StringValue)value2).value)) continue;
                    v0 = t;
                    break block5;
                }
                v0 = null;
            }
            if ((dialoguePage = (DialoguePage)v0) == null) {
                Cobblemon.INSTANCE.getLOGGER().error("Dialogue requested page " + ((StringValue)value2).value + " but it doesn't exist");
                return;
            }
        } else {
            int pageNum = (int)value2.asDouble();
            if (pageNum < 0 || pageNum >= this.dialogueReference.getPages().size()) {
                Cobblemon.INSTANCE.getLOGGER().error("Dialogue requested page " + pageNum + " but it doesn't exist");
                return;
            }
            dialoguePage = this.dialogueReference.getPages().get(pageNum);
        }
        DialoguePage page = dialoguePage;
        this.setPage(page);
    }

    public final boolean isActive() {
        return Intrinsics.areEqual((Object)DialogueManager.INSTANCE.getActiveDialogues().get(this.playerEntity.m_20148_()), (Object)this);
    }

    public final void incrementPage() {
        this.setPage(this.getCurrentPageIndex() + 1);
    }

    public final void setPage(@NotNull DialoguePage page) {
        Intrinsics.checkNotNullParameter((Object)page, (String)"page");
        this.currentPage = page;
        this.activeInput = new ActiveInput(this, this.currentPage.getInput());
        DialogueTimeout dialogueTimeout = this.currentPage.getInput().getTimeout();
        Float deadline = dialogueTimeout != null ? Float.valueOf(dialogueTimeout.getDuration()) : null;
        UUID inputId = this.activeInput.getInputId();
        if (deadline != null && deadline.floatValue() > 0.0f) {
            ServerRealTimeTaskTracker.INSTANCE.after(deadline.floatValue(), (Function0<Unit>)((Function0)new Function0<Unit>(inputId, this){
                final /* synthetic */ UUID $inputId;
                final /* synthetic */ ActiveDialogue this$0;
                {
                    this.$inputId = $inputId;
                    this.this$0 = $receiver;
                    super(0);
                }

                public final void invoke() {
                    block0: {
                        Object object;
                        if (!Intrinsics.areEqual((Object)this.$inputId, (Object)this.this$0.getActiveInput().getInputId()) || !this.this$0.isActive() || (object = this.this$0.getActiveInput().getDialogueInput().getTimeout()) == null || (object = ((DialogueTimeout)object).getAction()) == null) break block0;
                        object.invoke(this.this$0, null);
                    }
                }
            }));
        }
        CobblemonNetwork.INSTANCE.sendPacket(this.playerEntity, new DialogueOpenedPacket(this, false));
    }

    public final void setPage(int index) {
        if (index == this.dialogueReference.getPages().size()) {
            this.close();
            return;
        }
        if (index < 0 || index > this.dialogueReference.getPages().size()) {
            Cobblemon.INSTANCE.getLOGGER().error("Dialogue requested page " + index + " but it doesn't exist");
            return;
        }
        this.setPage(this.dialogueReference.getPages().get(index));
    }

    @NotNull
    public final MoStruct toMoLangStruct() {
        Pair[] pairArray = new Pair[]{TuplesKt.to((Object)"current_page", arg_0 -> ActiveDialogue.toMoLangStruct$lambda$3(this, arg_0)), TuplesKt.to((Object)"current_page_number", arg_0 -> ActiveDialogue.toMoLangStruct$lambda$4(this, arg_0)), TuplesKt.to((Object)"next_page", arg_0 -> ActiveDialogue.toMoLangStruct$lambda$5(this, arg_0)), TuplesKt.to((Object)"set_page", arg_0 -> ActiveDialogue.toMoLangStruct$lambda$6(this, arg_0)), TuplesKt.to((Object)"close", arg_0 -> ActiveDialogue.toMoLangStruct$lambda$7(this, arg_0)), TuplesKt.to((Object)"input", arg_0 -> ActiveDialogue.toMoLangStruct$lambda$8(this, arg_0))};
        return MoLangFunctions.INSTANCE.addStandardFunctions(new QueryStruct(MapsKt.hashMapOf((Pair[])pairArray)));
    }

    public final void close() {
        DialogueManager.INSTANCE.stopDialogue(this.playerEntity);
    }

    public final void escape() {
        DialogueAction dialogueAction = this.currentPage.getEscapeAction();
        if (dialogueAction == null) {
            dialogueAction = this.dialogueReference.getEscapeAction();
        }
        DialogueAction action2 = dialogueAction;
        action2.invoke(this, null);
    }

    private static final Object _init_$lambda$0(ActiveDialogue this$0, MoParams moParams) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)moParams, (String)"<anonymous parameter 0>");
        return this$0.toMoLangStruct();
    }

    private static final Object _init_$lambda$1(ActiveDialogue this$0, MoParams moParams) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)moParams, (String)"<anonymous parameter 0>");
        return this$0.playerStruct;
    }

    private static final Object toMoLangStruct$lambda$3(ActiveDialogue this$0, MoParams moParams) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        return this$0.currentPage.toMoLangStruct(this$0);
    }

    private static final Object toMoLangStruct$lambda$4(ActiveDialogue this$0, MoParams moParams) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        return new DoubleValue(this$0.getCurrentPageIndex());
    }

    private static final Unit toMoLangStruct$lambda$5(ActiveDialogue this$0, MoParams moParams) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        this$0.incrementPage();
        return Unit.INSTANCE;
    }

    private static final Unit toMoLangStruct$lambda$6(ActiveDialogue this$0, MoParams args) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Object t = args.get(0);
        Intrinsics.checkNotNullExpressionValue(t, (String)"args[0]");
        this$0.setPage((MoValue)t);
        return Unit.INSTANCE;
    }

    private static final Unit toMoLangStruct$lambda$7(ActiveDialogue this$0, MoParams moParams) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        this$0.close();
        return Unit.INSTANCE;
    }

    private static final Unit toMoLangStruct$lambda$8(ActiveDialogue this$0, MoParams params) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        ActiveInput activeInput = this$0.activeInput;
        String string = params.getParams().size() > 0 ? params.get(0).asString() : "";
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"if (params.params.size >\u2026ue>(0).asString() else \"\"");
        activeInput.handle(string);
        return Unit.INSTANCE;
    }
}

