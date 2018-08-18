package com.bancolombia.citrus.reto_tecnico.walmart.test;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.bancolombia.citrus.reto_tecnico.walmart.util.Printer;
import com.bancolombia.citrus.reto_tecnico.walmart.util.Validation;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.dsl.runner.TestRunner;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Test with TestNGCitrusTestDesigner
 * 
 * @author John Hader Pelaez Noreña - jhpelaezn@gmail.com
 *
 */
public class Walmart_IT extends TestNGCitrusTestRunner {

	@Autowired
	private HttpClient walmart;

	@Test
	@Parameters("runner")
	@CitrusTest(name = "Walmart Test")
	public void test(@Optional @CitrusResource TestRunner runner) {
		try {
			String api = variable("api", "${api}");
			String query = variable("query", "${query}");

			http(HttpActionBuilder -> HttpActionBuilder
					.client(walmart)
					.send()
					.get(api + query)
					.accept(ContentType.APPLICATION_JSON.getMimeType()));

			http(httpActionBuilder -> httpActionBuilder
					.client(walmart)
					.receive()
					.response(HttpStatus.OK)
					.name("msgReceived")
					.messageType(MessageType.JSON)
					.extractFromPayload("$.numItems", "numItems")
					.extractFromPayload("$.items[${itemIndex}].name", "firstItem")
					.extractFromPayload("$.items[${giftOptionsIndex}].giftOptions", "giftOptions"));

			String messageReceived = variable("msg", "citrus:message(msgReceived)");
			JsonParser parser = new JsonParser();
			JsonElement messageData = parser.parse(messageReceived);

			Printer.separator("inciso a");
			String numItems = variable("countItems", "${numItems}");
			if (Validation.isGreaterThan(Integer.parseInt(numItems), 1)) {
				Printer.message("Numero de items: ", numItems);
			} else {
				Printer.message("No cumple las condiciones del test, fallo el inciso a");
				fail("No cumple las condiciones del test, fallo el inciso a");
			}

			Printer.separator("inciso b");
			String nameItem = variable("var", "${firstItem}");
			Printer.message("nombre del primer Item encontrado: ", nameItem);

			Printer.separator("inciso c");
			String giftOptions = variable("varGift", "${giftOptions}");
			Printer.message("Opciones de regalo para el producto 3: ", giftOptions);

			Printer.separator("inciso d");
			String nameObjectSerched = variable("value", "${nameObjectSearched}");
			Query.searchItemInMessage(messageData, nameObjectSerched);
			String itemsName = Query.getItemsName().toString();
			Printer.message("Nombre de todos los Items: \n", itemsName);

			/**
			 * FALTA QUE SE VEA TODO EL STRING
			 */
			Printer.separator("inciso e");
			String productName = variable("name", "${productName}");
			JsonElement items = messageData.getAsJsonObject().get("items");
			Query.searchAllItemsByProductName(items, productName);
			StringBuilder allItems = Query.getAllItems();
			Printer.message("Los items relacionados con el producto " + productName + " son: ", allItems.toString());

			Printer.separator("inciso f");
			String price = variable("price", "${price}");
			Query.searchItemByPrice(items);
			StringBuilder salePriceObject = Query.getSalePriceObject();
			StringBuilder itemNameSalePriceObject = Query.getItemNameSalePriceObject();
			StringBuilder names = Validation.searchNamesByPrice(salePriceObject, itemNameSalePriceObject, price);
			Printer.message("los Items cuyo valor de venta es menor que " + price + " dolares son: \n",
					names.toString());

			Printer.separator("inciso g");
			String endWith = variable("value", "${endWith}");
			Query.searchItemInMessageThatEndWith(messageData, nameObjectSerched, endWith);
			String itemsEndWith = Query.getItemsEndWith().toString();
			Printer.message("Los Items que terminan con la palabra " + endWith + " son: ", itemsEndWith);

			Printer.separator("inciso h");
			int numItemsThatEndWith = Query.getCount();
			Printer.message("El numero de items que terminan con la palabra " + endWith + " es: " + numItemsThatEndWith);

		} catch (Exception e) {
			fail("Error. Prueba fallida");
		}
	}

}
