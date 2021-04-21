package ActivityUtils

import android.util.Patterns
import java.util.regex.Pattern

const val MINLENGHT = 6

fun validateEmail(email: String): Boolean {
    val pattern: Pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches()
}

fun minimumLength(text: String): Boolean{
    return text.length >= MINLENGHT
}