package org.atinject.tck.auto;

import org.atinject.tck.auto.accessories.Cupholder;

public final class SeatFactory {
    private SeatFactory() {
    }

    public static Seat create(Cupholder cupholder) {
        return new Seat(cupholder);
    }
}
