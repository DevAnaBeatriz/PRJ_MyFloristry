package com.example.inventory.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventory.FloristryApplication
import com.example.inventory.ui.home.HomeViewModel
import com.example.inventory.ui.item.ItemDetailsViewModel
import com.example.inventory.ui.item.ItemEditViewModel
import com.example.inventory.ui.item.ItemEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.flowerRepository
            )
        }
        initializer {
            ItemEntryViewModel(inventoryApplication().container.flowerRepository)
        }

        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.flowerRepository
            )
        }

        initializer {
            HomeViewModel(inventoryApplication().container.flowerRepository)
        }
    }
}

fun CreationExtras.inventoryApplication(): FloristryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as FloristryApplication)
