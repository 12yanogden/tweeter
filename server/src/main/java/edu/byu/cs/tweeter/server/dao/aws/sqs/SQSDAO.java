package edu.byu.cs.tweeter.server.dao.aws.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;

import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.Request;
import edu.byu.cs.tweeter.server.dao.QueueDAO;

public abstract class SQSDAO implements QueueDAO {
    private final AmazonSQS sqs;
    protected String queueName;
    protected String queueURL;

    public SQSDAO(String region, String queueName) {
        sqs = AmazonSQSClientBuilder.defaultClient();
        queueURL = "https://sqs." + region + ".amazonaws.com/406592857822/" + queueName;
    }

    @Override
    public void sendToQueue(String requestJson) {
        System.out.println("queueURL: " + getQueueURL());
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(getQueueURL())
                .withMessageBody(requestJson)
                .withDelaySeconds(5);

        System.out.println("Send message to " + getQueueURL() + ":");
        System.out.println(requestJson);

        SendMessageResult sendMessageResult = sqs.sendMessage(sendMessageRequest);

        String msgId = sendMessageResult.getMessageId();
        System.out.println("Message ID: " + msgId);
    }

    public String getQueueName() {
        return queueName;
    }

    public String getQueueURL() {
        return queueURL;
    }
}
