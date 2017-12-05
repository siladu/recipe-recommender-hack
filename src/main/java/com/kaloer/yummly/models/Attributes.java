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

import java.util.List;

public class Attributes {
	
	private List<String> course;
	private List<String> holiday;
	private List<String> cuisine;

	public List<String> getCourse() {
		return this.course;
	}

	public void setCourse(List<String> course) {
		this.course = course;
	}

	public List<String> getHoliday() {
		return this.holiday;
	}

	public void setHoliday(List<String> holiday) {
		this.holiday = holiday;
	}

	public List<String> getCuisine() {
		return this.cuisine;
	}

	public void setCuisine(List<String> cuisine) {
		this.cuisine = cuisine;
	}
}
