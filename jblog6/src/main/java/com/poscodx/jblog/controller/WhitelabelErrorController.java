package com.poscodx.jblog.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class WhitelabelErrorController implements ErrorController {
	/** [1] container 내부 에러
	 *  controller에서 터진 경우 GlobalExceptionHandler로 넘어가 
	 *  error/404, erorr/500요청 보내서 WhitelabelErrorController에서 처리
	 */
	@RequestMapping("/404")
	public String _404() {
		return "errors/404";
	}

	@RequestMapping("/500")
	public String _500() {
		return "errors/500";
	}
	
	/** [2] container 외부 에러
	 *  resource handelr 에서 터진 경우 (ex /assets/*론 왔지만 이미지가 없는 경우)
	 *  yml에서 error path를 /error 로 설정했고 요청 보내서 WhitelabelErrorController에서 처리
	 */
	@RequestMapping("")
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			int statusCode = Integer.valueOf(status.toString());

			// springboot가 제공하는 whitelabelerror 페이지 대신 custom한 error 페이지 보이기
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "errors/404";
			} else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
				return "errors/400";
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return "errors/403";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "errors/500";
			}
		}

		return "errors/unknown";
	}
}