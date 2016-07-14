package ph.kana.reor.type;

import javafx.scene.paint.Paint;

public enum MessageType {
	SUCCESS(Paint.valueOf("#5cb85c")),
	WARNING(Paint.valueOf("#f0ad4e")),
	ERROR  (Paint.valueOf("#d9534f"));

	private Paint paint;

	private MessageType(Paint paint) {
		this.paint = paint;
	}

	public Paint getPaint() {
		return paint;
	}
}
