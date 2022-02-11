package org.joe.ocw.service.base.exception;

import org.joe.ocw.common.base.result.ResultCodeEnum;
import lombok.Data;

/**
 * 由于程序可能出很多Exception，不可能在GlobalExceptionHandler里给每一个都写一个处理方法，
 * 用Exception接的话，因为Exception没有code属性，所以返回不了错误码code，只能是UNKNOWN
 * 所以自定义一个通用Exception，有着跟返回对象R一样的code属性和message属性
 */
@Data
public class GuliException extends RuntimeException{

    private Integer code;

    public GuliException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public GuliException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "GuliException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
