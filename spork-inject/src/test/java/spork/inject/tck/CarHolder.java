package spork.inject.tck;

import org.atinject.tck.auto.Car;

import javax.inject.Inject;

class CarHolder {
    @Inject
    private Car car;

	public Car getCar() {
		return car;
	}
}
