package com.bancolombia.citrus.reto_tecnico.walmart.util;

/**
 * Clase que se encargara de las validaciones que se requieran
 * @author John Hader Pelaez Noreña - jhpelaezn@gmail.com
 *
 */
public class Validation {

	/**
	 * Compara que que el number1 sea mayor qe number2
	 * @param number1
	 * @param number2
	 * @return
	 */
	public static boolean isGreaterThan(int number1, int number2) {
		if (number1 > number2)
			return true;
		else
			return false;
	}

	/**
	 * Metodo que busca los nombres de los productos que tengan un precio de venta
	 * inferior al precio pasado como parametro
	 * @param salePriceObject = informacion de todos los precios de venta
	 * @param itemNameSalePriceObject = nombre de todos los productos
	 * @param price = Precio de venta
	 * @return
	 */
	public static StringBuilder searchNamesByPrice(StringBuilder salePriceObject, StringBuilder itemNameSalePriceObject, String price) {
		String[] salePriceObjectSplitByComa = salePriceObject.toString().split(",");
		String[] itemNameSalePriceObjectByComa = itemNameSalePriceObject.toString().split("\" ,");
		StringBuilder names = new StringBuilder();
		for (int i = 0; i < salePriceObjectSplitByComa.length - 1; i++) {
			String splitTemp[] = salePriceObjectSplitByComa[i].split("=");
			if (Float.parseFloat(splitTemp[1]) < Integer.parseInt(price)) {
				names.append("- " + itemNameSalePriceObjectByComa[i]);
				names.append("\n");
			}
		}
		return names;
	}

}
