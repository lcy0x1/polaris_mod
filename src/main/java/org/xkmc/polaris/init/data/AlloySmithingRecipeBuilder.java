package org.xkmc.polaris.init.data;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import org.xkmc.polaris.init.registry.PolarisRecipeTypes;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class AlloySmithingRecipeBuilder extends ShapedRecipeBuilder {

	public AlloySmithingRecipeBuilder(IItemProvider result, int count) {
		super(result, count);
	}

	public void save(Consumer<IFinishedRecipe> pvd, ResourceLocation id) {
		this.ensureValid(id);
		this.advancement.parent(new ResourceLocation("recipes/root"))
				.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
				.rewards(AdvancementRewards.Builder.recipe(id)).requirements(IRequirementsStrategy.OR);
		ItemGroup group = this.result.getItemCategory();
		String folder = (group == null ? "nil" : group.getRecipeFolderName()) + "/";
		pvd.accept(new Result(id, this.result, this.count, this.group == null ? "" : this.group,
				this.rows, this.key, this.advancement, new ResourceLocation(id.getNamespace(),
				"recipes/" + folder + id.getPath())));
	}


	public AlloySmithingRecipeBuilder unlockedBy(RegistrateRecipeProvider pvd, IItemProvider item) {
		this.advancement.addCriterion("has_" + pvd.safeName(item.asItem()),
				DataIngredient.items(item.asItem()).getCritereon(pvd));
		return this;
	}

	class Result extends ShapedRecipeBuilder.Result {

		public Result(ResourceLocation id, Item result, int count, String group, List<String> pattern,
					  Map<Character, Ingredient> key, Advancement.Builder advancement, ResourceLocation path) {
			super(id, result, count, group, pattern, key, advancement, path);
		}

		@Override
		public IRecipeSerializer<?> getType() {
			return PolarisRecipeTypes.RS_ALLOY_SMITHING.get();
		}
	}


}
