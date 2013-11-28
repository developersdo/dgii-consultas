namespace consulta_dgii_csharp
{
    public static class StringExtensions
    {
        public static string SuperSplit(this string str)
        {
            return str.Replace("&nbsp;", "");
        }
    }
}