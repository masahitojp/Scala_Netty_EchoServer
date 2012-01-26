import java.net.InetSocketAddress
import java.util.concurrent.Executors

import org.jboss.netty.bootstrap.ServerBootstrap
import org.jboss.netty.channel.{ChannelPipeline, ChannelPipelineFactory, Channels}
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory

class EchoServer(port: Int) {

  def run(): Unit = {
    val bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
      Executors.newCachedThreadPool(), Executors.newCachedThreadPool()
    ))

    bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
      @throws(classOf[Exception])
      def getPipeline(): ChannelPipeline = Channels.pipeline(new EchoServerHandler)
    })

    bootstrap.bind(new InetSocketAddress(port))
  }
}

object EchoServer {
  def main(args: Array[String]): Unit = {

    val port = args(0).toInt
    new EchoServer(port).run()
  }
}
