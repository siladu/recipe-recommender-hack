/*
 * (C) Copyright 2013 Mads Kal�r
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kaloer.yummly;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaloer.yummly.models.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class for interacting with the Yummly api.
 */
public class Yummly {

	private static final String BASE_URL = "http://api.yummly.com/v1/api/";

	private String mAppId;
	private String mAppKey;

	/**
	 * Initializes a new Yummly API client instance.
	 * 
	 * @param appId
	 *            The app-id provided by Yummly.
	 * @param appKey
	 *            The app-key provided by Yummly.
	 */
	public Yummly(String appId, String appKey) {
		mAppId = appId;
		mAppKey = appKey;
	}

	/**
	 * Searches for recipes matching a given query.
	 * 
	 * @param query
	 *            The query to search for.
	 * @return The result of the search. Be aware that the the returned recipes
	 *         are sparse. Use the {@link #getRecipe(String)} method to receive
	 *         all available information.
	 * @throws IOException
	 *             Thrown if network or parsing errors occur.
	 */
	public SearchResult search(String query) throws IOException {
		return search(query, false, null, null, -1, null, null, false, false, null, null);
	}

	public SearchResult search(String query, List<String> requiredIngredients) throws IOException {
		return search(query, false, requiredIngredients, null, -1, null, null, false, false, null, null);
	}

    public SearchResult searchWithCuisine(String query, List<String> ingredients, String cuisine) throws IOException {
        return search(query, false, ingredients, null, -1, null, null, false, false, null, cuisine);
    }

	/**
	 * Searches for recipes matching a given query.
	 *
	 * @param query
	 *            The query to search for.
	 * @param requirePictures
	 *            If true, only recipes with pictures are returned.
	 * @return The result of the search. Be aware that the the returned recipes
	 *         are sparse. Use the {@link #getRecipe(String)} method to receive
	 *         all available information.
	 * @throws IOException
	 *             Thrown if network or parsing errors occur.
	 */
	public SearchResult search(String query, boolean requirePictures)
			throws IOException {
		return search(query, requirePictures, null, null, -1, null, null, false, false, null, null);
	}

	/**
	 * Searches for recipes matching a given query.
	 *
	 * @param query
	 *            The query to search for.
	 * @param requirePictures
	 *            If true, only recipes with pictures are returned.
	 * @param requiredIngredients
	 *            A list of required ingredient names in the recipes.
	 * @param excludedIngredients
	 *            A list of excluded ingredient names in the recipes.
	 * @param maxTotalTimeInSeconds
	 *            The maximum total preparetion and cooking time in seconds.
	 * @param minFlavors
	 *            The minimum flavor levels.
	 * @param maxFlavors
	 *            The maximum flavor levels.
	 * @param ingredientFacetField
	 *            If true, the result will contain the total count of each
	 *            ingredient in the matched recipes.
	 * @param dietFacetField
	 *            If true, the result will contain the total count of each
	 *            diet in the matched recipes.
	 * @param nutritionRanges
	 * @param cuisine
     * @return The result of the search. Be aware that the the returned recipes
	 *         are sparse. Use the {@link #getRecipe(String)} method to receive
	 *         all available information.
	 * @throws IOException
	 *             Thrown if network or parsing errors occur.
	 */
	public SearchResult search(String query, boolean requirePictures,
                               List<String> requiredIngredients,
                               List<String> excludedIngredients, int maxTotalTimeInSeconds,
                               Flavors minFlavors, Flavors maxFlavors,
                               boolean ingredientFacetField, boolean dietFacetField,
                               List<NutritionRange> nutritionRanges, final String cuisine)
			throws IOException {

		// Set parameters.
		ArrayList<Parameter> params = new ArrayList<Parameter>();
		params.add(new Parameter("q", URLEncoder.encode(query, "utf8")));
		params.add(new Parameter("requirePictures", requirePictures ? "true"
				: "false"));
		if (requiredIngredients != null) {
			for (String ingredient : requiredIngredients) {
				params.add(new Parameter(URLEncoder.encode(
						"requiredIngredient[]", "utf8"), URLEncoder.encode(
						ingredient, "utf8")));
			}
		}
		if (excludedIngredients != null) {
			for (String ingredient : excludedIngredients) {
				params.add(new Parameter(URLEncoder.encode(
						"excludedIngredient[]", "utf8"), URLEncoder.encode(
						ingredient, "utf8")));
			}
		}
        if (cuisine != null) {
            params.add(new Parameter(URLEncoder.encode(
                "allowedCuisine[]", "utf8"), URLEncoder.encode(cuisine, "utf8")
            ));
        }

		if (ingredientFacetField) {
			params.add(new Parameter(URLEncoder.encode("facetField[]", "utf8"),
					URLEncoder.encode("ingredient", "utf8")));
		}

		if (dietFacetField) {
			params.add(new Parameter(URLEncoder.encode("facetField[]", "utf8"),
					URLEncoder.encode("diet", "utf8")));
		}

		if (maxTotalTimeInSeconds > 0) {
			params.add(new Parameter("maxTotalTimeInSeconds", Integer
					.toString(maxTotalTimeInSeconds)));
		}

		if (minFlavors != null) {
			if (minFlavors.getSweet() != null) {
				params.add(new Parameter("flavor.sweet.min", minFlavors
						.getSweet().toString()));
			}
			if (minFlavors.getBitter() != null) {
				params.add(new Parameter("flavor.bitter.min", minFlavors
						.getBitter().toString()));
			}
			if (minFlavors.getMeaty() != null) {
				params.add(new Parameter("flavor.meaty.min", minFlavors
						.getMeaty().toString()));
			}
			if (minFlavors.getPiquant() != null) {
				params.add(new Parameter("flavor.piquant.min", minFlavors
						.getPiquant().toString()));
			}
			if (minFlavors.getSalty() != null) {
				params.add(new Parameter("flavor.salty.min", minFlavors
						.getSalty().toString()));
			}
			if (minFlavors.getSour() != null) {
				params.add(new Parameter("flavor.sour.min", minFlavors
						.getSour().toString()));
			}
		}

		if (maxFlavors != null) {
			if (maxFlavors.getSweet() != null) {
				params.add(new Parameter("flavor.sweet.max", maxFlavors
						.getSweet().toString()));
			}
			if (maxFlavors.getBitter() != null) {
				params.add(new Parameter("flavor.bitter.max", maxFlavors
						.getBitter().toString()));
			}
			if (maxFlavors.getMeaty() != null) {
				params.add(new Parameter("flavor.meaty.max", maxFlavors
						.getMeaty().toString()));
			}
			if (maxFlavors.getPiquant() != null) {
				params.add(new Parameter("flavor.piquant.max", maxFlavors
						.getPiquant().toString()));
			}
			if (maxFlavors.getSalty() != null) {
				params.add(new Parameter("flavor.salty.max", maxFlavors
						.getSalty().toString()));
			}
			if (maxFlavors.getSour() != null) {
				params.add(new Parameter("flavor.sour.max", maxFlavors
						.getSour().toString()));
			}
		}

		if (nutritionRanges != null) {
			for (NutritionRange range : nutritionRanges) {
				params.add(new Parameter(URLEncoder.encode(
						String.format("nutrition.%s.max", range.getNutrition().toString()), "utf8"), range.getMax().toString()));
				params.add(new Parameter(URLEncoder.encode(
						String.format("nutrition.%s.min", range.getNutrition().toString()), "utf8"), range.getMin().toString()));
			}
		}

		// Perform search request.
		InputStream in = performRequest("recipes", params);
		// Parse json.
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        SearchResult result = mapper.readValue(in, SearchResult.class);

		in.close();

		return result;
	}

	/**
	 * Requests information about a recipe with the given id.
	 *
	 * @param recipeId
	 *            The id of the recipe.
	 * @return The requested recipe.
	 * @throws IOException
	 *             Thrown if network errors or parsing errors occur.
	 */
	public Recipe getRecipe(String recipeId) throws IOException {

		// Perform recipe request.
		InputStream in = performRequest(
				String.format("recipe/%s", URLEncoder.encode(recipeId, "utf8")),
				null);
		// Parse json.
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


		Recipe result = mapper.readValue(in, Recipe.class);

		in.close();

		return result;
	}

	/**
	 * Performs the actual HTTP-request.
	 *
	 * @param appendedUrl
	 *            The specific URL-extension of the api.
	 * @param parameters
	 *            The url parameters.
	 * @return An inputstream of the response.
	 * @throws IOException
	 *             Thrown if network errors occur.
	 */
	private InputStream performRequest(String appendedUrl,
			ArrayList<Parameter> parameters) throws IOException {
		StringBuilder paramString = new StringBuilder();
		// Set parameters.
		if (parameters != null) {
			boolean firstLoop = true;
			for (Parameter param : parameters) {
				if (firstLoop) {
					paramString.append("?");
					firstLoop = false;
				} else {
					paramString.append("&");
				}
				paramString.append(param.key);
				paramString.append("=");
				paramString.append(param.value);
			}
		}

		URL endpoint = new URL(BASE_URL + appendedUrl + paramString);
		System.out.println(endpoint);
		URLConnection urlCon = endpoint.openConnection();
		// Add header fields.
		urlCon.setRequestProperty("X-Yummly-App-ID", mAppId);
		urlCon.setRequestProperty("X-Yummly-App-Key", mAppKey);

		return urlCon.getInputStream();
	}
}
