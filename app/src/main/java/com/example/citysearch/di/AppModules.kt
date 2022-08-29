package com.example.citysearch.di


import com.example.citysearch.fetching.data.CitiesRepository
import com.example.citysearch.fetching.data.CityDto
import com.example.citysearch.fetching.data.ICitiesDataSource
import com.example.citysearch.fetching.data.localfile.AssetJsonFileFromContext
import com.example.citysearch.fetching.data.localfile.FileDataSource
import com.example.citysearch.fetching.data.localfile.JsonDataProvider
import com.example.citysearch.fetching.domain.City
import com.example.citysearch.fetching.domain.FetchCitiesUseCase
import com.example.citysearch.fetching.domain.mapper.CityMapper
import com.example.citysearch.fetching.domain.mapper.IMapper
import com.example.citysearch.view.CitiesViewModel
import com.example.citysearch.searching.*
import com.example.citysearch.searching.data.*
import com.example.citysearch.searching.domain.QueryValidator
import com.example.citysearch.searching.domain.SearchCityUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Module for Koin dependency injection
 * */
val fetchCitiesModule = module {
    factory<IMapper<List<CityDto>, List<City>>> { CityMapper() }
    factory<ICitiesDataSource> { FileDataSource(get()) }
    factory { CitiesRepository(get(), get(), get()) }
    factory { FetchCitiesUseCase(get()) }
    viewModel { CitiesViewModel(get(), get()) }
}

val searchCityModule = module {
    single<IAppCache<String, List<City>>> { SimpleCache(1) }
    factory<ICitySearcher> { CitySearcher(get()) }
    factory { CitySearchRepository(get()) }
    factory { QueryValidator() }
    factory { SearchCityUseCase(get(), get()) }
}

val fetchCitiesModuleWithContext = module {
    factory<JsonDataProvider> { AssetJsonFileFromContext(androidContext()) }
}

