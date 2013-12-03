package dodevelopers.consultas.dgii.util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonObject;

import dodevelopers.consultas.dgii.models.Rnc;

/**
 * Clase utilitaria para parsear datos de la pagina de consulta de la DGII
 * @author daniel paniagua
 *
 */
public class ParserUtil {

  protected ParserUtil() {
    // Avoid instance creation
  }

  /**
   * Parsea el html de la consulta de RNC y crea la entidad RNC
   * @param html
   * @param jsonConfig
   * @return
   */
  public static Rnc createRncFromHtml(String html, JsonObject jsonConfig) {
    List<String> rncValues = new ArrayList<String>();
    Document doc = Jsoup.parse(html);
    Elements tds = doc.select("tr.GridItemStyle td");
    //Validate if rnc/cedula was found
    if (tds == null || tds.size() < 6) {
      String errorMsgTemplate = jsonConfig.get("not_found_string").getAsString();
      throw new IllegalArgumentException(errorMsgTemplate);
    }    
    for (Element td : tds) {
      rncValues.add(td.text().replace("&nbsp;", " ").trim());
    }     
    Rnc rnc = new Rnc(rncValues);
    return rnc;
  }
}
