package com.github.vectorwing.farmersdelight.setup;

import com.github.vectorwing.farmersdelight.FarmersDelight;
import com.github.vectorwing.farmersdelight.init.ItemInit;
import net.minecraft.block.ComposterBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = FarmersDelight.MODID)
public class CommonEventHandler
{
	private static final Logger LOGGER = LogManager.getLogger();
	private static final ResourceLocation SHIPWRECK_SUPPLY_CHEST = new ResourceLocation("minecraft", "chests/shipwreck_supply");

	public static void init(final FMLCommonSetupEvent event)
	{
		ComposterBlock.CHANCES.put(ItemInit.CABBAGE_SEEDS.get(), 0.3F);
		ComposterBlock.CHANCES.put(ItemInit.TOMATO_SEEDS.get(), 0.3F);
		ComposterBlock.CHANCES.put(ItemInit.CABBAGE.get(), 0.65F);
		ComposterBlock.CHANCES.put(ItemInit.ONION.get(), 0.65F);
		ComposterBlock.CHANCES.put(ItemInit.TOMATO.get(), 0.65F);
	}

	@SubscribeEvent
	public static void onLootLoad(LootTableLoadEvent event) {
		String prefix = "minecraft:chests/village/";
		String name = event.getName().toString();

		if (event.getName().equals(SHIPWRECK_SUPPLY_CHEST)) {
			event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(FarmersDelight.MODID, "inject/shipwreck_supply"))).build());
		}

		if (name.startsWith(prefix)) {
			String file = name.substring(name.indexOf(prefix) + prefix.length());
			switch (file) {
				case "village_plains_house":
				case "village_savanna_house":
				case "village_desert_house":
				case "village_snowy_house":
				case "village_taiga_house":
					event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(FarmersDelight.MODID, "inject/" + file))).build());
					LOGGER.info("[FD] Loaded " + file + " loot pool.");
					break;
				default:
					break;
			}
		}
	}
}