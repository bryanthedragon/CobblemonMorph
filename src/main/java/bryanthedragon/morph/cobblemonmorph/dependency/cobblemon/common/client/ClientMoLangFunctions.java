/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.StringValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0011\u0010\u0003\u001a\u00020\u0002*\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004RK\u0010\n\u001a6\u0012\u0004\u0012\u00020\u0006\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00070\u0005j\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0007`\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/client/ClientMoLangFunctions;", "", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "setupClient", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;)Lcom/bedrockk/molang/runtime/MoLangRuntime;", "Ljava/util/HashMap;", "", "Ljava/util/function/Function;", "Lcom/bedrockk/molang/runtime/MoParams;", "Lkotlin/collections/HashMap;", "clientFunctions", "Ljava/util/HashMap;", "getClientFunctions", "()Ljava/util/HashMap;", "<init>", "()V", "common"})
public final class ClientMoLangFunctions {
    @NotNull
    public static final ClientMoLangFunctions INSTANCE = new ClientMoLangFunctions();
    @NotNull
    private static final HashMap<String, Function<MoParams, Object>> clientFunctions;

    private ClientMoLangFunctions() {
    }

    @NotNull
    public final HashMap<String, Function<MoParams, Object>> getClientFunctions() {
        return clientFunctions;
    }

    @NotNull
    public final MoLangRuntime setupClient(@NotNull MoLangRuntime $this$setupClient) {
        Intrinsics.checkNotNullParameter((Object)$this$setupClient, (String)"<this>");
        MoLangEnvironment moLangEnvironment = $this$setupClient.getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"environment");
        MoLangFunctions.INSTANCE.addFunctions(MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null), (Map<String, ? extends Function<MoParams, Object>>)clientFunctions);
        return $this$setupClient;
    }

    private static final Unit clientFunctions$lambda$0(MoParams params) {
        Intrinsics.checkNotNullParameter((Object)params, (String)"params");
        if (!(params.get(0) instanceof StringValue)) {
            return Unit.INSTANCE;
        }
        String string = params.getString(0);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"params.getString(0)");
        SoundEvent soundEvent = SoundEvent.m_262824_((ResourceLocation)ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(string, null, 1, null));
        if (soundEvent != null) {
            float pitch = params.contains(2) ? (float)params.getDouble(2) : 1.0f;
            Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)soundEvent, (float)pitch));
        }
        return Unit.INSTANCE;
    }

    private static final Object clientFunctions$lambda$1(MoParams params) {
        Intrinsics.checkNotNullParameter((Object)params, (String)"params");
        ClientLevel clientLevel = Minecraft.m_91087_().f_91073_;
        long time = (clientLevel != null ? clientLevel.m_46468_() : 0L) % (long)24000;
        int min2 = params.getInt(0);
        int max2 = params.getInt(1);
        return (long)min2 <= time ? time <= (long)max2 : false;
    }

    private static final Unit clientFunctions$lambda$2(MoParams params) {
        Unit unit;
        Intrinsics.checkNotNullParameter((Object)params, (String)"params");
        LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
        if (localPlayer != null) {
            String string = params.getString(0);
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"params.getString(0)");
            localPlayer.m_213846_((Component)TextKt.text(string));
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            // empty if block
        }
        return Unit.INSTANCE;
    }

    static {
        Pair[] pairArray = new Pair[]{TuplesKt.to((Object)"sound", ClientMoLangFunctions::clientFunctions$lambda$0), TuplesKt.to((Object)"is_time", ClientMoLangFunctions::clientFunctions$lambda$1), TuplesKt.to((Object)"say", ClientMoLangFunctions::clientFunctions$lambda$2)};
        clientFunctions = MapsKt.hashMapOf((Pair[])pairArray);
    }
}

