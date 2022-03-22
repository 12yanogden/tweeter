package edu.byu.cs.tweeter.server.lambda;

public abstract class ValidationHandler extends ServiceHandler {
    protected void validateUsername(String name, String username) {
        if (username == null) {
            throw new RuntimeException("[BadRequest] Missing a " + name);
        } else if (!username.startsWith("@")) {
            throw new RuntimeException("[BadRequest] " + name + " missing @ prefix");
        }
    }

    protected void validateNotNull(String name, Object value) {
        if (value == null) {
            throw new RuntimeException("[BadRequest] Missing a " + name);
        }
    }

    protected void validatePositive(String name, int value) {
        if (value <= 0) {
            throw new RuntimeException("[BadRequest] Request needs to have a positive " + name);
        }
    }
}
