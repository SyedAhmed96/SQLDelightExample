package com.example.sqldelightexamplee

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import example.persondb.PersonEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val personDataSource: PersonDataSource
) : ViewModel() {

    val persons = personDataSource.getAllPersons()

    // var personDetails by mutableStateOf<PersonEntity?>(null)
    var personDetails = MutableLiveData<PersonEntity?>(null)
    // private set

    var firstNameText: MutableLiveData<String> = MutableLiveData("")

    // private set
    val firstNameTextUI: LiveData<String> = firstNameText

    var lastNameText: MutableLiveData<String> = MutableLiveData("")
    // private set

    fun onInsertPerson() {
        if (firstNameText.value.isNullOrBlank() || lastNameText.value.isNullOrBlank()) {
            return
        }

        viewModelScope.launch {
            personDataSource.insertPerson(
                firstNameText.value.toString(),
                lastNameText.value.toString()
            )
            firstNameText.value = ""
            lastNameText.value = ""
        }
    }

    /** Overloaded Class Function */
    fun onInsertPerson(firstNameTexts: String, lastNameTexts: String) {
        if (firstNameTexts.isBlank() || lastNameTexts.isBlank()) {
            return
        }

        viewModelScope.launch {
            personDataSource.insertPerson(firstNameTexts, lastNameTexts)
        }
    }

    fun onDeleteClick(id: Long) {
        viewModelScope.launch {
            personDataSource.deletePersonById(id)
        }
    }

    fun getPersonById(id: Long) {
        viewModelScope.launch {
            personDetails.value = personDataSource.getPersonById(id)
        }
    }

    fun onFirstNameChange(data: String) {
        firstNameText.value = data
    }

    fun onLastNameChange(data: String) {
        lastNameText.value = data
    }

    fun onPersonDetailsDialogDismiss() {
        personDetails.value = null
    }
}