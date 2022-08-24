package com.example.citysearch.fetching.di


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
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val fetchCitiesModule = module {
    factory<IMapper<List<CityDto>, List<City>>> { CityMapper() }
    factory<ICitiesDataSource> { FileDataSource(get()) }
    factory { CitiesRepository(get(), get()) }
    factory { FetchCitiesUseCase(get()) }
    viewModel { CitiesViewModel(get()) }
}

val fetchCitiesModuleWithContext = module {
    factory<JsonDataProvider> { AssetJsonFileFromContext(androidContext())}
}

