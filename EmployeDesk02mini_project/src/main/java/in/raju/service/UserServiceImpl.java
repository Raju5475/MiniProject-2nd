package in.raju.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.raju.Utils.EmailUtils;
import in.raju.Utils.PwUtils;
import in.raju.binding.LoginBinding;
import in.raju.binding.SingnInBindind;
import in.raju.binding.UnlockBinding;
import in.raju.entity.UserEntity;
import in.raju.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private EmailUtils mail;

	@Autowired
	private HttpSession session;

	@Override
	public boolean signup(SingnInBindind signin) {

		// TODO: Check weather record is already inserted or not

		UserEntity email = userRepo.findByEmail(signin.getEmail());
		if (email != null) {
			return false;
		}

		// TODO:Copy form obj to entity Obj

		UserEntity entity = new UserEntity();
		// Now copy the properties
		BeanUtils.copyProperties(signin, entity);

		// TODO:Set a Random Password
		String randomPw = PwUtils.getRandomPw();
		entity.setPwd(randomPw);

		// ToDo:Set Account status as Locked
		entity.setAccStatus("Locked");

		// TODO:Set Email

		String to = signin.getEmail();
		String sub = "Unlock Your Account || Ashok-IT";
		StringBuffer body = new StringBuffer();
		body.append("Temporary Password : " + randomPw);
		body.append("<hr>");
		body.append("<h1>Click Below Link to Unlock Your Account</h1>");
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email=" + to + "\">Click here to unlock your Account</a>");

		mail.sendmail(to, body.toString(), sub);

		// TODO: Insert Data In the Table
		userRepo.save(entity);

		return true;
	}

	@Override
	public boolean unlock(UnlockBinding unlock) {

		// TODO Check weather the given temporary pwd is equal to user entered temp pw
		// from the DB table
		UserEntity entity = userRepo.findByEmail(unlock.getEmail());

		if (entity.getPwd().equals(unlock.getTempPw())) {
			entity.setPwd(unlock.getNewPW());
			entity.setAccStatus("Unlocked");
			userRepo.save(entity);
			return true;
		}

		return false;
	}

	// Loin Functionallity
	@Override
	public String login(LoginBinding login) {
		UserEntity entity = userRepo.findByEmailAndPwd(login.getEmail(), login.getPassword());
		if (entity == null) {
			return "Invalid Credintials";
		}
		if (entity.getAccStatus().equals("Locked")) {
			return "Your Account is Locked";
		}
		session.setAttribute("UserId", entity.getUserId());
		return "success";
	}

	@Override
	public boolean forgotPw(String email) {
		// TODO check weather the given is available in db or not
		UserEntity entity = userRepo.findByEmail(email);

		// TODO if its available write the condition
		if (entity == null) {
			return false;
		}
		String body = "<h2>This is Your Password ::</h2>" + entity.getPwd();
		String sub = "Recover Password";
		// TODO and send the password to the mail
		mail.sendmail(email, body, sub);
		return true;
	}

}
