package `fun`.mochen.learn.english.system.utils

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody

class HttpUtils {

    companion object {

        @JvmStatic
        private val HTTP_CLIENT = OkHttpClient()

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
            val body = json.toRequestBody(JSON)
            val request = okhttp3.Request.Builder()
                .url(url)
                .post(body)
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
