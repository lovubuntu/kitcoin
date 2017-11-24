package tech.ajira.kitCoin.models

import org.junit.Test

import org.junit.Assert.*

class BlockChainTest {

  @Test
  fun shouldHaveAValidGenesisBlock() {
    val genesisBlock = BlockChain.blocks.first()
    assertTrue(genesisBlock.validate())
  }

  @Test
  fun shouldGenerateAValidNextPossibleBlockUsingProofOfWork() {
    val newBlock = BlockChain.proofOfWork()
    assertTrue(newBlock.validate())
  }

  @Test
  fun shouldIncludeTheNewlyMinedBlockToBlockChain() {
    val chainSizeBeforeMining = BlockChain.blocks.size

    val newlyMinedBlock = BlockChain.mine()
    val chainSizeAfterMining = BlockChain.blocks.size

    assertTrue(newlyMinedBlock.validate())
    assertTrue(chainSizeAfterMining == chainSizeBeforeMining + 1)
  }

  @Test
  fun shouldAddNextValidBlockToTheChain() {
    val inputBlock = BlockChain.proofOfWork()
    val chainSizeBeforeAddingBlock = BlockChain.blocks.size

    val isBlockAdded = BlockChain.addNewBlock(inputBlock)
    val chainSizeAfterAddingBlock = BlockChain.blocks.size

    assertTrue(isBlockAdded)
    assertTrue(chainSizeAfterAddingBlock - chainSizeBeforeAddingBlock == 1)
  }

  @Test
  fun shouldNotAddBlockToChainWhenItAlreadyHasBlockForThatIndex() {
    val inputBlock = BlockChain.proofOfWork()
    BlockChain.mine()
    val chainSizeBeforeAddingBlock = BlockChain.blocks.size

    val isBlockAdded = BlockChain.addNewBlock(inputBlock)

    val chainSizeAfterAddingBlock = BlockChain.blocks.size

    assertFalse(isBlockAdded)
    assertTrue(chainSizeAfterAddingBlock == chainSizeBeforeAddingBlock)
  }

  @Test
  fun shouldNotAddBlockToChainWhenItIsNotValid() {
    val (index, timestamp, transactions, proof, _) = BlockChain.proofOfWork()
    val inputBlock = Block(index, timestamp, transactions, proof, "bad-hash-from-attacker")
    val chainSizeBeforeAddingBlock = BlockChain.blocks.size

    val isBlockAdded = BlockChain.addNewBlock(inputBlock)

    val chainSizeAfterAddingBlock = BlockChain.blocks.size

    assertFalse(isBlockAdded)
    assertTrue(chainSizeAfterAddingBlock == chainSizeBeforeAddingBlock)
  }

  @Test
  fun shouldAddTransactionToTransactionList() {
    val newTransaction = Transaction("sender_public_key", "receiver_public_key", 10.0)
    val transactionSizeBeforeAddition = BlockChain.currentTransactions.size

    BlockChain.createNewTransaction(newTransaction)

    val transactionSizeAfterAddition = BlockChain.currentTransactions.size
    assertTrue(transactionSizeAfterAddition - transactionSizeBeforeAddition == 1)
  }

  @Test
  fun shouldRegisterNewNode() {
    val nodeSizeBeforeAddition = BlockChain.nodes.size

    BlockChain.registerNode("http://localhost:7809")
    val nodeSizeAfterAddition = BlockChain.nodes.size

    assertTrue(nodeSizeAfterAddition - nodeSizeBeforeAddition == 1)
  }

  @Test
  fun shouldNotRegisterNodeWhenAlreadyPresent() {
    val existingNode = "http://localhost:17809"
    BlockChain.registerNode(existingNode)
    val nodeSizeBeforeAddition = BlockChain.nodes.size

    BlockChain.registerNode(existingNode)
    val nodeSizeAfterAddition = BlockChain.nodes.size

    assertTrue(nodeSizeAfterAddition == nodeSizeBeforeAddition)
  }

  @Test
  fun shouldReturnTrueWhenChainIsValid() {
    (1..5).forEach { BlockChain.mine() }
    val blocks = BlockChain.blocks

    assertTrue(BlockChain.isValidChain(blocks))
  }

}