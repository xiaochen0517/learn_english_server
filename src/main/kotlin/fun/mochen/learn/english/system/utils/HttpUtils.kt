package `fun`.mochen.learn.english.system.utils

import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.Proxy
import java.net.ProxySelector

class HttpUtils {

    companion object {

        @JvmStatic
        private val HTTP_CLIENT = OkHttpClient.Builder()
            .proxy(proxy = Proxy(Proxy.Type.SOCKS, java.net.InetSocketAddress("localhost", 10808)))
            .build()

        @JvmStatic
        private val JSON = "application/json; charset=utf-8".toMediaType()

        @JvmStatic
        private val FORM = "application/x-www-form-urlencoded".toMediaType()

        @JvmStatic
        fun get(url: String): String? {
            val request = okhttp3.Request.Builder()
                .url(url)
                .get()
                .build()
            HTTP_CLIENT.newCall(request).execute().use { response ->
                return response.body?.string()
            }
        }

        @JvmStatic
        fun postJson(url: String, json: String): String? {
            return postJsonWithHeader(url, json, mapOf())
        }

        @JvmStatic
        fun postJsonWithHeader(url: String, json: String, headerMap: Map<String, String>): String? {
            val headersBuilder = Headers.Builder()
            headerMap.forEach { (key, value) ->
                headersBuilder.add(key, value)
            }
            val headers = headersBuilder.build()
            val body = json.toRequestBody(JSON)
            val request = okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .headers(headers)
                .build()
            HTTP_CLIENT.newCall(request).execute().use { response ->
                return response.body?.string()
            }
        }

        @JvmStatic
        fun postForm(url: String, form: Map<String, String>): String? {
            val body = okhttp3.FormBody.Builder()
            form.forEach { (key, value) ->
                body.add(key, value)
            }
            val request = okhttp3.Request.Builder()
                .url(url)
                .post(body.build())
                .build()
            HTTP_CLIENT.newCall(request).execute().use { response ->
                return response.body?.string()
            }
        }

    }

}
