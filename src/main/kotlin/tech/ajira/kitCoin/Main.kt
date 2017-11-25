package tech.ajira.kitCoin

import io.javalin.Javalin
import tech.ajira.kitCoin.models.Block
import tech.ajira.kitCoin.models.BlockChain
import tech.ajira.kitCoin.models.Transaction

fun main(args: Array<String>) {
  val app = Javalin.start(7891)
  app.get("/") { ctx -> ctx.result("New Series") }

  app.get("/mine") { ctx ->
    ctx.status(201)
    ctx.json(BlockChain.mine())
  }

  app.post("/transactions") { ctx ->
    BlockChain.createNewTransaction(ctx.bodyAsClass(Transaction::class.java))
    ctx.status(201)
  }

  app.post("/blocks") { ctx ->
    val status = BlockChain.addNewBlock(ctx.bodyAsClass(Block::class.java))
    ctx.status(when(status) {
      true -> 201
      false -> 200
    })
  }

  app.get("/blocks") { ctx ->
    ctx.status(200)
    ctx.json(BlockChain.blocks)
  }

  app.post("/blocks/resolve") { ctx ->
    BlockChain.resolveConflicts()
    ctx.status(200)
  }

  app.get("/blocks/:id") { ctx ->
    val id = ctx.param("id")!!.toInt()
    val block = BlockChain.getBlock(id)
    when (block) {
      null -> ctx.status(404)
      else -> {
        ctx.status(200)
        ctx.json(block)
      }
    }
  }

  app.post("/nodes") { ctx ->
    BlockChain.registerNode(ctx.formParam("address")!!)
    ctx.status(201)
  }

}