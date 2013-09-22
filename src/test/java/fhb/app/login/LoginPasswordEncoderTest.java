package fhb.app.login;

import org.junit.Test;

import fhb.app.login.LoginPasswordEncoder;

public class LoginPasswordEncoderTest {
	
	@Test
	public void encodePasswordWhenAValidPasswordIsGiven() {
		String encodedPassword = LoginPasswordEncoder.encodePassword("apasswordtoencode");
	    assert(encodedPassword != null);
	}

}
