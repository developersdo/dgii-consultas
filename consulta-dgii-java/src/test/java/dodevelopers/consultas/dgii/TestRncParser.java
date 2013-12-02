package dodevelopers.consultas.dgii;
import java.io.IOException;
import java.net.URISyntaxException;

import junit.framework.TestCase;
import dodevelopers.consultas.dgii.RncParser;
import dodevelopers.consultas.dgii.models.Rnc;

/**
 * @author daniel paniagua
 * 
 */
public class TestRncParser extends TestCase {

  RncParser parser;

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {    
    parser = new RncParser("config.json");
    //parser = new RncParser("D://pydev//consulta-dgii//consulta_dgii//config.json");
    assertNotNull(parser);
    assertNotNull(parser.getJsonConfig());
  }

  /**
   * Test method for {@link dodevelopers.consultas.dgii.RncParser#parseRncConsult(java.lang.String)}.
   * 
   * @throws IOException
   * @throws URISyntaxException
   */
  public void testParseRncConsultWithValidCedula() throws URISyntaxException, IOException {
    Rnc rnc = parser.parseRncConsult("00113453914");
    assertEquals("CEDULA: 00113453914 es valida", "JUAN ANTONIO BULOS", rnc.getNombre());
    assertEquals("ACTIVO", rnc.getEstado());
    
  }

  public void testparseRncConsultWithValidRnc() throws Exception {
    Rnc rnc = parser.parseRncConsult("130267537");
    assertEquals("CLARO 8 DEVELOPMENT S A", "CLARO 8 DEVELOPMENT S A", rnc.getNombre());
    assertEquals("ACTIVO", rnc.getEstado());
  }

  public void testparseRncConsultFailingWithValidRnc() throws Exception {
    try {
      parser.parseRncConsult("13026000");
    } catch (IllegalArgumentException e) {
      assertEquals(parser.getJsonConfig().get("not_found_string").getAsString(), e.getMessage());
    }
  }

}
