package com.example.docs.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;

import com.example.docs.FabricDocsReference;
import com.example.docs.component.ModComponents;
import com.example.docs.item.armor.ModArmorMaterials;
import com.example.docs.item.custom.CounterItem;
import com.example.docs.item.custom.LightningStick;
import com.example.docs.item.tool.GuiditeMaterial;

// :::1
public class ModItems {
	// :::1

	// :::6
	public static final Item GUIDITE_HELMET = register(settings -> {
		
		settings.maxDamage(ArmorItem.Type.HELMET.getMaxDamage(ModArmorMaterials.GUIDITE_DURABILITY_MULTIPLIER));
		
		return new ArmorItem(ModArmorMaterials.GUIDITE, ArmorItem.Type.HELMET, settings);
	}, "guidite_helmet");
	public static final Item GUIDITE_CHESTPLATE = register(settings -> {
		
		settings.maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(ModArmorMaterials.GUIDITE_DURABILITY_MULTIPLIER));
		
		return new ArmorItem(ModArmorMaterials.GUIDITE, ArmorItem.Type.CHESTPLATE, settings);
	}, "guidite_chestplate");
	public static final Item GUIDITE_LEGGINGS = register(settings -> {
		
		settings.maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(ModArmorMaterials.GUIDITE_DURABILITY_MULTIPLIER))
			
		return new ArmorItem(ModArmorMaterials.GUIDITE, ArmorItem.Type.LEGGINGS, settings);
	}, "guidite_leggings");
	public static final Item GUIDITE_BOOTS = register(settings -> {
		
		.maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(ModArmorMaterials.GUIDITE_DURABILITY_MULTIPLIER));
		
		return new ArmorItem(ModArmorMaterials.GUIDITE, ArmorItem.Type.BOOTS, settings);
	}, "guidite_boots");
	// :::6
	public static final Item LIGHTNING_STICK = register(LightningStick::new, "lightning_stick");
	// :::7
	public static final Item GUIDITE_SWORD = register(settings -> new SwordItem(GuiditeMaterial.INSTANCE, settings), "guidite_sword");
	// :::7
	// :::_13
	public static final Item COUNTER = register(settings -> {
		
		// Initialize the click count component with a default value of 0
		settings.component(ModComponents.CLICK_COUNT_COMPONENT, 0)
		
		return new CounterItem(settings);
	}, "counter");
	// :::_13
	// :::9
	public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(FabricDocsReference.MOD_ID, "item_group"));
	public static final ItemGroup CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
			.icon(() -> new ItemStack(ModItems.GUIDITE_SWORD))
			.displayName(Text.translatable("itemGroup.fabric_docs_reference"))
			.build();
	// :::9
	// :::5
	public static final FoodComponent POISON_FOOD_COMPONENT = new FoodComponent.Builder()
			.alwaysEdible()
			.snack()
			// The duration is in ticks, 20 ticks = 1 second
			.statusEffect(new StatusEffectInstance(StatusEffects.POISON, 6 * 20, 1), 1.0f)
			.build();
	// :::5

	// :::poisonous_apple
	public static final Item POISONOUS_APPLE = register(
			new Item(new Item.Settings().food(POISON_FOOD_COMPONENT)),
			"poisonous_apple"
	);
	// :::poisonous_apple

	// :::2
	public static final Item SUSPICIOUS_SUBSTANCE = register(
			new Item(new Item.Settings()),
			"suspicious_substance"
	);
	// :::2

	// :::1
	
	public static Item register(Function<Item.Settings, Item> itemProvider, String id) {
		
		// creates a registryKey ... I dunno how to explain this one tbh. Someone will fill this in.
		RegistryKey key = RegistryKey.of(Registries.ITEM, Identifier.of(FabricDocsReference.MOD_ID, id));

		// Returns registered item. & adds the registrykey from before to the settings :D.
		return Registry.register(Registries.ITEM, key, itemProvider.apply(new Item.Settings().registryKey(key)));
	}
	
	// :::1

	// :::3
	public static void initialize() {
		// :::3
		// :::4
		// Get the event for modifying entries in the ingredients group.
		// And register an event handler that adds our suspicious item to the ingredients group.
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
				.register((itemGroup) -> itemGroup.add(ModItems.SUSPICIOUS_SUBSTANCE));
		// :::4

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
				.register((itemGroup) -> {
					itemGroup.add(ModItems.GUIDITE_HELMET);
					itemGroup.add(ModItems.GUIDITE_BOOTS);
					itemGroup.add(ModItems.GUIDITE_LEGGINGS);
					itemGroup.add(ModItems.GUIDITE_CHESTPLATE);
				});

		// :::8
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS)
				.register((itemGroup) -> itemGroup.add(ModItems.GUIDITE_SWORD));
		// :::8

		// :::_12
		// Register the group.
		Registry.register(Registries.ITEM_GROUP, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);

		// Register items to the custom item group.
		ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
			itemGroup.add(ModItems.SUSPICIOUS_SUBSTANCE);
			itemGroup.add(ModItems.POISONOUS_APPLE);
			itemGroup.add(ModItems.GUIDITE_SWORD);
			itemGroup.add(ModItems.GUIDITE_HELMET);
			itemGroup.add(ModItems.GUIDITE_BOOTS);
			itemGroup.add(ModItems.GUIDITE_LEGGINGS);
			itemGroup.add(ModItems.GUIDITE_CHESTPLATE);
			itemGroup.add(ModItems.LIGHTNING_STICK);
			// ...
		});
		// :::_12

		// :::_10
		// Add the suspicious substance to the composting registry with a 30% chance of increasing the composter's level.
		CompostingChanceRegistry.INSTANCE.add(ModItems.SUSPICIOUS_SUBSTANCE, 0.3f);
		// :::_10

		// :::_11
		// Add the suspicious substance to the registry of fuels, with a burn time of 30 seconds.
		// Remember, Minecraft deals with logical based-time using ticks.
		// 20 ticks = 1 second.
		FuelRegistry.INSTANCE.add(ModItems.SUSPICIOUS_SUBSTANCE, 30 * 20);
		// :::_11
		// :::3
	}

	// :::3
}
// :::1
