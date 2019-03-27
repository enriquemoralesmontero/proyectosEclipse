package prueba1;

import java.io.IOException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainPruebaJaroba {
	
	public static final String url = "https://jarroba.com/";
	
	public static void main(String[] args) throws IOException {
				
        if (getStatusConnectionCode(url) == 200) {		// Compruebo si el c�digo es 200 al hacer la petici�n.
			
            Document document = getHtmlDocument(url); 	// Obtengo el HTML de la web en un objeto Document.
			
            // Busco todas las entradas que est�n dentro de: 
            			
            Elements entradas = document.select("div.col-md-4.col-xs-12").not("div.col-md-offset-2.col-md-4.col-xs-12"); 
            System.out.println("N�mero de entradas en la p�gina inicial de Jarroba: "+ entradas.size()+"\n");
            
            for (Element elem : entradas) {
            	
                String titulo = elem.getElementsByClass("tituloPost").text();	// Con "text()" obtengo el contenido que hay dentro de las etiquetas HTML.
                String autor = elem.getElementsByClass("autor").toString();		// Con "toString()" obtengo todo el HTML con etiquetas incluidas.
                String fecha = elem.getElementsByClass("fecha").text();
				
                System.out.println(titulo + "\n" + autor + "\n" + fecha + "\n\n");
            }
				
        } else {
        	System.err.println("El Status Code no es OK es: " + getStatusConnectionCode(url));
        }

	}
	
	/**
	 * Con esta m�todo compruebo el Status code de la respuesta que recibo al hacer la petici�n
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
	    	System.err.println("Excepci�n al obtener el Status Code: " + ex.getMessage());
	    }
	    
	    return response.statusCode();
	}
	
	/**
	 * Con este m�todo devuelvo un objeto de la clase Document con el contenido del
	 * HTML de la web que me permitir� parsearlo con los m�todos de la librelia JSoup.
	 * 
	 * @param url
	 * @return Documento con el HTML
	 */
	public static Document getHtmlDocument(String url) {

	    Document doc = null;
	    
		try {
		    doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
		} catch (IOException ex) {
			System.err.println("Excepci�n al obtener el HTML de la p�gina: " + ex.getMessage());
		}
		
	    return doc;
	}
}