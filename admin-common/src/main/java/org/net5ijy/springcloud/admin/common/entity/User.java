package org.net5ijy.springcloud.admin.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * User 类
 *
 * @author xuguofeng
 * @date 2019/5/29 9:37
 */
@Data
public class User implements Serializable {

	public static final long serialVersionUID = 6641328611539019031L;

	private Integer id;

	private String username;

	private String password;

	private String phone;

	private String email;

	private Set<Role> roles = new HashSet<>();

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
}
