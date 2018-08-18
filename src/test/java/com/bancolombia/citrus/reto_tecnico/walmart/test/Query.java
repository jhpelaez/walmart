package com.bancolombia.citrus.reto_tecnico.walmart.test;

import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Clase para la extraccion de informacion del archivo json
 * @author John Hader Pelaez Noreña - jhpelaezn@gmail.com
 *
 */
public class Query extends TestNGCitrusTestRunner {
	
	private static StringBuilder itemsName = new StringBuilder();
	private static StringBuilder allItems = new StringBuilder();
	private static StringBuilder itemsEndWith = new StringBuilder();
	private static StringBuilder salePriceObject = new StringBuilder();
	private static StringBuilder itemNameSalePriceObject = new StringBuilder();
	private static int count = 0;
	
	static StringBuilder getItemsName() {
		return itemsName;
	}

	public static StringBuilder getAllItems() {
		return allItems;
	}

	public static StringBuilder getItemsEndWith() {
		return itemsEndWith;
	}

	public static int getCount() {
		return count;
	}
	
	public static StringBuilder getSalePriceObject() {
		return salePriceObject;
	}

	public static StringBuilder getItemNameSalePriceObject() {
		return itemNameSalePriceObject;
	}
	
	/**
	 * Recorre todo el mensaje y busca el valor del "objeto" pasado como parametro
	 * @param messageData = Mensaje json
	 * @param item = objeto a buscar
	 */
	public static void searchItemInMessage(JsonElement messageData, String item) {
		if (messageData.isJsonObject()) {
	        JsonObject obj = messageData.getAsJsonObject();
	        java.util.Set<java.util.Map.Entry<String,JsonElement>> values = obj.entrySet();
	        java.util.Iterator<java.util.Map.Entry<String,JsonElement>> iter = values.iterator();
	        while (iter.hasNext()) {
	            java.util.Map.Entry<String,JsonElement> data = iter.next();
	            if (data.getKey().equals(item)) {
		            itemsName.append("- "+data.getValue());
		            itemsName.append('\n');
	            }
	            searchItemInMessage(data.getValue(), item);
	        }
	    } else if (messageData.isJsonArray()) {
	        JsonArray array = messageData.getAsJsonArray();
	        java.util.Iterator<JsonElement> iter = array.iterator();
	        while (iter.hasNext()) {
	            JsonElement entrada = iter.next();
	            searchItemInMessage(entrada, item);
	        }
	    } 
	}

	/**
	 * Recorre el mensaje en busca de los parametros de un item pasado como parametro
	 * @param messageData
	 * @param productName
	 */
	public static void searchAllItemsByProductName(JsonElement messageData, String productName) {
		System.err.println("productName: "+productName);
		if (messageData.isJsonObject()) {
	        JsonObject obj = messageData.getAsJsonObject();
	        java.util.Set<java.util.Map.Entry<String,JsonElement>> entradas = obj.entrySet();
	        if (entradas.toString().contains(productName)) {
	        	allItems.append(entradas.toString());
	        }
	    } else if (messageData.isJsonArray()) {
	    	JsonArray array = messageData.getAsJsonArray();
	        java.util.Iterator<JsonElement> iter = array.iterator();
	        while (iter.hasNext()) {
	            JsonElement entrada = iter.next();
	            searchAllItemsByProductName(entrada, productName);
	        }
	    } 
	}

	/**
	 * Busca en el mensaje el valor de un "objeto" pasado como parametro y que ademas termina con la palabra
	 * pasada en el parametro item
	 * @param messageData = Mensaje json
	 * @param item = objeto a buscar
	 * @param endWith = palabra con la que debe terminar el nombre del objeto
	 */
	public static void searchItemInMessageThatEndWith(JsonElement messageData, String item,
			String endWith) {
		if (messageData.isJsonObject()) {
	        JsonObject obj = messageData.getAsJsonObject();
	        java.util.Set<java.util.Map.Entry<String,JsonElement>> values = obj.entrySet();
	        java.util.Iterator<java.util.Map.Entry<String,JsonElement>> iter = values.iterator();
	        while (iter.hasNext()) {
	            java.util.Map.Entry<String,JsonElement> data = iter.next();
	            if (data.getKey().equals(item) && data.getValue().toString().endsWith(endWith)) {
		            itemsEndWith.append("- "+data.getValue());
		            itemsEndWith.append('\n');
		            count ++;
	            }
	            searchItemInMessageThatEndWith(data.getValue(), item, endWith);
	        }
	    } else if (messageData.isJsonArray()) {
	        JsonArray array = messageData.getAsJsonArray();
	        java.util.Iterator<JsonElement> iter = array.iterator();
	        while (iter.hasNext()) {
	            JsonElement entrada = iter.next();
	            searchItemInMessageThatEndWith(entrada, item, endWith);
	        }
	    }
	}
	
	/**
	 * Busca en el mensaje todos los nombres y precios de venta de los productos.
	 * @param messageData = Mensaje json
	 */
	public static void searchItemByPrice(JsonElement messageData) {
		if (messageData.isJsonObject()) {
	        JsonObject obj = messageData.getAsJsonObject();
	        java.util.Set<java.util.Map.Entry<String,JsonElement>> entradas = obj.entrySet();
	        String data = entradas.toString();
	        int index = data.indexOf("salePrice=");
	        
	        for (int i=index; i<data.length(); i++) {
	        	String character = data.charAt(i)+""; 
	        	if (character.equals(",")) {
	        		i = data.length()+1;
	        		salePriceObject.append(" , ");
	        	} else {
	        		salePriceObject.append(data.charAt(i));
	        	}
	        }
	        itemsName = new StringBuilder();
	        index = data.indexOf("name=");
	        
	        for (int i=index; i<data.length(); i++) {
	        	String character1 = data.charAt(i)+"";
	        	String character2 = data.charAt(i+1)+"";
	        	if (character1.equals("\"") && character2.equals(",")) {
	        		i = data.length()+1;
	        		itemNameSalePriceObject.append("\" , ");
	        	} else {
	        		itemNameSalePriceObject.append(data.charAt(i));
	        	}
	        }
	    } else if (messageData.isJsonArray()) {
	    	JsonArray array = messageData.getAsJsonArray();
	        java.util.Iterator<JsonElement> iter = array.iterator();
	        while (iter.hasNext()) {
	            JsonElement entrada = iter.next();
	            searchItemByPrice(entrada);
	        }
	    
	    }
	}

	

}
