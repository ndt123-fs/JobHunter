package vn.hoidanit.jobhunter.utils;

import org.antlr.v4.runtime.misc.ObjectEqualityComparator;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpServletResponse;
import vn.hoidanit.jobhunter.domain.RestResponse;

@RestControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		// khi nao muon phan hoi , khi nao muon ghi de => return true => bat cu phan hoi
		// nao cung lam v
		// tra true -> chay xuong func beforeBodyWrite
		return true;
	}

	@Override
	public Object beforeBodyWrite(
			Object body,
			MethodParameter returnType,
			MediaType selectedContentType,
			Class selectedConverterType,
			ServerHttpRequest request,
			ServerHttpResponse response) {
		HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
		int status = servletResponse.getStatus();

		RestResponse<Object> res = new RestResponse<Object>();
		res.setStatusCode(status);

		if (body instanceof String) {
			return body;
		}

		if (status >= 400) {
			// case error
			return body;

		} else {

			// case success
			res.setMessage("Call API SUCCESS!");
			res.setData(body);

		}

		return res;
	}

}
