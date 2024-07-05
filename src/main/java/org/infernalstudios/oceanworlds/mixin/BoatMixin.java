package org.infernalstudios.oceanworlds.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;

@Mixin(Boat.class)
public abstract class BoatMixin extends Entity {

	@Shadow
	private float invFriction;
	@Shadow
	private float outOfControlTicks;
	@Shadow
	private float deltaRotation;
	@Shadow
	private int lerpSteps;
	@Shadow
	private double lerpX;
	@Shadow
	private double lerpY;
	@Shadow
	private double lerpZ;
	@Shadow
	private double lerpYRot;
	@Shadow
	private double lerpXRot;
	@Shadow
	private boolean inputLeft;
	@Shadow
	private boolean inputRight;
	@Shadow
	private boolean inputUp;
	@Shadow
	private boolean inputDown;
	@Shadow
	private double waterLevel;
	@Shadow
	private float landFriction;
	@Shadow
	private Boat.Status status;
	@Shadow
	private Boat.Status oldStatus;
	@Shadow
	private double lastYd;
	@Shadow
	private boolean isAboveBubbleColumn;
	@Shadow
	private boolean bubbleColumnDirectionIsDown;
	@Shadow
	private float bubbleMultiplier;
	@Shadow
	private float bubbleAngle;
	@Shadow
	private float bubbleAngleO;

	public BoatMixin(EntityType<?> p_19870_, Level p_19871_) {
		super(p_19870_, p_19871_);
	}

	@Inject(method = /* Whatever */ "float" /* Your */ + "Boat", at = @At("HEAD"))
	private void oceanWorlds$floatBoat(CallbackInfo ci) {
		if (this.status != Boat.Status.ON_LAND) {
			AABB aabb = this.getBoundingBox();
			int i = Mth.floor(aabb.minX);
			int j = Mth.ceil(aabb.maxX);
			int k = Mth.floor(aabb.minY) - 1;
			int l = Mth.ceil(aabb.minY + 0.001D) + 1;
			int i1 = Mth.floor(aabb.minZ);
			int j1 = Mth.ceil(aabb.maxZ);
			this.waterLevel = -Double.MAX_VALUE;
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

			for (int k1 = i; k1 < j; ++k1) {
				for (int l1 = k; l1 < l; ++l1) {
					for (int i2 = i1; i2 < j1; ++i2) {
						blockpos$mutableblockpos.set(k1, l1, i2);
						FluidState fluidstate = this.level().getFluidState(blockpos$mutableblockpos);
						if (fluidstate.is(FluidTags.WATER)) {
							float f = (float) l1 + fluidstate.getHeight(this.level(), blockpos$mutableblockpos);
							this.waterLevel = Math.max((double) f, this.waterLevel);
						}
					}
				}
			}
			if (this.waterLevel > -Double.MAX_VALUE) {
				this.setPos(this.getX(), (this.waterLevel - this.getBbHeight()) + 0.101D, this.getZ());
			}
		}
	}

	@Shadow
	public abstract float getWaterLevelAbove();

}
