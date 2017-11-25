package tech.ajira.kitCoin.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import tech.ajira.kitCoin.models.Block

object BlockService {
  fun broadcastNewBlock(nodeAddresses: Set<String>, newBlock: Block) {
    val jsonString = ObjectMapper().writeValueAsString(newBlock)
    nodeAddresses.forEach { nodeAddress ->
      "$nodeAddress/blocks"
        .httpPost()
        .body(jsonString)
        .response { _, _, _ -> println("Posted to $nodeAddress") }
    }
  }

  fun fetchChain(nodeAddress: String, callback: (ArrayList<Block>) -> Unit) {
    "$nodeAddress/blocks"
      .httpGet()
      .responseObject(Block.ListDeserializer()) { _, _, result ->
        callback(result.get())
      }
  }
}