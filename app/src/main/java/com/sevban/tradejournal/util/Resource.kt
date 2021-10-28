package com.sevban.tradejournal.util

//Gelen verilerin sonuçlarını success, error ve loading olarak ele almaya yarayan sınıf.
//Herhangi bir işlemin sonucunu bu üç durumda değerlendirmek için kullanılır.
sealed class Resource <T> (val data: T?= null, val message: String?= nu