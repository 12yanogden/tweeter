package edu.byu.cs.tweeter.server.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.DAOFactory;

public abstract class AuthTokenService extends FactoryService {
    private final AuthTokenDAO authTokenDAO;
    private final String invalidTokenMsg;
    private final int expirationInMins;
    private final String dateTimePattern;

    public AuthTokenService(DAOFactory factory) {
        super(factory);

        authTokenDAO = factory.makeAuthTokenDAO();
        invalidTokenMsg = "The authToken is invalid";
        expirationInMins = 30;
        dateTimePattern = "MMM d yyyy h:mm a";
    }

    protected boolean validateAuthToken(AuthToken clientAuthToken) {
        System.out.println("Enter AuthTokenService.validateAuthToken");
        boolean isValid = true;

        if (clientAuthToken == null) {
            System.out.println("authToken is null");

            isValid = false;
        } else {
            long clientEpoch = dateTimeToEpoch(clientAuthToken.getDatetime());
            long expiresEpoch = incrementEpochByMins(clientEpoch, getExpirationInMins());
            long currentEpoch = dateTimeToEpoch(calcCurrentDateTime());


            if (currentEpoch > expiresEpoch) {
                System.out.println("authToken has expired");

                getAuthTokenDAO().deleteAuthToken(clientAuthToken.getToken());

                isValid = false;
            }
        }

        return isValid;
    }

    private String calcCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getDateTimePattern());
        LocalDateTime now = LocalDateTime.now();

        return formatter.format(now);
    }

    private long incrementEpochByMins(long epoch, int minCount) {
        long incrementation = Integer.toUnsignedLong(minCount * 60 * 1000);

        return epoch + incrementation;
    }

    private long dateTimeToEpoch(String dateTime) {
        SimpleDateFormat format = new SimpleDateFormat(getDateTimePattern());
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

    public String getDateTimePattern() {
        return dateTimePattern;
    }
}
