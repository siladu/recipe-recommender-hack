/*
 * (C) Copyright 2013 Mads Kal¿r
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

package com.kaloer.yummly.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipe {

	private Attributes attributes;
	private Attribution attribution;
	private Flavors flavors;
	private String id;
	private List<Images> images;
	private List<String> ingredientLines;
	private String name;	
	private Number numberOfServings;
	private List<NutritionEstimate> nutritionEstimates;
	private Number rating;
	private Source source;
	private String totalTime;
	private Number totalTimeInSeconds;
	private String yield;

	public Attributes getAttributes() {
		return this.attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public Attribution getAttribution() {
		return this.attribution;
	}

	public void setAttribution(Attribution attribution) {
		this.attribution = attribution;
	}

	public Flavors getFlavors() {
		return this.flavors;
	}

	public void setFlavors(Flavors flavors) {
		this.flavors = flavors;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Images> getImages() {
		return this.images;
	}

	public void setImages(List<Images> images) {
		this.images = images;
	}

	public List<String> getIngredientLines() {
		return this.ingredientLines;
	}

	@JsonProperty("ingredients")
	public void setIngredients(List<String> ingredientLines) {
		setIngredientLines(ingredientLines);
	}
	
	public void setIngredientLines(List<String> ingredientLines) {
		this.ingredientLines = ingredientLines;
	}
	
	public String getName() {
		return this.name;
	}

	@JsonProperty("recipeName")
	public void setRecipeName(String name) {
		setName(name);
	}
	
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	public Number getNumberOfServings() {
		return this.numberOfServings;
	}

	public void setNumberOfServings(Number numberOfServings) {
		this.numberOfServings = numberOfServings;
	}

	public List<NutritionEstimate> getNutritionEstimates() {
		return this.nutritionEstimates;
	}

	public void setNutritionEstimates(List<NutritionEstimate> nutritionEstimates) {
		this.nutritionEstimates = nutritionEstimates;
	}

	public Number getRating() {
		return this.rating;
	}

	public void setRating(Number rating) {
		this.rating = rating;
	}

	public Source getSource() {
		return this.source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public String getTotalTime() {
		return this.totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public Number getTotalTimeInSeconds() {
		return this.totalTimeInSeconds;
	}

	public void setTotalTimeInSeconds(Number totalTimeInSeconds) {
		this.totalTimeInSeconds = totalTimeInSeconds;
	}

	public String getYield() {
		return this.yield;
	}

	public void setYield(String yield) {
		this.yield = yield;
	}
	
	@JsonProperty("smallImageUrls")
	public void setSmallImageUrls(ArrayList<String> urls) {
		ArrayList<Images> imgs = new ArrayList<Images>();
		for (String url : urls) {
			Images i = new Images();
			i.setHostedSmallUrl(url);
			imgs.add(i);
		}
		setImages(imgs);
	}
	
	@JsonProperty("sourceDisplayName")
	public void setSourceDisplayName(String name) {
		Source source = getSource();
		if(source == null) {
			source = new Source();
		}
		source.setSourceDisplayName(name);
		setSource(source);
	}
	
	

}
