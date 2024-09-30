package org.infernalstudios.oceanworlds.mixin;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

import net.minecraftforge.fml.ModList;
import org.infernalstudios.oceanworlds.OceanWorlds;
import org.infernalstudios.oceanworlds.compat.ValkyrienSkiesCompat;
import org.infernalstudios.oceanworlds.config.FastNoiseSampler;
import org.infernalstudios.oceanworlds.config.FastNoiseSampler.CellularSettings;
import org.infernalstudios.oceanworlds.config.FastNoiseSampler.DomainWarpFractalSettings;
import org.infernalstudios.oceanworlds.config.FastNoiseSampler.DomainWarpSettings;
import org.infernalstudios.oceanworlds.config.FastNoiseSampler.FractalSettings;
import org.infernalstudios.oceanworlds.config.FastNoiseSampler.GeneralSettings;
import org.infernalstudios.oceanworlds.config.OceanWorldsOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.datafixers.util.Either;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

@Mixin(ChunkStatus.class)
public class ChunkStatusMixin {

	@Inject(method = "lambda$static$12(Lnet/minecraft/world/level/chunk/ChunkStatus;Ljava/util/concurrent/Executor;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager;Lnet/minecraft/server/level/ThreadedLevelLightEngine;Ljava/util/function/Function;Ljava/util/List;Lnet/minecraft/world/level/chunk/ChunkAccess;)Ljava/util/concurrent/CompletableFuture;", at = @At(value = "RETURN", shift = Shift.BEFORE))
	private static void oceanWorlds$features(ChunkStatus chunkStatus, Executor executor, ServerLevel level,
											 ChunkGenerator chunkGenerator, StructureTemplateManager structureManager,
											 ThreadedLevelLightEngine lightLevelEngine,
											 Function f,
											 List chunks, ChunkAccess chunk,
											 CallbackInfoReturnable<CompletableFuture> ci) {

		if (chunkGenerator instanceof NoiseBasedChunkGeneratorAccessor noiseChunkGenerator) {

			if (noiseChunkGenerator.getSettings().value().defaultFluid().getBlock() instanceof LiquidBlock liquid) {

				if (level.dimension().equals(Level.OVERWORLD) || level.dimension().equals(Level.NETHER)) {
					BlockState defaultFluid = noiseChunkGenerator.getSettings().value().defaultFluid();
					int height = !defaultFluid.is(Blocks.WATER) ? OceanWorldsOptions.getOceanLavaHeight()
							: OceanWorldsOptions.getOceanHeight();
					int scale = !defaultFluid.is(Blocks.WATER) ? OceanWorldsOptions.getOceanLavaScale()
							: OceanWorldsOptions.getOceanScale();
					WorldGenRegion worldgenregion = new WorldGenRegion(level, chunks, chunkStatus, 1);
					FastNoiseSampler noise = new FastNoiseSampler(GeneralSettings.get(), FractalSettings.get(),
						CellularSettings.get(), DomainWarpSettings.get(), DomainWarpFractalSettings.get());

					boolean fillChunk = true;
					if (ModList.get().isLoaded("valkyrienskies")) {
						fillChunk = ValkyrienSkiesCompat.shouldFillChunkWithWater(chunk, level);
					}
					if (!fillChunk) return;

					for (int x = 0; x < 16; x++) {

						for (int z = 0; z < 16; z++) {
							BlockPos noisePos = chunk.getPos().getWorldPosition().offset(x, 0, z);
							double extra = (noise
								.GetNoise(noisePos.getX(), noisePos.getZ(), worldgenregion.getSeed() ^ ~2) + 1) * scale;

							for (int y = chunk.getMinBuildHeight(); y <= height + (int) Math.ceil(extra); y++) {
								BlockPos pos = noisePos.above(y);
								BlockState state = worldgenregion.getBlockState(pos);
								BlockState fluid = defaultFluid.is(Blocks.WATER)
										? OceanWorlds.FALSE_WATER.get().defaultBlockState()
										: OceanWorlds.FALSE_LAVA.get().defaultBlockState();

								if ((y + 1) > height + Math.ceil(extra)) {
									int val = 8 - (int) Math.floor((extra % 1) * 8.0D);

									if (val < 8) {
										fluid = fluid.setValue(LiquidBlock.LEVEL, val);
									} else {
										continue;
									}

								}

								if (state.is(Blocks.AIR) || ((FlowingFluidAccessor) liquid.getFluid())
									.callCanHoldFluid(worldgenregion, pos, state,
										liquid.getFluid()) || state.is(defaultFluid.getBlock())) {
									worldgenregion.setBlock(pos, fluid, Block.UPDATE_NONE, 0);
								} else if (state.hasProperty(BlockStateProperties.WATERLOGGED) && defaultFluid
									.is(Blocks.WATER)) {
									worldgenregion
										.setBlock(pos, state.setValue(BlockStateProperties.WATERLOGGED, true),
											Block.UPDATE_NONE, 0);
								}

							}

						}

					}

				}

			}

		}

	}

}
