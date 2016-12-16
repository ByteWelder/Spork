package spork.inject.tck;

import junit.framework.TestCase;

import org.atinject.tck.Tck;

import spork.Spork;

public class TckTests extends TestCase {

	public static junit.framework.Test suite() {
		CarHolder carHolder = new CarHolder();
		CarModule carModule = new CarModule();

		Spork.bind(carHolder, carModule);

		return Tck.testsFor(carHolder.getCar(), true, true);
	}
}
