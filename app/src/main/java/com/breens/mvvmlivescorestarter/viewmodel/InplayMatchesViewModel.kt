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
class InplayMatchesViewModel @Inject constructor(private val inplayMatchesRepository: InplayMatchesRepository): ViewModel() {

    private var _inplayMatchesState = MutableStateFlow<MatchesState>(MatchesState.Empty)
    private val inplayMatchesState: StateFlow<MatchesState> =  _inplayMatchesState

    init {
        getAllInPlayMatches()
    }

    private fun getAllInPlayMatches() {
        _inplayMatchesState.value = MatchesState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val inplayMatchesResponse = inplayMatchesRepository.getAllInPlayMatches()
                _inplayMatchesState.value = MatchesState.Success(inplayMatchesResponse)
            }
            catch (exception: HttpException) {
                _inplayMatchesState.value = MatchesState.Error("No internet connection")
            }
            catch (exception: IOException) {
                _inplayMatchesState.value = MatchesState.Error("Something went wrong")
            }
        }
    }
}