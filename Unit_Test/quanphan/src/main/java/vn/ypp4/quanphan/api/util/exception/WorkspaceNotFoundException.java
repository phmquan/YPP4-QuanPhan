package vn.ypp4.quanphan.api.util.exception;

public class WorkspaceNotFoundException extends RuntimeException {
    private final String message;

    public WorkspaceNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
