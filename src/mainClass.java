import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import core.file.Config;
import hexapaper.hexapaper;
import hexapaper.network.server.HexaServer;
import hexapaper.source.HPSklad;
import dungeonmapper.dungeonMapper;

public class mainClass {

	static Thread th;
	static CmdLineParser parser = new CmdLineParser(new mainClass());
	
	@Option(name="-dm",usage="Runs dungeonMapper")
	private static boolean dm;
	
	@Option(name="-s",usage="Runs HexaServer")
	private static boolean server;
	
	@Option(name="-ip",usage="Specifies IP")
	private static String ip;
	
	@Option(name="-port",usage="Specifies port")
	private static Integer port;
	
	@Option(name="-name",usage="Specifies name")
	private static String name;
	
	@Option(name="-console",usage="No gui for server")
	private static boolean console = false;
	
	public static void main(String[] args) throws CloneNotSupportedException {
		// TODO Vlastni vlakno pro kazdou aplikaci (mozna)
		ParseArgument(args);
		System.out.println(Config.getConfigFile());
		Config.loadConfig();
		Config cfg = Config.getInstance();
		boolean s=(ip!=null && port!=null);
		boolean connect=(s && name!=null);
		if (ip != null) {
			if (server) {
				cfg.serverIP = ip;
			} else {
				cfg.IP = ip;
			}
		}
		if (port != null) {
			System.out.println(port);
			if (server) {
				cfg.serverport = port;
			} else {
				cfg.port = port;
			}
		}
		if(name!=null){cfg.lastName=name;}
		if(server){
			new HexaServer(console,s);
			return;
		}
		if(dm){
			new dungeonMapper();
			return;
		}
		else{
			new hexapaper();
			//System.setErr(new PrintStream(new LoggingStream("HexaServer.log")));
			if(connect){
				HPSklad.getInstance().connect();
			}
			return;
		}
		
		//new dungeonMapper();
	}

	private static void ParseArgument(String[] args) {
		try {
			parser.parseArgument(removeChars(args));
		} catch (CmdLineException e) {
			 System.err.println(e.getMessage());
			 System.err.println("HexaClient [options...] arguments...");
			 // print the list of available options
			 parser.printUsage(System.err);
			 System.err.println();
		}		
	}

	private static String[] removeChars(String[] args) {
		for(int i = 0;i<args.length;i++){
			args[i] = args[i].replaceAll("[^\\x20-\\x7e]", "");
		}
		return args;		
	}

}
