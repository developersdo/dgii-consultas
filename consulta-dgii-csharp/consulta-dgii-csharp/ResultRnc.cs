namespace consulta_dgii_csharp
{
    /// <summary>
    /// Estructura retornada por el servicio de consulta de RNC
    /// </summary>
    public class ResultRnc
    {
        public string CedulaRnc { get; set; }
        public string Nombre { get; set; }
        public string NombreComercial { get; set; }
        public string Categoria { get; set; }
        public string RegimenDePago { get; set; }
        public string Estado { get; set; }
    }
}
