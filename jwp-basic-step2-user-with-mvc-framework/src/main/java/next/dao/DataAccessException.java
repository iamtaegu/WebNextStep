package next.dao;

//SQLException(컴파일 Exception) > RuntimeException으로 전환용
public class DataAccessException extends RuntimeException {

    public static final long serialVersionUID = 1L;

    public DataAccessException() {
    }

    public DataAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
