package org.infernalstudios.oceanworlds.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockAndTintGetter;

@Mixin(BiomeColors.class)
public abstract class BiomeColorsMixin {

	@Inject(method = "getAverageWaterColor", at = @At("HEAD"), cancellable = true)
	private static void oceanWorlds$getAverageWaterColor(BlockAndTintGetter level, BlockPos pos, CallbackInfoReturnable<Integer> ci) {
		String loc = level.getBlockState(pos).getBlock().builtInRegistryHolder().key().location().getPath();

		if (loc.endsWith("_false_water")) {
			String dyecol = loc.split("_false_water")[0];
			DyeColor col = DyeColor.byName(dyecol, DyeColor.BLUE);
			ci.setReturnValue(col.getTextColor());
		} else if (loc.endsWith("_full_water")) {
			String dyecol = loc.split("_full_water")[0];
			DyeColor col = DyeColor.byName(dyecol, DyeColor.BLUE);
			ci.setReturnValue(col.getTextColor());
		}

	}

}
