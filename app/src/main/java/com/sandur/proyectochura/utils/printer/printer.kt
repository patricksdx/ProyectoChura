import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sandur.proyectochura.R

fun loadHtmlAndExportAsPdf(webView: WebView?, context: Context, nombre: String, especiemascota: String, razamascota: String, ubicacion: String, sexo: String, foto: String) {
    if (webView != null) {
        val inputStream = context.resources.openRawResource(R.raw.printer)
        val htmlString = inputStream.bufferedReader().use { it.readText() }

        // Reemplazar los marcadores de posición con los datos reales
        val htmlFinal = htmlString.replace("{{nombre}}", nombre)
            .replace("{{especiemascota}}", especiemascota)
            .replace("{{razamascota}}", razamascota)
            .replace("{{ubicacion}}", ubicacion)
            .replace("{{sexo}}", if (sexo == "1"){ "Masculino" } else { "Femenino"})
            .replace("{{foto}}", if (foto == "https://api-proyectochura-express.onrender.com/") {"No se encontro imagen"} else {foto})

        // Cargar el contenido HTML en la WebView
        webView.webViewClient = WebViewClient()
        webView.loadDataWithBaseURL(null, htmlFinal, "text/html", "UTF-8", null)

        // Exportar como PDF una vez que se haya cargado el contenido
        webView.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                exportAsPdf(webView, context)
            }
        })
    }
}


fun exportAsPdf(webView: WebView?, context: Context) {
    if (webView != null) {
        // Obtener el servicio de impresión
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager

        // Crear el adaptador de impresión a partir de la WebView
        val printAdapter = webView.createPrintDocumentAdapter("DocumentoPDF")

        // Configurar los atributos de impresión, como el tamaño del papel
        val printAttributes = PrintAttributes.Builder()
            .setMediaSize(PrintAttributes.MediaSize.ISO_A4) // Tamaño de papel A4
            .setResolution(PrintAttributes.Resolution("id", "pdf_resolution", 300, 300)) // Resolución del PDF
            .setMinMargins(PrintAttributes.Margins.NO_MARGINS) // Sin márgenes
            .build()

        // Enviar la tarea de impresión al PrintManager para generar el PDF
        printManager.print("DocumentoPDF", printAdapter, printAttributes)
    }
}
