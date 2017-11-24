package tech.ajira.kitCoin.models

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.reflect.TypeToken
import java.io.Reader
import java.security.MessageDigest
import java.util.*
import kotlin.collections.ArrayList

data class Block(
  val index: Int = 0,
  val timestamp: Date = Date(),
  val transactions: ArrayList<Transaction> = ArrayList(),
  val proof: Int = 0,
  val previousHash: String = ""
) {

  fun hash(): String = ""

  override fun toString(): String {
    return """{
      |index:$index,
      |timestamp:$timestamp,
      |transaction:$transactions,
      |proof:$proof,
      |previousHash:$previousHash
      |}""".trimMargin()
  }

  fun validate(): Boolean = false
}