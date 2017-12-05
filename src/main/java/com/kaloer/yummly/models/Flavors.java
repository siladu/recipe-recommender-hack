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

import com.fasterxml.jackson.annotation.JsonProperty;


public class Flavors {
	
	private Number bitter;
	private Number meaty;
	private Number piquant;
	private Number salty;
	private Number sour;
	private Number sweet;
	
	public Number getBitter() {
		return this.bitter;
	}

	public void setBitter(Number bitter) {
		this.bitter = bitter;
	}
	
	@JsonProperty("Bitter")
	public void setBitterCapitalized(Number bitter) {
		setBitter(bitter);
	}

	public Number getMeaty() {
		return this.meaty;
	}

	public void setMeaty(Number meaty) {
		this.meaty = meaty;
	}
	
	@JsonProperty("Meaty")
	public void setMeatyCapitalized(Number meaty) {
		setMeaty(meaty);
	}

	public Number getPiquant() {
		return this.piquant;
	}

	public void setPiquant(Number piquant) {
		this.piquant = piquant;
	}
	
	@JsonProperty("Piquant")
	public void setPiquantCapitalized(Number piquant) {
		setPiquant(piquant);
	}

	public Number getSalty() {
		return this.salty;
	}

	public void setSalty(Number salty) {
		this.salty = salty;
	}
	
	@JsonProperty("Salty")
	public void setSaltyCapitalized(Number salty) {
		setSalty(salty);
	}

	public Number getSour() {
		return this.sour;
	}

	public void setSour(Number sour) {
		this.sour = sour;
	}
	
	@JsonProperty("Sour")
	public void setSourCapitalized(Number sour) {
		setSour(sour);
	}

	public Number getSweet() {
		return this.sweet;
	}

	public void setSweet(Number sweet) {
		this.sweet = sweet;
	}
	
	@JsonProperty("Sweet")
	public void setSweetCapitalized(Number sweet) {
		setSweet(sweet);
	}
}
