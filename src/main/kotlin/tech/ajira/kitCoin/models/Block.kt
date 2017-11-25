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

  fun hash(): String {
    val bytes = this.toString().toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("", { str, it -> str + "%02x".format(it) })
  }

  override fun toString(): String {
    return """{
      |index:$index,
      |timestamp:$timestamp,
      |transaction:$transactions,
      |proof:$proof,
      |previousHash:$previousHash
      |}""".trimMargin()
  }

  fun validate(): Boolean = this.hash().substring(0, 2) == "00"

  class Deserializer : ResponseDeserializable<Block> {
    override fun deserialize(reader: Reader) = Gson().fromJson(reader, Block::class.java)
  }

  class ListDeserializer : ResponseDeserializable<ArrayList<Block>> {
    override fun deserialize(reader: Reader): ArrayList<Block> {

      val builder = GsonBuilder()

      // Register an adapter to manage the date types as long values
      builder.registerTypeAdapter(Date::class.java, JsonDeserializer<Date> { json, typeOfT, context -> Date(json.asJsonPrimitive.asLong) })

      val gson = builder.create()
      val type = object : TypeToken<ArrayList<Block>>() {}.type
      return gson.fromJson(reader, type)
    }
  }
}