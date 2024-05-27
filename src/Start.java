
// Importo las librerías necesarias 
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class Start {

	public static void main(String[] args) throws SocketException, IOException {

		// Creamos las variables y las hacemos constantes
		final String SERVIDOR = "demo.wftpserver.com";
		final int PUERTO = 21;
		final String USER = "demo";
		final String PASSWORD = "demo";

		// Objeto cliente
		FTPClient clienteFtp = new FTPClient();

		// Conexión con el servidor
		clienteFtp.connect(SERVIDOR, PUERTO);
		int respuesta = clienteFtp.getReplyCode();

		// Si la conexión falla, saldrá un mensaje de error
		if (!FTPReply.isPositiveCompletion(respuesta)) {

			System.err.println("Algo ha fallado: " + respuesta);
		}

		// Si la conexión funciona, iniciamos sesión en el servidor y nos saldrá un
		// mensaje de confirmación
		boolean inicioSatisfactorio = clienteFtp.login(USER, PASSWORD);

		if (inicioSatisfactorio) {

			System.out.println("Conexión correcta");
			System.out.println("******************************");
		}

		// Listo los archivos que hay en el server
		System.out.println("Mostrando los archivos desde la raíz:");
		FTPFile[] files = clienteFtp.listFiles();

		for (FTPFile archivo : files) {

			System.out.println(archivo.getName());
		}
		System.out.println("******************************");

		// Listo los archivos que hay dentro de la carpeta download
		System.out.println("Mostrando los archivos de download:");

		files = clienteFtp.listFiles("/download");

		for (FTPFile archivo : files) {

			System.out.println(archivo.getName());
		}
		System.out.println("******************************");

		// Subir un archivo.docx
		String archivoSubir = "archivo.docx";
		FileInputStream inStream = new FileInputStream(archivoSubir);
		Boolean subido = clienteFtp.appendFile("/upload/archivo.docx", inStream);
		inStream.close();

		if (subido) {

			System.out.println("Archivo subido correctamente");
		}
		System.out.println("******************************");

		// Descargar un archivo.docx
		String archivoDescargar = "/upload/archivo.docx";
		FileOutputStream outStream = new FileOutputStream("archivo.docx");
		Boolean descargado = clienteFtp.retrieveFile(archivoDescargar, outStream);
		outStream.close();

		if (descargado) {

			System.out.println("Archivo descargado correctamente");
		}
		System.out.println("******************************");

		// Subir el archivo.docx a uploads con el nombre archivo2.docx
		String archivoSubir2 = "archivo.docx";
		FileInputStream inStream2 = new FileInputStream(archivoSubir2);
		Boolean subido2 = clienteFtp.appendFile("/upload/archivo2.docx", inStream2);
		inStream.close();

		if (subido2) {

			System.out.println("Archivo subido correctamente");
		}
		System.out.println("******************************");

		// Desconexión del servidor
		System.out.println("Desconexión correcta");
		clienteFtp.disconnect();
	}
}
