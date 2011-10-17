/*******************************************************************************
 * Copyright (c) 2011 IBM Corporation.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution. 
 *
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *
 *    Steve Speicher - initial API and implementation
 *******************************************************************************/
package org.eclipse.lyo.testsuite.server.oslcv2tests;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathException;

import org.junit.Before;
import org.xml.sax.SAXException;

/**
 * This class provides JUnit tests for the basic validation of query factories
 * as specified in the OSLC version 2 spec. This version of the query tests only
 * tests the basic status code and form of the query responses, as without
 * shapes implemented it is difficult to represent the needed various templates
 * of different change request types and to query for the templates.
 */
public class SimplifiedQueryBaseTests extends TestsBase {

	protected String queryProperty;
	protected String queryPropertyValue;
	protected String queryComparisonProperty;
	protected String queryComparisonValue;
	protected String fullTextSearchTerm;
	protected String additionalParameters;

	public SimplifiedQueryBaseTests(String thisUri) {
		super(thisUri);
	}

	@Before
	public void setup() throws IOException, ParserConfigurationException,
			SAXException, XPathException {
		super.setup();
		queryProperty = setupProps.getProperty("queryEqualityProperty");
		queryPropertyValue = setupProps.getProperty("queryEqualityValue");
		queryComparisonProperty = setupProps
				.getProperty("queryComparisonProperty");
		queryComparisonValue = setupProps.getProperty("queryComparisonValue");
		fullTextSearchTerm = setupProps.getProperty("fullTextSearchTerm");
		additionalParameters = setupProps
				.getProperty("queryAdditionalParameters");
		if (additionalParameters == null)
			additionalParameters = "";
	}

	protected String getQueryBase() {
		String query = (additionalParameters.length() == 0) ? "?" : "?"
				+ additionalParameters + "&";
		return query;
	}

	protected String getQueryUrlForalidEqualsQueryContainsExpectedResources()
			throws UnsupportedEncodingException {
		String query = getQueryBase() + "oslc.where=" + queryProperty
				+ URLEncoder.encode("=\"" + queryPropertyValue + "\"", "UTF-8")
				+ "&oslc.select=" + queryProperty;
		return query;
	}

	protected String getQueryUrlForValidNotEqualQueryContainsExpectedResources()
			throws UnsupportedEncodingException {
		return getQueryBase()
				+ "oslc.where="
				+ queryProperty
				+ URLEncoder
						.encode("!=\"" + queryPropertyValue + "\"", "UTF-8")
				+ "&oslc.select=" + queryProperty;
	}

	protected String getQueryUrlForValidLessThanQueryContainsExpectedResources()
			throws UnsupportedEncodingException {
		return getQueryBase()
				+ "oslc.where="
				+ queryComparisonProperty
				+ URLEncoder.encode("<\"" + queryComparisonValue + "\"",
						"UTF-8") + "&oslc.select=" + queryComparisonProperty;
	}

	protected String getQueryUrlForValidGreaterThanQueryContainsExpectedResources()
			throws UnsupportedEncodingException {
		return getQueryBase()
				+ "oslc.where="
				+ queryComparisonProperty
				+ URLEncoder.encode(">=\"" + queryComparisonValue + "\"",
						"UTF-8") + "&oslc.select=" + queryComparisonProperty;
	}

	protected String getQueryUrlForValidCompoundQueryContainsExpectedResources()
			throws UnsupportedEncodingException {
		return getQueryBase()
				+ "oslc.where="
				+ queryProperty
				+ URLEncoder.encode("=\"" + queryPropertyValue + "\" and "
						+ queryComparisonProperty + ">=\""
						+ queryComparisonValue + "\"", "UTF-8")
				+ "&oslc.select=" + queryProperty + ","
				+ queryComparisonProperty;
	}

	protected String getQueryUrlForFullTextSearchContainsExpectedResults()
			throws UnsupportedEncodingException {
		return getQueryBase() + "oslc.searchTerms="
				+ URLEncoder.encode("\"" + fullTextSearchTerm + "\"", "UTF-8");
	}

}