package com.green.campingsmore.sign;

import com.green.campingsmore.sign.model.UpdatePwDto;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailApi {

	@Value("${Naver.NAVER_ID}")
	private String NAVER_ID;

	public void sendMail(UpdatePwDto user,String pw) throws Exception {
		//Mail Server 설정
		String charSet = "utf-8";
		String hostSMTP = "smtp.naver.com"; //SMTP 서버명
		String hostSMTPid = "green502test"; //이메일 아이디
		String hostSMTPpw = "@green502"; // 이메일 패스워드

		//보내는 사람
		String fromEmail = "green502test@naver.com"; //보내는 사람의 이메일
		String fromName = "캠핑스모어";

		String subject = ""; //메일 제목
		String msg = ""; //메일 내용



		subject = "[ 캠핑스모어 ] 임시 비밀번호 발급 안내";
		msg += "<div align = 'left'>";
		msg += "<h3>";
		msg += user.getName() + "님의 임시 비밀번호입니다. <br> 비밀번호를 변경하여 사용하세요 </h3>";
		msg += "<p>임시 비밀번호 : ";
		msg += pw + "</p></div>";


		//email 전송
		String mailRecipient = user.getEmail(); //받는 사람 이메일 주소
		try {
			//객체 선언
			HtmlEmail mail = new HtmlEmail();
			mail.setDebug(true);
			mail.setCharset(charSet);
			mail.setSSLOnConnect(true); //SSL 사용
			mail.setHostName(hostSMTP);
			mail.setSmtpPort(465); //SMTP 포트 번호
			mail.setAuthentication(hostSMTPid, hostSMTPpw);
			mail.setStartTLSEnabled(false); //TLS 사용
			mail.addTo(mailRecipient, charSet);
			mail.setFrom(fromEmail, fromName, charSet);
			mail.setSubject(subject);
			mail.setHtmlMsg(msg);
			mail.send();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
