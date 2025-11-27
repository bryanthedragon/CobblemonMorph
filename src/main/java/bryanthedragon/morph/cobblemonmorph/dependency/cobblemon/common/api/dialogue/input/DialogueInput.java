/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.MoStruct;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public interface DialogueInput {
    @Nullable
    public DialogueTimeout getTimeout();

    public void setTimeout(@Nullable DialogueTimeout var1);

    @NotNull
    public MoStruct toMoLangStruct(@NotNull ActiveInput var1);

    public void handle(@NotNull ActiveInput var1, @NotNull String var2);
}

