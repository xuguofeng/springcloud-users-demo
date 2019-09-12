package org.net5ijy.springcloud.admin.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Role 类
 *
 * @author xuguofeng
 * @date 2019/5/29 9:38
 */
@Data
public class Role implements Serializable {

	public static final long serialVersionUID = 8551327484428498338L;

	private Integer id;

	private String name;
}
