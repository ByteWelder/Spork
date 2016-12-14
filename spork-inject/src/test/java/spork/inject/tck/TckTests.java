package spork.inject.tck;

import org.atinject.tck.Tck;
import org.junit.Test;

import spork.Spork;

public class TckTests {

	@Test
	public void test() {
		CarHolder carHolder = new CarHolder();
		CarModule carModule = new CarModule();

		Spork.bind(carHolder, carModule);

		Tck.testsFor(carHolder.getCar(), true, true);
	}
}
