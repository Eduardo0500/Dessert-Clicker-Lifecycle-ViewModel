package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel : ViewModel() {

    private val desserts = Datasource.dessertList

    private val _uiState = MutableStateFlow(DessertUiState())
    val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()


    fun onDessertClicked() {
        _uiState.update { cur ->
            val dessertsSold = cur.dessertsSold + 1
            val nextIndex = determineDessertIndex(dessertsSold)
            cur.copy(
                revenue = cur.revenue + cur.currentDessertPrice,
                dessertsSold = dessertsSold,
                currentDessertIndex = nextIndex,
                currentDessertPrice = desserts[nextIndex].price,
                currentDessertImageId = desserts[nextIndex].imageId
            )
        }
    }


    private fun determineDessertIndex(dessertsSold: Int): Int {
        var dessertIndex = 0
        for (index in desserts.indices) {
            if (dessertsSold >= desserts[index].startProductionAmount) {
                dessertIndex = index
            } else {
                break
            }
        }
        return dessertIndex
    }
}