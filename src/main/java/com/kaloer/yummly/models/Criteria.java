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
import java.util.HashMap;

public class Criteria {

	private ArrayList<String> allowedIngredients;
	private ArrayList<String> excludedIngredients;
	private ArrayList<String> allowedAttributes;
	private ArrayList<String> excludedAttributes;
	private ArrayList<String> facetFields;
	private int maxResults;
	private boolean requirePictures;
	private int resultsToSkip;
	private ArrayList<String> terms;
	HashMap<String, NutritionRange> nutritionRestrictions;
	public HashMap<String, NutritionRange> getNutritionRestrictions() {
		return nutritionRestrictions;
	}

	public void setNutritionRestrictions(
			HashMap<String, NutritionRange> nutritionRestrictions) {
		this.nutritionRestrictions = nutritionRestrictions;
	}

	private Object attributeRanges;

	public void setAttributeRanges(Object attributeRanges) {
		this.attributeRanges = attributeRanges;
	}

	public ArrayList<String> getAllowedIngredients() {
		return allowedIngredients;
	}

	public void setAllowedIngredients(ArrayList<String> allowedIngredients) {
		this.allowedIngredients = allowedIngredients;
	}

	public ArrayList<String> getExcludedIngredients() {
		return excludedIngredients;
	}

	public void setExcludedIngredients(ArrayList<String> excludedIngredients) {
		this.excludedIngredients = excludedIngredients;
	}

	public ArrayList<String> getAllowedAttributes() {
		return allowedAttributes;
	}

	public void setAllowedAttributes(ArrayList<String> allowedAttributes) {
		this.allowedAttributes = allowedAttributes;
	}

	public ArrayList<String> getExcludedAttributes() {
		return excludedAttributes;
	}

	public void setExcludedAttributes(ArrayList<String> excludedAttributes) {
		this.excludedAttributes = excludedAttributes;
	}

	public ArrayList<String> getFacetFields() {
		return facetFields;
	}

	public void setFacetFields(ArrayList<String> facetFields) {
		this.facetFields = facetFields;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public boolean isRequirePictures() {
		return requirePictures;
	}

	public void setRequirePictures(boolean requirePictures) {
		this.requirePictures = requirePictures;
	}

	public int getResultsToSkip() {
		return resultsToSkip;
	}

	public void setResultsToSkip(int resultsToSkip) {
		this.resultsToSkip = resultsToSkip;
	}

	public ArrayList<String> getTerms() {
		return terms;
	}

	public void setTerms(ArrayList<String> terms) {
		this.terms = terms;
	}

	public Object getAttributeRanges() {
		return attributeRanges;
	}

}
