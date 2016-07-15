package ph.kana.reor.type;

import javafx.scene.paint.Paint;

public enum MessageType {
	SUCCESS(Paint.valueOf("#5cb85c"), '\u2714'),
	WARNING(Paint.valueOf("#f0ad4e"), '\u2757'),
	ERROR  (Paint.valueOf("#d9534f"), '\u2716');

	private Paint paint;
	private char icon;

	private MessageType(Paint paint, char icon) {
		this.paint = paint;
		this.icon = icon;
	}

	public Paint getPaint() {
		return paint;
	}

	public char getIcon() {
		return icon;
	}
}
