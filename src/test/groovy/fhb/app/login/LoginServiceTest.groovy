package fhb.app.login

import fhb.app.login.LoginService;
import spock.lang.Specification

class LoginServiceTest extends Specification {

    static final VALID_USERNAME = 'hans'
    static final VALID_PASSWORD = 'wurst'
    static final INVALID_PASSWORD = 'unknown'
    static final INVALID_USERNAME = 'unknown'
    static final IS_AUTHENTICATED = true
    static final IS_REJECTED = false

    LoginService service = new LoginService()
    def isAuthenticated

    void 'Login user when username and passowrd are valid'() {
        when: userTriesToLogInWith VALID_USERNAME, VALID_PASSWORD
        then: userIs IS_AUTHENTICATED
    }

    void 'Deny access when username or password do not match' () {
        when: userTriesToLogInWith VALID_USERNAME, INVALID_PASSWORD
        then: userIs IS_REJECTED
    }

    void 'Deny access when username is unknown' () {
        when: userTriesToLogInWith INVALID_USERNAME, VALID_PASSWORD
        then: userIs IS_REJECTED
    }

    // when
    private void userTriesToLogInWith(username, password) {
        isAuthenticated = service.login(username, password)
    }

    // then
    private void userIs(expectation) {
        assert isAuthenticated == expectation
    }
}

/**
 *  'state machine' global state ermöglicht die Verwendung einfacher Methodensignaturen (super für die Tests)
 *  es müssen keine technischen Parameter mit an die Methoden übergeben werden
 *  
 *  durch sprechende Methoden und Variablen tritt die Fachlichkeit 
 *  in den Vordergrund (das WAS) und nicht die technische Umsetzung (das WIE)
 *  
 */

