package edu.byu.cs.tweeter.server.dao.dynamoDB;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;

public class DynamoDBUserDAO extends DynamoDBDAO implements UserDAO  {
    public DynamoDBUserDAO() {
        super("user");
    }

    @Override
    public Pair<User, AuthToken> register(String firstName, String lastName, String username, String password, String image) {
        String hashAndSalt = hash(password);
        String imageURL = putImage(image);
        User user = getFakeData().getFirstUser();
        AuthToken authToken = getFakeData().getAuthToken();

        System.out.println("Register user: " + username);

        PutItemOutcome outcome = getTable().putItem(
                new Item()
                        .withPrimaryKey(
                                "username",
                                username)
                        .withString(
                                "firstName",
                                firstName)
                        .withString(
                                "lastName",
                                lastName)
                        .withString(
                                "password",
                                hashAndSalt)
                        .withString(
                                "imageURL",
                                imageURL)
        );

        System.out.println("Register succeeded:\n" + outcome.getPutItemResult()); // TODO: Determine how to detect if user already exists

        return new Pair<>(user, authToken); // TODO: Determine what to return if DB request or hashAndSalt fails
    }

    @Override
    public Pair<User, AuthToken> login(String username, String password) {
        User user = getUser(username);
        AuthToken authToken = getFakeData().getAuthToken();

        if (false) { // TODO: user is valid
            // TODO: username is invalid
        }

        if (false) { // TODO: isValidPassword(user.getPassword(), password), which algorithm to choose?
            // TODO: password is invalid
        }

        // TODO: generate new authToken

        return new Pair<>(user, authToken); // TODO: What to return if username/password are incorrect?
    }

    @Override
    public User getUser(String username) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("username", username);

        System.out.println("getUser: " + username);

        Item outcome = getTable().getItem(spec);

        System.out.println("getUser succeeded: " + outcome);

        // TODO: Extract user from outcome
        // TODO: Determine what to return if request fails. A: 400 error

        return getFakeData().findUserByAlias(username);
    }

    protected FakeData getFakeData() {
        return new FakeData();
    }

    private String hash(String password) {
        byte[] bytes;
        StringBuilder stringBuilder = new StringBuilder();
        String out = "FAILED TO HASH"; // TODO: 500 error

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(password.getBytes());

            bytes = md.digest();

            for (byte aByte : bytes) {
                stringBuilder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            out = stringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return out;
    }

    // TODO: put the image into an s3 bucket and return the url
    private String putImage(String image) {
        return "";
    }
}
