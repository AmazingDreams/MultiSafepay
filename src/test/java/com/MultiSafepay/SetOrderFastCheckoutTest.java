package com.MultiSafepay;

import java.util.ArrayList;
import java.util.List;

import com.MultiSafepay.classes.*;
import com.MultiSafepay.client.MultiSafepayClient;
import com.google.gson.JsonObject;
import org.junit.Test;

/**
 * Creates a Checkout Order
 * POST - orders
 * @author Multisafepay.com
 */

public class SetOrderFastCheckoutTest {

	static java.util.Date date	= null;

	@Test
	public void main() {
		date		= new java.util.Date();
    	Order order	= null;

        MultiSafepayClient client = new MultiSafepayClient("", true);

    	order 		= noShippingMethod();
    	//order 	= shippingMethodPickup();
    	//order 	= alternateCustomTaxTable();
    	

    	JsonObject jsonResponse 	= client.createOrder(order);
    	System.out.println(jsonResponse);
    	
    	String payment_url			= client.getPaymenUrl(jsonResponse);
    	System.out.println(payment_url);

	}
	
	public static Order noShippingMethod() {
		CheckoutOptions checkout_options	= new CheckoutOptions();
    	checkout_options.no_shipping_method = true;
    	
    	List<ShoppingCartItem> items 		= new ArrayList<ShoppingCartItem>();
    	items.add(ShoppingCartItem.add(	"Test Product","Sample description","90.00","EUR","1","123456","none",Weight.set("KG", "12")));
    	
    	Order order = new Order();
    	order.setFastCheckout(Long.toString(date.getTime()), "Product description", 1000, "EUR", 
    			new PaymentOptions("http://example.com/notify", "http://example.com/success", "http://example.com/failed"),
    			new ShoppingCart(items),
    			checkout_options
    	);
    	return order;
	}
	
	public static Order shippingMethodPickup()
	{
		CheckoutOptions checkout_options			= new CheckoutOptions();
    	checkout_options.shipping_methods			= new ShippingMethods();
    	
    	checkout_options.shipping_methods.setPickup(ShippingMethod.set("Shipping","7.00","EUR"));
    	
    	List<ShoppingCartItem> items 				= new ArrayList<ShoppingCartItem>();
    	items.add(ShoppingCartItem.add(	"Test Product","Sample description","90.00","EUR","1","123456","none",Weight.set("KG", "12")));
    	
    	Order order = new Order();
    	order.setFastCheckout(Long.toString(date.getTime()), "Product description", 1000, "EUR", 
    			new PaymentOptions("http://example.com/notify", "http://example.com/success", "http://example.com/failed"),
    			new ShoppingCart(items),
    			checkout_options
    	);
    	return order;
	}
	
	public static Order alternateCustomTaxTable()
	{
		CheckoutOptions checkout_options			= new CheckoutOptions();
    	
    	checkout_options.shipping_methods			= new ShippingMethods();
    	List<ShippingMethod> flatRate				= new ArrayList<ShippingMethod>();
    	
    	flatRate.add(ShippingMethod.set("Shipping","7.00","EUR"));
    	
    	checkout_options.shipping_methods.setFlatRate(flatRate);
    	
    	checkout_options.tax_tables	= new TaxTables();
    	
    	//checkout_options.tax_tables.addDefault(TaxTable.setDefault("0.21",true));
    	
    	List<TaxTable> tables 		= new ArrayList<TaxTable>();
    	
    	List<TaxTableRule> tableRule= new ArrayList<TaxTableRule>();
    	tableRule.add(TaxTableRule.addRule("0.21",null,null));
    	tables.add(TaxTable.setAlternate("BTW21",null,tableRule,null,null));
    	
    	checkout_options.tax_tables.addAlternates(tables);

    	List<ShoppingCartItem> items 	= new ArrayList<ShoppingCartItem>();
    	items.add(ShoppingCartItem.add(	"Test Product","Sample description","90.00","EUR","1","123456","Alternate",Weight.set("KG", "12")));
    	
    	Order order = new Order();
    	order.setFastCheckout(Long.toString(date.getTime()), "Product description", 1000, "EUR", 
    			new PaymentOptions("http://example.com/notify", "http://example.com/success", "http://example.com/failed"),
    			new ShoppingCart(items),
    			checkout_options
    	);
    	return order;
	}
	
}
