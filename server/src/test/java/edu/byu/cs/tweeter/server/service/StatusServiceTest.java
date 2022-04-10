package edu.byu.cs.tweeter.server.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.PagedRequest;
import edu.byu.cs.tweeter.model.net.response.PagedStatusResponse;
import edu.byu.cs.tweeter.server.dao.DAOFactory;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.aws.AWSDAOFactory;
import edu.byu.cs.tweeter.server.dao.aws.dynamoDB.DynamoDBStoryDAO;
import edu.byu.cs.tweeter.util.Pair;

public class StatusServiceTest {
    private DAOFactory mockFactory;
    private StatusService statusService;
    private StoryDAO mockStoryDAO;

    @Before
    public void setup() {
        mockFactory = Mockito.mock(AWSDAOFactory.class);
        mockStoryDAO = Mockito.mock(DynamoDBStoryDAO.class);

        when(mockFactory.makeStoryDAO()).thenReturn(mockStoryDAO);

        statusService = Mockito.spy(new StatusService(mockFactory));
    }

    @Test
    public void testGetStory_GetStoryReturnsPagedStatuses() {
        AuthToken authToken = new AuthToken();
        String ownerAlias = "ownerAlias";
        int limit = 10;
        String lastItemAlias = "lastItemAlias";
        String lastItemDateTime = "lastItemDateTime";
        PagedRequest request = new PagedRequest(authToken, ownerAlias, limit, lastItemAlias, lastItemDateTime);
        Answer<Pair<List<Status>, Boolean>> queryStoryAnswer = makeQueryStoryAnswer();
        PagedStatusResponse response;
        List<Status> items;

        Mockito.doAnswer(queryStoryAnswer).when(mockStoryDAO).queryStory(Mockito.anyString(), Mockito.anyInt(), Mockito.any());
        response = statusService.getStory(request);
        items = response.getItems();

        Mockito.verify(mockStoryDAO).queryStory(eq(ownerAlias), eq(limit), Mockito.any());
        assertEquals(limit, items.size());
        assertFalse(response.getHasMorePages());

        for (int i = 0; i < items.size(); i++) {
            assertEquals(ownerAlias, items.get(i).getUser().getAlias());
        }
    }

    private Answer<Pair<List<Status>, Boolean>> makeQueryStoryAnswer() {
        return new Answer<Pair<List<Status>, Boolean>>() {
            @Override
            public Pair<List<Status>, Boolean> answer(InvocationOnMock invocation) {
                String ownerAlias = invocation.getArgument(0);
                int statusCount = invocation.getArgument(1);
                Pair<List<Status>, Boolean> queryResponse = new Pair<>(makePagedStatuses(ownerAlias, statusCount), false);

                return queryResponse;
            }
        };
    }

    private List<Status> makePagedStatuses(String ownerAlias, int statusCount) {
        List<Status> statuses = new ArrayList<>();

        for (int i = 0; i < statusCount; i++) {
            statuses.add(makeTestStoryStatus(ownerAlias, i));
        }

        return statuses;
    }

    private Status makeTestStoryStatus(String ownerAlias, int iteration) {
        return new Status("post" + iteration,
                makeTestUser(ownerAlias, iteration),
                "dateTime" + iteration,
                new ArrayList<>(),
                new ArrayList<>());
    }

    private User makeTestUser(String ownerAlias, int iteration) {
        return new User("first" + iteration,
                "last" + iteration,
                ownerAlias,
                "imageURL" + iteration);
    }
}
