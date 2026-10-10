package com.sculkorbs.recipe;

import com.sculkorbs.item.PerfectEnchantItem;
import com.sculkorbs.registry.ModItems;
import com.sculkorbs.registry.ModRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class PerfectEnchantCraftingRecipe extends CustomRecipe {

    public PerfectEnchantCraftingRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return getResultTier(input) > 0;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        int targetTier = getResultTier(input);
        return switch (targetTier) {
            case 1 -> new ItemStack(ModItems.PERFECT_ENCHANT_1.get());
            case 2 -> new ItemStack(ModItems.PERFECT_ENCHANT_2.get());
            case 3 -> new ItemStack(ModItems.PERFECT_ENCHANT_3.get());
            case 4 -> new ItemStack(ModItems.PERFECT_ENCHANT_4.get());
            case 5 -> new ItemStack(ModItems.PERFECT_ENCHANT_5.get());
            default -> ItemStack.EMPTY;
        };
    }

    private int getResultTier(CraftingInput input) {
        int totalSculk = 0;
        int prevEnchantLevel = 0;
        int enchantCount = 0;

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;

            if (stack.is(Blocks.SCULK.asItem())) {
                totalSculk += stack.getCount();
            } else if (stack.getItem() instanceof PerfectEnchantItem enchantItem) {
                enchantCount++;
                prevEnchantLevel = enchantItem.getLevel();
            } else {
                return 0; // Item non autorisé dans la recette
            }
        }

        // Il faut au moins 64 blocs de sculk au total dans la grille
        if (totalSculk < 64) {
            return 0;
        }

        // Niveau 1 : 64 sculk et aucun item précédent
        if (enchantCount == 0) {
            return 1;
        }

        // Niveau N+1 : 1 item de niveau N + 64 sculk (jusqu'à 5)
        if (enchantCount == 1 && prevEnchantLevel >= 1 && prevEnchantLevel < 5) {
            return prevEnchantLevel + 1;
        }

        return 0;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        return NonNullList.withSize(input.size(), ItemStack.EMPTY);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.PERFECT_ENCHANT_CRAFTING.get();
    }
}