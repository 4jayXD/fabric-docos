package com.example.docs.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import com.example.docs.FabricDocsReference;
import com.example.docs.block.custom.PrismarineLampBlock;
import com.example.docs.item.ModItems;

// :::1
public class ModBlocks {
	// :::1

	// :::2
	public static final Block CONDENSED_DIRT = register("condensed_dirt", settings -> {
		
		settings.sounds(BlockSoundGroup.GRASS));
		
		return new Block(settings);
	},true);
	// :::2
	// :::3
	public static final Block CONDENSED_OAK_LOG = register("condensed_oak_log", settings -> {
		
		settings.sounds(BlockSoundGroup.WOOD);
		
		return new PillarBlock(settings);
	}, true);
	// :::3
	// :::4
	public static final Block PRISMARINE_LAMP = register("prismarine_lamp", settings -> {

		settings
			.sounds(BlockSoundGroup.LANTERN)
			.luminance(PrismarineLampBlock::getLuminance);
		
		return new PrismarineLampBlock(settings);
	}, true);
	// :::4
	// :::1

	private static Block reigster(String id, Function<AbstractBlock.Settings, Block> blockProvider, boolean shouldRegisterItem) {
		
		// makes identifier for the registryKeys (so you don't repeat yourself)
		Identifier identifier = Identifier.of(FabricDocsReference.MOD_ID, id);

		// creates registryKey for block. (needed)
		RegistryKey blockKey = RegistryKey.of(Registries.BLOCK, identifier);

		// creates the block & adds the block key to the settings. 
		Block block = blockProvider.apply(AbstractBlock.Settings.create().registryKey(blockKey));

		// registers block.
		Block registeredBlock = Registry.register(Registries.ITEM, blockKey, block);
		
		if (shouldRegisterItem) {
			
			// creates item registry key. (needed)
			RegistryKey itemKey = RegistryKey.of(Registries.ITEM, identifier);
			
			// registers blockItem
			Registry.register(Registries.ITEM, itemKey, new BlockItem(registeredBlock, new Item.Settings().registryKey(itemKey)));
		}
		
		// returns registered block.
		return registeredBlock;
	}

	// :::1
	public static void initialize() {
		// :::3
		ItemGroupEvents.modifyEntriesEvent(ModItems.CUSTOM_ITEM_GROUP_KEY).register((itemGroup) -> {
			itemGroup.add(ModBlocks.CONDENSED_DIRT.asItem());
		});
		// :::3

		ItemGroupEvents.modifyEntriesEvent(ModItems.CUSTOM_ITEM_GROUP_KEY).register((itemGroup) -> {
			itemGroup.add(ModBlocks.CONDENSED_OAK_LOG.asItem());
			itemGroup.add(ModBlocks.PRISMARINE_LAMP.asItem());
		});
	};

	// :::1
}
// :::1
