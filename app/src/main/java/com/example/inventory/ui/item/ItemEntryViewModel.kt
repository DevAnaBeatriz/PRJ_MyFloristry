package com.example.inventory.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventory.data.Flower
import com.example.inventory.data.FlowerRepository
import java.text.NumberFormat

class ItemEntryViewModel(private val flowerRepository: FlowerRepository) : ViewModel() {

    var itemUiState by mutableStateOf(ItemUiState())
        private set

    // Atualiza o estado da UI com os detalhes do item
    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    // Salva o item no repositório
    suspend fun saveItem() {
        if (validateInput()) {
            flowerRepository.insertItem(itemUiState.itemDetails.toItem())
        }
    }

    // Validação do input
    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank() && photoUri.isNotBlank()
        }
    }

}

// Estado da UI para o item
data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)


data class ItemDetails(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
    val photoUri: String = ""
)

// Converter ItemDetails para Flower
fun ItemDetails.toItem(): Flower = Flower(
    id = id,
    name = name,
    price = price.toDoubleOrNull() ?: 0.0,
    quantity = quantity.toIntOrNull() ?: 0,
    photoUri = photoUri
)

// Função para formatar o preço
fun Flower.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}

// Converter Flower para ItemUiState
fun Flower.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

// Converter Flower para ItemDetails
fun Flower.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    name = name,
    price = price.toString(),
    quantity = quantity.toString(),
    photoUri = photoUri
)
