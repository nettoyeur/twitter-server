package com.twitter.server

import com.twitter.app.App
import com.twitter.finagle.http.HttpMuxer
import com.twitter.finagle.{ListeningServer, Http, NullServer}
import java.net.InetSocketAddress

trait HttpServer { self: App =>
  def defaultHttpPort = 8080
  val httpPort = flag("http.port", new InetSocketAddress(defaultHttpPort), "Http server port")

  @volatile protected var httpServer: ListeningServer = NullServer

  premain {
    httpServer = Http.serve(httpPort(), HttpMuxer)
  }

  onExit {
    httpServer.close()
  }
}
