package com.nr.acronyminitalismcodechallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nr.acronyminitalismcodechallenge.repo.SfRepo

class SfViewModelFactory constructor(private val sfRepo: SfRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SfViewModel::class.java)){
            SfViewModel(this.sfRepo) as T
        } else throw IllegalArgumentException ("Model not found")
    }
}