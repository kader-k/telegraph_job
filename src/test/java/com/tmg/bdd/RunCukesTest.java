package com.tmg.bdd;

import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(features={"classpath:user_manager.feature"})
public class RunCukesTest {
}
