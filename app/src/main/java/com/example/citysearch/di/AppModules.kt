package com.example.citysearch.di


import com.example.citysearch.data.CitiesRepository
import com.example.citysearch.data.CityDto
import com.example.citysearch.data.ICitiesDataSource
import com.example.citysearch.data.cache.AppLruCache
import com.example.citysearch.data.cache.IAppCache
import com.example.citysearch.data.localfile.FileDataSource
import com.example.citysearch.data.localfile.JsonDataProvider
import com.example.citysearch.domain.*
import com.example.citysearch.view.CitiesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val fetchCitiesModule = module {
    factory<IMapper<List<CityDto>, List<City>>> { CityMapper() }
    factory { JsonDataProvider() }
    factory<ICitiesDataSource> { FileDataSource(get()) }
    factory<IAppCache<String, List<City>>> { AppLruCache(1) }
    factory { CitiesRepository(get(), get(), get()) }
    factory { Pager(50) }
    factory { FetchCitiesUseCase(get(), get()) }
    viewModel { CitiesViewModel(get()) }
}

