package Utils.src.emailSender;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 */
public class JavaMailUtil {

	// 发件人的邮箱和密码
//	public static String emailAccount = "qnmlgcnmdsb@qq.com";
	public static String emailAccount = "support@all-about-me.info";
	// 发件人邮箱密码（有的是授权码）
//	public static String emailPassword = "mcpigqmgjmjeiajb";
	public static String emailPassword = "1531828613aA";
	// 发件人邮箱服务地址
//	public static String emailSMTPHost = "smtp.qq.com";
	public static String emailSMTPHost = "smtp.mxhichina.com";
	//  收件人邮箱
	public static String receiveMailAccount = "";
	//  生成的验证码
	public static String code = "";


	@Test
	public void test() {
		sendToEmail("alience98@gmail.com");
	}

	public static void sendToEmail(String email) {
		try {
			//指定的smtp服务器 指定发送邮件的主机smtp.qq.com(QQ)|smtp.163.com(网易)
			JavaMailUtil.receiveMailAccount = email;
			// 1、创建参数配置，用于连接邮箱服务器的参数配置
			Properties props = new Properties();
			// 开启debug调试
			props.setProperty("mail.debug", "true");
			// 发送服务器需要身份验证
			props.setProperty("mail.smtp.auth", "true");
			//QQ邮箱的SSL加密
			props.put("mail.smtp.ssl.enable", "true");
			// 设置右键服务器的主机名
			props.setProperty("mail.host", JavaMailUtil.emailSMTPHost);
			// 发送邮件协议名称
			props.setProperty("mail.transport.protocol", "smtp");
			// 2、根据配置创建会话对象，用于和邮件服务器交互
			Session session = Session.getInstance(props);
			// 设置debug，可以查看详细的发送log
			session.setDebug(true);
			// 3、创建一封邮件
			JavaMailUtil.code = getRandom();
			System.out.println("邮箱验证码：" + code);
			//  邮件内容
			String html = "验证码：<h2 style='color:red;'>" + code + "</h2><br/>";
			MimeMessage message = JavaMailUtil.creatMimeMessage(session, JavaMailUtil.emailAccount,
					JavaMailUtil.receiveMailAccount, html);

			// 4、根据session获取邮件传输对象
			Transport transport = session.getTransport();
			// 5、使用邮箱账号和密码连接邮箱服务器emailAccount必须与message中的发件人邮箱一致，否则报错
			transport.connect(JavaMailUtil.emailAccount, JavaMailUtil.emailPassword);
			// 6、发送邮件,发送所有收件人地址
			transport.sendMessage(message, message.getAllRecipients());
			// 7、关闭连接
			transport.close();
			//TODO:写入session
		} catch (Exception e) {
			System.err.println("异常抛出");
		}

	}

	/**
	 * 创建一封邮件(发件人、收件人、邮件内容)
	 *
	 * @param session
	 * @param sendMail
	 * @param receiveMail
	 * @param html
	 * @return
	 * @throws MessagingException
	 * @throws IOException        cc:抄送
	 *                            Bcc:密送
	 *                            To:发送
	 */
	public static MimeMessage creatMimeMessage(Session session, String sendMail, String receiveMail, String html) throws MessagingException, IOException {
		// 1、创建一封邮件对象
		MimeMessage message = new MimeMessage(session);
		// 2、From：发件人
		message.setFrom(new InternetAddress(sendMail, "客服", "UTF-8"));
		// 3、To:收件人（可以增加多个收件人：抄送或者密送）
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiveMailAccount, "UTF-8"));
		// 4、Subject:邮件主题
		message.setSubject("查收验证码", "UTF-8");
		// 5、Content:邮件正文（可以使用Html标签）
		message.setContent(html, "text/html;charset=UTF-8");
		// 6、设置发送时间
		message.setSentDate(new Date());
		// 7、保存设置
		message.saveChanges();
		// 8、将该邮件保存在本地
		OutputStream out = new FileOutputStream("D://" + UUID.randomUUID().toString() + ".eml");
		message.writeTo(out);
		out.flush();
		out.close();
		return message;
	}

	//  获取6为随机验证码
	public static String getRandom() {
		/*String[] letters = new String[]{
				"q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m",
				"A", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M",
				"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};*/
		String[] letters = new String[]{
				"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		String code = "";
		for (int i = 0; i < 6; i++) {
			code = code + letters[(int) Math.floor(Math.random() * letters.length)];
		}
		return code;
	}
}