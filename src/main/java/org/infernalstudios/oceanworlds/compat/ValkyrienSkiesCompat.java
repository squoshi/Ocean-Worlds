package org.infernalstudios.oceanworlds.compat;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

public class ValkyrienSkiesCompat {
    // NOTE: This does NOT allow ships to work on this mod's false water
    // Use the Block Swap mod to replace the false water with real water
    public static boolean shouldFillChunkWithWater(ChunkAccess chunk, Level level) {
        return !VSGameUtilsKt.isChunkInShipyard(level, chunk.getPos().x, chunk.getPos().z);
    }
}
