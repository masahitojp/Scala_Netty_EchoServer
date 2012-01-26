import java.net.InetSocketAddress
import java.util.concurrent.Executors

import org.jboss.netty.bootstrap.ServerBootstrap
import org.jboss.netty.channel.{ChannelPipeline, ChannelPipelineFactory, Channels}
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory

object EchoServer {
  @throws(classOf[Exception])
  def main(args: Array[String]): Unit = {
    val bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
      Executors.newCachedThreadPool(), Executors.newCachedThreadPool()
    ))

    bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
      @throws(classOf[Exception])
      def getPipeline(): ChannelPipeline = Channels.pipeline(new EchoServerHandler)
    })

    bootstrap.bind(new InetSocketAddress(8080))
  }
}
