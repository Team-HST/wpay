package com.hst.wpay.user.model.response;

import com.hst.wpay.user.model.entity.User;
import lombok.Getter;

import java.util.Set;

/**
 * @author dlgusrb0808@gmail.com
 */
@Getter
public class UserResponse {

	private long sequence;
	private String id;
	private String name;

	/***
	 * 사용자 응답정보 생성
	 * @param user 사용자 엔티티
	 * @return 사용자 응답정보
	 */
	public static UserResponse of(User user) {
		UserResponse response = new UserResponse();
		response.sequence = user.getSequence();
		response.id = user.getId();
		response.name = user.getName();
		return response;
	}
}
