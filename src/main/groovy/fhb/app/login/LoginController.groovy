package fhb.app.login

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod


import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.POST

@Controller
class LoginController {

    @Autowired
    LoginService loginService

    @RequestMapping(value = 'index', method = RequestMethod.GET)
    def test() {
        println 'Hello'
        return 'indexPage'
    }

    @RequestMapping(value = 'login', method = RequestMethod.POST)
    def doLogin(String username, String password) {
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