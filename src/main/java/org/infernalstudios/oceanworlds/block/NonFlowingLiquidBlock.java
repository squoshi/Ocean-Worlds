package org.infernalstudios.oceanworlds.block;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

public class NonFlowingLiquidBlock extends LiquidBlock {

	private final Block fallback;

	public NonFlowingLiquidBlock(Supplier<? extends FlowingFluid> p_54694_, Properties p_54695_, Block fallback) {
		super(p_54694_, p_54695_);
		this.fallback = fallback;
	}

	@Override
	public void onPlace(BlockState p_54754_, Level p_54755_, BlockPos p_54756_, BlockState p_54757_, boolean p_54758_) {
	}

	@Override
	public BlockState updateShape(BlockState p_54723_, Direction p_54724_, BlockState p_54725_, LevelAccessor p_54726_, BlockPos p_54727_, BlockPos p_54728_) {
		/*
		 * return p_54726_.getBlockState(p_54728_).is(fallback) ?
		 * fallback.withPropertiesOf(p_54723_) : p_54723_;
		 */
		return p_54723_;
	}

	@Override
	public void neighborChanged(BlockState p_54709_, Level p_54710_, BlockPos p_54711_, Block p_54712_, BlockPos p_54713_, boolean p_54714_) {
	}

}
