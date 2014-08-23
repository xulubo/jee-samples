package ca.loobo.helloworld;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.multitype.MultiTypeCaptchaService;

@Controller
public class HelloWorld {
	@Autowired
	private DefaultManageableImageCaptchaService captchaService;

	@RequestMapping("/")
	public String hello() {

		return "index";

	}

	public static final String CAPTCHA_IMAGE_FORMAT = "jpeg";

	@RequestMapping("/captcha.jpg")
	public void showForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		byte[] captchaChallengeAsJpeg = null;
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			String captchaId = request.getSession().getId();
			BufferedImage challenge = captchaService.getImageChallengeForID(
					captchaId, request.getLocale());

			ImageIO.write(challenge, CAPTCHA_IMAGE_FORMAT, jpegOutputStream);
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		} catch (CaptchaServiceException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

		// flush it in the response
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/" + CAPTCHA_IMAGE_FORMAT);

		ServletOutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}

	@RequestMapping("/validate")
	@ResponseBody
	protected String validateCaptcha(HttpServletRequest request,
			String j_captcha_response) {
		// If the captcha field is already rejected
		boolean validCaptcha = false;
		System.err.println("captchaResponse:" + j_captcha_response);
		try {
			validCaptcha = captchaService.validateResponseForID(request
					.getSession().getId(), j_captcha_response);
			
			System.err.println("Result : " + validCaptcha);
		} catch (CaptchaServiceException e) {
			System.err.println("-------------");
			// should not happen, may be thrown if the id is not valid
			e.printStackTrace();
		}
		
		return "Result1: "+validCaptcha;
	}

}
