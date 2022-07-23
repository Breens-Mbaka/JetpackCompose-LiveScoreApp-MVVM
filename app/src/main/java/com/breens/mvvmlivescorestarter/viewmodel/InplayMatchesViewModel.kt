package com.breens.mvvmlivescorestarter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.breens.mvvmlivescorestarter.repository.InplayMatchesRepository
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
class InplayMatchesViewModel @Inject constructor(private val inPlayMatchesRepository: InplayMatchesRepository): ViewModel() {

    private var _inPlayMatchesState = MutableStateFlow<MatchesState>(MatchesState.Empty)
    val inPlayMatchesState: StateFlow<MatchesState> =  _inPlayMatchesState

    init {
        getAllInPlayMatches()
    }

    private fun getAllInPlayMatches() {
        _inPlayMatchesState.value = MatchesState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val inplayMatchesResponse = inPlayMatchesRepository.getAllInPlayMatches()
                _inPlayMatchesState.value = MatchesState.Success(inplayMatchesResponse)
            }
            catch (exception: HttpException) {
                _inPlayMatchesState.value = MatchesState.Error("Something went wrong")
            }
            catch (exception: IOException) {
                _inPlayMatchesState.value = MatchesState.Error("No internet connection")
            }
        }
    }
}