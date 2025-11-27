/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.JvmField
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.level.block.SoundType
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\bz\b\u00c6\u0002\u0018\u00002&\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0004\u0012\u0004\u0012\u00020\u00030\u0001B\u000b\b\u0002\u00a2\u0006\u0006\b\u0084\u0001\u0010\u0085\u0001J\u0017\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\nR\u0014\u0010\r\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\nR\u0014\u0010\u0010\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\nR\u0014\u0010\u0011\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\nR\u0014\u0010\u0012\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u000eR\u0014\u0010\u0013\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\nR\u0014\u0010\u0014\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\nR\u0014\u0010\u0015\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\nR\u0014\u0010\u0016\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\nR\u0014\u0010\u0017\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\nR\u0014\u0010\u0018\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\nR\u0014\u0010\u0019\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u000eR\u0014\u0010\u001a\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\nR\u0014\u0010\u001b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001b\u0010\nR\u0014\u0010\u001c\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\u000eR\u0014\u0010\u001d\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\nR\u0014\u0010\u001e\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001e\u0010\nR\u0014\u0010\u001f\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001f\u0010\nR\u0014\u0010 \u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b \u0010\nR\u0014\u0010!\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b!\u0010\nR\u0014\u0010\"\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\"\u0010\nR\u0014\u0010#\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b#\u0010\nR\u0014\u0010$\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b$\u0010\nR\u0014\u0010%\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b%\u0010\nR\u0014\u0010&\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b&\u0010\nR\u0014\u0010'\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b'\u0010\nR\u0014\u0010(\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b(\u0010\nR\u0014\u0010)\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b)\u0010\nR\u0014\u0010*\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b*\u0010\nR\u0014\u0010+\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b+\u0010\nR\u0014\u0010,\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b,\u0010\nR\u0014\u0010-\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b-\u0010\nR\u0014\u0010.\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b.\u0010\nR\u0014\u0010/\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b/\u0010\u000eR\u0014\u00100\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b0\u0010\nR\u0014\u00101\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b1\u0010\nR\u0014\u00102\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b2\u0010\nR\u0014\u00103\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b3\u0010\nR\u0014\u00104\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b4\u0010\nR\u0014\u00105\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b5\u0010\nR\u0014\u00106\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b6\u0010\nR\u0014\u00107\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b7\u0010\nR\u0014\u00108\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b8\u0010\nR\u0014\u00109\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b9\u0010\nR\u0014\u0010:\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b:\u0010\nR\u0014\u0010;\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b;\u0010\nR\u0014\u0010<\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b<\u0010\nR\u0014\u0010=\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b=\u0010\nR\u0014\u0010>\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b>\u0010\nR\u0014\u0010?\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b?\u0010\nR\u0014\u0010@\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b@\u0010\nR\u0014\u0010A\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bA\u0010\nR\u0014\u0010B\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bB\u0010\nR\u0014\u0010C\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bC\u0010\nR\u0014\u0010D\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bD\u0010\nR\u0014\u0010E\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bE\u0010\nR\u0014\u0010F\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bF\u0010\nR\u0014\u0010G\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bG\u0010\nR\u0014\u0010H\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bH\u0010\nR\u0014\u0010I\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bI\u0010\nR\u0014\u0010J\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bJ\u0010\u000eR\u0014\u0010K\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bK\u0010\nR\u0014\u0010L\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bL\u0010\nR\u0014\u0010M\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bM\u0010\nR\u0014\u0010N\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bN\u0010\nR\u0014\u0010O\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bO\u0010\nR\u0014\u0010P\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bP\u0010\nR\u0014\u0010Q\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bQ\u0010\nR\u0014\u0010R\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bR\u0010\u000eR\u0014\u0010S\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bS\u0010\nR\u0014\u0010T\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bT\u0010\nR\u0014\u0010U\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bU\u0010\nR\u0014\u0010V\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bV\u0010\nR\u0014\u0010W\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bW\u0010\nR\u0014\u0010X\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bX\u0010\nR\u0014\u0010Y\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bY\u0010\nR\u0014\u0010Z\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bZ\u0010\nR\u0014\u0010[\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b[\u0010\nR\u0014\u0010\\\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\\\u0010\nR\u0014\u0010]\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b]\u0010\nR\u0014\u0010^\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b^\u0010\nR\u0014\u0010_\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b_\u0010\nR\u0014\u0010`\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b`\u0010\nR\u0014\u0010a\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\ba\u0010\nR\u0014\u0010b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bb\u0010\nR\u0014\u0010c\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bc\u0010\nR\u0014\u0010d\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bd\u0010\nR\u0014\u0010e\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\be\u0010\nR\u0014\u0010f\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bf\u0010\nR\u0014\u0010g\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bg\u0010\nR\u0014\u0010h\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bh\u0010\nR\u0014\u0010i\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bi\u0010\u000eR\u0014\u0010j\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bj\u0010\nR\u0014\u0010k\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bk\u0010\nR\u0014\u0010l\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bl\u0010\nR\u0014\u0010m\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bm\u0010\u000eR\u0014\u0010n\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bn\u0010\nR\u0014\u0010o\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bo\u0010\nR\u0014\u0010p\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bp\u0010\nR\u0014\u0010q\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bq\u0010\u000eR\u0014\u0010r\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\br\u0010\nR\u0014\u0010s\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bs\u0010\u000eR\u0014\u0010t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bt\u0010\nR\u0014\u0010u\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bu\u0010\nR\u0014\u0010v\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bv\u0010\nR\u0014\u0010w\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bw\u0010\u000eR\u0014\u0010x\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bx\u0010\nR\u0014\u0010y\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\by\u0010\nR\u0014\u0010z\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bz\u0010\nR\u0014\u0010{\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b{\u0010\u000eR \u0010|\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b|\u0010}\u001a\u0004\b~\u0010\u007fR+\u0010\u0080\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u00048\u0016X\u0096\u0004\u00a2\u0006\u0010\n\u0006\b\u0080\u0001\u0010\u0081\u0001\u001a\u0006\b\u0082\u0001\u0010\u0083\u0001\u00a8\u0006\u0086\u0001"}, d2={"Lcom/cobblemon/mod/common/CobblemonSounds;", "Lcom/cobblemon/mod/common/platform/PlatformRegistry;", "Lnet/minecraft/core/Registry;", "Lnet/minecraft/sounds/SoundEvent;", "Lnet/minecraft/resources/ResourceKey;", "", "name", "create", "(Ljava/lang/String;)Lnet/minecraft/sounds/SoundEvent;", "BERRY_BUSH_BREAK", "Lnet/minecraft/sounds/SoundEvent;", "BERRY_BUSH_PLACE", "Lnet/minecraft/world/level/block/SoundType;", "BERRY_BUSH_SOUNDS", "Lnet/minecraft/world/level/block/SoundType;", "BERRY_EAT", "BERRY_HARVEST", "BIG_ROOT_BREAK", "BIG_ROOT_SOUNDS", "CAN_EVOLVE", "DISPLAY_CASE_ADD_ITEM", "DISPLAY_CASE_BREAK", "DISPLAY_CASE_HIT", "DISPLAY_CASE_PLACE", "DISPLAY_CASE_REMOVE_ITEM", "DISPLAY_CASE_SOUNDS", "DISPLAY_CASE_STEP", "ENERGY_ROOT_PLACE", "ENERGY_ROOT_SOUNDS", "EVOLVE", "EVOLVING", "FOSSIL_MACHINE_ACTIVATE", "FOSSIL_MACHINE_ACTIVE_LOOP", "FOSSIL_MACHINE_ASSEMBLE", "FOSSIL_MACHINE_DNA_FULL", "FOSSIL_MACHINE_FINISHED", "FOSSIL_MACHINE_INSERT_DNA", "FOSSIL_MACHINE_INSERT_DNA_SMALL", "FOSSIL_MACHINE_INSERT_FOSSIL", "FOSSIL_MACHINE_RETRIEVE_FOSSIL", "FOSSIL_MACHINE_RETRIEVE_POKEMON", "FOSSIL_MACHINE_UNPROTECTED", "GILDED_CHEST_BREAK", "GILDED_CHEST_CLOSE", "GILDED_CHEST_HIT", "GILDED_CHEST_OPEN", "GILDED_CHEST_PLACE", "GILDED_CHEST_SOUNDS", "GILDED_CHEST_STEP", "GIMMIGHOUL_GIVE_ITEM_SMALL", "GIMMIGHOUL_REVEAL", "GUI_CLICK", "HEALING_MACHINE_ACTIVE", "IMPACT_BUG", "IMPACT_DARK", "IMPACT_DRAGON", "IMPACT_ELECTRIC", "IMPACT_FAIRY", "IMPACT_FIGHTING", "IMPACT_FIRE", "IMPACT_FLYING", "IMPACT_GHOST", "IMPACT_GRASS", "IMPACT_GROUND", "IMPACT_ICE", "IMPACT_NORMAL", "IMPACT_POISON", "IMPACT_PSYCHIC", "IMPACT_ROCK", "IMPACT_STEEL", "IMPACT_WATER", "ITEM_USE", "MEDICINAL_LEEK_BREAK", "MEDICINAL_LEEK_PLACE", "MEDICINAL_LEEK_SOUNDS", "MEDICINE_FEATHER_USE", "MEDICINE_HERB_USE", "MEDICINE_LIQUID_USE", "MEDICINE_PILLS_USE", "MEDICINE_SPRAY_USE", "MINT_BREAK", "MINT_PLACE", "MINT_SOUNDS", "MULCH_PLACE", "MULCH_REMOVE", "PC_CLICK", "PC_DROP", "PC_GRAB", "PC_OFF", "PC_ON", "PC_RELEASE", "POKE_BALL_CAPTURE_STARTED", "POKE_BALL_CAPTURE_SUCCEEDED", "POKE_BALL_HIT", "POKE_BALL_OPEN", "POKE_BALL_RECALL", "POKE_BALL_SEND_OUT", "POKE_BALL_SHAKE", "POKE_BALL_THROW", "POKE_BALL_TRAIL", "PVN_BATTLE", "PVP_BATTLE", "PVW_BATTLE", "RELIC_COIN_POUCH_BREAK", "RELIC_COIN_POUCH_PLACE", "RELIC_COIN_POUCH_SOUNDS", "RELIC_COIN_SACK_BREAK", "RELIC_COIN_SACK_HIT", "RELIC_COIN_SACK_PLACE", "RELIC_COIN_SACK_SOUNDS", "RELIC_COIN_SACK_STEP", "REVIVAL_HERB_BREAK", "REVIVAL_HERB_PLACE", "REVIVAL_HERB_SOUNDS", "TUMBLESTONE_BLOCK_BREAK", "TUMBLESTONE_BLOCK_SOUNDS", "TUMBLESTONE_BREAK", "TUMBLESTONE_HIT", "TUMBLESTONE_PLACE", "TUMBLESTONE_SOUNDS", "TUMBLESTONE_STEP", "VIVICHOKE_BREAK", "VIVICHOKE_PLACE", "VIVICHOKE_SOUNDS", "registry", "Lnet/minecraft/core/Registry;", "getRegistry", "()Lnet/minecraft/core/Registry;", "registryKey", "Lnet/minecraft/resources/ResourceKey;", "getRegistryKey", "()Lnet/minecraft/resources/ResourceKey;", "<init>", "()V", "common"})
public final class CobblemonSounds
extends PlatformRegistry<Registry<SoundEvent>, ResourceKey<Registry<SoundEvent>>, SoundEvent> {
    @NotNull
    public static final CobblemonSounds INSTANCE = new CobblemonSounds();
    @NotNull
    private static final Registry<SoundEvent> registry;
    @NotNull
    private static final ResourceKey<Registry<SoundEvent>> registryKey;
    @JvmField
    @NotNull
    public static final SoundEvent GUI_CLICK;
    @JvmField
    @NotNull
    public static final SoundEvent PC_ON;
    @JvmField
    @NotNull
    public static final SoundEvent PC_OFF;
    @JvmField
    @NotNull
    public static final SoundEvent PC_GRAB;
    @JvmField
    @NotNull
    public static final SoundEvent PC_DROP;
    @JvmField
    @NotNull
    public static final SoundEvent PC_RELEASE;
    @JvmField
    @NotNull
    public static final SoundEvent PC_CLICK;
    @JvmField
    @NotNull
    public static final SoundEvent HEALING_MACHINE_ACTIVE;
    @JvmField
    @NotNull
    public static final SoundEvent POKE_BALL_CAPTURE_STARTED;
    @JvmField
    @NotNull
    public static final SoundEvent POKE_BALL_CAPTURE_SUCCEEDED;
    @JvmField
    @NotNull
    public static final SoundEvent POKE_BALL_SHAKE;
    @JvmField
    @NotNull
    public static final SoundEvent POKE_BALL_OPEN;
    @JvmField
    @NotNull
    public static final SoundEvent POKE_BALL_HIT;
    @JvmField
    @NotNull
    public static final SoundEvent POKE_BALL_SEND_OUT;
    @JvmField
    @NotNull
    public static final SoundEvent POKE_BALL_RECALL;
    @JvmField
    @NotNull
    public static final SoundEvent POKE_BALL_THROW;
    @JvmField
    @NotNull
    public static final SoundEvent POKE_BALL_TRAIL;
    @JvmField
    @NotNull
    public static final SoundEvent ITEM_USE;
    @JvmField
    @NotNull
    public static final SoundEvent CAN_EVOLVE;
    @JvmField
    @NotNull
    public static final SoundEvent EVOLVING;
    @JvmField
    @NotNull
    public static final SoundEvent EVOLVE;
    @JvmField
    @NotNull
    public static final SoundEvent PVN_BATTLE;
    @JvmField
    @NotNull
    public static final SoundEvent PVP_BATTLE;
    @JvmField
    @NotNull
    public static final SoundEvent PVW_BATTLE;
    @JvmField
    @NotNull
    public static final SoundEvent MEDICINE_HERB_USE;
    @JvmField
    @NotNull
    public static final SoundEvent MEDICINE_LIQUID_USE;
    @JvmField
    @NotNull
    public static final SoundEvent MEDICINE_PILLS_USE;
    @JvmField
    @NotNull
    public static final SoundEvent MEDICINE_SPRAY_USE;
    @JvmField
    @NotNull
    public static final SoundEvent MEDICINE_FEATHER_USE;
    @JvmField
    @NotNull
    public static final SoundEvent BERRY_HARVEST;
    @JvmField
    @NotNull
    public static final SoundEvent BERRY_EAT;
    @JvmField
    @NotNull
    public static final SoundEvent MULCH_PLACE;
    @JvmField
    @NotNull
    public static final SoundEvent MULCH_REMOVE;
    @JvmField
    @NotNull
    public static final SoundEvent FOSSIL_MACHINE_ACTIVATE;
    @JvmField
    @NotNull
    public static final SoundEvent FOSSIL_MACHINE_ACTIVE_LOOP;
    @JvmField
    @NotNull
    public static final SoundEvent FOSSIL_MACHINE_ASSEMBLE;
    @JvmField
    @NotNull
    public static final SoundEvent FOSSIL_MACHINE_DNA_FULL;
    @JvmField
    @NotNull
    public static final SoundEvent FOSSIL_MACHINE_FINISHED;
    @JvmField
    @NotNull
    public static final SoundEvent FOSSIL_MACHINE_INSERT_DNA;
    @JvmField
    @NotNull
    public static final SoundEvent FOSSIL_MACHINE_INSERT_DNA_SMALL;
    @JvmField
    @NotNull
    public static final SoundEvent FOSSIL_MACHINE_INSERT_FOSSIL;
    @JvmField
    @NotNull
    public static final SoundEvent FOSSIL_MACHINE_RETRIEVE_FOSSIL;
    @JvmField
    @NotNull
    public static final SoundEvent FOSSIL_MACHINE_RETRIEVE_POKEMON;
    @JvmField
    @NotNull
    public static final SoundEvent FOSSIL_MACHINE_UNPROTECTED;
    @JvmField
    @NotNull
    public static final SoundEvent RELIC_COIN_SACK_BREAK;
    @JvmField
    @NotNull
    public static final SoundEvent RELIC_COIN_SACK_HIT;
    @JvmField
    @NotNull
    public static final SoundEvent RELIC_COIN_SACK_STEP;
    @JvmField
    @NotNull
    public static final SoundEvent RELIC_COIN_SACK_PLACE;
    @JvmField
    @NotNull
    public static final SoundEvent RELIC_COIN_POUCH_BREAK;
    @JvmField
    @NotNull
    public static final SoundEvent RELIC_COIN_POUCH_PLACE;
    @JvmField
    @NotNull
    public static final SoundEvent TUMBLESTONE_BREAK;
    @JvmField
    @NotNull
    public static final SoundEvent TUMBLESTONE_BLOCK_BREAK;
    @JvmField
    @NotNull
    public static final SoundEvent TUMBLESTONE_HIT;
    @JvmField
    @NotNull
    public static final SoundEvent TUMBLESTONE_PLACE;
    @JvmField
    @NotNull
    public static final SoundEvent TUMBLESTONE_STEP;
    @JvmField
    @NotNull
    public static final SoundEvent GIMMIGHOUL_GIVE_ITEM_SMALL;
    @JvmField
    @NotNull
    public static final SoundEvent GIMMIGHOUL_REVEAL;
    @JvmField
    @NotNull
    public static final SoundEvent BERRY_BUSH_BREAK;
    @JvmField
    @NotNull
    public static final SoundEvent BERRY_BUSH_PLACE;
    @JvmField
    @NotNull
    public static final SoundEvent BIG_ROOT_BREAK;
    @JvmField
    @NotNull
    public static final SoundEvent ENERGY_ROOT_PLACE;
    @JvmField
    @NotNull
    public static final SoundEvent VIVICHOKE_BREAK;
    @JvmField
    @NotNull
    public static final SoundEvent VIVICHOKE_PLACE;
    @JvmField
    @NotNull
    public static final SoundEvent MINT_BREAK;
    @JvmField
    @NotNull
    public static final SoundEvent MINT_PLACE;
    @JvmField
    @NotNull
    public static final SoundEvent REVIVAL_HERB_BREAK;
    @JvmField
    @NotNull
    public static final SoundEvent REVIVAL_HERB_PLACE;
    @JvmField
    @NotNull
    public static final SoundEvent MEDICINAL_LEEK_BREAK;
    @JvmField
    @NotNull
    public static final SoundEvent MEDICINAL_LEEK_PLACE;
    @JvmField
    @NotNull
    public static final SoundEvent GILDED_CHEST_OPEN;
    @JvmField
    @NotNull
    public static final SoundEvent GILDED_CHEST_CLOSE;
    @JvmField
    @NotNull
    public static final SoundEvent GILDED_CHEST_STEP;
    @JvmField
    @NotNull
    public static final SoundEvent GILDED_CHEST_HIT;
    @JvmField
    @NotNull
    public static final SoundEvent GILDED_CHEST_BREAK;
    @JvmField
    @NotNull
    public static final SoundEvent GILDED_CHEST_PLACE;
    @JvmField
    @NotNull
    public static final SoundType RELIC_COIN_SACK_SOUNDS;
    @JvmField
    @NotNull
    public static final SoundType RELIC_COIN_POUCH_SOUNDS;
    @JvmField
    @NotNull
    public static final SoundEvent IMPACT_NORMAL;
    @JvmField
    @NotNull
    public static final SoundEvent IMPACT_BUG;
    @JvmField
    @NotNull
    public static final SoundEvent IMPACT_DARK;
    @JvmField
    @NotNull
    public static final SoundEvent IMPACT_DRAGON;
    @JvmField
    @NotNull
    public static final SoundEvent IMPACT_ELECTRIC;
    @JvmField
    @NotNull
    public static final SoundEvent IMPACT_FAIRY;
    @JvmField
    @NotNull
    public static final SoundEvent IMPACT_FIGHTING;
    @JvmField
    @NotNull
    public static final SoundEvent IMPACT_FIRE;
    @JvmField
    @NotNull
    public static final SoundEvent IMPACT_FLYING;
    @JvmField
    @NotNull
    public static final SoundEvent IMPACT_GHOST;
    @JvmField
    @NotNull
    public static final SoundEvent IMPACT_GRASS;
    @JvmField
    @NotNull
    public static final SoundEvent IMPACT_GROUND;
    @JvmField
    @NotNull
    public static final SoundEvent IMPACT_ICE;
    @JvmField
    @NotNull
    public static final SoundEvent IMPACT_POISON;
    @JvmField
    @NotNull
    public static final SoundEvent IMPACT_PSYCHIC;
    @JvmField
    @NotNull
    public static final SoundEvent IMPACT_ROCK;
    @JvmField
    @NotNull
    public static final SoundEvent IMPACT_STEEL;
    @JvmField
    @NotNull
    public static final SoundEvent IMPACT_WATER;
    @JvmField
    @NotNull
    public static final SoundType TUMBLESTONE_SOUNDS;
    @JvmField
    @NotNull
    public static final SoundType TUMBLESTONE_BLOCK_SOUNDS;
    @JvmField
    @NotNull
    public static final SoundType BERRY_BUSH_SOUNDS;
    @JvmField
    @NotNull
    public static final SoundType BIG_ROOT_SOUNDS;
    @JvmField
    @NotNull
    public static final SoundType ENERGY_ROOT_SOUNDS;
    @JvmField
    @NotNull
    public static final SoundType MEDICINAL_LEEK_SOUNDS;
    @JvmField
    @NotNull
    public static final SoundType VIVICHOKE_SOUNDS;
    @JvmField
    @NotNull
    public static final SoundType MINT_SOUNDS;
    @JvmField
    @NotNull
    public static final SoundType REVIVAL_HERB_SOUNDS;
    @JvmField
    @NotNull
    public static final SoundType GILDED_CHEST_SOUNDS;
    @JvmField
    @NotNull
    public static final SoundEvent DISPLAY_CASE_ADD_ITEM;
    @JvmField
    @NotNull
    public static final SoundEvent DISPLAY_CASE_REMOVE_ITEM;
    @JvmField
    @NotNull
    public static final SoundEvent DISPLAY_CASE_BREAK;
    @JvmField
    @NotNull
    public static final SoundEvent DISPLAY_CASE_HIT;
    @JvmField
    @NotNull
    public static final SoundEvent DISPLAY_CASE_PLACE;
    @JvmField
    @NotNull
    public static final SoundEvent DISPLAY_CASE_STEP;
    @JvmField
    @NotNull
    public static final SoundType DISPLAY_CASE_SOUNDS;

    private CobblemonSounds() {
    }

    @Override
    @NotNull
    public Registry<SoundEvent> getRegistry() {
        return registry;
    }

    @Override
    @NotNull
    public ResourceKey<Registry<SoundEvent>> getRegistryKey() {
        return registryKey;
    }

    private final SoundEvent create(String name) {
        SoundEvent soundEvent = this.create(name, SoundEvent.m_262824_((ResourceLocation)MiscUtils.cobblemonResource(name)));
        Intrinsics.checkNotNullExpressionValue((Object)soundEvent, (String)"this.create(name, SoundE\u2026cobblemonResource(name)))");
        return soundEvent;
    }

    static {
        Registry registry = BuiltInRegistries.f_256894_;
        Intrinsics.checkNotNullExpressionValue((Object)registry, (String)"SOUND_EVENT");
        CobblemonSounds.registry = registry;
        ResourceKey resourceKey = Registries.f_256840_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"SOUND_EVENT");
        registryKey = resourceKey;
        GUI_CLICK = INSTANCE.create("gui.click");
        PC_ON = INSTANCE.create("pc.on");
        PC_OFF = INSTANCE.create("pc.off");
        PC_GRAB = INSTANCE.create("pc.grab");
        PC_DROP = INSTANCE.create("pc.drop");
        PC_RELEASE = INSTANCE.create("pc.release");
        PC_CLICK = INSTANCE.create("pc.click");
        HEALING_MACHINE_ACTIVE = INSTANCE.create("healing_machine.active");
        POKE_BALL_CAPTURE_STARTED = INSTANCE.create("poke_ball.capture_started");
        POKE_BALL_CAPTURE_SUCCEEDED = INSTANCE.create("poke_ball.capture_succeeded");
        POKE_BALL_SHAKE = INSTANCE.create("poke_ball.shake");
        POKE_BALL_OPEN = INSTANCE.create("poke_ball.open");
        POKE_BALL_HIT = INSTANCE.create("poke_ball.hit");
        POKE_BALL_SEND_OUT = INSTANCE.create("poke_ball.send_out");
        POKE_BALL_RECALL = INSTANCE.create("poke_ball.recall");
        POKE_BALL_THROW = INSTANCE.create("poke_ball.throw");
        POKE_BALL_TRAIL = INSTANCE.create("poke_ball.trail");
        ITEM_USE = INSTANCE.create("item.use");
        CAN_EVOLVE = INSTANCE.create("pokemon.can_evolve");
        EVOLVING = INSTANCE.create("pokemon.evolving");
        EVOLVE = INSTANCE.create("evolution.evolve");
        PVN_BATTLE = INSTANCE.create("battle.pvn.default");
        PVP_BATTLE = INSTANCE.create("battle.pvp.default");
        PVW_BATTLE = INSTANCE.create("battle.pvw.default");
        MEDICINE_HERB_USE = INSTANCE.create("medicine_herb.use");
        MEDICINE_LIQUID_USE = INSTANCE.create("medicine_liquid.use");
        MEDICINE_PILLS_USE = INSTANCE.create("medicine_pills.use");
        MEDICINE_SPRAY_USE = INSTANCE.create("medicine_spray.use");
        MEDICINE_FEATHER_USE = INSTANCE.create("medicine_feather.use");
        BERRY_HARVEST = INSTANCE.create("berry.harvest");
        BERRY_EAT = INSTANCE.create("berry.eat");
        MULCH_PLACE = INSTANCE.create("mulch.place");
        MULCH_REMOVE = INSTANCE.create("mulch.remove");
        FOSSIL_MACHINE_ACTIVATE = INSTANCE.create("fossil_machine.activate");
        FOSSIL_MACHINE_ACTIVE_LOOP = INSTANCE.create("fossil_machine.active_loop");
        FOSSIL_MACHINE_ASSEMBLE = INSTANCE.create("fossil_machine.assemble");
        FOSSIL_MACHINE_DNA_FULL = INSTANCE.create("fossil_machine.dna_full");
        FOSSIL_MACHINE_FINISHED = INSTANCE.create("fossil_machine.finished");
        FOSSIL_MACHINE_INSERT_DNA = INSTANCE.create("fossil_machine.insert_dna");
        FOSSIL_MACHINE_INSERT_DNA_SMALL = INSTANCE.create("fossil_machine.insert_dna_small");
        FOSSIL_MACHINE_INSERT_FOSSIL = INSTANCE.create("fossil_machine.insert_fossil");
        FOSSIL_MACHINE_RETRIEVE_FOSSIL = INSTANCE.create("fossil_machine.retrieve_fossil");
        FOSSIL_MACHINE_RETRIEVE_POKEMON = INSTANCE.create("fossil_machine.retrieve_pokemon");
        FOSSIL_MACHINE_UNPROTECTED = INSTANCE.create("fossil_machine.unprotected");
        RELIC_COIN_SACK_BREAK = INSTANCE.create("relic_coin_sack.break");
        RELIC_COIN_SACK_HIT = INSTANCE.create("relic_coin_sack.hit");
        RELIC_COIN_SACK_STEP = INSTANCE.create("relic_coin_sack.step");
        RELIC_COIN_SACK_PLACE = INSTANCE.create("relic_coin_sack.place");
        RELIC_COIN_POUCH_BREAK = INSTANCE.create("relic_coin_pouch.break");
        RELIC_COIN_POUCH_PLACE = INSTANCE.create("relic_coin_pouch.place");
        TUMBLESTONE_BREAK = INSTANCE.create("tumblestone.break");
        TUMBLESTONE_BLOCK_BREAK = INSTANCE.create("tumblestone.block_break");
        TUMBLESTONE_HIT = INSTANCE.create("tumblestone.hit");
        TUMBLESTONE_PLACE = INSTANCE.create("tumblestone.place");
        TUMBLESTONE_STEP = INSTANCE.create("tumblestone.step");
        GIMMIGHOUL_GIVE_ITEM_SMALL = INSTANCE.create("gimmighoul.give_item_small");
        GIMMIGHOUL_REVEAL = INSTANCE.create("gimmighoul.reveal");
        BERRY_BUSH_BREAK = INSTANCE.create("berry_bush.break");
        BERRY_BUSH_PLACE = INSTANCE.create("berry_bush.place");
        BIG_ROOT_BREAK = INSTANCE.create("big_root.break");
        ENERGY_ROOT_PLACE = INSTANCE.create("energy_root.place");
        VIVICHOKE_BREAK = INSTANCE.create("vivichoke.break");
        VIVICHOKE_PLACE = INSTANCE.create("vivichoke.place");
        MINT_BREAK = INSTANCE.create("mint.break");
        MINT_PLACE = INSTANCE.create("mint.place");
        REVIVAL_HERB_BREAK = INSTANCE.create("revival_herb.break");
        REVIVAL_HERB_PLACE = INSTANCE.create("revival_herb.place");
        MEDICINAL_LEEK_BREAK = INSTANCE.create("medicinal_leek.break");
        MEDICINAL_LEEK_PLACE = INSTANCE.create("medicinal_leek.plant");
        GILDED_CHEST_OPEN = INSTANCE.create("gilded_chest.open");
        GILDED_CHEST_CLOSE = INSTANCE.create("gilded_chest.close");
        GILDED_CHEST_STEP = INSTANCE.create("gilded_chest.step");
        GILDED_CHEST_HIT = INSTANCE.create("gilded_chest.hit");
        GILDED_CHEST_BREAK = INSTANCE.create("gilded_chest.break");
        GILDED_CHEST_PLACE = INSTANCE.create("gilded_chest.place");
        RELIC_COIN_SACK_SOUNDS = new SoundType(1.0f, 1.0f, RELIC_COIN_SACK_BREAK, RELIC_COIN_SACK_STEP, RELIC_COIN_SACK_PLACE, RELIC_COIN_SACK_HIT, RELIC_COIN_SACK_STEP);
        RELIC_COIN_POUCH_SOUNDS = new SoundType(1.0f, 1.0f, RELIC_COIN_POUCH_BREAK, RELIC_COIN_SACK_STEP, RELIC_COIN_POUCH_PLACE, RELIC_COIN_SACK_HIT, RELIC_COIN_SACK_STEP);
        IMPACT_NORMAL = INSTANCE.create("impact.normal");
        IMPACT_BUG = INSTANCE.create("impact.bug");
        IMPACT_DARK = INSTANCE.create("impact.dark");
        IMPACT_DRAGON = INSTANCE.create("impact.dragon");
        IMPACT_ELECTRIC = INSTANCE.create("impact.electric");
        IMPACT_FAIRY = INSTANCE.create("impact.fairy");
        IMPACT_FIGHTING = INSTANCE.create("impact.fighting");
        IMPACT_FIRE = INSTANCE.create("impact.fire");
        IMPACT_FLYING = INSTANCE.create("impact.flying");
        IMPACT_GHOST = INSTANCE.create("impact.ghost");
        IMPACT_GRASS = INSTANCE.create("impact.grass");
        IMPACT_GROUND = INSTANCE.create("impact.ground");
        IMPACT_ICE = INSTANCE.create("impact.ice");
        IMPACT_POISON = INSTANCE.create("impact.poison");
        IMPACT_PSYCHIC = INSTANCE.create("impact.psychic");
        IMPACT_ROCK = INSTANCE.create("impact.rock");
        IMPACT_STEEL = INSTANCE.create("impact.steel");
        IMPACT_WATER = INSTANCE.create("impact.water");
        TUMBLESTONE_SOUNDS = new SoundType(1.0f, 1.0f, TUMBLESTONE_BREAK, TUMBLESTONE_STEP, TUMBLESTONE_PLACE, TUMBLESTONE_HIT, TUMBLESTONE_STEP);
        TUMBLESTONE_BLOCK_SOUNDS = new SoundType(1.0f, 1.0f, TUMBLESTONE_BLOCK_BREAK, TUMBLESTONE_STEP, TUMBLESTONE_PLACE, TUMBLESTONE_HIT, TUMBLESTONE_STEP);
        BERRY_BUSH_SOUNDS = new SoundType(1.0f, 1.0f, BERRY_BUSH_BREAK, SoundEvents.f_11992_, BERRY_BUSH_PLACE, SoundEvents.f_11990_, SoundEvents.f_11992_);
        BIG_ROOT_SOUNDS = new SoundType(1.0f, 1.0f, BIG_ROOT_BREAK, SoundEvents.f_11903_, SoundEvents.f_11904_, SoundEvents.f_11905_, SoundEvents.f_11906_);
        ENERGY_ROOT_SOUNDS = new SoundType(1.0f, 1.0f, SoundEvents.f_11902_, SoundEvents.f_11903_, ENERGY_ROOT_PLACE, SoundEvents.f_11905_, SoundEvents.f_11906_);
        MEDICINAL_LEEK_SOUNDS = new SoundType(1.0f, 1.0f, MEDICINAL_LEEK_BREAK, SoundEvents.f_11992_, MEDICINAL_LEEK_PLACE, SoundEvents.f_11990_, SoundEvents.f_11989_);
        VIVICHOKE_SOUNDS = new SoundType(1.0f, 1.0f, VIVICHOKE_BREAK, SoundEvents.f_11992_, VIVICHOKE_PLACE, SoundEvents.f_11990_, SoundEvents.f_11989_);
        MINT_SOUNDS = new SoundType(1.0f, 1.0f, MINT_BREAK, SoundEvents.f_11992_, MINT_PLACE, SoundEvents.f_11990_, SoundEvents.f_11989_);
        REVIVAL_HERB_SOUNDS = new SoundType(1.0f, 1.0f, REVIVAL_HERB_BREAK, SoundEvents.f_11992_, REVIVAL_HERB_PLACE, SoundEvents.f_11990_, SoundEvents.f_11989_);
        GILDED_CHEST_SOUNDS = new SoundType(1.0f, 1.0f, GILDED_CHEST_BREAK, GILDED_CHEST_STEP, GILDED_CHEST_PLACE, GILDED_CHEST_HIT, GILDED_CHEST_STEP);
        DISPLAY_CASE_ADD_ITEM = INSTANCE.create("display_case.add_item");
        DISPLAY_CASE_REMOVE_ITEM = INSTANCE.create("display_case.remove_item");
        DISPLAY_CASE_BREAK = INSTANCE.create("display_case.break");
        DISPLAY_CASE_HIT = INSTANCE.create("display_case.hit");
        DISPLAY_CASE_PLACE = INSTANCE.create("display_case.place");
        DISPLAY_CASE_STEP = INSTANCE.create("display_case.step");
        DISPLAY_CASE_SOUNDS = new SoundType(1.0f, 1.0f, DISPLAY_CASE_BREAK, DISPLAY_CASE_STEP, DISPLAY_CASE_PLACE, DISPLAY_CASE_HIT, DISPLAY_CASE_STEP);
    }
}

