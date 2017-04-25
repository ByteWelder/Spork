package spork.stubs;

@TestAnnotation
public class TestBindTarget {
	@TestAnnotation
	private int value;

	public TestBindTarget(int value) {
		this.value = value;
	}

	public TestBindTarget() {
		this(1);
	}

	@TestAnnotation
	public int getValue() {
		return value;
	}
}