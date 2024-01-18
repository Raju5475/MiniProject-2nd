package in.raju.Utils;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
	
	@Autowired
	private JavaMailSender mail;
	 
	public boolean sendmail(String to,String body,String sub) {
		
		boolean is_sent=false;
		try {
			MimeMessage message = mail.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(message);
			helper.setTo(to);
			helper.setSubject(sub);
			helper.setText(body,true);
			
			mail.send(message);
			
			is_sent=true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		
		
		
		return is_sent;
	}
	
}
