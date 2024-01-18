package in.raju.service;

import in.raju.binding.LoginBinding;
import in.raju.binding.SingnInBindind;
import in.raju.binding.UnlockBinding;

public interface UserService {
	
	

public boolean signup(SingnInBindind signin);

public boolean unlock(UnlockBinding unlock);

public String login(LoginBinding login);

public boolean forgotPw(String email);


}
