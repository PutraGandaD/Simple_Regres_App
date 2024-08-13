package com.putragandad.simpleregresapp.domain.usecase

class CheckPalindromeUseCase {
    operator fun invoke(word: String) : Boolean {
        var newStr = ""

        for (c in word) {
            if(c in 'a'..'z' || c in 'A'..'Z' || c in '0'..'9') {
                newStr += c.lowercaseChar()
            }
        }

        return newStr == newStr.reversed()
    }
}