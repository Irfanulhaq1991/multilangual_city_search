package com.example.citysearch.di


import com.example.citysearch.data.CitiesRepository
import com.example.citysearch.data.CityDto
import com.example.citysearch.data.ICitiesDataSource
import com.example.citysearch.data.localfile.AssetJsonFileFromContext
import com.example.citysearch.data.localfile.FileDataSource
import com.example.citysearch.data.localfile.JsonDataProvider
import com.example.citysearch.domain.City
import com.example.citysearch.domain.CityMapper
import com.example.citysearch.domain.FetchCitiesUseCase
import com.example.citysearch.domain.IMapper
import com.example.citysearch.view.CitiesViewModel
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

