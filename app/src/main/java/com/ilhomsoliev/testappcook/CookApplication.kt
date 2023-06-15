package com.ilhomsoliev.testappcook

import android.app.Application
import androidx.room.Room
import com.ilhomsoliev.basket.viewmodel.BasketViewModel
import com.ilhomsoliev.data.local.dao.DishDao
import com.ilhomsoliev.data.local.db.ApplicationDatabase
import com.ilhomsoliev.data.remote.MainRepository
import com.ilhomsoliev.data.remote.source.WebSource
import com.ilhomsoliev.domain.repository.MainRepositoryImpl
import com.ilhomsoliev.domain.use_cases.DeleteDishUseCase
import com.ilhomsoliev.domain.use_cases.GetCategoriesUseCase
import com.ilhomsoliev.domain.use_cases.GetDishesLocallyUseCase
import com.ilhomsoliev.domain.use_cases.GetDishesUseCase
import com.ilhomsoliev.domain.use_cases.InsertDishUseCase
import com.ilhomsoliev.mainscreen.viewmodel.CategoryDetailsViewModel
import com.ilhomsoliev.mainscreen.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class CookApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val appModule = module {
            single{
                WebSource()
            }
            single<MainRepository>{
                MainRepositoryImpl(get(), get())
            }
            single {
                GetCategoriesUseCase(get())
            }
            single{
                GetDishesUseCase(get())
            }
            viewModel{
                MainViewModel(get())
            }
            viewModel{
                CategoryDetailsViewModel(get(), get())
            }
            viewModel {
                BasketViewModel(get())
            }
            single<ApplicationDatabase>{
                Room.databaseBuilder(this@CookApplication, ApplicationDatabase::class.java, "cook_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            single<DishDao> {
                get<ApplicationDatabase>().dishDao()
            }
            single {
                InsertDishUseCase(get())
            }
            single {
                DeleteDishUseCase(get())
            }
            single{
                GetDishesLocallyUseCase(get())
            }

        }
        startKoin {

            androidLogger()

            androidContext(this@CookApplication)
            modules(appModule)
        }
    }
}