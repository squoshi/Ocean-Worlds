package org.infernalstudios.oceanworlds.block;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FullWaterBlock extends NonFlowingLiquidBlock {

	public FullWaterBlock(Supplier<? extends FlowingFluid> p_54694_, Properties p_54695_, Block fallback) {
		super(p_54694_, p_54695_, fallback);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState p_54760_, BlockGetter p_54761_, BlockPos p_54762_, CollisionContext p_54763_) {
		return Block.box(0.0, 0.0, 0.0, 1.0,
				p_54760_.getValue(LiquidBlock.LEVEL) > 7 ? 1.0F : p_54760_.getValue(LiquidBlock.LEVEL) == 0 ? 1.0F : (Mth.abs(p_54760_.getValue(LiquidBlock.LEVEL) - 7) / 6.0F), 1.0);
	}

}
