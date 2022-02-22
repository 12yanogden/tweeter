package edu.byu.cs.tweeter.client.presenter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import edu.byu.cs.tweeter.client.presenter.observer.AuthenticateObserver;

public class RegisterPresenter extends AuthenticatePresenter {
    public RegisterPresenter(AuthenticateView view) {
        super(view);
    }

    public void validateRegistration(String firstName, String lastName, String alias, String password, Drawable imageDrawable) throws IllegalArgumentException {
        if (firstName.length() == 0) {
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if (lastName.length() == 0) {
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }
        if (alias.length() == 0) {
            throw new IllegalArgumentException("Alias cannot be empty.");
        }

        validateLogin(alias, password);

        if (imageDrawable == null) {
            throw new IllegalArgumentException("Profile image must be uploaded.");
        }
    }

    public void register(String firstName, String lastName, String alias, String password, Bitmap image) {
        String imageURL = imageToURL(image);

        getUserService().register(firstName, lastName, alias, password, imageURL, new AuthenticateObserver(this, "register"));
    }

    private String imageToURL(Bitmap image) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] imageBytes = bos.toByteArray();

        // Intentionally, Use the java Base64 encoder so it is compatible with M4.
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
