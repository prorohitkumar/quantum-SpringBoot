package com.stackroute.dialogueflow.controller;

import java.net.URI;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/v1/dialogflow")
public class DialogFlowController 
{
	private final String langCode;
	private final String projectId;
	private String sessionId;
	private String clientId;
	private String clientEmail;
	private String privateKeyId;
	private String privateKey;
	private String  tokenServerUri;
	
	
	public DialogFlowController()
	{
		langCode = "en-US";
		projectId = "teambuilder-bhbi";
		sessionId = UUID.randomUUID().toString();
		clientId = "113492530931156844056";
    	clientEmail = "quantum@teambuilder-bhbi.iam.gserviceaccount.com";
    	privateKeyId = "d29cc6e1288bb666c08d21f0c4d7b6714301e801";
    	privateKey = "-----BEGIN PRIVATE KEY-----\nMIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCiqL6nX71klfGu\n04S/dAjS0ZMX+ZCEfkXD0LGviEnSwvZ5MQGNPKVP9QWHySA37lBsy+piIZJaz08N\n679XwOCvxMcIvoQVy7Dv2KyHrjKhBmWyhqugp0OcD1xtgKGUQxHjdjXPNEDHdyOJ\nkohvcRCm2uyg191VTWW1V03d5W7oyPbtGepRn9HhAjg7Tg1JZ4k8sYiX5GF4ZyBq\n95n7wg7CCwIo0cw/jIHcTZokQrlF0CzMo92BKlVEeBnKYVCAKvvSX8OVZgKZMoDN\nxx7juVnMRPJTTHZbelpQ3xDyx16GTgQgQvAmsPZwX9wX5zNcg/Noeh4eiCJuSZAU\n5P7gKA6zAgMBAAECggEAC9nNGjCvG2PGGf17KUYkJm5Y3MeBlsWeAgHaEEN9U9SB\nVJvhB9zbrOAMwrN0Nd79D3aQrkAEtu0HtQ69M02JxGr+MIFNtxc1RIqkXbZJWTk4\nGGVLawcEtZtA5qsXTdADi6KSXqhPix8ymm4jCcuF2EJD0mwnyg4IU2Mq+MTwhORe\nS9D4CNLyJqDUiT4Tjmq+dd/UT1zW0r7J6YHqKImEpyO0jmR2egHfBZ+knZ1E6Ipf\n+vKkc1w54yrbxCByUPS0ehKy3qSLi0BVwLXNOHx0fNUNWDigAIxjxqJRn4AIAZ0t\nWb/Uq50INiYKOo+gWjVT8+5oqG/MeIt3bJR+uoLowQKBgQDjyBvxeZF6SuoW7Nkz\nQBTzdgfFVjEMWMLlBLpLMD9KLsBZxxnlsM7rUhrccvbCD11vwrsr8H5lQzvZUHMD\nSWPpW7WewRisNLv7CRjLsc4L8sMQPe7BiFxJ+cAJ9efmQJC+RKbaI1bPUTP9gPBw\nQ9amku1qN+PQ8tI/PejUBpPA4wKBgQC2z1VkxV3wpbJ6dAouA36hfshFOCjpGe+v\nukYeWIj822qg5E/y0iXeBlWe5KT7Kz+7ycuPNWeFwFfIAS3M+lpDtF2o+LSs5Q8p\n5vm2C5da/caQoL6mwfCPRElehK/i5i1cpej9ccWQOrHHcFN8+iI29MbllrW6A0PY\n4+ZZ3qjz8QKBgQDbMmfJkd5wXAU3OIev7Rv8SfoiadZbdlNfBpSr5GsTXTgXbSU7\nx1DoXaOhvzQ8/YJCGZf7KbzIf9HpR6TWZdqy52E4u8OdfEXlxxRIePu6vcv/JkpL\njYiaW6/1JeIazsk7gF88jkNs0E0CyAEZV/8JX1z/GXevTO0MlOwolYe8VwKBgQCv\nYmzfuAavKJj5qDhX+txTXcoPtphxQoPUyNYaqQAL7wn2f/Q/1uOjhdpBDcVGfzCv\nHiPgKfE+vdC5vn/NZuaQjAtLYAge08dfgQxTXf2Au3X0LXObkvcSzAXD23LPMduV\nRksoVC2heN40pAxFG8kVhnMHszZ31kgSXlsSH29iUQKBgQDfq+8evwyCKELqO189\nVncgESjXnNU7KGpqfBuVTybqdnyHBd0yBlRCMCQkT24pcRIMl6zT1YhOyuFODg7K\n28Jy4vyZCs6UpThbkRWSEO7OcreJ+UGHhf4Yq6m8XV62u1+bfhZU+Fs856JQGxos\nWPUSNURJv38pj+UXJ3g3/Vc+qw==\n-----END PRIVATE KEY-----\n";
    	tokenServerUri= "https://oauth2.googleapis.com/token";

	
	}
	private SessionsSettings config;
	private Credentials credentials;
	
	@GetMapping("text/{message}")
	public ResponseEntity<?> postDialogFlow(@PathVariable String message) throws Exception
	{	
		this.privateKey = this.privateKey.replace("-----BEGIN PRIVATE KEY-----", "");
		this.privateKey = this.privateKey.replace("-----END PRIVATE KEY-----", "");
		this.privateKey = this.privateKey.replaceAll("\\s+", "");
		this.privateKey = this.privateKey.replaceAll("\\\\n","\n");

		
		
        byte[] privateKeyBytes = Base64.getMimeDecoder().decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        
        
        KeyFactory kf;
        PrivateKey privKey = null;
        
        try 
        {
            kf = KeyFactory.getInstance("RSA");
            try 
            {
                privKey = kf.generatePrivate(keySpec);
            } 
            catch (InvalidKeySpecException e) 
            {
                throw new Exception(e);
            }
        } 
        catch (NoSuchAlgorithmException e) 
        {
            throw new Exception(e);
        }

		
		this.credentials =  ServiceAccountCredentials.newBuilder().setProjectId(projectId)
                .setPrivateKeyId(privateKeyId).setPrivateKey(privKey)
                .setClientEmail(clientEmail).setClientId(clientId)
                .setTokenServerUri(URI.create(tokenServerUri)).build();
		
		this.config = SessionsSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
		
		

		
		try (SessionsClient sessionsClient = SessionsClient.create(this.config)) 
		{
            SessionName session = SessionName.of(this.projectId, this.sessionId);
            
            TextInput textInput = TextInput.newBuilder().setText(message).setLanguageCode(langCode).build();

            QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
            
            DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);
            
            QueryResult queryResult = response.getQueryResult();

            return new ResponseEntity<>( queryResult.getFulfillmentText(), HttpStatus.OK);
        }
	}

	@GetMapping("name/{message}")
	public ResponseEntity<?> getDialogFlowIntentName(@PathVariable String message) throws Exception
	{
		this.privateKey = this.privateKey.replace("-----BEGIN PRIVATE KEY-----", "");
		this.privateKey = this.privateKey.replace("-----END PRIVATE KEY-----", "");
		this.privateKey = this.privateKey.replaceAll("\\s+", "");
		this.privateKey = this.privateKey.replaceAll("\\\\n","\n");



		byte[] privateKeyBytes = Base64.getMimeDecoder().decode(privateKey);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);


		KeyFactory kf;
		PrivateKey privKey = null;

		try
		{
			kf = KeyFactory.getInstance("RSA");
			try
			{
				privKey = kf.generatePrivate(keySpec);
			}
			catch (InvalidKeySpecException e)
			{
				throw new Exception(e);
			}
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new Exception(e);
		}


		this.credentials =  ServiceAccountCredentials.newBuilder().setProjectId(projectId)
				.setPrivateKeyId(privateKeyId).setPrivateKey(privKey)
				.setClientEmail(clientEmail).setClientId(clientId)
				.setTokenServerUri(URI.create(tokenServerUri)).build();

		this.config = SessionsSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();




		try (SessionsClient sessionsClient = SessionsClient.create(this.config))
		{
			SessionName session = SessionName.of(this.projectId, this.sessionId);

			TextInput textInput = TextInput.newBuilder().setText(message).setLanguageCode(langCode).build();

			QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

			DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

			QueryResult queryResult = response.getQueryResult();

			return new ResponseEntity<>( queryResult.getIntent().getDisplayName(), HttpStatus.OK);
		}
	}
}
