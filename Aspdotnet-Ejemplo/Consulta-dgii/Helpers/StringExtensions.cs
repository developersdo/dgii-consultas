using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Consulta_dgii.Helpers
{
    public static class StringExtensions
    {
        public static string SuperSplit(this string str)
        {
            return str.Replace("&nbsp;", "");
        }
    }
}