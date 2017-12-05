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

/**
 * A recipe search result containing matches, counts, etc.
 */
public class SearchResult {

	private int totalMatchCount;
	private Attribution attribution;
	private ArrayList<Recipe> matches;
	private Criteria criteria;
	private HashMap<String, HashMap<String, Number>> facetCounts;

	public void setTotalMatchCount(int totalMatchCount) {
		this.totalMatchCount = totalMatchCount;
	}

	public int getTotalMatchCount() {
		return totalMatchCount;
	}

	public void setAttribution(Attribution attribution) {
		this.attribution = attribution;
	}

	public Attribution getAttribution() {
		return attribution;
	}

	public void setMatches(ArrayList<Recipe> matches) {
		this.matches = matches;
	}

	public ArrayList<Recipe> getMatches() {
		return matches;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public void setFacetCounts(HashMap<String, HashMap<String, Number>> facetCounts) {
		this.facetCounts = facetCounts;
	}

	public HashMap<String, HashMap<String, Number>> getFacetCounts() {
		return facetCounts;
	}

}
