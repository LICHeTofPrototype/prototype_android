package com.example.lichet.di.module

import android.content.Context
import com.example.lichet.di.module.ApplicationModule
import com.example.lichet.util.DefaultPrefs
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class HttpClientModule {

    private val CACHE_FILE_NAME = "okhttp.cache"

    private val MAX_CACHE_SIZE = (4 * 1024 * 1024).toLong()

    @Singleton
    @Provides
    fun provideHttpClient(context: Context,
                          defaultPrefsWrapper: ApplicationModule.DefaultPrefsWrapper): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        val cacheDir = File(context.cacheDir, CACHE_FILE_NAME)
        val cache = Cache(cacheDir, MAX_CACHE_SIZE)

        val headerInterceptor = HeaderInterceptor(defaultPrefsWrapper.prefs)

        val tokenRefreshAuthenticator = TokenRefreshAuthenticator(defaultPrefsWrapper.prefs)

        val c = OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .authenticator(tokenRefreshAuthenticator)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(cache)
        return c.build()
    }

    private class HeaderInterceptor(val defaultPrefs: DefaultPrefs) : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response? {

            chain?: return null

            val accessToken = defaultPrefs.accessToken

            val request = chain.request().newBuilder()
                    .header("Authorization", accessToken)
                    .build()

            return chain.proceed(request)

        }
    }
    private class TokenRefreshAuthenticator(val defaultPrefs: DefaultPrefs) : Authenticator {
        @Throws(IOException::class)
        override fun authenticate(route: Route, response: Response): Request? {
            // TODO error handling for unrecoverable situation
            val accessToken = defaultPrefs.accessToken

            return response.request().newBuilder()
                    .header("Authorization", accessToken)
                    .build()
        }
    }
}