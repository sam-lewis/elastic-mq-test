package com.example;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;
import org.elasticmq.rest.sqs.SQSRestServer;
import org.elasticmq.rest.sqs.SQSRestServerBuilder;
import org.junit.Test;

public class ElasticMqTest {

	@Test
	public void testQueueCreation() {

		String queueName = "queue1";

		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");

		SQSRestServer server = SQSRestServerBuilder.start();
		server.waitUntilStarted();

		AmazonSQSClient client = new AmazonSQSClient(new BasicAWSCredentials("x", "x"));

		client.setEndpoint("http://localhost:9324");

//		Test passes when the queue is explicitly created
//		client.createQueue(queueName);

		String queueUrl = client.getQueueUrl(queueName).getQueueUrl();

		client.sendMessage(queueUrl, "hello");

		server.stopAndWait();
	}
}
