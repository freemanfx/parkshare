package ro.parkshare.parkshare.api;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String toString() {
        return "ApiException - " + getLocalizedMessage();
    }
}
