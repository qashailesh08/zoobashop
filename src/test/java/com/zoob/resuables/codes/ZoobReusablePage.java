package com.zoob.resuables.codes;

import com.zoob.webutils.WebUtil;

public class ZoobReusablePage extends ZoobReusableOr {

	private WebUtil util;

	public ZoobReusablePage(WebUtil util) {
		super(util);
		this.util = util;
	}

	public void signUpBT() {
		util.click(getSignUpBT(), "Sign Up Button");
	}

	public void loginBT() {
		util.click(getLoginBT(), "Login Button");
	}

	public void contactUsLT() {
		util.click(getContactLT(), "Constact US Link");
	}

	public void addListingLT() {
		util.click(getAddListLT(), "Add a Listing Link");
	}

	public void sportLT() {
		util.click(getSportsLT(), "Sport Link");
	}

	public void directoryLT() {
		util.click(getDirectoryLT(), "Directory Link");
	}

	public void eventsLT() {
		util.click(getEventsLT(), "Events Link");
	}

	public void classifiedsLT() {
		util.click(getClassifiedsLT(), "Classifields Link");
	}

	public void jobsLT() {
		util.click(getJobsLT(), "Job Link");
	}

	public void realEstateLT() {
		util.click(getRealEstateLT(), "Real Estate Link");
	}

	public void autosLT() {
		util.click(getAutosLT(), "Autos Link");
	}

	public void newsLT() {
		util.click(getNewsLT(), "News Link");
	}

	public void searchBarTB(String searchValue) {
		util.clear(getSearchBarTB(), "Search Bas Text Box");
		util.inputValue(getSearchBarTB(), searchValue, "Search Bas Text Box");
	}

	public void searchSubmitBT() {
		util.click(getSearchSubmitBT(), "Search Submit Button");
	}

	public void advancedSearchLT() {
		util.click(getAdvancedSearchLT(), "Advanced SearchLT");
	}

	public void cmnSearchBar(String searchValue) {
		util.clear(getCmnSearchTB(), "Common Search Text Bar");
		util.inputValue(getCmnSearchTB(), searchValue, "Common Search Text Bar");
	}

	public void cmnSubmitButton() {
		util.click(getCmnSearchButton(), "Common Search Button");
	}

}
