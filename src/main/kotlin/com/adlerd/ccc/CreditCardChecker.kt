package com.adlerd.ccc

import javafx.application.Application

class CreditCardChecker {
    /** Return true if the card number is valid  */
    fun isValid(number: Long): Boolean {
        return (sumOfDoubleEvenPlace(number) + sumOfOddPlace(number)) % 10 == 0
    }

    /** Get the result from Step 2  */
    fun sumOfDoubleEvenPlace(number: Long): Int {
        var sumDouble = 0
        val nums = number.toString().toCharArray()
        var i = 0
        while (i < nums.size) {
            val currChar = nums[i]
            val currInt = Integer.parseInt(currChar.toString())
            sumDouble += getDigit(currInt * 2)
            i += 2
        }
        return sumDouble
    }

    /** Return this number if it is a single digit, otherwise, return the sum of the
     * two digits  */
    fun getDigit(number: Int): Int {
        if (number > 9 || number < 0) {
            if (number < 0) {
                System.err.println("ERROR: Input needs to be positive!")
            }
            return getDigit(number % 10 + number / 10)
        }
        return number
    }

    /** Return sum of odd-place digits in number  */
    fun sumOfOddPlace(number: Long): Int {
        var sumOdd = 0
        val nums = number.toString().toCharArray()
        var i = 1
        while (i < nums.size) {
            sumOdd += getDigit(Integer.parseInt(nums[i].toString()))
            i += 2
        }
        return sumOdd
    }

    /** Return the number of digits in d  */
    fun getSize(d: Long): Int {
        return d.toString().toCharArray().size
    }

    /** Return the first k number of digits from number. If the number of digits in
     * number is less than k, return number.  */
    fun getPrefix(number: Long, k: Int): Long {
        val cardNumbers = number.toString()
        if (k >= getSize(number)) {
            return java.lang.Long.getLong(cardNumbers)!!
        }
        val nums = cardNumbers.substring(0, k - 1)
        return nums.toLong()
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Application.launch(CreditCardCheckerGUI::class.java)
        }
    }
}
