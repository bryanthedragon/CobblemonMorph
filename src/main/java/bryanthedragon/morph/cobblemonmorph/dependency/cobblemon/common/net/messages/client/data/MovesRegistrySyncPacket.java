/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategories;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.MoveTarget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.DataRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0005\u0018\u0000 \u00182\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0018B\u0015\u0012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00020\u0014\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001f\u0010\t\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u001d\u0010\r\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00020\u000bH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u000f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/data/MovesRegistrySyncPacket;", "Lcom/cobblemon/mod/common/net/messages/client/data/DataRegistrySyncPacket;", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "decodeEntry", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "entry", "", "encodeEntry", "(Lnet/minecraft/network/FriendlyByteBuf;Lcom/cobblemon/mod/common/api/moves/MoveTemplate;)V", "", "entries", "synchronizeDecoded", "(Ljava/util/Collection;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "", "moves", "<init>", "(Ljava/util/List;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nMovesRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MovesRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/MovesRegistrySyncPacket\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,64:1\n13579#2,2:65\n37#3,2:67\n*S KotlinDebug\n*F\n+ 1 MovesRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/MovesRegistrySyncPacket\n*L\n35#1:65,2\n53#1:67,2\n*E\n"})
public final class MovesRegistrySyncPacket
extends DataRegistrySyncPacket<MoveTemplate, MovesRegistrySyncPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("moves_sync");

    public MovesRegistrySyncPacket(@NotNull List<? extends MoveTemplate> moves) {
        Intrinsics.checkNotNullParameter(moves, (String)"moves");
        super((Collection)moves);
        this.id = ID;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void encodeEntry(@NotNull FriendlyByteBuf buffer, @NotNull MoveTemplate entry) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Intrinsics.checkNotNullParameter((Object)entry, (String)"entry");
        buffer.m_130070_(entry.getName());
        buffer.writeInt(entry.getNum());
        buffer.m_130070_(entry.getElementalType().getName());
        buffer.m_130070_(entry.getDamageCategory().getName());
        buffer.writeDouble(entry.getPower());
        buffer.m_130068_((Enum)entry.getTarget());
        buffer.writeDouble(entry.getAccuracy());
        buffer.writeInt(entry.getPp());
        buffer.writeInt(entry.getPriority());
        buffer.writeDouble(entry.getCritRatio());
        buffer.m_130130_(entry.getEffectChances().length);
        Double[] $this$forEach$iv = entry.getEffectChances();
        boolean $i$f$forEach = false;
        for (Double element$iv : $this$forEach$iv) {
            double chance = ((Number)element$iv).doubleValue();
            boolean bl = false;
            buffer.writeDouble(chance);
        }
    }

    @Override
    @NotNull
    public MoveTemplate decodeEntry(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        String name = buffer.m_130277_();
        int num = buffer.readInt();
        String string = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
        ElementalType type = ElementalTypes.INSTANCE.getOrException(string);
        String string2 = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"buffer.readString()");
        DamageCategory damageCategory = DamageCategories.INSTANCE.getOrException(string2);
        double power = buffer.readDouble();
        MoveTarget target = (MoveTarget)buffer.m_130066_(MoveTarget.class);
        double accuracy = buffer.readDouble();
        int pp = buffer.readInt();
        int priority = buffer.readInt();
        double critRatio = buffer.readDouble();
        ArrayList effectChances = new ArrayList();
        int n = buffer.m_130242_();
        int n2 = 0;
        while (n2 < n) {
            int it = n2++;
            boolean bl = false;
            ((Collection)effectChances).add(buffer.readDouble());
        }
        Intrinsics.checkNotNullExpressionValue((Object)name, (String)"name");
        Intrinsics.checkNotNullExpressionValue((Object)((Object)target), (String)"target");
        Collection $this$toTypedArray$iv = effectChances;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        return new MoveTemplate(name, num, type, damageCategory, power, target, accuracy, pp, priority, critRatio, thisCollection$iv.toArray(new Double[0]), null);
    }

    @Override
    public void synchronizeDecoded(@NotNull Collection<? extends MoveTemplate> entries) {
        Intrinsics.checkNotNullParameter(entries, (String)"entries");
        Moves.INSTANCE.receiveSyncPacket$common(entries);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/data/MovesRegistrySyncPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/data/MovesRegistrySyncPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/data/MovesRegistrySyncPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nMovesRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MovesRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/MovesRegistrySyncPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,64:1\n1#2:65\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final MovesRegistrySyncPacket decode(@NotNull FriendlyByteBuf buffer) {
            MovesRegistrySyncPacket movesRegistrySyncPacket;
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            MovesRegistrySyncPacket $this$decode_u24lambda_u240 = movesRegistrySyncPacket = new MovesRegistrySyncPacket(CollectionsKt.emptyList());
            boolean bl = false;
            $this$decode_u24lambda_u240.decodeBuffer$common(buffer);
            return movesRegistrySyncPacket;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

