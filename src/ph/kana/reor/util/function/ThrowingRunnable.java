package ph.kana.reor.util.function;

@FunctionalInterface
public interface ThrowingRunnable<T extends Throwable> {

	void runWithThrowable() throws T;
}
