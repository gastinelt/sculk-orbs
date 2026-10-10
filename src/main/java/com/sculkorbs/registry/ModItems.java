package com.sculkorbs.registry;

import com.sculkorbs.SculkOrbsMod;
import com.sculkorbs.item.PerfectEnchantItem;
import com.sculkorbs.item.SculkOrbItem;
import com.sculkorbs.item.SculkOrbTier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SculkOrbsMod.MOD_ID);

    // --- Les 5 Orbes de Sculk ---
    public static final DeferredItem<SculkOrbItem> MASSIVE_ORB = ITEMS.register("massive_orb",
            () -> new SculkOrbItem(SculkOrbTier.MASSIVE, new Item.Properties().rarity(Rarity.COMMON).stacksTo(64)));

    public static final DeferredItem<SculkOrbItem> GIANT_ORB = ITEMS.register("giant_orb",
            () -> new SculkOrbItem(SculkOrbTier.GIANT, new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(64)));

    public static final DeferredItem<SculkOrbItem> COLOSSAL_ORB = ITEMS.register("colossal_orb",
            () -> new SculkOrbItem(SculkOrbTier.COLOSSAL, new Item.Properties().rarity(Rarity.RARE).stacksTo(64)));

    public static final DeferredItem<SculkOrbItem> MYTHIC_ORB = ITEMS.register("mythic_orb",
            () -> new SculkOrbItem(SculkOrbTier.MYTHIC, new Item.Properties().rarity(Rarity.EPIC).stacksTo(64)));

    public static final DeferredItem<SculkOrbItem> MAXIMAL_ORB = ITEMS.register("maximal_orb",
            () -> new SculkOrbItem(SculkOrbTier.MAXIMAL, new Item.Properties().rarity(Rarity.EPIC).stacksTo(64)));

    // --- Les 5 Niveaux de Perfect Enchant ---
    public static final DeferredItem<PerfectEnchantItem> PERFECT_ENCHANT_1 = ITEMS.register("perfect_enchant_1",
            () -> new PerfectEnchantItem(1, new Item.Properties().rarity(Rarity.COMMON).stacksTo(16)));

    public static final DeferredItem<PerfectEnchantItem> PERFECT_ENCHANT_2 = ITEMS.register("perfect_enchant_2",
            () -> new PerfectEnchantItem(2, new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(16)));

    public static final DeferredItem<PerfectEnchantItem> PERFECT_ENCHANT_3 = ITEMS.register("perfect_enchant_3",
            () -> new PerfectEnchantItem(3, new Item.Properties().rarity(Rarity.RARE).stacksTo(16)));

    public static final DeferredItem<PerfectEnchantItem> PERFECT_ENCHANT_4 = ITEMS.register("perfect_enchant_4",
            () -> new PerfectEnchantItem(4, new Item.Properties().rarity(Rarity.EPIC).stacksTo(16)));

    public static final DeferredItem<PerfectEnchantItem> PERFECT_ENCHANT_5 = ITEMS.register("perfect_enchant_5",
            () -> new PerfectEnchantItem(5, new Item.Properties().rarity(Rarity.EPIC).stacksTo(16)));
}