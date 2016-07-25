package ph.kana.reor.util.helperinterface;

@FunctionalInterface
public interface ThrowingRunnable<T extends Throwable> {

	void runWithThrowable() throws T;
}
