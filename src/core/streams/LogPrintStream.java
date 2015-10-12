package core.streams;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;

import addons.dice.LogWindow;

public final class LogPrintStream extends PrintStream {

	private final DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

	public LogPrintStream(LogWindow log) {
		super(new LogOutputStream(log));
		//super.println("<span style='color:#ff00ff;font-weight:bold;'>Test</span>");
	}

	private String getCurrentDate() {
		return dateFormat.format(new Date());
	}

	private StringBuffer getPrefix() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[").append(getCurrentDate()).append("]: ");
		return buffer;
	}

	@Override
	public void print(String message) {
		super.print(getPrefix() + message);
	}

}