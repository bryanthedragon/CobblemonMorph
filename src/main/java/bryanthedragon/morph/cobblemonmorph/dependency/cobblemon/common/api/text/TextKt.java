/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.Regex
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.HoverEvent
 *  net.minecraft.network.chat.HoverEvent$Action
 *  net.minecraft.network.chat.HoverEvent$EntityTooltipInfo
 *  net.minecraft.network.chat.HoverEvent$ItemStackInfo
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.Text;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u001c\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a8\u0010\n\u001a\u00020\t2\u0006\u0010\u0001\u001a\u00020\u00002!\u0010\b\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00070\u0002\u00a2\u0006\u0004\b\n\u0010\u000b\u001a:\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\r\u001a\u00020\f2!\u0010\b\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00070\u0002\u00a2\u0006\u0004\b\n\u0010\u000e\u001a\u0015\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u000f\u00a2\u0006\u0004\b\u0012\u0010\u0013\u001a\u0015\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b\u0012\u0010\u0016\u001a\u0015\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u0017\u00a2\u0006\u0004\b\u0012\u0010\u0019\u001a\u0015\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u001a\u00a2\u0006\u0004\b\u0012\u0010\u001b\u001a!\u0010\u0010\u001a\u00020\u001f2\u0012\u0010\u001e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0\u001c\"\u00020\u001d\u00a2\u0006\u0004\b\u0010\u0010 \u001a\u0019\u0010\"\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010!\u001a\u00020\u000f\u00a2\u0006\u0004\b\"\u0010#\u001a\u0019\u0010\"\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010$\u001a\u00020\u001a\u00a2\u0006\u0004\b\"\u0010%\u001a\u0011\u0010&\u001a\u00020\u001f*\u00020\u000f\u00a2\u0006\u0004\b&\u0010'\u001a\u0011\u0010&\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\b&\u0010(\u001a\u0011\u0010)\u001a\u00020\u001f*\u00020\u000f\u00a2\u0006\u0004\b)\u0010'\u001a\u0011\u0010)\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\b)\u0010(\u001a\u0011\u0010*\u001a\u00020\u001f*\u00020\u000f\u00a2\u0006\u0004\b*\u0010'\u001a\u0011\u0010*\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\b*\u0010(\u001a\u0011\u0010+\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\b+\u0010(\u001a\u0011\u0010,\u001a\u00020\u001f*\u00020\u000f\u00a2\u0006\u0004\b,\u0010'\u001a\u0011\u0010,\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\b,\u0010(\u001a\u0011\u0010-\u001a\u00020\u001f*\u00020\u000f\u00a2\u0006\u0004\b-\u0010'\u001a\u0011\u0010-\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\b-\u0010(\u001a\u0011\u0010.\u001a\u00020\u001f*\u00020\u000f\u00a2\u0006\u0004\b.\u0010'\u001a\u0011\u0010.\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\b.\u0010(\u001a\u0011\u0010/\u001a\u00020\u001f*\u00020\u000f\u00a2\u0006\u0004\b/\u0010'\u001a\u0011\u0010/\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\b/\u0010(\u001a\u0011\u00100\u001a\u00020\u001f*\u00020\u000f\u00a2\u0006\u0004\b0\u0010'\u001a\u0011\u00100\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\b0\u0010(\u001a\u0011\u00101\u001a\u00020\u001f*\u00020\u000f\u00a2\u0006\u0004\b1\u0010'\u001a\u0011\u00101\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\b1\u0010(\u001a\u0019\u00104\u001a\u00020\u001f*\u00020\u001f2\u0006\u00103\u001a\u000202\u00a2\u0006\u0004\b4\u00105\u001a\u0011\u00106\u001a\u00020\u001f*\u00020\u000f\u00a2\u0006\u0004\b6\u0010'\u001a\u0011\u00106\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\b6\u0010(\u001a\u0011\u00107\u001a\u00020\u001f*\u00020\u000f\u00a2\u0006\u0004\b7\u0010'\u001a\u0011\u00107\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\b7\u0010(\u001a\u0011\u00108\u001a\u00020\u001f*\u00020\u000f\u00a2\u0006\u0004\b8\u0010'\u001a\u0011\u00108\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\b8\u0010(\u001a\u0011\u00109\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\b9\u0010(\u001a\u0011\u0010:\u001a\u00020\u001f*\u00020\u000f\u00a2\u0006\u0004\b:\u0010'\u001a\u0011\u0010:\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\b:\u0010(\u001a\u0011\u0010;\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\b;\u0010(\u001a<\u0010<\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010\u0001\u001a\u00020\u00002!\u0010\b\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00070\u0002\u00a2\u0006\u0004\b<\u0010=\u001a>\u0010<\u001a\u00020\u001f*\u00020\u001f2\b\b\u0002\u0010\r\u001a\u00020\f2!\u0010\b\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00070\u0002\u00a2\u0006\u0004\b<\u0010>\u001a\u0019\u0010?\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010!\u001a\u00020\u000f\u00a2\u0006\u0004\b?\u0010#\u001a\u0019\u0010?\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010\u0010\u001a\u00020\u001f\u00a2\u0006\u0004\b?\u0010@\u001a\u0019\u0010?\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010\u0010\u001a\u00020\u001a\u00a2\u0006\u0004\b?\u0010%\u001a\u001c\u0010A\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010!\u001a\u00020\u000fH\u0086\u0002\u00a2\u0006\u0004\bA\u0010#\u001a\u001c\u0010A\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010B\u001a\u00020\u001aH\u0086\u0002\u00a2\u0006\u0004\bA\u0010%\u001a\u0011\u0010C\u001a\u00020\u001f*\u00020\u000f\u00a2\u0006\u0004\bC\u0010'\u001a\u0011\u0010C\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\bC\u0010(\u001a\u0011\u0010D\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\bD\u0010(\u001a\u0011\u0010E\u001a\u00020\u000f*\u00020\u000f\u00a2\u0006\u0004\bE\u0010F\u001a\u0019\u0010H\u001a\u00020\u001f*\u00020\u001f2\u0006\u0010G\u001a\u00020\u000f\u00a2\u0006\u0004\bH\u0010#\u001a!\u0010K\u001a\u00020\u001f*\b\u0012\u0004\u0012\u00020\u001f0I2\b\b\u0002\u0010J\u001a\u00020\u001f\u00a2\u0006\u0004\bK\u0010L\u001a\u0011\u0010\u0010\u001a\u00020\u001f*\u00020\u000f\u00a2\u0006\u0004\b\u0010\u0010'\u001a\u0011\u0010M\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\bM\u0010(\u001a\u0011\u0010N\u001a\u00020\u001f*\u00020\u000f\u00a2\u0006\u0004\bN\u0010'\u001a\u0011\u0010N\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\bN\u0010(\u001a\u0011\u0010O\u001a\u00020\u001f*\u00020\u000f\u00a2\u0006\u0004\bO\u0010'\u001a\u0011\u0010O\u001a\u00020\u001f*\u00020\u001f\u00a2\u0006\u0004\bO\u0010(\"\u0017\u0010Q\u001a\u00020P8\u0006\u00a2\u0006\f\n\u0004\bQ\u0010R\u001a\u0004\bS\u0010T\"\u0017\u0010U\u001a\u00020P8\u0006\u00a2\u0006\f\n\u0004\bU\u0010R\u001a\u0004\bV\u0010T\"\u0017\u0010W\u001a\u00020P8\u0006\u00a2\u0006\f\n\u0004\bW\u0010R\u001a\u0004\bX\u0010T\"\u0017\u0010Y\u001a\u00020P8\u0006\u00a2\u0006\f\n\u0004\bY\u0010R\u001a\u0004\bZ\u0010T\"\u0017\u0010[\u001a\u00020P8\u0006\u00a2\u0006\f\n\u0004\b[\u0010R\u001a\u0004\b\\\u0010T\"i\u0010`\u001aT\u0012\u0004\u0012\u00020^\u0012\u001f\u0012\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00070\u00020]j)\u0012\u0004\u0012\u00020^\u0012\u001f\u0012\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u00070\u0002`_8\u0006\u00a2\u0006\f\n\u0004\b`\u0010a\u001a\u0004\bb\u0010c\u00a8\u0006d"}, d2={"Ljava/util/concurrent/atomic/AtomicBoolean;", "consumed", "Lkotlin/Function1;", "Lnet/minecraft/server/level/ServerPlayer;", "Lkotlin/ParameterName;", "name", "p", "", "action", "Lnet/minecraft/network/chat/ClickEvent;", "click", "(Ljava/util/concurrent/atomic/AtomicBoolean;Lkotlin/jvm/functions/Function1;)Lnet/minecraft/network/chat/ClickEvent;", "", "onlyOnce", "(ZLkotlin/jvm/functions/Function1;)Lnet/minecraft/network/chat/ClickEvent;", "", "text", "Lnet/minecraft/network/chat/HoverEvent;", "hover", "(Ljava/lang/String;)Lnet/minecraft/network/chat/HoverEvent;", "Lnet/minecraft/world/entity/LivingEntity;", "entity", "(Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/network/chat/HoverEvent;", "Lnet/minecraft/world/item/ItemStack;", "item", "(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/network/chat/HoverEvent;", "Lnet/minecraft/network/chat/Component;", "(Lnet/minecraft/network/chat/Component;)Lnet/minecraft/network/chat/HoverEvent;", "", "", "components", "Lnet/minecraft/network/chat/MutableComponent;", "([Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;", "string", "add", "(Lnet/minecraft/network/chat/MutableComponent;Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;", "other", "(Lnet/minecraft/network/chat/MutableComponent;Lnet/minecraft/network/chat/Component;)Lnet/minecraft/network/chat/MutableComponent;", "aqua", "(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;", "(Lnet/minecraft/network/chat/MutableComponent;)Lnet/minecraft/network/chat/MutableComponent;", "black", "blue", "bold", "darkAqua", "darkBlue", "darkGray", "darkGreen", "darkPurple", "darkRed", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "font", "(Lnet/minecraft/network/chat/MutableComponent;Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/network/chat/MutableComponent;", "gold", "gray", "green", "italicise", "lightPurple", "obfuscate", "onClick", "(Lnet/minecraft/network/chat/MutableComponent;Ljava/util/concurrent/atomic/AtomicBoolean;Lkotlin/jvm/functions/Function1;)Lnet/minecraft/network/chat/MutableComponent;", "(Lnet/minecraft/network/chat/MutableComponent;ZLkotlin/jvm/functions/Function1;)Lnet/minecraft/network/chat/MutableComponent;", "onHover", "(Lnet/minecraft/network/chat/MutableComponent;Lnet/minecraft/network/chat/MutableComponent;)Lnet/minecraft/network/chat/MutableComponent;", "plus", "component", "red", "strikethrough", "stripCodes", "(Ljava/lang/String;)Ljava/lang/String;", "command", "suggest", "", "separator", "sum", "(Ljava/lang/Iterable;Lnet/minecraft/network/chat/MutableComponent;)Lnet/minecraft/network/chat/MutableComponent;", "underline", "white", "yellow", "Ljava/lang/Object;", "BOLD", "Ljava/lang/Object;", "getBOLD", "()Ljava/lang/Object;", "ITALIC", "getITALIC", "OBFUSCATED", "getOBFUSCATED", "RESET", "getRESET", "UNDERLINED", "getUNDERLINED", "Ljava/util/HashMap;", "Ljava/util/UUID;", "Lkotlin/collections/HashMap;", "textClickHandlers", "Ljava/util/HashMap;", "getTextClickHandlers", "()Ljava/util/HashMap;", "common"})
@SourceDebugExtension(value={"SMAP\nText.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Text.kt\ncom/cobblemon/mod/common/api/text/TextKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,183:1\n1#2:184\n2661#3,7:185\n*S KotlinDebug\n*F\n+ 1 Text.kt\ncom/cobblemon/mod/common/api/text/TextKt\n*L\n183#1:185,7\n*E\n"})
public final class TextKt {
    @NotNull
    private static final HashMap<UUID, Function1<ServerPlayer, Unit>> textClickHandlers = new HashMap();
    @NotNull
    private static final Object BOLD = new Object();
    @NotNull
    private static final Object ITALIC = new Object();
    @NotNull
    private static final Object UNDERLINED = new Object();
    @NotNull
    private static final Object OBFUSCATED = new Object();
    @NotNull
    private static final Object RESET = new Object();

    @NotNull
    public static final MutableComponent text(Object ... components) {
        Intrinsics.checkNotNullParameter((Object)components, (String)"components");
        return new Text().parse(Arrays.copyOf(components, components.length));
    }

    @NotNull
    public static final HashMap<UUID, Function1<ServerPlayer, Unit>> getTextClickHandlers() {
        return textClickHandlers;
    }

    @NotNull
    public static final ClickEvent click(@NotNull AtomicBoolean consumed, @NotNull Function1<? super ServerPlayer, Unit> action2) {
        Intrinsics.checkNotNullParameter((Object)consumed, (String)"consumed");
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        UUID uuid2 = UUID.randomUUID();
        Map map = textClickHandlers;
        Intrinsics.checkNotNullExpressionValue((Object)uuid2, (String)"uuid");
        map.put(uuid2, new Function1<ServerPlayer, Unit>(consumed, action2, uuid2){
            final /* synthetic */ AtomicBoolean $consumed;
            final /* synthetic */ Function1<ServerPlayer, Unit> $action;
            final /* synthetic */ UUID $uuid;
            {
                this.$consumed = $consumed;
                this.$action = $action;
                this.$uuid = $uuid;
                super(1);
            }

            public final void invoke(@NotNull ServerPlayer it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                if (!this.$consumed.get()) {
                    this.$action.invoke((Object)it);
                    this.$consumed.set(true);
                }
                TextKt.getTextClickHandlers().remove(this.$uuid);
            }
        });
        return new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cobblemonclicktext " + uuid2);
    }

    @NotNull
    public static final ClickEvent click(boolean onlyOnce, @NotNull Function1<? super ServerPlayer, Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        UUID uuid2 = UUID.randomUUID();
        Map map = textClickHandlers;
        Intrinsics.checkNotNullExpressionValue((Object)uuid2, (String)"uuid");
        map.put(uuid2, onlyOnce ? (Function1)new Function1<ServerPlayer, Unit>(uuid2, action2){
            final /* synthetic */ UUID $uuid;
            final /* synthetic */ Function1<ServerPlayer, Unit> $action;
            {
                this.$uuid = $uuid;
                this.$action = $action;
                super(1);
            }

            public final void invoke(@NotNull ServerPlayer it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                TextKt.getTextClickHandlers().remove(this.$uuid);
                this.$action.invoke((Object)it);
            }
        } : (Function1)new Function1<ServerPlayer, Unit>(action2){
            final /* synthetic */ Function1<ServerPlayer, Unit> $action;
            {
                this.$action = $action;
                super(1);
            }

            public final void invoke(@NotNull ServerPlayer it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                this.$action.invoke((Object)it);
            }
        });
        return new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cobblemonclicktext " + uuid2);
    }

    public static /* synthetic */ ClickEvent click$default(boolean bl, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        return TextKt.click(bl, (Function1<? super ServerPlayer, Unit>)function1);
    }

    @NotNull
    public static final HoverEvent hover(@NotNull Component text) {
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        return new HoverEvent(HoverEvent.Action.f_130831_, (Object)text);
    }

    @NotNull
    public static final HoverEvent hover(@NotNull String text) {
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        Component component = Component.m_130674_((String)text);
        Intrinsics.checkNotNullExpressionValue((Object)component, (String)"of(text)");
        return TextKt.hover(component);
    }

    @NotNull
    public static final HoverEvent hover(@NotNull ItemStack item) {
        Intrinsics.checkNotNullParameter((Object)item, (String)"item");
        return new HoverEvent(HoverEvent.Action.f_130832_, (Object)new HoverEvent.ItemStackInfo(item));
    }

    @NotNull
    public static final HoverEvent hover(@NotNull LivingEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        return new HoverEvent(HoverEvent.Action.f_130833_, (Object)new HoverEvent.EntityTooltipInfo(entity2.m_6095_(), entity2.m_20148_(), entity2.m_5446_()));
    }

    @NotNull
    public static final Object getBOLD() {
        return BOLD;
    }

    @NotNull
    public static final Object getITALIC() {
        return ITALIC;
    }

    @NotNull
    public static final Object getUNDERLINED() {
        return UNDERLINED;
    }

    @NotNull
    public static final Object getOBFUSCATED() {
        return OBFUSCATED;
    }

    @NotNull
    public static final Object getRESET() {
        return RESET;
    }

    @NotNull
    public static final MutableComponent red(@NotNull String $this$red) {
        Intrinsics.checkNotNullParameter((Object)$this$red, (String)"<this>");
        Object[] objectArray = new Object[]{ChatFormatting.RED, $this$red};
        return TextKt.text(objectArray);
    }

    @NotNull
    public static final MutableComponent black(@NotNull String $this$black) {
        Intrinsics.checkNotNullParameter((Object)$this$black, (String)"<this>");
        Object[] objectArray = new Object[]{ChatFormatting.BLACK, $this$black};
        return TextKt.text(objectArray);
    }

    @NotNull
    public static final MutableComponent darkBlue(@NotNull String $this$darkBlue) {
        Intrinsics.checkNotNullParameter((Object)$this$darkBlue, (String)"<this>");
        Object[] objectArray = new Object[]{ChatFormatting.DARK_BLUE, $this$darkBlue};
        return TextKt.text(objectArray);
    }

    @NotNull
    public static final MutableComponent darkGreen(@NotNull String $this$darkGreen) {
        Intrinsics.checkNotNullParameter((Object)$this$darkGreen, (String)"<this>");
        Object[] objectArray = new Object[]{ChatFormatting.DARK_GREEN, $this$darkGreen};
        return TextKt.text(objectArray);
    }

    @NotNull
    public static final MutableComponent darkAqua(@NotNull String $this$darkAqua) {
        Intrinsics.checkNotNullParameter((Object)$this$darkAqua, (String)"<this>");
        Object[] objectArray = new Object[]{ChatFormatting.DARK_AQUA, $this$darkAqua};
        return TextKt.text(objectArray);
    }

    @NotNull
    public static final MutableComponent darkRed(@NotNull String $this$darkRed) {
        Intrinsics.checkNotNullParameter((Object)$this$darkRed, (String)"<this>");
        Object[] objectArray = new Object[]{ChatFormatting.DARK_RED, $this$darkRed};
        return TextKt.text(objectArray);
    }

    @NotNull
    public static final MutableComponent darkPurple(@NotNull String $this$darkPurple) {
        Intrinsics.checkNotNullParameter((Object)$this$darkPurple, (String)"<this>");
        Object[] objectArray = new Object[]{ChatFormatting.DARK_PURPLE, $this$darkPurple};
        return TextKt.text(objectArray);
    }

    @NotNull
    public static final MutableComponent gold(@NotNull String $this$gold) {
        Intrinsics.checkNotNullParameter((Object)$this$gold, (String)"<this>");
        Object[] objectArray = new Object[]{ChatFormatting.GOLD, $this$gold};
        return TextKt.text(objectArray);
    }

    @NotNull
    public static final MutableComponent gray(@NotNull String $this$gray) {
        Intrinsics.checkNotNullParameter((Object)$this$gray, (String)"<this>");
        Object[] objectArray = new Object[]{ChatFormatting.GRAY, $this$gray};
        return TextKt.text(objectArray);
    }

    @NotNull
    public static final MutableComponent darkGray(@NotNull String $this$darkGray) {
        Intrinsics.checkNotNullParameter((Object)$this$darkGray, (String)"<this>");
        Object[] objectArray = new Object[]{ChatFormatting.DARK_GRAY, $this$darkGray};
        return TextKt.text(objectArray);
    }

    @NotNull
    public static final MutableComponent blue(@NotNull String $this$blue) {
        Intrinsics.checkNotNullParameter((Object)$this$blue, (String)"<this>");
        Object[] objectArray = new Object[]{ChatFormatting.BLUE, $this$blue};
        return TextKt.text(objectArray);
    }

    @NotNull
    public static final MutableComponent green(@NotNull String $this$green) {
        Intrinsics.checkNotNullParameter((Object)$this$green, (String)"<this>");
        Object[] objectArray = new Object[]{ChatFormatting.GREEN, $this$green};
        return TextKt.text(objectArray);
    }

    @NotNull
    public static final MutableComponent aqua(@NotNull String $this$aqua) {
        Intrinsics.checkNotNullParameter((Object)$this$aqua, (String)"<this>");
        Object[] objectArray = new Object[]{ChatFormatting.AQUA, $this$aqua};
        return TextKt.text(objectArray);
    }

    @NotNull
    public static final MutableComponent lightPurple(@NotNull String $this$lightPurple) {
        Intrinsics.checkNotNullParameter((Object)$this$lightPurple, (String)"<this>");
        Object[] objectArray = new Object[]{ChatFormatting.LIGHT_PURPLE, $this$lightPurple};
        return TextKt.text(objectArray);
    }

    @NotNull
    public static final MutableComponent yellow(@NotNull String $this$yellow) {
        Intrinsics.checkNotNullParameter((Object)$this$yellow, (String)"<this>");
        Object[] objectArray = new Object[]{ChatFormatting.YELLOW, $this$yellow};
        return TextKt.text(objectArray);
    }

    @NotNull
    public static final MutableComponent white(@NotNull String $this$white) {
        Intrinsics.checkNotNullParameter((Object)$this$white, (String)"<this>");
        Object[] objectArray = new Object[]{ChatFormatting.WHITE, $this$white};
        return TextKt.text(objectArray);
    }

    @NotNull
    public static final MutableComponent red(@NotNull MutableComponent $this$red) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$red, (String)"<this>");
        MutableComponent it = mutableComponent = $this$red;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131140_(ChatFormatting.RED));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent black(@NotNull MutableComponent $this$black) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$black, (String)"<this>");
        MutableComponent it = mutableComponent = $this$black;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131140_(ChatFormatting.BLACK));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent darkBlue(@NotNull MutableComponent $this$darkBlue) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$darkBlue, (String)"<this>");
        MutableComponent it = mutableComponent = $this$darkBlue;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131140_(ChatFormatting.DARK_BLUE));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent darkGreen(@NotNull MutableComponent $this$darkGreen) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$darkGreen, (String)"<this>");
        MutableComponent it = mutableComponent = $this$darkGreen;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131140_(ChatFormatting.DARK_GREEN));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent darkAqua(@NotNull MutableComponent $this$darkAqua) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$darkAqua, (String)"<this>");
        MutableComponent it = mutableComponent = $this$darkAqua;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131140_(ChatFormatting.DARK_AQUA));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent darkRed(@NotNull MutableComponent $this$darkRed) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$darkRed, (String)"<this>");
        MutableComponent it = mutableComponent = $this$darkRed;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131140_(ChatFormatting.DARK_RED));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent darkPurple(@NotNull MutableComponent $this$darkPurple) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$darkPurple, (String)"<this>");
        MutableComponent it = mutableComponent = $this$darkPurple;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131140_(ChatFormatting.DARK_PURPLE));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent gold(@NotNull MutableComponent $this$gold) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$gold, (String)"<this>");
        MutableComponent it = mutableComponent = $this$gold;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131140_(ChatFormatting.GOLD));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent gray(@NotNull MutableComponent $this$gray) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$gray, (String)"<this>");
        MutableComponent it = mutableComponent = $this$gray;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131140_(ChatFormatting.GRAY));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent darkGray(@NotNull MutableComponent $this$darkGray) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$darkGray, (String)"<this>");
        MutableComponent it = mutableComponent = $this$darkGray;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131140_(ChatFormatting.DARK_GRAY));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent blue(@NotNull MutableComponent $this$blue) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$blue, (String)"<this>");
        MutableComponent it = mutableComponent = $this$blue;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131140_(ChatFormatting.BLUE));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent green(@NotNull MutableComponent $this$green) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$green, (String)"<this>");
        MutableComponent it = mutableComponent = $this$green;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131140_(ChatFormatting.GREEN));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent aqua(@NotNull MutableComponent $this$aqua) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$aqua, (String)"<this>");
        MutableComponent it = mutableComponent = $this$aqua;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131140_(ChatFormatting.AQUA));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent lightPurple(@NotNull MutableComponent $this$lightPurple) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$lightPurple, (String)"<this>");
        MutableComponent it = mutableComponent = $this$lightPurple;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131140_(ChatFormatting.LIGHT_PURPLE));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent yellow(@NotNull MutableComponent $this$yellow) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$yellow, (String)"<this>");
        MutableComponent it = mutableComponent = $this$yellow;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131140_(ChatFormatting.YELLOW));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent white(@NotNull MutableComponent $this$white) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$white, (String)"<this>");
        MutableComponent it = mutableComponent = $this$white;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131140_(ChatFormatting.WHITE));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent font(@NotNull MutableComponent $this$font, @NotNull ResourceLocation identifier) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$font, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        MutableComponent it = mutableComponent = $this$font;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131150_(identifier));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent text(@NotNull String $this$text) {
        Intrinsics.checkNotNullParameter((Object)$this$text, (String)"<this>");
        Object[] objectArray = new Object[]{$this$text};
        return TextKt.text(objectArray);
    }

    @NotNull
    public static final String stripCodes(@NotNull String $this$stripCodes) {
        Intrinsics.checkNotNullParameter((Object)$this$stripCodes, (String)"<this>");
        CharSequence charSequence = $this$stripCodes;
        Regex regex = new Regex("[&\u00a7][A-Ea-e0-9K-Ok-oRr]");
        String string = "";
        return regex.replace(charSequence, string);
    }

    @NotNull
    public static final MutableComponent onClick(@NotNull MutableComponent $this$onClick, @NotNull AtomicBoolean consumed, @NotNull Function1<? super ServerPlayer, Unit> action2) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$onClick, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)consumed, (String)"consumed");
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        MutableComponent it = mutableComponent = $this$onClick;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131142_(TextKt.click(consumed, action2)));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent onClick(@NotNull MutableComponent $this$onClick, boolean onlyOnce, @NotNull Function1<? super ServerPlayer, Unit> action2) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$onClick, (String)"<this>");
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        MutableComponent it = mutableComponent = $this$onClick;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131142_(TextKt.click(onlyOnce, action2)));
        return mutableComponent;
    }

    public static /* synthetic */ MutableComponent onClick$default(MutableComponent mutableComponent, boolean bl, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        return TextKt.onClick(mutableComponent, bl, (Function1<? super ServerPlayer, Unit>)function1);
    }

    @NotNull
    public static final MutableComponent onHover(@NotNull MutableComponent $this$onHover, @NotNull String string) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$onHover, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)string, (String)"string");
        MutableComponent it = mutableComponent = $this$onHover;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131144_(TextKt.hover(string)));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent onHover(@NotNull MutableComponent $this$onHover, @NotNull Component text) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$onHover, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        MutableComponent it = mutableComponent = $this$onHover;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131144_(TextKt.hover(text)));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent onHover(@NotNull MutableComponent $this$onHover, @NotNull MutableComponent text) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$onHover, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        MutableComponent it = mutableComponent = $this$onHover;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131144_(TextKt.hover((Component)text)));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent underline(@NotNull MutableComponent $this$underline) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$underline, (String)"<this>");
        MutableComponent it = mutableComponent = $this$underline;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131162_(Boolean.valueOf(true)));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent bold(@NotNull MutableComponent $this$bold) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$bold, (String)"<this>");
        MutableComponent it = mutableComponent = $this$bold;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131136_(Boolean.valueOf(true)));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent italicise(@NotNull MutableComponent $this$italicise) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$italicise, (String)"<this>");
        MutableComponent it = mutableComponent = $this$italicise;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131155_(Boolean.valueOf(true)));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent strikethrough(@NotNull MutableComponent $this$strikethrough) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$strikethrough, (String)"<this>");
        MutableComponent it = mutableComponent = $this$strikethrough;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_178522_(Boolean.valueOf(true)));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent obfuscate(@NotNull MutableComponent $this$obfuscate) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$obfuscate, (String)"<this>");
        MutableComponent it = mutableComponent = $this$obfuscate;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_178524_(Boolean.valueOf(true)));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent suggest(@NotNull MutableComponent $this$suggest, @NotNull String command) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)$this$suggest, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)command, (String)"command");
        MutableComponent it = mutableComponent = $this$suggest;
        boolean bl = false;
        it.m_6270_(it.m_7383_().m_131142_(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command)));
        return mutableComponent;
    }

    @NotNull
    public static final MutableComponent add(@NotNull MutableComponent $this$add, @NotNull Component other) {
        Intrinsics.checkNotNullParameter((Object)$this$add, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)other, (String)"other");
        $this$add.m_7220_(other);
        return $this$add;
    }

    @NotNull
    public static final MutableComponent add(@NotNull MutableComponent $this$add, @NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)$this$add, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)string, (String)"string");
        Object[] objectArray = new Object[]{string};
        TextKt.add($this$add, (Component)TextKt.text(objectArray));
        return $this$add;
    }

    @NotNull
    public static final MutableComponent plus(@NotNull MutableComponent $this$plus, @NotNull Component component) {
        Intrinsics.checkNotNullParameter((Object)$this$plus, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)component, (String)"component");
        return TextKt.add($this$plus, component);
    }

    @NotNull
    public static final MutableComponent plus(@NotNull MutableComponent $this$plus, @NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)$this$plus, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)string, (String)"string");
        return TextKt.add($this$plus, string);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final MutableComponent sum(@NotNull Iterable<? extends MutableComponent> $this$sum, @NotNull MutableComponent separator) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter($this$sum, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)separator, (String)"separator");
        if (CollectionsKt.any($this$sum)) {
            Iterable<? extends MutableComponent> $this$reduce$iv = $this$sum;
            boolean $i$f$reduce = false;
            Iterator<? extends MutableComponent> iterator$iv = $this$reduce$iv.iterator();
            if (!iterator$iv.hasNext()) {
                throw new UnsupportedOperationException("Empty collection can't be reduced.");
            }
            MutableComponent accumulator$iv = iterator$iv.next();
            while (iterator$iv.hasNext()) {
                void next;
                MutableComponent mutableComponent2 = iterator$iv.next();
                MutableComponent acc = accumulator$iv;
                boolean bl = false;
                accumulator$iv = TextKt.plus(TextKt.plus(acc, (Component)separator), (Component)next);
            }
            mutableComponent = accumulator$iv;
        } else {
            mutableComponent = TextKt.text("");
        }
        return mutableComponent;
    }

    public static /* synthetic */ MutableComponent sum$default(Iterable iterable, MutableComponent mutableComponent, int n, Object object) {
        if ((n & 1) != 0) {
            mutableComponent = TextKt.text(", ");
        }
        return TextKt.sum(iterable, mutableComponent);
    }
}

