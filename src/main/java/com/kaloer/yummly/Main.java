package com.kaloer.yummly;

import com.kaloer.yummly.models.Flavors;
import com.kaloer.yummly.models.NutritionRange;
import com.kaloer.yummly.models.NutritionRange.NUTRITION;
import com.kaloer.yummly.models.Recipe;
import com.kaloer.yummly.models.SearchResult;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		Yummly y = new Yummly("87489517", "bafc256f1e442c00e57753cdd653636d");
		SearchResult result;
		try {
			Flavors minFlavors = new Flavors();
			minFlavors.setPiquant(0.8);
			ArrayList<NutritionRange> nutrition = new ArrayList<NutritionRange>();
			NutritionRange range = new NutritionRange();
			range.setMin(5);
			range.setMax(100);
			range.setNutrition(NUTRITION.PROCNT);
			nutrition.add(range);
			result = y.search("Chicken", true, null, null, -1, minFlavors, null, true, true, nutrition, null);
			/*
			for (Recipe recipe : result.getMatches()) {
				System.out.println(String.format("%s, (%d), (%s) %s", recipe.getName(), recipe.getRating(), recipe.getId(), recipe.getImages().get(0).getHostedSmallUrl()));
			}
			*/
			Recipe recipe = y.getRecipe(result.getMatches().get(0).getId());
			System.out.println(String.format("%s, (%d), (%s) %s", recipe.getName(), recipe.getRating(), recipe.getId(), recipe.getImages().get(0).getHostedSmallUrl()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
