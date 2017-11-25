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

  init {
    val genesisBlock = Block(1, Date.from(Instant.EPOCH), ArrayList(), 522, "")
    this.blocks.add(genesisBlock)
  }

  fun addNewBlock(block: Block): Boolean = when {
    block.validate() && block.index == this.blocks.last().index + 1 -> {
      this.blocks.add(block)
      true
    }
    else -> false
  }

  fun createNewTransaction(transaction: Transaction) {
    this.currentTransactions.add(transaction)
  }

  fun proofOfWork(): Block {
    val lastBlock = this.blocks.last()
    val index = lastBlock.index + 1
    var blockCreatedTime = Date()
    val availableTransactions = ArrayList(this.currentTransactions)
    val previousHash = lastBlock.hash()
    var possibleProof = 0
    var possibleBlock: Block
    do {
      possibleProof %= Int.MAX_VALUE
      if (possibleProof == 0) blockCreatedTime = Date()
      possibleBlock = Block(index, blockCreatedTime, availableTransactions, ++possibleProof, previousHash)
    } while (!possibleBlock.validate())
    return possibleBlock
  }

  fun isValidChain(blocks: ArrayList<Block>): Boolean =
    when {(0..blocks.size - 2).any { i ->
      blocks[i].hash() != blocks[i + 1].previousHash || !blocks[i + 1].validate()
    } -> false
      else -> true
    }

  fun resolveConflicts() = Unit

  fun registerNode(address: String) {
    this.nodes.add(address)
  }

  fun mine(): Block {
    this.currentTransactions.add(Transaction("", NODE_IDENTIFIER, 12.5))
    val nextBlock = this.proofOfWork()
    this.blocks.add(nextBlock)
    this.currentTransactions.clear()
    BlockService.broadcastNewBlock(this.nodes, nextBlock)
    return nextBlock
  }

  fun getBlock(id: Int): Block? = if (id >= blocks.size) null else blocks[id - 1]

}

