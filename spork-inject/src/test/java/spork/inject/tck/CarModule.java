package spork.inject.tck;

import org.atinject.tck.auto.Car;
import org.atinject.tck.auto.Drivers;
import org.atinject.tck.auto.DriversSeat;
import org.atinject.tck.auto.FuelTank;
import org.atinject.tck.auto.Seat;
import org.atinject.tck.auto.ConvertibleFactory;
import org.atinject.tck.auto.SeatFactory;
import org.atinject.tck.auto.Tire;
import org.atinject.tck.auto.accessories.Cupholder;
import org.atinject.tck.auto.accessories.SpareTire;

import javax.inject.Named;
import javax.inject.Provider;

import spork.inject.Module;
import spork.inject.Provides;

@Module
public class CarModule {

    @Provides
    public Car provideCar(Seat plainSeat,
                          @Drivers Seat driversSeat,
                          Tire plainTire,
                          @Named("spare") Tire spareTire,
                          Provider<Seat> plainSeatProvider,
                          @Drivers Provider<Seat> driversSeatProvider,
                          Provider<Tire> plainTireProvider,
                          @Named("spare") Provider<Tire> spareTireProvider) {
        return ConvertibleFactory.create(plainSeat,
                driversSeat,
                plainTire,
                spareTire,
                plainSeatProvider,
                driversSeatProvider,
                plainTireProvider,
                spareTireProvider);
    }
}
