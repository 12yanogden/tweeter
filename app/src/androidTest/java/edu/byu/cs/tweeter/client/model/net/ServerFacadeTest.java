package edu.byu.cs.tweeter.client.model.net;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowCountRequest;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.AuthenticateResponse;
import edu.byu.cs.tweeter.model.net.response.FollowCountResponse;
import edu.byu.cs.tweeter.model.net.response.PagedUserResponse;
import edu.byu.cs.tweeter.util.FakeData;

public class ServerFacadeTest {
    private ServerFacade serverFacade;
    private RegisterRequest registerRequest;
    private PagedRequest pagedRequest;
    private FollowCountRequest followCountRequest;
    private String registerUrlPath;
    private String getFollowersUrlPath;
    private String getFollowersCountUrlPath;
    private FakeData fakeData;

    @Before
    public void setup() {
        serverFacade = new ServerFacade();

        registerRequest = new RegisterRequest("first", "last", "@user", "pass", "image");
        pagedRequest = new PagedRequest(new AuthToken(), "@allen", 10, "@elizabeth");
        followCountRequest = new FollowCountRequest(new AuthToken(), "@allen");

        registerUrlPath = "/register";
        getFollowersUrlPath = "/getfollowers";
        getFollowersCountUrlPath = "/getfollowerscount";

        fakeData = new FakeData();
    }

    @Test
    public void testRegister_validRequest_validResponse() {
        try {
            AuthenticateResponse response = serverFacade.request(registerRequest, registerUrlPath, AuthenticateResponse.class);
            User expectedUser = fakeData.getFirstUser();

            Assert.assertNotNull(response);
            Assert.assertTrue(response.isSuccess());
            Assert.assertTrue(expectedUser.equals(response.getUser()));
            Assert.assertNotNull(response.getAuthToken());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testGetFollowers_validRequest_validResponse() {
        try {
            PagedUserResponse response = serverFacade.request(pagedRequest, getFollowersUrlPath, PagedUserResponse.class);
            boolean hasMorePages;
            List<User> items;

            Assert.assertNotNull(response);
            Assert.assertTrue(response.isSuccess());
            Assert.assertNull(response.getMessage());

            hasMorePages = response.getHasMorePages();
            items = response.getItems();

            Assert.assertTrue(hasMorePages);
            Assert.assertEquals(10, items.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testGetFollowersCount_validRequest_validResponse() {
        try {
            FollowCountResponse response = serverFacade.request(followCountRequest, getFollowersCountUrlPath, FollowCountResponse.class);

            Assert.assertNotNull(response);
            Assert.assertTrue(response.isSuccess());
            Assert.assertEquals(21, response.getCount()); // Extra 1 expected, 20 shows in app

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
