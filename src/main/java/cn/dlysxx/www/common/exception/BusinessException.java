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
     * @param code error code
     */
    public BusinessException(String code) {
        this(code, null);
    }

    /**
     * Constructor.
     * @param code error code
     * @param message error message
     */
    public BusinessException(String code, String message) {
        this(code, message, null);
    }

    /**
     * Constructor.
     * @param code error code
     * @param message error message
     * @param cause exception
     */
    public BusinessException(String code, String message, Throwable cause) {
        super(cause);
        this.code = code;
        this.message = message;
    }

    /**
     * Get error code
     * @return error code
     */
    public String getCode() {
        return code;
    }

    /**
     * Set error code
     * @param code error code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Get error message
     * @return error message
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Set error message
     * @param message error message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
