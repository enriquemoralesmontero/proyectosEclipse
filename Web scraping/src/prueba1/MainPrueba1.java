package prueba1;

import java.io.IOException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @version 1
 * @author diurno
 */
public class MainPrueba1 {
	
	public static final String url = "http://cnmv.es/Portal/Consultas/IFI/ListaIFI.aspx?XBRL=S";
	
	public static void main(String[] args) throws IOException {
				
        if (getStatusConnectionCode(url) == 200) {		// Compruebo si el código es 200 al hacer la petición.
			
            Document document = getHtmlDocument(url); 	// Obtengo el HTML de la web en un objeto Document.
			
            // Busco todas las entradas que están dentro de: 
            		
            Elements entradas = document.select("tr"); 
            System.out.println("Número de entradas: " + entradas.size() + "\n");
            
            listarElementos(entradas, "data-th", "Fecha registro oficial");
			listarElementos(entradas, "data-th", "Nombre del emisor");
			listarElementos(entradas, "data-th", "Tipo de información");
			
        } else {
        	System.err.println("El Status Code no es OK es: " + getStatusConnectionCode(url));
        }

	}

	private static void listarElementos(Elements entradas, String nombreEtiqueta, String valorEtiqueta) {
		
		for (Element elem : entradas) {
			            	
		    //String textoScrapeado = elem.getElementsByTag("td").text();
		    String textoScrapeado = elem.getElementsByAttributeValue(nombreEtiqueta, valorEtiqueta).text();                
		    System.out.println(textoScrapeado);
		}
	}
	
	/**
	 * Con esta método compruebo el Status code de la respuesta que recibo al hacer la petición
	 * 
	 * 		200 OK					300 Multiple Choices
	 * 		301 Moved Permanently	305 Use Proxy
	 * 		400 Bad Request			403 Forbidden
	 * 		404 Not Found			500 Internal Server Error
	 * 		502 Bad Gateway			503 Service Unavailable
	 * 
	 * @param url
	 * @return Status Code
	 */
	public static int getStatusConnectionCode(String url) {
			
	    Response response = null;
		
	    try {
	    	response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
	    } catch (IOException ex) {
	    	System.err.println("Excepción al obtener el Status Code: " + ex.getMessage());
	    }
	    
	    return response.statusCode();
	}
	
	/**
	 * Con este método devuelvo un objeto de la clase Document con el contenido del
	 * HTML de la web que me permitirá parsearlo con los métodos de la librelia JSoup.
	 * 
	 * @param url
	 * @return Documento con el HTML
	 */
	public static Document getHtmlDocument(String url) {

	    Document doc = null;
	    
		try {
		    doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
		} catch (IOException ex) {
			System.err.println("Excepción al obtener el HTML de la página: " + ex.getMessage());
		}
		
	    return doc;
	}
}