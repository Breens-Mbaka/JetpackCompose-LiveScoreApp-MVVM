package com.breens.mvvmlivescorestarter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breens.mvvmlivescorestarter.repository.UpcomingMatchesRepository
import com.breens.mvvmlivescorestarter.viewmodel.state.MatchesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class UpcomingMatchesViewModel @Inject constructor(private val upcomingMatchesRepository: UpcomingMatchesRepository) :
    ViewModel() {

    private var _upcomingMatchesState = MutableStateFlow<MatchesState>(MatchesState.Empty)
    val upcomingMatchesState: StateFlow<MatchesState> = _upcomingMatchesState

    init {
        getUpcomingMatches()
    }

    private fun getUpcomingMatches() {
        _upcomingMatchesState.value = MatchesState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val inplayMatchesResponse = upcomingMatchesRepository.getAllUpcomingMatches()
                _upcomingMatchesState.value = MatchesState.Success(inplayMatchesResponse)
            } catch (exception: HttpException) {
                _upcomingMatchesState.value = MatchesState.Error("Something went wrong")
            } catch (exception: IOException) {
                _upcomingMatchesState.value = MatchesState.Error("No internet connection")
            }
        }
    }
}