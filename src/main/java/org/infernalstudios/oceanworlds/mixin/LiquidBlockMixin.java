package org.infernalstudios.oceanworlds.mixin;

import org.infernalstudios.oceanworlds.OceanWorlds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(LiquidBlock.class)
public class LiquidBlockMixin {

	@Inject(method = "shouldSpreadLiquid", at = @At("HEAD"), cancellable = true)
	private void oceanWorlds$shouldSpreadLiquid(Level level, BlockPos blockPos, BlockState blockState, CallbackInfoReturnable<Boolean> ci) {
		if (level.getBlockState(blockPos).is(OceanWorlds.FALSE_WATER.get())) {
			ci.setReturnValue(false);
		}
	}

}
