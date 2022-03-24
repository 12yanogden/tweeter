package edu.byu.cs.tweeter.server.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.DAOFactory;

public abstract class AuthTokenService extends FactoryService {
    private AuthTokenDAO authTokenDAO;
    private String invalidTokenMsg;
    private int expirationInMins;

    public AuthTokenService(DAOFactory factory) {
        super(factory);

        authTokenDAO = factory.makeAuthTokenDAO();
        invalidTokenMsg = "authToken has expired";
        expirationInMins = 30;
    }

    protected boolean validateAuthToken(AuthToken clientAuthToken) {
        AuthToken dbAuthToken = getAuthTokenDAO().getAuthToken(clientAuthToken.getToken());
        long clientEpoch = dateTimeToEpoch(clientAuthToken.getDatetime());
        long expiresEpoch = incrementEpochByMins(clientEpoch, getExpirationInMins());
        long currentEpoch = dateTimeToEpoch(calcCurrentDateTime());
        boolean isValid = true;

        if (currentEpoch > expiresEpoch) {
            getAuthTokenDAO().deleteAuthToken(dbAuthToken.getToken());

            isValid = false;
        }

        return isValid;
    }

    private String calcCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy h:mm aaa");
        LocalDateTime now = LocalDateTime.now();

        return formatter.format(now);
    }

    private long incrementEpochByMins(long epoch, int minCount) {
        long incrementation = Integer.toUnsignedLong(minCount * 60 * 1000);

        return epoch + incrementation;
    }

    private long dateTimeToEpoch(String dateTime) {
        SimpleDateFormat format = new SimpleDateFormat("MMM d yyyy h:mm aaa");
        long epoch;

        try {
            Date date = format.parse(dateTime);
            epoch = date.getTime();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();

            throw new RuntimeException("[Server Error] Parse to epoch from " + dateTime + " failed: " + e.getMessage());
        }

        return epoch;
    }

    public AuthTokenDAO getAuthTokenDAO() {
        return authTokenDAO;
    }

    public String getInvalidTokenMsg() {
        return invalidTokenMsg;
    }

    public int getExpirationInMins() {
        return expirationInMins;
    }
}
