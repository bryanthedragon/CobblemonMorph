/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonBlockingKeyBinding;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.BattleChallengePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.RequestPlayerInteractionsPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.SendOutPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001f\u0010\u0004J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\r\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010\b\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\b\u0010\u0004J\u000f\u0010\t\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\t\u0010\u0004J\u000f\u0010\n\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\n\u0010\u0004J'\u0010\u0011\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000fH\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012R\"\u0010\u0013\u001a\u00020\u00058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0007\"\u0004\b\u0016\u0010\u0017R\"\u0010\u0019\u001a\u00020\u00188\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001e\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/client/keybind/keybinds/PartySendBinding;", "Lcom/cobblemon/mod/common/client/keybind/CobblemonBlockingKeyBinding;", "", "actioned", "()V", "", "canAction", "()Z", "onPress", "onRelease", "onTick", "Lnet/minecraft/client/player/LocalPlayer;", "player", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lnet/minecraft/world/entity/LivingEntity;", "entity", "processEntityTarget", "(Lnet/minecraft/client/player/LocalPlayer;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lnet/minecraft/world/entity/LivingEntity;)V", "canApplyChange", "Z", "getCanApplyChange", "setCanApplyChange", "(Z)V", "", "secondsSinceActioned", "F", "getSecondsSinceActioned", "()F", "setSecondsSinceActioned", "(F)V", "<init>", "common"})
public final class PartySendBinding
extends CobblemonBlockingKeyBinding {
    @NotNull
    public static final PartySendBinding INSTANCE = new PartySendBinding();
    private static boolean canApplyChange = true;
    private static float secondsSinceActioned;

    private PartySendBinding() {
        super("key.cobblemon.throwpartypokemon", InputConstants.Type.KEYSYM, 82, "key.cobblemon.categories.cobblemon");
    }

    public final boolean getCanApplyChange() {
        return canApplyChange;
    }

    public final void setCanApplyChange(boolean bl) {
        canApplyChange = bl;
    }

    public final float getSecondsSinceActioned() {
        return secondsSinceActioned;
    }

    public final void setSecondsSinceActioned(float f) {
        secondsSinceActioned = f;
    }

    public final void actioned() {
        canApplyChange = false;
        secondsSinceActioned = 0.0f;
        this.setWasDown(false);
    }

    public final boolean canAction() {
        return canApplyChange;
    }

    @Override
    public void onTick() {
        if (secondsSinceActioned < 100.0f) {
            secondsSinceActioned += Minecraft.m_91087_().m_91296_();
        }
        super.onTick();
    }

    @Override
    public void onRelease() {
        Pokemon pokemon;
        this.setWasDown(false);
        if (!this.canAction()) {
            canApplyChange = true;
            return;
        }
        canApplyChange = true;
        LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
        if (localPlayer == null) {
            return;
        }
        LocalPlayer player = localPlayer;
        if (player.m_5833_()) {
            return;
        }
        ClientBattle battle2 = CobblemonClient.INSTANCE.getBattle();
        if (battle2 != null) {
            battle2.setMinimised(!battle2.getMinimised());
            if (!battle2.getMinimised()) {
                Minecraft.m_91087_().m_91152_((Screen)new BattleGUI());
            }
            return;
        }
        if (CobblemonClient.INSTANCE.getStorage().getSelectedSlot() != -1 && Minecraft.m_91087_().f_91080_ == null && (pokemon = CobblemonClient.INSTANCE.getStorage().getMyParty().get(CobblemonClient.INSTANCE.getStorage().getSelectedSlot())) != null && pokemon.getCurrentHealth() > 0) {
            LivingEntity targetEntity = (LivingEntity)PlayerExtensionsKt.traceFirstEntityCollision$default((Player)player, 0.0f, 0.0f, LivingEntity.class, (Entity)player, 3, null);
            if (targetEntity == null || targetEntity instanceof PokemonEntity && Intrinsics.areEqual((Object)((PokemonEntity)targetEntity).m_21805_(), (Object)player.m_20148_())) {
                CobblemonNetwork.INSTANCE.sendPacketToServer(new SendOutPokemonPacket(CobblemonClient.INSTANCE.getStorage().getSelectedSlot()));
            } else {
                this.processEntityTarget(player, pokemon, targetEntity);
            }
        }
    }

    private final void processEntityTarget(LocalPlayer player, Pokemon pokemon, LivingEntity entity2) {
        LivingEntity livingEntity = entity2;
        if (livingEntity instanceof Player) {
            UUID uUID = ((Player)entity2).m_20148_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"entity.uuid");
            int n = ((Player)entity2).m_19879_();
            UUID uUID2 = pokemon.getUuid();
            Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"pokemon.uuid");
            CobblemonNetwork.INSTANCE.sendPacketToServer(new RequestPlayerInteractionsPacket(uUID, n, uUID2));
        } else if (livingEntity instanceof PokemonEntity) {
            if (!((PokemonEntity)entity2).canBattle((Player)player)) {
                return;
            }
            int n = ((PokemonEntity)entity2).m_19879_();
            UUID uUID = pokemon.getUuid();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"pokemon.uuid");
            CobblemonNetwork.INSTANCE.sendPacketToServer(new BattleChallengePacket(n, uUID));
        }
    }

    @Override
    public void onPress() {
    }
}

