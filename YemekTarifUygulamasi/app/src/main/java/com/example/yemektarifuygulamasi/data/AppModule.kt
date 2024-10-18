package com.example.yemektarifuygulamasi.data

import android.content.Context
import androidx.room.Room
import com.example.yemektarifuygulamasi.data.remote.localdtb.LocalRepository
import com.example.yemektarifuygulamasi.data.remote.localdtb.RecipeDatabase
import com.example.yemektarifuygulamasi.data.remote.localdtb.RecipesDao
import com.example.yemektarifuygulamasi.data.remote.service.RecipesAPI
import com.example.yemektarifuygulamasi.data.repository.RecipesRetrofitImpl
import com.example.yemektarifuygulamasi.domain.repository.RecipesRepository
import com.example.yemektarifuygulamasi.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
     fun provideRetrofit() :Retrofit{ // hilt tarafından sağlanacak istediğim methoda
       return Retrofit.Builder()
             .baseUrl(BASE_URL)
             .addConverterFactory(GsonConverterFactory.create())
             .build()
     }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): RecipesAPI {
        return retrofit.create(RecipesAPI::class.java)
    }


    @Provides
    @Singleton
    fun provideRecipesAPI(api: RecipesAPI) : RecipesRepository{
        return RecipesRetrofitImpl(api)
    }


    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context) : RecipeDatabase{
        return Room.databaseBuilder(
            context ,
            RecipeDatabase::class.java , "recipe-database1")
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipesDao(recipeDatabase: RecipeDatabase) : RecipesDao{

        return recipeDatabase.recipesDao()
    }

    @Provides
    @Singleton
    fun provideLocalRepository(recipesDao: RecipesDao) : LocalRepository{
        return LocalRepository(recipesDao)
    }

}