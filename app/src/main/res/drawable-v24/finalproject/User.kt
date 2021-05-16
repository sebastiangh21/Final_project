package com.sgh21.finalproject

import java.io.Serializable

data class User(
    var name: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var password: String? = null
) : Serializable