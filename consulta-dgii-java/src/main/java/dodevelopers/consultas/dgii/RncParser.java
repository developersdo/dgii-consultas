package dodevelopers.consultas.dgii;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.corn.httpclient.HttpForm;
import net.sf.corn.httpclient.HttpResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dodevelopers.consultas.dgii.models.Rnc;
import dodevelopers.consultas.dgii.util.ParserUtil;

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

    Rnc rnc = null;
    String url = getJsonConfig().get("url").getAsString() + getJsonConfig().get("web_resource").getAsString();
    URI uri = new URI(url);

    HttpForm form = new HttpForm(uri);
    setCommonRequestParamsfromConfig(form);

    // Set rnc or cedula paramater
    form.putFieldValue("txtRncCed", rncOrCedula);

    HttpResponse response = form.doPost();
    if (response.getCode() == HttpURLConnection.HTTP_OK) {
      // Http request was Ok, now parsing it
      rnc = ParserUtil.createRncFromHtml(response.getData(), getJsonConfig());
    }
    return rnc;
  }

  /**
   * Asigna los parametros contantes del request http
   * 
   * @param httpform
   */
  private void setCommonRequestParamsfromConfig(HttpForm httpform) {
    Map<String, String> headersParams = new LinkedHashMap<String, String>();
    JsonObject jsonHeaderCofig = getJsonConfig().get("request_headers").getAsJsonObject();

    for (Map.Entry<String, JsonElement> entry : jsonHeaderCofig.entrySet()) {
      headersParams.put(entry.getKey(), entry.getValue().getAsString());
      httpform.putAdditionalRequestProperty(entry.getKey(), entry.getValue().getAsString());
    }

    Map<String, String> requestParams = new LinkedHashMap<String, String>();
    JsonObject jsonRequestCofig = getJsonConfig().get("request_parameters").getAsJsonObject();

    for (Map.Entry<String, JsonElement> entry : jsonRequestCofig.entrySet()) {
      requestParams.put(entry.getKey(), entry.getValue().getAsString());
      httpform.putFieldValue(entry.getKey(), entry.getValue().getAsString());
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
