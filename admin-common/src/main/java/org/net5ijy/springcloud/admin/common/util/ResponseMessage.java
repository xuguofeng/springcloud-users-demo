package org.net5ijy.springcloud.admin.common.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * org.net5ijy.springcloud.admin.service.util.ResponseMessage 类
 *
 * @author xuguofeng
 * @date 2019/5/29 10:08
 */
@Data
public class ResponseMessage implements Serializable {

  public static final String MESSAGE_SUCCESS = "操作成功";

  public static final String MESSAGE_ERROR = "操作失败";

  public static final int MESSAGE_SUCCESS_CODE = 0;

  public static final int MESSAGE_ERROR_CODE = 99;

  private int code;

  private String message;

  private Map<String, Object> data = new HashMap<>();

  public ResponseMessage() {
    super();
  }

  public ResponseMessage(int code, String message) {
    this();
    this.code = code;
    this.message = message;
  }

  public static ResponseMessage success() {
    return new ResponseMessage(MESSAGE_SUCCESS_CODE, MESSAGE_SUCCESS);
  }

  public static ResponseMessage error() {
    return new ResponseMessage(MESSAGE_ERROR_CODE, MESSAGE_ERROR);
  }

  public ResponseMessage addAttribute(String name, Object val) {
    this.data.put(name, val);
    return this;
  }
}
