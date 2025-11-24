/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.MutablePropertyReference1
 *  kotlin.jvm.internal.MutablePropertyReference1Impl
 *  kotlin.jvm.internal.Reflection
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.properties.Delegates
 *  kotlin.properties.ObservableProperty
 *  kotlin.properties.ReadWriteProperty
 *  kotlin.reflect.KProperty
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.toast;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.ObservableSubscription;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.toast.ToastPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.properties.Delegates;
import kotlin.properties.ObservableProperty;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000 S2\u00020\u0001:\u0001SB=\u0012\u0006\u0010L\u001a\u00020\u001a\u0012\u0006\u0010\"\u001a\u00020\u001a\u0012\u0006\u00100\u001a\u00020*\u0012\b\b\u0002\u0010)\u001a\u00020#\u0012\b\b\u0002\u0010?\u001a\u000209\u0012\b\b\u0002\u0010D\u001a\u00020\n\u00a2\u0006\u0004\bQ\u0010RJ!\u0010\u0006\u001a\u00020\u00052\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002\"\u00020\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\r\u0010\b\u001a\u00020\u0005\u00a2\u0006\u0004\b\b\u0010\tJ\u0015\u0010\f\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\f\u0010\rJ\u000f\u0010\u000e\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\u000e\u0010\tJ!\u0010\u000f\u001a\u00020\u00052\u0012\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002\"\u00020\u0003\u00a2\u0006\u0004\b\u000f\u0010\u0007J\r\u0010\u0010\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0010\u0010\tJ\u0017\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0011H\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001f\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0013H\u0002\u00a2\u0006\u0004\b\u0018\u0010\u0019R+\u0010\"\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a8F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R+\u0010)\u001a\u00020#2\u0006\u0010\u001b\u001a\u00020#8F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b$\u0010\u001d\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R+\u00100\u001a\u00020*2\u0006\u0010\u001b\u001a\u00020*8F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b+\u0010\u001d\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R$\u00104\u001a\u0012\u0012\u0004\u0012\u00020201j\b\u0012\u0004\u0012\u000202`38\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b4\u00105R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u0003068F\u00a2\u0006\u0006\u001a\u0004\b7\u00108R+\u0010?\u001a\u0002092\u0006\u0010\u001b\u001a\u0002098F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b:\u0010\u001d\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>R+\u0010D\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\n8F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b@\u0010\u001d\u001a\u0004\bA\u0010B\"\u0004\bC\u0010\rR\u001a\u0010G\u001a\b\u0012\u0004\u0012\u00020F0E8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bG\u0010HR+\u0010L\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a8F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\bI\u0010\u001d\u001a\u0004\bJ\u0010\u001f\"\u0004\bK\u0010!R\u001a\u0010M\u001a\u0002028\u0000X\u0080\u0004\u00a2\u0006\f\n\u0004\bM\u0010N\u001a\u0004\bO\u0010P\u00a8\u0006T"}, d2={"Lcom/cobblemon/mod/common/api/toast/Toast;", "", "", "Lnet/minecraft/server/level/ServerPlayer;", "listeners", "", "addListeners", "([Lnet/minecraft/server/level/ServerPlayer;)V", "expire", "()V", "", "ticks", "expireAfter", "(I)V", "launchUpdate", "removeListeners", "setNoProgress", "Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket$Behaviour;", "behaviour", "Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket;", "toPacket", "(Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket$Behaviour;)Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket;", "player", "packet", "updateFor", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket;)V", "Lnet/minecraft/network/chat/Component;", "<set-?>", "description$delegate", "Lkotlin/properties/ReadWriteProperty;", "getDescription", "()Lnet/minecraft/network/chat/Component;", "setDescription", "(Lnet/minecraft/network/chat/Component;)V", "description", "Lnet/minecraft/resources/ResourceLocation;", "frameTexture$delegate", "getFrameTexture", "()Lnet/minecraft/resources/ResourceLocation;", "setFrameTexture", "(Lnet/minecraft/resources/ResourceLocation;)V", "frameTexture", "Lnet/minecraft/world/item/ItemStack;", "icon$delegate", "getIcon", "()Lnet/minecraft/world/item/ItemStack;", "setIcon", "(Lnet/minecraft/world/item/ItemStack;)V", "icon", "Ljava/util/HashSet;", "Ljava/util/UUID;", "Lkotlin/collections/HashSet;", "listenerUuids", "Ljava/util/HashSet;", "", "getListeners", "()Ljava/util/Collection;", "", "progress$delegate", "getProgress", "()F", "setProgress", "(F)V", "progress", "progressColor$delegate", "getProgressColor", "()I", "setProgressColor", "progressColor", "Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent$Login;", "subscription", "Lcom/cobblemon/mod/common/api/reactive/ObservableSubscription;", "title$delegate", "getTitle", "setTitle", "title", "uuid", "Ljava/util/UUID;", "getUuid$common", "()Ljava/util/UUID;", "<init>", "(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/resources/ResourceLocation;FI)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nToast.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Toast.kt\ncom/cobblemon/mod/common/api/toast/Toast\n+ 2 Delegates.kt\nkotlin/properties/Delegates\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 6 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,206:1\n33#2,3:207\n33#2,3:210\n33#2,3:213\n33#2,3:216\n33#2,3:219\n33#2,3:222\n1603#3,9:225\n1855#3:234\n1856#3:236\n1612#3:237\n1855#3,2:244\n1#4:235\n13579#5,2:238\n13579#5,2:240\n37#6,2:242\n*S KotlinDebug\n*F\n+ 1 Toast.kt\ncom/cobblemon/mod/common/api/toast/Toast\n*L\n49#1:207,3\n54#1:210,3\n59#1:213,3\n64#1:216,3\n69#1:219,3\n74#1:222,3\n79#1:225,9\n79#1:234\n79#1:236\n79#1:237\n165#1:244,2\n79#1:235\n110#1:238,2\n127#1:240,2\n158#1:242,2\n*E\n"})
public final class Toast {
    @NotNull
    public static final Companion Companion;
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties;
    @NotNull
    private final ReadWriteProperty title$delegate;
    @NotNull
    private final ReadWriteProperty description$delegate;
    @NotNull
    private final ReadWriteProperty icon$delegate;
    @NotNull
    private final ReadWriteProperty frameTexture$delegate;
    @NotNull
    private final ReadWriteProperty progress$delegate;
    @NotNull
    private final ReadWriteProperty progressColor$delegate;
    @NotNull
    private final UUID uuid;
    @NotNull
    private final HashSet<UUID> listenerUuids;
    @NotNull
    private final ObservableSubscription<ServerPlayerEvent.Login> subscription;
    @NotNull
    private static final ResourceLocation VANILLA_FRAME;
    public static final int VANILLA_PROGRESS_COLOR = -1675545;

    public Toast(@NotNull Component title, @NotNull Component description, @NotNull ItemStack icon, @NotNull ResourceLocation frameTexture, float progress2, int progressColor) {
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        Intrinsics.checkNotNullParameter((Object)description, (String)"description");
        Intrinsics.checkNotNullParameter((Object)icon, (String)"icon");
        Intrinsics.checkNotNullParameter((Object)frameTexture, (String)"frameTexture");
        Delegates this_$iv = Delegates.INSTANCE;
        boolean $i$f$observable = false;
        this.title$delegate = (ReadWriteProperty)new ObservableProperty<Component>((Object)title, this){
            final /* synthetic */ Toast this$0;
            {
                this.this$0 = toast;
                super($initialValue);
            }

            /*
             * Ignored method signature, as it can't be verified against descriptor
             * WARNING - void declaration
             */
            protected void afterChange(@NotNull KProperty property, Object oldValue, Object newValue) {
                void new_;
                Intrinsics.checkNotNullParameter((Object)property, (String)"property");
                Component component = (Component)newValue;
                Component old = (Component)oldValue;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)old, (Object)new_)) {
                    Toast.access$launchUpdate(this.this$0);
                }
            }
        };
        this_$iv = Delegates.INSTANCE;
        $i$f$observable = false;
        this.description$delegate = (ReadWriteProperty)new ObservableProperty<Component>((Object)description, this){
            final /* synthetic */ Toast this$0;
            {
                this.this$0 = toast;
                super($initialValue);
            }

            /*
             * Ignored method signature, as it can't be verified against descriptor
             * WARNING - void declaration
             */
            protected void afterChange(@NotNull KProperty property, Object oldValue, Object newValue) {
                void new_;
                Intrinsics.checkNotNullParameter((Object)property, (String)"property");
                Component component = (Component)newValue;
                Component old = (Component)oldValue;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)old, (Object)new_)) {
                    Toast.access$launchUpdate(this.this$0);
                }
            }
        };
        this_$iv = Delegates.INSTANCE;
        $i$f$observable = false;
        this.icon$delegate = (ReadWriteProperty)new ObservableProperty<ItemStack>((Object)icon, this){
            final /* synthetic */ Toast this$0;
            {
                this.this$0 = toast;
                super($initialValue);
            }

            /*
             * Ignored method signature, as it can't be verified against descriptor
             * WARNING - void declaration
             */
            protected void afterChange(@NotNull KProperty property, Object oldValue, Object newValue) {
                void new_;
                Intrinsics.checkNotNullParameter((Object)property, (String)"property");
                ItemStack itemStack = (ItemStack)newValue;
                ItemStack old = (ItemStack)oldValue;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)old, (Object)new_)) {
                    Toast.access$launchUpdate(this.this$0);
                }
            }
        };
        this_$iv = Delegates.INSTANCE;
        $i$f$observable = false;
        this.frameTexture$delegate = (ReadWriteProperty)new ObservableProperty<ResourceLocation>((Object)frameTexture, this){
            final /* synthetic */ Toast this$0;
            {
                this.this$0 = toast;
                super($initialValue);
            }

            /*
             * Ignored method signature, as it can't be verified against descriptor
             * WARNING - void declaration
             */
            protected void afterChange(@NotNull KProperty property, Object oldValue, Object newValue) {
                void new_;
                Intrinsics.checkNotNullParameter((Object)property, (String)"property");
                ResourceLocation resourceLocation = (ResourceLocation)newValue;
                ResourceLocation old = (ResourceLocation)oldValue;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)old, (Object)new_)) {
                    Toast.access$launchUpdate(this.this$0);
                }
            }
        };
        this_$iv = Delegates.INSTANCE;
        Number initialValue$iv = Float.valueOf(progress2);
        boolean $i$f$observable2 = false;
        this.progress$delegate = (ReadWriteProperty)new ObservableProperty<Float>((Object)initialValue$iv, this){
            final /* synthetic */ Toast this$0;
            {
                this.this$0 = toast;
                super($initialValue);
            }

            /*
             * Ignored method signature, as it can't be verified against descriptor
             * WARNING - void declaration
             */
            protected void afterChange(@NotNull KProperty property, Object oldValue, Object newValue) {
                void new_;
                Intrinsics.checkNotNullParameter((Object)property, (String)"property");
                float f = ((Number)newValue).floatValue();
                float old = ((Number)oldValue).floatValue();
                boolean bl = false;
                if (!(old == new_)) {
                    Toast.access$launchUpdate(this.this$0);
                }
            }
        };
        this_$iv = Delegates.INSTANCE;
        initialValue$iv = progressColor;
        $i$f$observable2 = false;
        this.progressColor$delegate = (ReadWriteProperty)new ObservableProperty<Integer>((Object)initialValue$iv, this){
            final /* synthetic */ Toast this$0;
            {
                this.this$0 = toast;
                super($initialValue);
            }

            /*
             * Ignored method signature, as it can't be verified against descriptor
             * WARNING - void declaration
             */
            protected void afterChange(@NotNull KProperty property, Object oldValue, Object newValue) {
                void new_;
                Intrinsics.checkNotNullParameter((Object)property, (String)"property");
                int n = ((Number)newValue).intValue();
                int old = ((Number)oldValue).intValue();
                boolean bl = false;
                if (old != new_) {
                    Toast.access$launchUpdate(this.this$0);
                }
            }
        };
        UUID uUID = Mth.m_14002_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"randomUuid()");
        this.uuid = uUID;
        this.listenerUuids = new HashSet();
        this.subscription = Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_PLAYER_LOGIN, null, (Function1)new Function1<ServerPlayerEvent.Login, Unit>(this){
            final /* synthetic */ Toast this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            public final void invoke(@NotNull ServerPlayerEvent.Login event) {
                Intrinsics.checkNotNullParameter((Object)event, (String)"event");
                if (Toast.access$getListenerUuids$p(this.this$0).contains(event.getPlayer().m_20148_())) {
                    Toast.access$updateFor(this.this$0, event.getPlayer(), Toast.access$toPacket(this.this$0, ToastPacket.Behaviour.SHOW_OR_UPDATE));
                }
            }
        }, 1, null);
    }

    public /* synthetic */ Toast(Component component, Component component2, ItemStack itemStack, ResourceLocation resourceLocation, float f, int n, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 8) != 0) {
            resourceLocation = VANILLA_FRAME;
        }
        if ((n2 & 0x10) != 0) {
            f = -1.0f;
        }
        if ((n2 & 0x20) != 0) {
            n = -1675545;
        }
        this(component, component2, itemStack, resourceLocation, f, n);
    }

    @NotNull
    public final Component getTitle() {
        return (Component)this.title$delegate.getValue((Object)this, $$delegatedProperties[0]);
    }

    public final void setTitle(@NotNull Component component) {
        Intrinsics.checkNotNullParameter((Object)component, (String)"<set-?>");
        this.title$delegate.setValue((Object)this, $$delegatedProperties[0], (Object)component);
    }

    @NotNull
    public final Component getDescription() {
        return (Component)this.description$delegate.getValue((Object)this, $$delegatedProperties[1]);
    }

    public final void setDescription(@NotNull Component component) {
        Intrinsics.checkNotNullParameter((Object)component, (String)"<set-?>");
        this.description$delegate.setValue((Object)this, $$delegatedProperties[1], (Object)component);
    }

    @NotNull
    public final ItemStack getIcon() {
        return (ItemStack)this.icon$delegate.getValue((Object)this, $$delegatedProperties[2]);
    }

    public final void setIcon(@NotNull ItemStack itemStack) {
        Intrinsics.checkNotNullParameter((Object)itemStack, (String)"<set-?>");
        this.icon$delegate.setValue((Object)this, $$delegatedProperties[2], (Object)itemStack);
    }

    @NotNull
    public final ResourceLocation getFrameTexture() {
        return (ResourceLocation)this.frameTexture$delegate.getValue((Object)this, $$delegatedProperties[3]);
    }

    public final void setFrameTexture(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"<set-?>");
        this.frameTexture$delegate.setValue((Object)this, $$delegatedProperties[3], (Object)resourceLocation);
    }

    public final float getProgress() {
        return ((Number)this.progress$delegate.getValue((Object)this, $$delegatedProperties[4])).floatValue();
    }

    public final void setProgress(float f) {
        this.progress$delegate.setValue((Object)this, $$delegatedProperties[4], (Object)Float.valueOf(f));
    }

    public final int getProgressColor() {
        return ((Number)this.progressColor$delegate.getValue((Object)this, $$delegatedProperties[5])).intValue();
    }

    public final void setProgressColor(int n) {
        this.progressColor$delegate.setValue((Object)this, $$delegatedProperties[5], (Object)n);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Collection<ServerPlayer> getListeners() {
        void $this$mapNotNullTo$iv$iv;
        Iterable $this$mapNotNull$iv = this.listenerUuids;
        boolean $i$f$mapNotNull = false;
        Iterable iterable = $this$mapNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        Iterator iterator = $this$forEach$iv$iv$iv.iterator();
        while (iterator.hasNext()) {
            ServerPlayer it$iv$iv;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = iterator.next();
            boolean bl = false;
            UUID it = (UUID)element$iv$iv;
            boolean bl2 = false;
            MinecraftServer minecraftServer = Cobblemon.INSTANCE.getImplementation().server();
            if ((minecraftServer != null && (minecraftServer = minecraftServer.m_6846_()) != null ? minecraftServer.m_11259_(it) : null) == null) continue;
            it$iv$iv = it$iv$iv;
            boolean bl3 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @NotNull
    public final UUID getUuid$common() {
        return this.uuid;
    }

    public final void addListeners(ServerPlayer ... listeners) {
        Intrinsics.checkNotNullParameter((Object)listeners, (String)"listeners");
        ToastPacket packet = this.toPacket(ToastPacket.Behaviour.SHOW_OR_UPDATE);
        ServerPlayer[] $this$forEach$iv = listeners;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            ServerPlayer element$iv;
            ServerPlayer listener = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            if (!this.listenerUuids.add(listener.m_20148_())) continue;
            this.updateFor(listener, packet);
        }
    }

    public final void removeListeners(ServerPlayer ... listeners) {
        Intrinsics.checkNotNullParameter((Object)listeners, (String)"listeners");
        ToastPacket packet = this.toPacket(ToastPacket.Behaviour.HIDE);
        ServerPlayer[] $this$forEach$iv = listeners;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            ServerPlayer element$iv;
            ServerPlayer listener = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            if (!this.listenerUuids.remove(listener.m_20148_())) continue;
            this.updateFor(listener, packet);
        }
    }

    public final void expireAfter(int ticks) {
        SchedulingFunctionsKt.afterOnServer$default(ticks, 0.0f, (Function0)new Function0<Unit>(this){
            final /* synthetic */ Toast this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            public final void invoke() {
                this.this$0.expire();
            }
        }, 2, null);
    }

    public final void setNoProgress() {
        this.setProgress(-1.0f);
    }

    public final void expire() {
        Collection<ServerPlayer> $this$toTypedArray$iv = this.getListeners();
        boolean $i$f$toTypedArray = false;
        Collection<ServerPlayer> thisCollection$iv = $this$toTypedArray$iv;
        ServerPlayer[] serverPlayerArray = thisCollection$iv.toArray(new ServerPlayer[0]);
        this.removeListeners(Arrays.copyOf(serverPlayerArray, serverPlayerArray.length));
        this.listenerUuids.clear();
        this.subscription.unsubscribe();
    }

    private final void launchUpdate() {
        ToastPacket packet = this.toPacket(ToastPacket.Behaviour.SHOW_OR_UPDATE);
        Iterable $this$forEach$iv = this.getListeners();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ServerPlayer player = (ServerPlayer)element$iv;
            boolean bl = false;
            Cobblemon.INSTANCE.getImplementation().getNetworkManager().sendPacketToPlayer(player, packet);
        }
    }

    private final void updateFor(ServerPlayer player, ToastPacket packet) {
        Cobblemon.INSTANCE.getImplementation().getNetworkManager().sendPacketToPlayer(player, packet);
    }

    private final ToastPacket toPacket(ToastPacket.Behaviour behaviour) {
        return new ToastPacket(this.getTitle(), this.getDescription(), this.getIcon(), this.getFrameTexture(), this.getProgress(), this.getProgressColor(), this.uuid, behaviour);
    }

    public static final /* synthetic */ void access$launchUpdate(Toast $this) {
        $this.launchUpdate();
    }

    public static final /* synthetic */ HashSet access$getListenerUuids$p(Toast $this) {
        return $this.listenerUuids;
    }

    public static final /* synthetic */ void access$updateFor(Toast $this, ServerPlayer player, ToastPacket packet) {
        $this.updateFor(player, packet);
    }

    public static final /* synthetic */ ToastPacket access$toPacket(Toast $this, ToastPacket.Behaviour behaviour) {
        return $this.toPacket(behaviour);
    }

    static {
        KProperty[] kPropertyArray = new KProperty[]{Reflection.mutableProperty1((MutablePropertyReference1)((MutablePropertyReference1)new MutablePropertyReference1Impl(Toast.class, "title", "getTitle()Lnet/minecraft/text/Text;", 0))), Reflection.mutableProperty1((MutablePropertyReference1)((MutablePropertyReference1)new MutablePropertyReference1Impl(Toast.class, "description", "getDescription()Lnet/minecraft/text/Text;", 0))), Reflection.mutableProperty1((MutablePropertyReference1)((MutablePropertyReference1)new MutablePropertyReference1Impl(Toast.class, "icon", "getIcon()Lnet/minecraft/item/ItemStack;", 0))), Reflection.mutableProperty1((MutablePropertyReference1)((MutablePropertyReference1)new MutablePropertyReference1Impl(Toast.class, "frameTexture", "getFrameTexture()Lnet/minecraft/util/Identifier;", 0))), Reflection.mutableProperty1((MutablePropertyReference1)((MutablePropertyReference1)new MutablePropertyReference1Impl(Toast.class, "progress", "getProgress()F", 0))), Reflection.mutableProperty1((MutablePropertyReference1)((MutablePropertyReference1)new MutablePropertyReference1Impl(Toast.class, "progressColor", "getProgressColor()I", 0)))};
        $$delegatedProperties = kPropertyArray;
        Companion = new Companion(null);
        VANILLA_FRAME = new ResourceLocation("minecraft", "textures/gui/toasts.png");
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\b\u001a\u00020\u00078\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/api/toast/Toast$Companion;", "", "Lnet/minecraft/resources/ResourceLocation;", "VANILLA_FRAME", "Lnet/minecraft/resources/ResourceLocation;", "getVANILLA_FRAME", "()Lnet/minecraft/resources/ResourceLocation;", "", "VANILLA_PROGRESS_COLOR", "I", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getVANILLA_FRAME() {
            return VANILLA_FRAME;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

