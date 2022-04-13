package cn.dlysxx.www.common.exception;

/**
 * BusinessException class.
 *
 * @author shuai.zhou
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1321372647800456847L;

    /** error code */
    protected String code;

    /** error message */
    protected String message;

    /**
     * Constructor.
     *
     */
    public BusinessException(String code) {
        this(code, null);
    }

    /**
     * Constructor.
     *
     */
    public BusinessException(String code, String message) {
        this(code, message, null);
    }

    /**
     * Constructor.
     *
     */
    public BusinessException(String code, String message, Throwable cause) {
        super(cause);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
