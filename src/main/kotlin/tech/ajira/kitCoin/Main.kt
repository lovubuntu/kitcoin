package tech.ajira.kitCoin

import io.javalin.Javalin
import tech.ajira.kitCoin.models.Block
import tech.ajira.kitCoin.models.BlockChain
import tech.ajira.kitCoin.models.Transaction

fun main(args: Array<String>) {
  val app = Javalin.start(System.getenv("PORT").toInt())
  app.get("/") { ctx -> ctx.result("New Series") }

  app.get("/mine") { ctx ->
    ctx.result("Yet to be implemented")
  }

  app.post("/transactions") { ctx ->
    ctx.result("Yet to be implemented")
  }

  app.post("/blocks") { ctx ->
    ctx.result("Yet to be implemented")
  }

  app.get("/blocks") { ctx ->
    ctx.result("Yet to be implemented")
  }

  app.post("/blocks/resolve") { ctx ->
    ctx.result("Yet to be implemented")
  }

  app.get("/blocks/:id") { ctx ->
    ctx.result("Yet to be implemented")
  }

  app.post("/nodes") { ctx ->
    ctx.result("Yet to be implemented")
  }

}