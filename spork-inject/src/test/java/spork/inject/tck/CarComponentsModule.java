package spork.inject.tck;

import org.atinject.tck.auto.Drivers;
import org.atinject.tck.auto.DriversSeat;
import org.atinject.tck.auto.FuelTank;
import org.atinject.tck.auto.Seat;
import org.atinject.tck.auto.SeatFactory;
import org.atinject.tck.auto.Tire;
import org.atinject.tck.auto.accessories.Cupholder;
import org.atinject.tck.auto.accessories.SpareTire;

import javax.inject.Named;
import javax.inject.Provider;

import spork.inject.Module;
import spork.inject.Provides;

@Module
public class CarComponentsModule {
	@Provides
	public Seat provideDriverSeat(Cupholder cupholder) {
		return new DriversSeat(cupholder);
	}

	@Provides
	public Tire providePlainTire(FuelTank fuelTank) {
		return new Tire(fuelTank);
	}

	@Provides @Named("spare")
	public Tire provideSpareTire(FuelTank forSupertype, FuelTank forSubtype) {
		return new SpareTire(forSupertype, forSubtype);
	}

	@Provides
	public Seat providePlainSeat(Cupholder cupholder) {
		return SeatFactory.create(cupholder);
	}

	@Provides @Drivers
	public Seat provideDriversSeat(Cupholder cupholder) {
		return new DriversSeat(cupholder);
	}

	// endregion

	// region subcomponents

	@Provides
	public Cupholder provideCupholder(Provider<Seat> seatProvider) {
		return new Cupholder(seatProvider);
	}

	@Provides
	public FuelTank provideFuelTank() {
		return new FuelTank();
	}

	// endregion
}
