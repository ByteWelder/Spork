package org.atinject.tck.auto;

import javax.inject.Named;
import javax.inject.Provider;

import spork.Spork;

public final class ConvertibleFactory {
	private ConvertibleFactory() {
	}

	public static Convertible create(Seat plainSeat,
					  @Drivers Seat driversSeat,
					  Tire plainTire,
					  @Named("spare") Tire spareTire,
					  Provider<Seat> plainSeatProvider,
					  @Drivers Provider<Seat> driversSeatProvider,
					  Provider<Tire> plainTireProvider,
					  @Named("spare") Provider<Tire> spareTireProvider) {
		return new Convertible(plainSeat, driversSeat, plainTire, spareTire, plainSeatProvider, driversSeatProvider, plainTireProvider, spareTireProvider);
	}
}
