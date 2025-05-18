package com.management.shopfashion.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.management.shopfashion.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

	@Override
	public void sendVerificationEmail(String toEmail, String code) {
		
        try {
            // Tạo một MimeMessage để gửi email HTML
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Thiết lập các thông tin của email
            helper.setTo(toEmail);
            helper.setSubject("Xác thực tài khoản của bạn");
//            helper.setFrom("your-email@gmail.com"); // Địa chỉ email của bạn

            // Nội dung email dưới dạng HTML
            String htmlContent = String.format("<html>"
                    + "<body>"
                    + "<h2 style='color:#4CAF50;'>Welcome to Dilys Fashion Shop!</h2>"
                    + "<p>Dear User,</p>"
                    + "<p>Thank you for registering with us. Please find your verification code below:</p>"
                    + "<h3 style='color:#0000FF;'>Verification Code: <strong>%s</strong></h3>"
                    + "<p>Enter this code on our website to complete your account registration.</p>"
                    + "<p>If you did not request this, please ignore this email.</p>"
                    + "<p>Best regards,</p>"
                    + "<p>The Dilys Fashion Shop Team</p>"
                    + "<footer style='color:#888;'>"
                    + "<p style='font-size:12px;'>Dilys Fashion Shop</p>"
                    + "<p style='font-size:12px;'>123 Fashion Street, New York, NY</p>"
                    + "</footer>"
                    + "</body>"
                    + "</html>", code);

          
            helper.setText(htmlContent, true);

         
            mailSender.send(message);

        } catch (MessagingException e) {
            // Xử lý lỗi khi gửi email
            e.printStackTrace();
            // Thêm xử lý lỗi ở đây, ví dụ: log hoặc gửi thông báo lỗi
        }
	}

	@Override
	public void sendVerificationChangePassword(String toEmail, String fullName) {
	    try {
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");

	        helper.setTo(toEmail);
	        helper.setSubject("Cảnh báo bảo mật tài khoản");

	        String htmlContent = String.format("<html>"
	            + "<body style='font-family:Arial, sans-serif; background-color:#f9f9f9; padding:20px;'>"
	            + "<div style='max-width:600px; margin:auto; background:#ffffff; padding:30px; border-radius:8px; box-shadow:0 0 10px rgba(0,0,0,0.1);'>"
	            + "<h2 style='color:#d9534f;'>Cảnh báo bảo mật tài khoản</h2>"
	            + "<p>Xin chào <strong>%s</strong>,</p>"
	            + "<p>Chúng tôi phát hiện tài khoản của bạn đã thực hiện <span style='color:#d9534f; font-weight:bold;'>thay đổi mật khẩu</span>.</p>"
	            + "<p>Nếu đây là bạn, vui lòng bỏ qua email này.</p>"
	            + "<p style='color:#d9534f; font-weight:bold;'>Nếu bạn KHÔNG thực hiện hành động này, vui lòng đổi mật khẩu ngay lập tức để đảm bảo an toàn.</p>"
	            + "<div style='text-align:center; margin-top:20px;'>"
	            + "<a href='http://localhost:3000/user-account' style='display:inline-block; padding:12px 20px; background-color:#0275d8; color:white; text-decoration:none; border-radius:4px;'>Đổi mật khẩu ngay</a>"
	            + "</div>"
	            + "<p style='margin-top:30px;'>Nếu bạn cần hỗ trợ, hãy liên hệ: <a href='mailto:support@dilysfashion.vn'>support@dilysfashion.vn</a></p>"
	            + "<p>Trân trọng,<br/>Đội ngũ Dilys Fashion Shop</p>"
	            + "<hr style='margin-top:30px; border:none; border-top:1px solid #eee;'>"
	            + "<footer style='color:#888; font-size:12px; text-align:center;'>"
	            + "<p>Dilys Fashion Shop</p>"
	            + "<p>123 Fashion Street, New York, NY</p>"
	            + "</footer>"
	            + "</div>"
	            + "</body>"
	            + "</html>", fullName);

	        helper.setText(htmlContent, true);
	        mailSender.send(message);
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	}

	

}
