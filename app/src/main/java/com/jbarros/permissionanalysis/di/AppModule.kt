package com.jbarros.permissionanalysis.di

import android.content.Context
import androidx.room.Room
import com.jbarros.permissionanalysis.data.local.*
import com.jbarros.permissionanalysis.network.PermissionExtractInfoService
import com.jbarros.permissionanalysis.utils.PackageManagerSource
import com.jbarros.permissionanalysis.utils.RiskCalculator
import com.jbarros.permissionanalysis.utils.SensitivePermissionInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun providesApplicationDatabase(@ApplicationContext app: Context): ApplicationDatabase {
        return Room.databaseBuilder(
            app,
            ApplicationDatabase::class.java, "application-db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesRetrofitPermissionExtractInfoService(): PermissionExtractInfoService {
        val baseUrl = "https://j18oacr9jc.execute-api.us-east-1.amazonaws.com"
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        // Create an OkHttpClient with a timeout
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS) // Set connection timeout
            .readTimeout(15, TimeUnit.SECONDS)    // Set read timeout
            .writeTimeout(15, TimeUnit.SECONDS)   // Set write timeout
            .build()

        /**
         * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
         */
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

        return retrofit.create(PermissionExtractInfoService::class.java)
    }

    @Provides
    @Singleton
    fun providesApplicationDao(applicationDatabase: ApplicationDatabase): ApplicationDao = applicationDatabase.applicationDao()

    @Provides
    @Singleton
    fun providesApplicationPermissionDao(applicationDatabase: ApplicationDatabase): ApplicationPermissionDao = applicationDatabase.applicationPermissionDao()

    @Provides
    @Singleton
    fun providesPermissionDao(applicationDatabase: ApplicationDatabase): PermissionDao = applicationDatabase.permissionDao()

    @Provides
    @Singleton
    fun providesPermissionAnalysisDao(applicationDatabase: ApplicationDatabase): PermissionAnalysisDao = applicationDatabase.permissionAnalysisDao()

    @Provides
    @Singleton
    fun providesDangerousGroupDao(applicationDatabase: ApplicationDatabase): DangerousGroupDao = applicationDatabase.dangerousGroupDao()

    @Provides
    @Singleton
    fun providesSensitiveDataCategoryDao(applicationDatabase: ApplicationDatabase): SensitiveDataCategoryDao = applicationDatabase.sensitiveDataCategoryDao()

    @Provides
    @Singleton
    fun providesPackageManagerSource(@ApplicationContext app: Context): PackageManagerSource {
        return PackageManagerSource(app)
    }

    @Provides
    @Singleton
    fun providesSensitivePermissionInfo(): SensitivePermissionInfo {
        return SensitivePermissionInfo()
    }

    @Provides
    @Singleton
    fun providesRiskCalculator(): RiskCalculator {
        return RiskCalculator()
    }
}