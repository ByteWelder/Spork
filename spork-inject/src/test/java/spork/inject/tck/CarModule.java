package spork.inject.tck;

import org.atinject.tck.auto.Car;
import org.atinject.tck.auto.Drivers;
import org.atinject.tck.auto.Seat;
import org.atinject.tck.auto.ConvertibleFactory;
import org.atinject.tck.auto.Tire;

import javax.inject.Named;
import javax.inject.Provider;

import spork.inject.Provides;

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
