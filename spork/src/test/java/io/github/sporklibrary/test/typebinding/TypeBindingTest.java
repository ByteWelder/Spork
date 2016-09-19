package io.github.sporklibrary.test.typebinding;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.exceptions.BindException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TypeBindingTest {

    @BindValue(123)
    public final class IntegerHolder implements IntSettable {
        private int mValue = 100;

        public IntegerHolder() {
            Spork.bind(this);
        }

        @Override
        public void setValue(int value) {
            mValue = value;
        }

        public int getValue() {
            return mValue;
        }
    }

    @BindValue(123)
    public final class FaultyIntegerHolder {
        public FaultyIntegerHolder() {
            Spork.bind(this);
        }
    }

    @Before
    public void registerTestBinders() {
        Spork.getBinderRegistry().register(new BindTypeBinder());
    }

    @Test
    public void test() {
        IntegerHolder holder = new IntegerHolder();
        assertEquals(123, holder.getValue());
    }

    @Test(expected = BindException.class)
    public void testFaultyType() {
        new FaultyIntegerHolder();
    }
}
