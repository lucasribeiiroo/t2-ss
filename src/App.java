import java.util.ArrayList;

public class App {
	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.out.println("Run: java -jar Hash.jar <nome_video>");
			System.exit(1);
		}
		// Nome do arquivo de entrada
		String file = args[0];
		Hash hash = new Hash();
		// Divide video em blocos de 1024 bytes
		ArrayList<byte[]> file = hash.divide(file);
		// Calcula hash do primeiro bloco H0
		hash.calHash(file);
	}
}
