package tech.ajira.kitCoin.models

import com.fasterxml.jackson.annotation.JsonIgnore

data class Transaction(val sender: String = "", val receiver: String = "", val amount: Double = 0.0) {
  @JsonIgnore
  override fun toString(): String = """{
    |sender:$sender,
    |receiver:$receiver,
    |amount:$amount
    }""".trimMargin()
}

