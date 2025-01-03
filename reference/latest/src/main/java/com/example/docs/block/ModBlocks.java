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
	public static final Block CONDENSED_DIRT = register(
			new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRASS)),
			"condensed_dirt",
			true
	);
	// :::2
	// :::3
	public static final Block CONDENSED_OAK_LOG = register(
			new PillarBlock(
					AbstractBlock.Settings.create()
							.sounds(BlockSoundGroup.WOOD)
			), "condensed_oak_log", true
	);
	// :::3
	// :::4
	public static final Block PRISMARINE_LAMP = register(
			new PrismarineLampBlock(
					AbstractBlock.Settings.create()
							.sounds(BlockSoundGroup.LANTERN)
							.luminance(PrismarineLampBlock::getLuminance)
			), "prismarine_lamp", true
	);
	// :::4
	// :::1
	public static Block register(Block block, String name, boolean shouldRegisterItem) {
		// Register the block and its item.
		Identifier id = Identifier.of(FabricDocsReference.MOD_ID, name);

		// Sometimes, you may not want to register an item for the block.
		// Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
		if (shouldRegisterItem) {
			BlockItem blockItem = new BlockItem(block, new Item.Settings());
			Registry.register(Registries.ITEM, id, blockItem);
		}

		return Registry.register(Registries.BLOCK, id, block);
	}

	// [1.21.3 and above]
	private static Block reigster(Function<AbstractBlock.Settings, Block> blockProvider, String id, boolean shouldRegisterItem) {
		
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

	// [1.21.3 and above] for adding settings
	public static final Block EXAMPLE_BLOCK_FOR_21_3_A = register(settings -> {

		// change the settings however you want.
		
		return new Block(settings);
	}, "example_block_for_21_3_a");

	// [1.21.3 and above] for no settings
	public static final Block EXAMPLE_BLOCK_FOR_21_3_B = register(Block::new, "example_block_for_21_3_b");
	
	
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
