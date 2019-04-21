package Agency;

public class AgencyException extends Exception {
    public AgencyException() {
    }

    public AgencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public AgencyException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
