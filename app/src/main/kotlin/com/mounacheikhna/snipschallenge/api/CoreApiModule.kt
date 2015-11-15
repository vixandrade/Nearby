package com.mounacheikhna.snipschallenge.api

import com.facebook.stetho.okhttp.StethoInterceptor
import com.mounacheikhna.snipschallenge.annotation.*
import com.squareup.moshi.Moshi
import com.squareup.okhttp.Interceptor
import com.squareup.okhttp.OkHttpClient
import dagger.Module
import dagger.Provides
import org.threeten.bp.Clock
import retrofit.MoshiConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
public class CoreApiModule {

    private val FOURSQUARE_ENDPOINT = "https://api.foursquare.com/";
    private val FOURSQUARE_CLIENT_ID = "DTK233D1LP2FQGDZDEYQIDUAVEAUX54IJ54IKWH0FE4ZBUL0"
    private val FOURSQUARE_CLIENT_SECRET = "5KH33YETVNSEQ42MFJ5UYCCGCIRBX5NBGM5JYY2S2PC4RIR1"
    private val FOURSQUARE_API_VERSION: String = "20151114"
    private val FOURSQUARE_API_TYPE: String = "foursquare"

    @Provides @Singleton @ClientId
    fun provideClientId(): String = FOURSQUARE_CLIENT_ID

    @Provides @Singleton @ClientSecret
    fun provideClientSecret(): String = FOURSQUARE_CLIENT_SECRET

    @Provides @Singleton @FoursquareApiVersion
    fun provideFoursquareApiVersion(): String = FOURSQUARE_API_VERSION

    @Provides @Singleton @FoursquareType
    fun provideFoursquareType(): String = FOURSQUARE_API_TYPE

    @Provides @Singleton
    fun provideFoursquareInterceptor(@ClientId clientId: String,
                                     @ClientSecret clientSecret: String,
                                     @FoursquareApiVersion apiVersion: String,
                                     @FoursquareType apiType: String): FoursquareInterceptor {
        return FoursquareInterceptor(clientId, clientSecret, apiVersion, apiType)
    }

    @Provides @Singleton
    fun provideFoursquareApi(retrofit: Retrofit): FoursquareApi {
        return retrofit.create(FoursquareApi::class.java)
    }

    @Provides @Singleton @Named("Api")
    fun provideApiClient(client: OkHttpClient,
                         @AppInterceptors interceptors: List<out Interceptor>,
                         @NetworkInterceptors networkInterceptors: List<out Interceptor>): OkHttpClient {
        var okClient = client.clone()
        okClient.interceptors().addAll(interceptors)//TODO: may need to add it instead to network interceptors
        okClient.networkInterceptors().addAll(networkInterceptors)
        return okClient
    }

    @Provides @Singleton @AppInterceptors
    fun provideAppInterceptors(foursquareInterceptor: FoursquareInterceptor): List<out Interceptor> {
        return arrayListOf(foursquareInterceptor);
    }

    @Provides @Singleton
    fun provideRetrofit(@Named("Api") apiClient: OkHttpClient,
                        moshi: Moshi): Retrofit {
        return Retrofit.Builder().baseUrl(FOURSQUARE_ENDPOINT).client(apiClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build()
    }

    //Maybe move this into a moshi module ?
    @Provides @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides @Singleton
    public fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient()
        return client
    }

    @Provides @Singleton
    fun provideClock(): Clock = Clock.systemDefaultZone()

}
