package com.hst.wpay.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author dlgusrb0808@gmail.com
 */
@Entity
@Table(name ="USER")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;
	@Column
	private String id;
	@Column
	private String password;
	@Column
	private String name;
	@Column(name = "access_token")
	private String bankAccessToken;
	@Column(name = "refresh_token")
	private String bankRefreshToken;
	@Column(name = "user_seq_num")
	private String bankUserSequenceNumber;
}
