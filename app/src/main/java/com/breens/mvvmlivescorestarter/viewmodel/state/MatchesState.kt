package com.breens.mvvmlivescorestarter.viewmodel.state

import com.breens.mvvmlivescorestarter.data.remote.models.Match

sealed class MatchesState {
    object Empty: MatchesState()
    object Loading: MatchesState()
    class Success(val data: List<Match>): MatchesState()
    class Error(val message: String): MatchesState()
}
