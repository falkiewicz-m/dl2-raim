using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace client_raim_dl1
{
    class Program
    {
        static void Main(string[] args)
        {
            string host = null, request = null , path = null;

            #region Komendy wejściowe
            if(args.Length == 0 || args.Length > 3)
            {
                host = "localhost";
                request = "GET / HTTP/1.1";
                path = "D:\\studia lokal\\" + DateTime.Now.ToShortDateString() + "1.html";
            }

            if(args.Length == 1)
            {
                host = args[0];
                request = "GET / HTTP/1.1";
                path = "D:\\studia lokal\\" + DateTime.Now.ToShortDateString() + ".html";
            }

            if(args.Length == 2)
            {
                host = args[0];
                request = args[1];
                path = "D:\\studia lokal\\" + DateTime.Now.ToShortDateString() + ".html";
            }

            if(args.Length == 3)
            {
                host = args[0];
                request = args[1];
                path = args[2];
            }
            #endregion
            try
            {
                TcpClient socket = new TcpClient(host, 50000);
                NetworkStream ns = socket.GetStream();
                StreamWriter sw = new StreamWriter(ns);
                sw.WriteLine(request);
                sw.Flush();

                StreamReader sr = new StreamReader(ns);
                string html = sr.ReadToEnd();
                File.WriteAllText(path, html);

                //zamykanie strumieni
                sr.Close();
                sw.Close();
                socket.Close();


            }
            catch (Exception e)
            {
                throw e;
            }
        }
    }
}
