package ph.kana.reor.util.function;

@FunctionalInterface
public interface CheckedRunnable {

	void run() throws Exception;
}
