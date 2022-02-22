package edu.byu.cs.tweeter.client.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter {
    public interface View {
        void displayToast(String message);
        void register(User registeredUser);

    }

    private View view;
    private UserService userService;

    public RegisterPresenter(View view) {
        this.view = view;
        userService = new UserService();
    }

    public void validateRegistration(String firstName, String lastName, String alias, String password, Drawable imageDrawable) {
        if (firstName.length() == 0) {
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if (lastName.length() == 0) {
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }
        if (alias.length() == 0) {
            throw new IllegalArgumentException("Alias cannot be empty.");
        }
        if (alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }

        if (imageDrawable == null) {
            throw new IllegalArgumentException("Profile image must be uploaded.");
        }
    }

    public void register(String firstName, String lastName, String alias, String password, Bitmap image) {
        String imageURL = imageToURL(image);

        userService.register(firstName, lastName, alias, password, imageURL, new RegisterObserver());
    }

    private String imageToURL(Bitmap image) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] imageBytes = bos.toByteArray();

        // Intentionally, Use the java Base64 encoder so it is compatible with M4.
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public class RegisterObserver implements UserService.RegisterObserver {
        @Override
        public void handleSuccess(User registeredUser, AuthToken authToken) {
            Cache.getInstance().setCurrUser(registeredUser);
            Cache.getInstance().setCurrUserAuthToken(authToken);

            view.register(registeredUser);
        }

        @Override
        public void handleFailure(String message) {
            view.displayToast("Failed to register: " + message);
        }

        @Override
        public void handleException(Exception exception) {
            view.displayToast("Failed to register because of exception: " + exception.getMessage());
        }
    }
}
