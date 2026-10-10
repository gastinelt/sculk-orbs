package com.sculkorbs;

import com.sculkorbs.event.AnvilEnchantHandler;
import com.sculkorbs.event.SculkDropHandler;
import com.sculkorbs.registry.ModCreativeTabs;
import com.sculkorbs.registry.ModItems;
import com.sculkorbs.registry.ModRecipeSerializers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SculkOrbsMod.MOD_ID)
public class SculkOrbsMod {

    public static final String MOD_ID = "sculkorbs";
    public static final Logger LOGGER = LoggerFactory.getLogger(SculkOrbsMod.class);

    public SculkOrbsMod(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("[SculkOrbs] Initialisation du mod Sculk Orbs & Perfect Enchant 1.21.1...");

        // Enregistrement des items, onglets et sérialiseurs de recettes
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);

        // Enregistrement des gestionnaires d'événements NeoForge
        NeoForge.EVENT_BUS.register(SculkDropHandler.class);
        NeoForge.EVENT_BUS.register(AnvilEnchantHandler.class);

        LOGGER.info("[SculkOrbs] Sculk Orbs, Perfect Enchant et Enclume universelle prêts !");
    }
}