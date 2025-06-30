package com.automation.runners;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * Cucumber Test Runner for UI Automation
 * 
 * This class serves as the entry point for running Cucumber feature files.
 * It configures Cucumber to scan for feature files and step definitions.
 * 
 * Configuration:
 * - Features: src/test/resources/features
 * - Step Definitions: com.automation.stepdefinitions
 * - Plugin: Pretty console output, JSON report, HTML report, Allure reporting
 * 
 * Usage:
 * Run this class as JUnit test to execute all Cucumber scenarios
 * Or use: ./gradlew test --tests "CucumberTestRunner"
 * 
 * @author QA Automation Framework
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.automation.stepdefinitions,com.automation.hooks")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty, json:build/cucumber-reports/cucumber.json, html:build/cucumber-reports/cucumber.html, io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
public class CucumberTestRunner {
    // This class serves as a runner for Cucumber tests
    // No additional code is needed - annotations handle the configuration
}
