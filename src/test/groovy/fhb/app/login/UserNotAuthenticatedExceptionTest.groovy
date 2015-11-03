package fhb.app.login

import spock.lang.Specification

class UserNotAuthenticatedExceptionTest extends Specification {

    void 'Exception can be initiated with a valid message as String' () {
        expect: UserNotAuthenticatedException unaex = new UserNotAuthenticatedException('A vaild Exception.^')
    }

    void 'Exception is constructed with right text' () {
        when: UserNotAuthenticatedException unaex = new UserNotAuthenticatedException('A vaild Exception.^')
        then: unaex.getMessage().startsWith('User not authenitcated')
    }
}
