package dodevelopers.consultas.dgii;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.corn.httpclient.HttpForm;
import net.sf.corn.httpclient.HttpResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dodevelopers.consultas.dgii.models.Rnc;

/**
 * Clase para parsear la pagina de la consulta de Rnc/Cedula de la DGII
 * 
 * @author daniel paniagua
 */
public class RncParser {

  private static final String DEF_JSON_CONFIG_FILENAME = "config.json";
  // jconfig config object
  private JsonObject jsonConfig;

  public RncParser() throws FileNotFoundException {
    this(DEF_JSON_CONFIG_FILENAME);
  }

  public RncParser(String configFileFullPath) throws FileNotFoundException {
    FileReader reader;
    reader = new FileReader(configFileFullPath);
    this.jsonConfig = (JsonObject) new JsonParser().parse(reader);
  }

  /**
   * Sobre carga para recibir el objeto con configuration Json
   * 
   * @param jsonConfig
   */
  public RncParser(JsonObject jsonConfig) {
    this.jsonConfig = jsonConfig;
  }

  /**
   * Busca Rnc o Cedula en la pagina de la DGII y parsea el resultado.
   * 
   * @param rncOrCedula
   * @throws URISyntaxException
   * @throws IOException
   */
  public Rnc parseRncConsult(String rncOrCedula) throws URISyntaxException, IOException {

    List<String> rncValues = new ArrayList<String>();
    String url = getJsonConfig().get("url").getAsString() + getJsonConfig().get("web_resource").getAsString();
    URI uri = new URI(url);

    HttpForm form = new HttpForm(uri);
    setHeaderParamsfromConfig(form);
    setRequestParamsfromConfig(form);

    // Set rnc or cedula paramater
    form.putFieldValue("txtRncCed", rncOrCedula);

    HttpResponse response = form.doPost();
    if (response.getCode() == HttpURLConnection.HTTP_OK) {
      // Http request was Ok, now parsing it
      Document doc = Jsoup.parse(response.getData());
      Element trElement = doc.getElementsByClass("GridItemStyle").first();
      if (trElement != null) {
        Elements tds = trElement.getElementsByTag("td");
        for (Element td : tds) {
          rncValues.add(td.text().replace("&nbsp;", " ").trim());
        }
      }
    }
    if (rncValues.size() < 6) {
      String errorMsgTemplate = getJsonConfig().get("not_found_string").getAsString();
      throw new IllegalArgumentException(errorMsgTemplate);
    }
    Rnc rnc = new Rnc(rncValues);
    return rnc;
  }

  /**
   * Asigna los parametros contantes del request http
   * 
   * @param httpform
   */
  private void setRequestParamsfromConfig(HttpForm httpform) {
    Map<String, String> requestParams = new LinkedHashMap<String, String>();
    JsonObject jsonHeaderCofig = getJsonConfig().get("request_parameters").getAsJsonObject();
    for (Map.Entry<String, JsonElement> entry : jsonHeaderCofig.entrySet()) {
      requestParams.put(entry.getKey(), entry.getValue().getAsString());
      httpform.putFieldValue(entry.getKey(), entry.getValue().getAsString());
    }
  }

  /**
   * Asigna los parametros contantes de la cabecera del request http
   * 
   * @param httpform
   */
  private void setHeaderParamsfromConfig(HttpForm httpform) {
    Map<String, String> headersParams = new LinkedHashMap<String, String>();
    JsonObject jsonHeaderCofig = getJsonConfig().get("request_headers").getAsJsonObject();
    for (Map.Entry<String, JsonElement> entry : jsonHeaderCofig.entrySet()) {
      headersParams.put(entry.getKey(), entry.getValue().getAsString());
      httpform.putAdditionalRequestProperty(entry.getKey(), entry.getValue().getAsString());
    }

  }

  /**
   * @return the jsonConfig
   */
  public JsonObject getJsonConfig() {
    return jsonConfig;
  }

  /**
   * @param jsonConfig
   *          the jsonConfig to set
   */
  public void setJsonConfig(JsonObject jsonConfig) {
    this.jsonConfig = jsonConfig;
  }
}
