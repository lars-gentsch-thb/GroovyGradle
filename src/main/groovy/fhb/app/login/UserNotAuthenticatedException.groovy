package fhb.app.login

import groovy.transform.CompileStatic

class UserNotAuthenticatedException extends RuntimeException {
    UserNotAuthenticatedException() {
        super()
    }

    @CompileStatic
    UserNotAuthenticatedException(message) {
        super("User not authenitcated! Cause: $message")
    }
}

