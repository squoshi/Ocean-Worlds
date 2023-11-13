package org.infernalstudios.oceanworlds.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

@Mixin(FlowingFluid.class)
public interface FlowingFluidAccessor {

	@Invoker
	boolean callCanHoldFluid(BlockGetter world, BlockPos pos, BlockState state, Fluid fluid);

}
