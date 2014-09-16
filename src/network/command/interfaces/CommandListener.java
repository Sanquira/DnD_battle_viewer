package network.command.interfaces;

import java.util.List;

public interface CommandListener {
	public void CommandExecuted(List<String> args);
}
