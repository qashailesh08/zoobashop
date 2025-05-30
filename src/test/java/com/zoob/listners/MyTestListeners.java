package com.zoob.listners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class MyTestListeners implements ITestListener {
	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("Test Started: " + result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Test Passed: " + result.getName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("Test Failed: " + result.getName());
	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println("Test Suite Started: " + context.getSuite().getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("Test Suite Finished: " + context.getSuite().getName());
	}

}
