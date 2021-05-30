import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
public class Hash {
	public Hash() {}

	// Divide o arquivo de video em blocos de 1KB (1024 bytes). Os blocos sao armazenados em um ArrayList retornado.
	public static ArrayList<byte[]> divideBlocos(String fileName) {
		try {
			BufferedInputStream file = new BufferedInputStream(new FileInputStream(fileName));
			ArrayList<byte[]> array_bytes = new ArrayList<byte[]>();
			byte[] buffer;
			while (file.available() > 0) {
				// Se o bloco for menor que 1024 bytes, entao o bloco sera do tamanho disponivel
				if (file.available() < 1024) {
					buffer = new byte[file.available()];
				}
				// Os outro blocos serao de tamanho 1024 bytes
				else {
					buffer = new byte[1024];
				}
				// Realiza a divisao do video em blocos
				file.read(buffer);
				// Armazena bloco em uma posicao do ArrayList
				array_bytes.add(buffer);
			}
			file.close();
			return array_bytes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/*
	 * Recebe os blocos e inicia calculando o hash SHA-256 do ultimo bloco, anexa esse valor no bloco anterior a ele, e assim
	 * sucessivamente ate chegar no primeiro bloco hO. Ao final da execução, imprime o hash hO calculado.
	 */
	public byte[] calculaHash(ArrayList<byte[]> bytesArray) throws NoSuchAlgorithmException {
		byte[] finalBlock = null;
		// Percorre ArrayList de forma invertida
		for (int i = bytesArray.size() - 1; i >= 0; i--) {
			// Funcao Hash SHA-256
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			// Se for o primeiro bloco h0, encerra a execucao e imprime o resultado
			if (i == 0) {
				atualBlock = md.digest(bytesArray.get(i));
				break;
			}
			// Calcula hash do bloco atual
			byte[] atualBlock = md.digest(bytesArray.get(i));
			// Anexa valor do hash calculado para o bloco atual no bloco anterior a ele
			byte[] previousBlock = bytesArray.get(i - 1);
			byte[] newBlock = concat(bloco_hash_anterior, atualBlock);
			array_bytes.set(i - 1, newBlock);
		}
		// Imprime o hash final calculado 'h0'
		System.out.println("h0: " + toHexString(finalBlock));
		return finalBlock;
	}

	// Funcao que concatena dois arrays. Anexa o array2 no final de array1.
	public static byte[] concat(byte[] array1, byte[] array2) {
		byte[] resultado = new byte[array1.length + array2.length];
		System.arraycopy(array1, 0, resultado, 0, array1.length);
		System.arraycopy(array2, 0, resultado, array1.length, array2.length);
		return resultado;
	}

	// Funcao para converter um array de bytes para uma String em hexadecimal.
	public static String toHexString(byte[] array) {
		return javax.xml.bind.DatatypeConverter.printHexBinary(array).toLowerCase();
	}

}
