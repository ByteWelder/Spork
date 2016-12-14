package spork.inject.tck;

import org.atinject.tck.Tck;
import org.atinject.tck.auto.Car;
import org.junit.Test;

import javax.inject.Inject;

import spork.Spork;

public class TckTests {

	public static class CarHolder {
		@Inject
		public Car car;
	}

	@Test
	public void test() {
		CarHolder carHolder = new CarHolder();
		CarModule carModule = new CarModule();

		Spork.bind(carHolder, carModule);

		Tck.testsFor(carHolder.car, true, true);
	}
}
