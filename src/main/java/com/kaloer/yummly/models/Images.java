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


public class Images {
	
	private String hostedLargeUrl;
	private String hostedMediumUrl;
	private String hostedSmallUrl;

	public String getHostedLargeUrl() {
		return this.hostedLargeUrl;
	}

	public void setHostedLargeUrl(String hostedLargeUrl) {
		this.hostedLargeUrl = hostedLargeUrl;
	}

	public String getHostedMediumUrl() {
		return this.hostedMediumUrl;
	}

	public void setHostedMediumUrl(String hostedMediumUrl) {
		this.hostedMediumUrl = hostedMediumUrl;
	}
	
	public String getHostedSmallUrl() {
		return this.hostedSmallUrl;
	}

	public void setHostedSmallUrl(String hostedSmallUrl) {
		this.hostedSmallUrl = hostedSmallUrl;
	}
}
