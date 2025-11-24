/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonKeyBinding;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000e\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0007\n\u000b\f\r\u000e\u000f\u0010B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/client/keybind/keybinds/DebugKeybindings;", "", "", "Lcom/cobblemon/mod/common/client/keybind/CobblemonKeyBinding;", "keybindings", "Ljava/util/List;", "getKeybindings", "()Ljava/util/List;", "<init>", "()V", "PrintModelSettingsKeybinding", "ScaleDownKeybinding", "ScaleUpKeybinding", "TranslateDownKeybinding", "TranslateLeftKeybinding", "TranslateRightKeybinding", "TranslateUpKeybinding", "common"})
public final class DebugKeybindings {
    @NotNull
    public static final DebugKeybindings INSTANCE = new DebugKeybindings();
    @NotNull
    private static final List<CobblemonKeyBinding> keybindings;

    private DebugKeybindings() {
    }

    @NotNull
    public final List<CobblemonKeyBinding> getKeybindings() {
        return keybindings;
    }

    static {
        CobblemonKeyBinding[] cobblemonKeyBindingArray = new CobblemonKeyBinding[]{new ScaleUpKeybinding(), new ScaleDownKeybinding(), new TranslateLeftKeybinding(), new TranslateRightKeybinding(), new TranslateUpKeybinding(), new TranslateDownKeybinding(), new PrintModelSettingsKeybinding()};
        keybindings = CollectionsKt.listOf((Object[])cobblemonKeyBindingArray);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/client/keybind/keybinds/DebugKeybindings$PrintModelSettingsKeybinding;", "Lcom/cobblemon/mod/common/client/keybind/CobblemonKeyBinding;", "", "onPress", "()V", "<init>", "common"})
    public static final class PrintModelSettingsKeybinding
    extends CobblemonKeyBinding {
        public PrintModelSettingsKeybinding() {
            super("key.cobblemon.printmodelsettings", InputConstants.Type.KEYSYM, 46, "key.cobblemon.categories.cobblemon.debug");
        }

        @Override
        public void onPress() {
            Pokemon currentlySelectedPokemon = CobblemonClient.INSTANCE.getStorage().getMyParty().get(CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
            if (currentlySelectedPokemon != null) {
                PokemonPoseableModel model = (PokemonPoseableModel)PokemonModelRepository.INSTANCE.getPoser(currentlySelectedPokemon.getSpecies().getResourceIdentifier(), currentlySelectedPokemon.getAspects());
                LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
                if (localPlayer != null) {
                    localPlayer.m_213846_(Component.m_130674_((String)("Portrait Translation: " + model.getPortraitTranslation())));
                }
                LocalPlayer localPlayer2 = Minecraft.m_91087_().f_91074_;
                if (localPlayer2 != null) {
                    localPlayer2.m_213846_(Component.m_130674_((String)("Portrait Scale: " + model.getPortraitScale())));
                }
                Cobblemon.INSTANCE.getLOGGER().info("override var portraitTranslation = Vec3d(" + model.getPortraitTranslation().f_82479_ + ", " + model.getPortraitTranslation().f_82480_ + ", " + model.getPortraitTranslation().f_82481_ + ")");
                Cobblemon.INSTANCE.getLOGGER().info("override var portraitScale = " + model.getPortraitScale() + "F");
            }
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/client/keybind/keybinds/DebugKeybindings$ScaleDownKeybinding;", "Lcom/cobblemon/mod/common/client/keybind/CobblemonKeyBinding;", "", "onPress", "()V", "<init>", "common"})
    public static final class ScaleDownKeybinding
    extends CobblemonKeyBinding {
        public ScaleDownKeybinding() {
            super("key.cobblemon.scaleportraitdown", InputConstants.Type.KEYSYM, 45, "key.cobblemon.categories.cobblemon.debug");
        }

        @Override
        public void onPress() {
            Pokemon currentlySelectedPokemon = CobblemonClient.INSTANCE.getStorage().getMyParty().get(CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
            if (currentlySelectedPokemon != null) {
                PokemonPoseableModel model = (PokemonPoseableModel)PokemonModelRepository.INSTANCE.getPoser(currentlySelectedPokemon.getSpecies().getResourceIdentifier(), currentlySelectedPokemon.getAspects());
                model.setPortraitScale(model.getPortraitScale() - 0.01f);
            }
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/client/keybind/keybinds/DebugKeybindings$ScaleUpKeybinding;", "Lcom/cobblemon/mod/common/client/keybind/CobblemonKeyBinding;", "", "onPress", "()V", "<init>", "common"})
    public static final class ScaleUpKeybinding
    extends CobblemonKeyBinding {
        public ScaleUpKeybinding() {
            super("key.cobblemon.scaleportraitup", InputConstants.Type.KEYSYM, 61, "key.cobblemon.categories.cobblemon.debug");
        }

        @Override
        public void onPress() {
            Pokemon currentlySelectedPokemon = CobblemonClient.INSTANCE.getStorage().getMyParty().get(CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
            if (currentlySelectedPokemon != null) {
                PokemonPoseableModel model = (PokemonPoseableModel)PokemonModelRepository.INSTANCE.getPoser(currentlySelectedPokemon.getSpecies().getResourceIdentifier(), currentlySelectedPokemon.getAspects());
                model.setPortraitScale(model.getPortraitScale() + 0.01f);
            }
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/client/keybind/keybinds/DebugKeybindings$TranslateDownKeybinding;", "Lcom/cobblemon/mod/common/client/keybind/CobblemonKeyBinding;", "", "onPress", "()V", "<init>", "common"})
    public static final class TranslateDownKeybinding
    extends CobblemonKeyBinding {
        public TranslateDownKeybinding() {
            super("key.cobblemon.translateportraitdown", InputConstants.Type.KEYSYM, 75, "key.cobblemon.categories.cobblemon.debug");
        }

        @Override
        public void onPress() {
            Pokemon currentlySelectedPokemon = CobblemonClient.INSTANCE.getStorage().getMyParty().get(CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
            if (currentlySelectedPokemon != null) {
                PokemonPoseableModel model = (PokemonPoseableModel)PokemonModelRepository.INSTANCE.getPoser(currentlySelectedPokemon.getSpecies().getResourceIdentifier(), currentlySelectedPokemon.getAspects());
                Vec3 vec3 = model.getPortraitTranslation().m_82520_(0.0, 0.01, 0.0);
                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"model.portraitTranslation.add(0.0, 0.01, 0.0)");
                model.setPortraitTranslation(vec3);
            }
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/client/keybind/keybinds/DebugKeybindings$TranslateLeftKeybinding;", "Lcom/cobblemon/mod/common/client/keybind/CobblemonKeyBinding;", "", "onPress", "()V", "<init>", "common"})
    public static final class TranslateLeftKeybinding
    extends CobblemonKeyBinding {
        public TranslateLeftKeybinding() {
            super("key.cobblemon.translateportraitleft", InputConstants.Type.KEYSYM, 74, "key.cobblemon.categories.cobblemon.debug");
        }

        @Override
        public void onPress() {
            Pokemon currentlySelectedPokemon = CobblemonClient.INSTANCE.getStorage().getMyParty().get(CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
            if (currentlySelectedPokemon != null) {
                PokemonPoseableModel model = (PokemonPoseableModel)PokemonModelRepository.INSTANCE.getPoser(currentlySelectedPokemon.getSpecies().getResourceIdentifier(), currentlySelectedPokemon.getAspects());
                Vec3 vec3 = model.getPortraitTranslation().m_82520_(-0.01, 0.0, 0.0);
                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"model.portraitTranslation.add(-0.01, 0.0, 0.0)");
                model.setPortraitTranslation(vec3);
            }
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/client/keybind/keybinds/DebugKeybindings$TranslateRightKeybinding;", "Lcom/cobblemon/mod/common/client/keybind/CobblemonKeyBinding;", "", "onPress", "()V", "<init>", "common"})
    public static final class TranslateRightKeybinding
    extends CobblemonKeyBinding {
        public TranslateRightKeybinding() {
            super("key.cobblemon.translateportraitright", InputConstants.Type.KEYSYM, 76, "key.cobblemon.categories.cobblemon.debug");
        }

        @Override
        public void onPress() {
            Pokemon currentlySelectedPokemon = CobblemonClient.INSTANCE.getStorage().getMyParty().get(CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
            if (currentlySelectedPokemon != null) {
                PokemonPoseableModel model = (PokemonPoseableModel)PokemonModelRepository.INSTANCE.getPoser(currentlySelectedPokemon.getSpecies().getResourceIdentifier(), currentlySelectedPokemon.getAspects());
                Vec3 vec3 = model.getPortraitTranslation().m_82520_(0.01, 0.0, 0.0);
                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"model.portraitTranslation.add(0.01, 0.0, 0.0)");
                model.setPortraitTranslation(vec3);
            }
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/client/keybind/keybinds/DebugKeybindings$TranslateUpKeybinding;", "Lcom/cobblemon/mod/common/client/keybind/CobblemonKeyBinding;", "", "onPress", "()V", "<init>", "common"})
    public static final class TranslateUpKeybinding
    extends CobblemonKeyBinding {
        public TranslateUpKeybinding() {
            super("key.cobblemon.translateportraitup", InputConstants.Type.KEYSYM, 73, "key.cobblemon.categories.cobblemon.debug");
        }

        @Override
        public void onPress() {
            Pokemon currentlySelectedPokemon = CobblemonClient.INSTANCE.getStorage().getMyParty().get(CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
            if (currentlySelectedPokemon != null) {
                PokemonPoseableModel model = (PokemonPoseableModel)PokemonModelRepository.INSTANCE.getPoser(currentlySelectedPokemon.getSpecies().getResourceIdentifier(), currentlySelectedPokemon.getAspects());
                Vec3 vec3 = model.getPortraitTranslation().m_82520_(0.0, -0.01, 0.0);
                Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"model.portraitTranslation.add(0.0, -0.01, 0.0)");
                model.setPortraitTranslation(vec3);
            }
        }
    }
}

