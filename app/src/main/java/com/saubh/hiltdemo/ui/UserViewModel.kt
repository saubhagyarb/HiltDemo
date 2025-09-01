package com.saubh.hiltdemo.ui

import androidx.lifecycle.ViewModel
import com.saubh.hiltdemo.data.Test
import com.saubh.hiltdemo.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repo: UserRepository,
    test : Test
) : ViewModel() {

    fun getData() = repo.getUser()
    val string = test.s.toString()
}