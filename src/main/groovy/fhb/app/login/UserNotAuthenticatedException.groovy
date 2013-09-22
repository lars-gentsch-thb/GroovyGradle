package fhb.app.login

class UserNotAuthenticatedException extends RuntimeException {
    UserNotAuthenticatedException() {
        super()
    }

    UserNotAuthenticatedException(message) {
        super("User not authenitcated! Cause: $message")
    }
}

