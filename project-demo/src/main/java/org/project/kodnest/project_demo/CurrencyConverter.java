package org.project.kodnest.project_demo;

import java.net.http.HttpResponse;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kong.unirest.GetRequest;
import kong.unirest.JsonResponse;
import kong.unirest.HttpRequest;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class CurrencyConverter 
{
	static  String apiKey = "b77e6acc251a56533702165bd72a45fb";
	static  String apiUrl = "https://open.er-api.com/v6/latest/";

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter currency to be converted :");
		String currencyToConvertFrom=scan.nextLine().toUpperCase();
		
		System.out.println("Enter currency to be converted to : ");
		String currencyToBeConvertedTo=scan.nextLine().toUpperCase();
		
		System.out.println("Enter amount to be converted :");
		double amount=scan.nextDouble();
		double convertedAmount =0;
		try {
			convertedAmount = convertCurrency(currencyToConvertFrom,currencyToBeConvertedTo,amount,apiKey,apiUrl);
			System.out.println(amount + " " + currencyToConvertFrom + " equals to: " + convertedAmount + " " + currencyToBeConvertedTo+".");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static  double convertCurrency(String currencyToConvertFrom, String currencyToBeConvertedTo, double amount,String apiKey,String apiUrl) {
		String requestUrl = apiUrl + currencyToConvertFrom + "?apikey=" + apiKey;

		kong.unirest.HttpResponse<JsonNode> response =  Unirest.get(requestUrl)
				.header("Accept", "application/json")
				.asJson();

		if (((kong.unirest.HttpResponse<JsonNode>) response).getStatus() == 200) {
			JsonObject rates = JsonParser.parseString((response).getBody().toString()).getAsJsonObject().getAsJsonObject("rates");
			double exchangeRate = rates.get(currencyToBeConvertedTo).getAsDouble();
			return amount * exchangeRate;
		} else {
			System.err.println("Error fetching exchange rates: " + ((kong.unirest.HttpResponse<JsonNode>) response).getStatusText());
			return 0;
		}
	}

}

