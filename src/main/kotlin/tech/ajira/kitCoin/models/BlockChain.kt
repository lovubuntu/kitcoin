package tech.ajira.kitCoin.models

import tech.ajira.kitCoin.services.BlockService
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList

object BlockChain {
  var blocks = ArrayList<Block>()
  var currentTransactions = ArrayList<Transaction>()
  var nodes = mutableSetOf<String>()
  private val NODE_IDENTIFIER = UUID.randomUUID().toString()

  fun addNewBlock(block: Block): Boolean = false

  fun createNewTransaction(transaction: Transaction) = Unit

  fun proofOfWork(): Block = Block()

  fun isValidChain(blocks: ArrayList<Block>): Boolean = false

  fun resolveConflicts() = Unit

  fun registerNode(address: String) = Unit

  fun mine(): Block = Block()

  fun getBlock(id: Int): Block? = Block()

}

