package com.turkcell.lyraapp.ui.screen.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Admin paneli is mantigi.
 * Logout islemini yoneterek Login ekranina geri donusu tetikler.
 */
class AdminViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AdminUiState())
    val uiState: StateFlow<AdminUiState> = _uiState.asStateFlow()

    private val _effect = Channel<AdminEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: AdminEvent) {
        when (event) {
            is AdminEvent.OnLogoutClick -> {
                viewModelScope.launch {
                    _effect.send(AdminEffect.NavigateToLogin)
                }
            }
        }
    }
}
