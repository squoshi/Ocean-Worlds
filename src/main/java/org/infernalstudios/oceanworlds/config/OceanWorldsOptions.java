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
package org.infernalstudios.oceanworlds.config;

import java.io.File;

import org.infernalstudios.oceanworlds.config.FastNoiseSampler.CellularSettings;
import org.infernalstudios.oceanworlds.config.FastNoiseSampler.DomainWarpFractalSettings;
import org.infernalstudios.oceanworlds.config.FastNoiseSampler.DomainWarpSettings;
import org.infernalstudios.oceanworlds.config.FastNoiseSampler.FractalSettings;
import org.infernalstudios.oceanworlds.config.FastNoiseSampler.GeneralSettings;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public class OceanWorldsOptions {

	private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
	private static ConfigValue<Integer> oceanHeight;
	private static ConfigValue<Integer> oceanScale;
	private static ConfigValue<Integer> oceanLavaHeight;
	private static ConfigValue<Integer> oceanLavaScale;
	private static ForgeConfigSpec config;

	public static void init() {
		builder.comment("Ocean Worlds");
		oceanHeight = builder.comment("Ocean Height").define("oceanworlds.height.ocean", 63);
		oceanScale = builder.comment("Ocean Scale").define("oceanworlds.scale.ocean", 1);
		oceanLavaHeight = builder.comment("Lava Ocean Height").define("oceanworlds.height.lava", 63);
		oceanLavaScale = builder.comment("Lava Ocean Scale").define("oceanworlds.scale.lava", 1);

		GeneralSettings.config(builder);
		FractalSettings.config(builder);
		CellularSettings.config(builder);
		DomainWarpSettings.config(builder);
		DomainWarpFractalSettings.config(builder);

		config = builder.build();

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, OceanWorldsOptions.config);

		CommentedFileConfig file = CommentedFileConfig
			.builder(new File(FMLPaths.CONFIGDIR.get().resolve("oceanworlds.toml").toString()))
			.sync()
			.autosave()
			.writingMode(WritingMode.REPLACE)
			.build();
		file.load();
		config.setConfig(file);
	}

	public static int getOceanHeight() {
		return oceanHeight.get();
	}

	public static int getOceanScale() {
		return oceanScale.get();
	}

	public static int getOceanLavaHeight() {
		return oceanLavaHeight.get();
	}

	public static int getOceanLavaScale() {
		return oceanLavaScale.get();
	}

}
