package com.saubh.hiltdemo.data

import javax.inject.Inject


class UserRepository /*@Inject constructor*/() {
    fun getUser() = "This is test message from getUser() in UserRepository"
}