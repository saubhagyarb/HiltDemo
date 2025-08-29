package com.saubh.hiltdemo.data

import javax.inject.Inject

class UserRepository @Inject constructor() {
    fun getUser() = "Hello from Hilt Demo"
}