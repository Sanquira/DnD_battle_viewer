package network.command.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandHandler implements Runnable {
	private CommandStorage cs=CommandStorage.getInstance();
    BufferedReader stdIn =
            new BufferedReader(
                new InputStreamReader(System.in));
	
	@Override
	public void run() {
        String userInput;
		try {
			while ((userInput = stdIn.readLine()) != null) {
				if(!cs.checkCommand(userInput)){
					unknownCommand(userInput);
				};
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void unknownCommand(String input) {
		if(cs.defaultCommand!=null){
			cs.defaultCommand.getListener().CommandExecuted(cs.cutString(input));
		}
	}





	
}
