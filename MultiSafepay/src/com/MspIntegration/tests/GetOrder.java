package com.MspIntegration.tests;

import com.MultiSafepay.client.MultiSafepayClient;
import com.google.gson.JsonObject;

/**
 * @author Multisafepay.com
 */
public class GetOrder {
	
    public static void main(String[] args) {

    	MultiSafepayClient.init(true);
    	
    	JsonObject jsonResponse = MultiSafepayClient.GetOrder("1418980067470");

    	System.out.println(jsonResponse);
    }
}
