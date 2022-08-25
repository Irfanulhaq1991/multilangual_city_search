package com.example.citysearch.di


import com.example.citysearch.fetching.data.CitiesRepository
import com.example.citysearch.fetching.data.CityDto
import com.example.citysearch.fetching.data.ICitiesDataSource
import com.example.citysearch.fetching.data.localfile.AssetJsonFileFromContext
import com.example.citysearch.fetching.data.localfile.FileDataSource
import com.example.citysearch.fetching.data.localfile.JsonDataProvider
import com.example.citysearch.fetching.domain.City
import com.example.citysearch.fetching.domain.CityMapper
import com.example.citysearch.fetching.domain.FetchCitiesUseCase
import com.example.citysearch.fetching.domain.IMapper
import com.example.citysearch.fetching.view.CitiesViewModel
import com.example.citysearch.searching.CitySearchRepository
import com.example.citysearch.searching.CitySearcher
import com.example.citysearch.searching.SearchCityUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val fetchCitiesModule = module {
    factory<IMapper<List<CityDto>, List<City>>> { CityMapper() }
    factory<ICitiesDataSource> { FileDataSource(get()) }
    factory { CitiesRepository(get(), get()) }
    factory { FetchCitiesUseCase(get()) }
    viewModel { CitiesViewModel(get(), get()) }
}

val searchCityModule = module {
    factory { CitySearcher() }
    factory { CitySearchRepository(get()) }
    factory { SearchCityUseCase(get()) }
}

val fetchCitiesModuleWithContext = module {
    factory<JsonDataProvider> { AssetJsonFileFromContext(androidContext()) }
}

