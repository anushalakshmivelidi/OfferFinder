package com.offerfinder;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.PushGateway;

public class Telemetry {

	public static void pushProducts(Set<Product> products, String marketplace, String catagory) {
		// push the product details to promethus
		CollectorRegistry registry = new CollectorRegistry();
		Gauge myGauge = Gauge.build().name("price").help("price of different things")
				.labelNames("name", "quantity", "marketplace","catagory").register(registry);

		for (Product product:products) {
			String productName = product.getName();
			String quantity = product.getQuantity();
			myGauge.labels(productName, quantity, marketplace,catagory).set(product.getPrice());
			PushGateway pg = new PushGateway(System.getenv("PUSH_GATEWAY"));
			try {
				pg.pushAdd(registry, "price_monitor");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
