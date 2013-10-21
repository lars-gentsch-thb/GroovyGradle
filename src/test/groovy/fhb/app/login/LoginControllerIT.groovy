package fhb.app.login

import geb.spock.GebSpec

import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver

class LoginControllerIT extends GebSpec {

    def url = 'http://localhost:8080/GroovyGradle'
	// Uncomment to see what happens in firefox
//    WebDriver drv = new FirefoxDriver()
	// Use this in CI server.
    WebDriver drv = new HtmlUnitDriver()

    def "testing home page" () {
        when: "page loaded"
        drv.get(url);

        then: "title should be 'Hello world'"
        
        drv.title == 'Hello world!'
        drv.close()
    }

}
