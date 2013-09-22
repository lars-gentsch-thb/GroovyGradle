package fhb.app.login

class LoginController {

    LoginService loginService

    void doLogin(String username, String password) {
        notNullOrEmpty('username', username)
        notNullOrEmpty('password', password)

        if(!loginService.login(username, password)) {
            throw new UserNotAuthenticatedException()
        }
    }

    private notNullOrEmpty(name, value) {
        if (!value) {
            throw new IllegalArgumentException("$name must not be NULL!")
        }
    }
}