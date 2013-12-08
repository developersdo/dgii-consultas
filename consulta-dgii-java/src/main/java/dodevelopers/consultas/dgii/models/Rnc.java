package dodevelopers.consultas.dgii.models;

import java.util.List;

/**
 * 
 * Clase representa un record RNC
 * 
 * @author daniel paniagua
 * 
 */
public class Rnc {

  public Rnc() {

  }

  public Rnc(String cedulaRnc, String nombre, String nombreComercial, String categoria, String regimenDePago,
      String estado) {
    super();
    this.cedulaRnc = cedulaRnc;
    this.nombre = nombre;
    this.nombreComercial = nombreComercial;
    this.categoria = categoria;
    this.regimenDePago = regimenDePago;
    this.estado = estado;
  }

  public Rnc(List<String> values) {
    this(values.get(0), values.get(1), values.get(2), values.get(3), values.get(4), values.get(5));
  }

  public String cedulaRnc;
  public String nombre;
  public String nombreComercial;
  public String categoria;
  public String regimenDePago;
  public String estado;

  /**
   * @return the cedulaRnc
   */
  public String getCedulaRnc() {
    return cedulaRnc;
  }

  /**
   * @return the nombre
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * @return the nombreComercial
   */
  public String getNombreComercial() {
    return nombreComercial;
  }

  /**
   * @return the categoria
   */
  public String getCategoria() {
    return categoria;
  }

  /**
   * @return the regimenDePago
   */
  public String getRegimenDePago() {
    return regimenDePago;
  }

  /**
   * @return the estado
   */
  public String getEstado() {
    return estado;
  }

  /**
   * @param cedulaRnc
   *          the cedulaRnc to set
   */
  public void setCedulaRnc(String cedulaRnc) {
    this.cedulaRnc = cedulaRnc;
  }

  /**
   * @param nombre
   *          the nombre to set
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * @param nombreComercial
   *          the nombreComercial to set
   */
  public void setNombreComercial(String nombreComercial) {
    this.nombreComercial = nombreComercial;
  }

  /**
   * @param categoria
   *          the categoria to set
   */
  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  /**
   * @param regimenDePago
   *          the regimenDePago to set
   */
  public void setRegimenDePago(String regimenDePago) {
    this.regimenDePago = regimenDePago;
  }

  /**
   * @param estado
   *          the estado to set
   */
  public void setEstado(String estado) {
    this.estado = estado;
  }

}
