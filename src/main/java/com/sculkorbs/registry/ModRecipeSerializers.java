package com.sculkorbs.registry;

import com.sculkorbs.SculkOrbsMod;
import com.sculkorbs.recipe.PerfectEnchantCraftingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, SculkOrbsMod.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<PerfectEnchantCraftingRecipe>> PERFECT_ENCHANT_CRAFTING =
            RECIPE_SERIALIZERS.register("perfect_enchant_crafting",
                    () -> new SimpleCraftingRecipeSerializer<>(PerfectEnchantCraftingRecipe::new));
}