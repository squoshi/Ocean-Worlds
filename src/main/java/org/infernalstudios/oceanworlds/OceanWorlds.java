/**
 * Copyright 2022 Infernal Studios
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.infernalstudios.oceanworlds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.infernalstudios.oceanworlds.block.FullWaterBlock;
import org.infernalstudios.oceanworlds.block.NonFlowingLiquidBlock;
import org.infernalstudios.oceanworlds.config.OceanWorldsOptions;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod("oceanworlds")
public class OceanWorlds {

	public static final String NAME = "Ocean Worlds";
	public static final String MOD_ID = "oceanworlds";
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
	public static final RegistryObject<Block> FALSE_WATER = BLOCKS.register("false_water",
			() -> new NonFlowingLiquidBlock(() -> Fluids.WATER, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable(), Blocks.WATER));
	public static final RegistryObject<Block> FALSE_LAVA = BLOCKS.register("false_lava", () -> new NonFlowingLiquidBlock(() -> Fluids.LAVA,
			BlockBehaviour.Properties.of(Material.LAVA).noCollission().randomTicks().strength(100.0F).lightLevel((p_50755_) -> 15).noLootTable(), Blocks.LAVA));
	public static final RegistryObject<Block> FULL_WATER = BLOCKS.register("full_water",
			() -> new FullWaterBlock(() -> Fluids.WATER, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable(), Blocks.WATER));

	public OceanWorlds() {
		OceanWorldsOptions.init();

		for (DyeColor dye : DyeColor.values()) {
			BLOCKS.register(dye.getName() + "_false_water",
					() -> new NonFlowingLiquidBlock(() -> Fluids.WATER, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable(), Blocks.WATER));
			BLOCKS.register(dye.getName() + "_full_water",
					() -> new FullWaterBlock(() -> Fluids.WATER, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable(), Blocks.WATER));
		}

		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

}
